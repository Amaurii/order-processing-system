package com.example.order.service;

import com.example.order.model.Order;
import com.example.order.repository.OrderRepository;
import com.rabbitmq.client.Channel;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.amqp.core.Message;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import static com.example.order.config.RabbitMQConfig.ORDER_QUEUE;

@Service
@Validated
public class OrderConsumer {

    private static final Logger logger = LoggerFactory.getLogger(OrderConsumer.class);
    private final Validator validator;
    private final OrderRepository orderRepository;
    private final OrderCacheService orderCacheService;
    private final io.micrometer.core.instrument.Counter successCounter;

    public OrderConsumer(Validator validator, OrderRepository paymentRepository, OrderCacheService orderCacheService, MeterRegistry meterRegistry) {
        this.validator = validator;
        this.orderRepository = paymentRepository;
        this.orderCacheService = orderCacheService;
        this.successCounter = meterRegistry.counter("rabbitmq.messages.processed.success");
    }

    @RabbitListener(queues = ORDER_QUEUE)
    public void consumePayment(Order order, Channel channel, Message message) {
        try {
            // 1. Validar o pedido antes de qualquer processamento
            Set<ConstraintViolation<Order>> violations = validator.validate(order);
            if (!violations.isEmpty()) {
                StringBuilder errorMessages = new StringBuilder("Validation errors:");
                violations.forEach(v -> errorMessages.append(" ").append(v.getMessage()));
                logger.error("Invalid order detected: {}", errorMessages.toString());
                throw new IllegalArgumentException(errorMessages.toString());
            }

            // 2. Verificar duplicidade no Redis antes de qualquer outra consulta
            if (orderCacheService.isOrderProcessed(order.getId())) {
                logger.warn("Pedido duplicado detectado no Redis! Ignorando: {}", order.getId());
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // Confirma que a mensagem foi processada
                return;
            }

            // 3. Verificar duplicidade no banco
            if (orderRepository.existsById(order.getId())) {
                logger.warn("Pedido duplicado detectado no MongoDB! Ignorando: {}", order.getId());
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // Confirma que a mensagem foi processada
                return;
            }

            // 4. Salvar no banco primeiro
            orderRepository.save(order);
            logger.info("Order saved: {} - {}", order.getId(), order.getCustomerId());

            // 5. Marcar o pedido como processado no Redis somente após persistência bem-sucedida
            orderCacheService.markOrderAsProcessed(order.getId());

            // 6. Confirmar a mensagem como processada com sucesso
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            successCounter.increment();
            logger.info("Order processed successfully: {}", order);

        } catch (Exception e) {
            logger.error("Error processing order: {}", order, e);
            try {
                // 7. Se houver erro, rejeita a mensagem e reencaminha para reprocessamento
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            } catch (IOException ioException) {
                logger.error("Failed to reject message", ioException);
            }
        }
    }

}
