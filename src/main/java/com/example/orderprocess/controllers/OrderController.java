package com.example.orderprocess.controllers;

import com.example.orderprocess.domains.Order;
import com.example.orderprocess.repositories.OrderRepository;
import com.example.orderprocess.services.PublishMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private PublishMessageService publishMessageService;

    @Autowired
    private OrderRepository orderRepository;


    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Order publishOrderMessage(@RequestBody Order order){
       return publishMessageService.publishMessage(order);

    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Order getOrder(@PathVariable String id){
        return publishMessageService.getOrder(id);
    }



}
