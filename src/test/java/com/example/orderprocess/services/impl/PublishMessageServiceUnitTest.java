package com.example.orderprocess.services.impl;

import com.example.orderprocess.domains.Order;
import com.example.orderprocess.repositories.OrderRepository;
import com.example.orderprocess.utils.OrderNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

@SpringBootTest
public class PublishMessageServiceUnitTest {

    @InjectMocks
    private PublishMessageServiceImpl publishMessageService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testPublishMessage(){
        String orderExchange = "exchange";
        String orderKey = "key";

        ReflectionTestUtils.setField(publishMessageService, "orderExchange", orderExchange);
        ReflectionTestUtils.setField(publishMessageService, "orderKey", orderKey);

        Mockito.when(orderRepository.save(Mockito.any())).thenReturn(getOrder());
        Mockito.doNothing().when(rabbitTemplate).convertAndSend(orderExchange, orderKey, getOrder());
        Order actual = publishMessageService.publishMessage(getOrder());
        Assertions.assertEquals(getOrder().getStatus(), actual.getStatus());
        Assertions.assertEquals(getOrder().getOrderName(), actual.getOrderName());
        Mockito.verify(orderRepository).save(Mockito.any());
        Mockito.verify(rabbitTemplate).convertAndSend(orderExchange, orderKey, getOrder());
    }

    @Test
    public void testGetOrder(){
        Optional<Order> orderOptional = Optional.of(getOrder());
        Mockito.when(orderRepository.findById(Mockito.anyString())).thenReturn(orderOptional);
        Order actual = publishMessageService.getOrder("sampleId");
        Assertions.assertEquals(getOrder().getOrderName(), actual.getOrderName());
        Assertions.assertEquals(getOrder().getStatus(), actual.getStatus());
        Mockito.verify(orderRepository).findById(Mockito.anyString());
    }

    @Test
    public void testGetOrderNull(){
        Assertions.assertThrows(OrderNotFoundException.class,()-> {
                    Optional<Order> orderOptional = Optional.of(getOrder());
                    Mockito.when(orderRepository.findById("sampleId2")).thenReturn(orderOptional);
                    publishMessageService.getOrder("sampleId");
                    Mockito.verify(orderRepository).findById(Mockito.anyString());
                }
                );

    }

    private Order getOrder() {

        Order order = new Order();
        order.setStatus("PLACED");
        order.setOrderName("order1");
        return order;
    }
}
