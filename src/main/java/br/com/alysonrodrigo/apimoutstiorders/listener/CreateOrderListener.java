package br.com.alysonrodrigo.apimoutstiorders.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class CreateOrderListener {

    @RabbitListener(queues = "order.creation.queue")
    public void receive(String order) {
        System.out.println("Order received: " + order);
    }
}
