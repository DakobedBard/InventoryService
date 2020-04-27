package org.mddarr.orders.service;

import org.mddarr.orders.Constants;
import org.mddarr.orders.beans.OrderDTO;
import org.mddarr.orders.event.dto.Event1;

import org.mddarr.orders.event.dto.Order;
import org.mddarr.orders.event.dto.OrderState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AvroProducer {

    @Autowired
    private KafkaTemplate<String, Event1> kafkaTemplateEvent1;
    @Autowired
    private KafkaTemplate<String, Order> kafkaTemplateOrder;
    private static final Logger logger = LoggerFactory.getLogger(AvroProducer.class);

    public void sendEvent1(Event1 event1) {
        logger.info("Send event 1 {}", event1);
        kafkaTemplateEvent1.send(Constants.EVENT_1_TOPIC, event1);
    }
    
    public void sendOrder(OrderDTO orderDTO) {

        UUID uuid =  UUID.randomUUID();
        Order order = new Order(uuid.toString(), orderDTO.getCustomerID(), OrderState.CREATED, orderDTO.getProducts(),orderDTO.getQuantities(),2D);
        logger.info("Send event 1 {}", order);
        kafkaTemplateOrder.send("orders", order);
    }



}
