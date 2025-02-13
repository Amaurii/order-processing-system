package com.example.order.service;


import com.example.order.repository.FailedMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class DLQConsumer {

    private static final Logger logger = LoggerFactory.getLogger(DLQConsumer.class);

    private final FailedMessageRepository failedMessageRepository;


    private final RabbitTemplate rabbitTemplate;

    public DLQConsumer(FailedMessageRepository failedMessageRepository, RabbitTemplate rabbitTemplate) {
        this.failedMessageRepository = failedMessageRepository;
        this.rabbitTemplate = rabbitTemplate;
    }
}
