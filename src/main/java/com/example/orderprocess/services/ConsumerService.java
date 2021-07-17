package com.example.orderprocess.services;

import com.example.orderprocess.domains.Order;

public interface ConsumerService {

    void consumeMessage(Order order);
}
