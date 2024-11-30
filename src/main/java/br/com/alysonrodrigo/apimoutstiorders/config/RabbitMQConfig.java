package br.com.alysonrodrigo.apimoutstiorders.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    // Nome das filas
    public static final String QUEUE_USER_CREATION = "user.creation.queue";
    public static final String QUEUE_ORDER_CREATION = "order.creation.queue";

    // Configura o RabbitTemplate com o ConnectionFactory
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public Queue userCreationQueue() {
        return new Queue(QUEUE_USER_CREATION, true); // Fila persistente
    }

    @Bean
    public Queue orderCreationQueue() {
        return new Queue(QUEUE_ORDER_CREATION, true); // Fila persistente
    }

}
