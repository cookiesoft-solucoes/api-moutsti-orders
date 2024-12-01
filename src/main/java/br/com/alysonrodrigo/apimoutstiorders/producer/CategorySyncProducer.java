package br.com.alysonrodrigo.apimoutstiorders.producer;

import br.com.alysonrodrigo.apimoutstiorders.dto.RepCategoryDTO;
import br.com.alysonrodrigo.apimoutstiorders.dto.RepUserDTO;
import br.com.alysonrodrigo.apimoutstiorders.util.ConstantsUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class CategorySyncProducer {

    private final RabbitTemplate rabbitTemplate;

    public CategorySyncProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendCategoryCreatedMessage(RepCategoryDTO repCategoryDTO) {
        rabbitTemplate.convertAndSend(ConstantsUtil.TOPIC_CATEGORY_SYNC, ConstantsUtil.ROUTE_KEY_CATEGORY_CREATED, repCategoryDTO);
        System.out.println("User created message sent: " + repCategoryDTO);
    }
}
