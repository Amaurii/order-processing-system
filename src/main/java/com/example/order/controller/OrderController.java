package com.example.order.controller;

import com.example.order.dto.OrderRequest;
import com.example.order.model.Order;
import com.example.order.repository.OrderRepository;
import com.example.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Este controller é para fins de testes, não será utilizado no projeto final.
// Ele é responsável por expor os endpoints para a criação e consulta de pedidos no order-service.
// O serviço externo A que será responsável por se comunicar com o RabbitMQ do order-service e o serviço externo B que será responsável por se comunicar com o banco de dados.


@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    public OrderController(OrderService orderService, OrderRepository paymentRepository) {
        this.orderService = orderService;
        this.orderRepository = paymentRepository;
    }

    @GetMapping
    public List<Order> getAllPayments() {
        return orderRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest orderRequest) {
        orderService.sendOrder(orderRequest);
        return ResponseEntity.ok("Order queued successfully with ID: " + orderRequest.getCustomerId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable String id) {
        return orderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
