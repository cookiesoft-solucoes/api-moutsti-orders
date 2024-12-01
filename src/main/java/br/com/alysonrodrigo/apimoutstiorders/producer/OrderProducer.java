package br.com.alysonrodrigo.apimoutstiorders.producer;

import br.com.alysonrodrigo.apimoutstiorders.dto.OrderCreateDTO;
import br.com.alysonrodrigo.apimoutstiorders.dto.RepCategoryDTO;
import br.com.alysonrodrigo.apimoutstiorders.util.ConstantsUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {

    private final RabbitTemplate rabbitTemplate;

    public OrderProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendOrderToProcess(OrderCreateDTO orderCreateDTO) {
        rabbitTemplate.convertAndSend(ConstantsUtil.TOPIC_ORDER_SYNC, ConstantsUtil.ROUTE_KEY_ORDER_CREATED, orderCreateDTO);
        System.out.println("Order send to processing: " + orderCreateDTO);
    }
}
