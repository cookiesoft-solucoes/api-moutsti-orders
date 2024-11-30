package br.com.alysonrodrigo.apimoutstiorders.listener;

import br.com.alysonrodrigo.apimoutstiorders.util.ConstantsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class CreateOrderListener {

    @RabbitListener(queues = ConstantsUtil.ORDER_SYNC_QUEUE)
    public void receive(String order) {
        System.out.println("Order received: " + order);
    }
}
