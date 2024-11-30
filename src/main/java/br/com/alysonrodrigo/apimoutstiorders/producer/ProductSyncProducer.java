package br.com.alysonrodrigo.apimoutstiorders.producer;

import br.com.alysonrodrigo.apimoutstiorders.dto.RepCategoryDTO;
import br.com.alysonrodrigo.apimoutstiorders.dto.RepProductDTO;
import br.com.alysonrodrigo.apimoutstiorders.util.ConstantsUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProductSyncProducer {

    private final RabbitTemplate rabbitTemplate;

    public ProductSyncProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendProductCreatedMessage(RepProductDTO repProductDTO) {
        rabbitTemplate.convertAndSend(ConstantsUtil.TOPIC_PRODUCT_SYNC, ConstantsUtil.ROUTE_KEY_PRODUCT_CREATED, repProductDTO);
        System.out.println("User created message sent: " + repProductDTO);
    }
}
