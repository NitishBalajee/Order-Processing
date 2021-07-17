package com.example.orderprocess.services.impl;

import com.example.orderprocess.domains.Order;
import com.example.orderprocess.repositories.OrderRepository;
import com.example.orderprocess.services.ConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerServiceImpl implements ConsumerService {

    @Autowired
    private OrderRepository orderRepository;

    @RabbitListener(queues = "${order.queue}")
    @Override
    public void consumeMessage(Order order){
        if(order.getStatus().equals("PLACED")){
            log.info("Processing the order");
            order.setStatus("PROCESSED");
            orderRepository.save(order);
        }
    }
}
