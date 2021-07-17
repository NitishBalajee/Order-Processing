package com.example.orderprocess.services.impl;

import com.example.orderprocess.domains.Order;
import com.example.orderprocess.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConsumerServiceUnitTest {

    @InjectMocks
    private ConsumerServiceImpl consumerService;

    @Mock
    private OrderRepository orderRepository;

    @Test
    public void testConsumeMessage(){
        Mockito.when(orderRepository.save(getOrder())).thenReturn(getOrder());
        consumerService.consumeMessage(getOrder());
        Order order = getOrder();
        order.setStatus("PROCESSED");
        Mockito.verify(orderRepository).save(order);
    }

    private Order getOrder() {

        Order order = new Order();
        order.setStatus("PLACED");
        order.setOrderName("order1");
        return order;
    }
}
