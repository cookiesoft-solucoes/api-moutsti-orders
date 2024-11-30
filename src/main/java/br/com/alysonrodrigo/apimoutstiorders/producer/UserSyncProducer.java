package br.com.alysonrodrigo.apimoutstiorders.producer;

import br.com.alysonrodrigo.apimoutstiorders.config.RabbitMQConfig;
import br.com.alysonrodrigo.apimoutstiorders.dto.RepUserDTO;
import br.com.alysonrodrigo.apimoutstiorders.util.ConstantsUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserSyncProducer {

    private final RabbitTemplate rabbitTemplate;

    public UserSyncProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendUserCreatedMessage(RepUserDTO userDTO) {
        rabbitTemplate.convertAndSend(ConstantsUtil.TOPIC_USER_SYNC, ConstantsUtil.ROUTE_KEY_USER_CREATED, userDTO);
        System.out.println("User created message sent: " + userDTO);
    }
}
