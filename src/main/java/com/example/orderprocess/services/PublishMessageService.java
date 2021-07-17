package com.example.orderprocess.services;

import com.example.orderprocess.domains.Order;

public interface PublishMessageService {

     Order publishMessage(Order order);
     Order getOrder(String id);


}
