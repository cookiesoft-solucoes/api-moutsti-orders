package br.com.alysonrodrigo.apimoutstiorders.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class CreateUserListener {

    @RabbitListener(queues = "user.creation.queue")
    public void receive(String user) {
        System.out.println("User received: " + user);
    }
}
