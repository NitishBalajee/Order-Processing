package com.example.orderprocess.services.impl;

import com.example.orderprocess.domains.Order;
import com.example.orderprocess.repositories.OrderRepository;
import com.example.orderprocess.services.PublishMessageService;
import com.example.orderprocess.utils.OrderNotFoundException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PublishMessageServiceImpl implements PublishMessageService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value("${order.exchange}")
    private String orderExchange;

    @Value("${order.routingkey}")
    private String orderKey;

    @Override
    public Order publishMessage(Order order){
        orderRepository.save(order);
        rabbitTemplate.convertAndSend(orderExchange,orderKey, order);
        return order;
    }

    @Override
    public Order getOrder(String id){
        Optional<Order> order = orderRepository.findById(id);
        if(!order.isPresent()){
            throw new OrderNotFoundException("Order Details not found");
        }
        return order.get();

    }
}
