package br.com.alysonrodrigo.apimoutstiorders.producer;

import br.com.alysonrodrigo.apimoutstiorders.dto.RepCategoryDTO;
import br.com.alysonrodrigo.apimoutstiorders.util.ConstantsUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class CreateOrderProducer {

    private final RabbitTemplate rabbitTemplate;

    public CreateOrderProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendOrderCreatedMessage(RepCategoryDTO repCategoryDTO) {
        rabbitTemplate.convertAndSend(ConstantsUtil.TOPIC_ORDER_SYNC, ConstantsUtil.ROUTE_KEY_ORDER_CREATED, repCategoryDTO);
        System.out.println("User created message sent: " + repCategoryDTO);
    }
}
