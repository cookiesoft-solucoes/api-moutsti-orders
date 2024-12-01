package br.com.alysonrodrigo.apimoutstiorders.config;

import br.com.alysonrodrigo.apimoutstiorders.util.ConstantsUtil;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Configura o RabbitTemplate com o ConnectionFactory
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    public TopicExchange categoryExchange() {
        return new TopicExchange(ConstantsUtil.TOPIC_CATEGORY_SYNC);
    }

    @Bean
    public TopicExchange productExchange() {
        return new TopicExchange(ConstantsUtil.TOPIC_PRODUCT_SYNC);
    }

    @Bean
    public TopicExchange userExchange() {
        return new TopicExchange(ConstantsUtil.TOPIC_USER_SYNC);
    }

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(ConstantsUtil.TOPIC_ORDER_SYNC);
    }

    @Bean
    public Queue categoryQueue() {
        return new Queue(ConstantsUtil.CATEGORY_SYNC_QUEUE, true);
    }

    @Bean
    public Queue productQueue() {
        return new Queue(ConstantsUtil.PRODUCT_SYNC_QUEUE, true);
    }

    @Bean
    public Queue userQueue() {
        return new Queue(ConstantsUtil.USER_SYNC_QUEUE, true);
    }

    @Bean
    public Queue orderQueue() {
        return new Queue(ConstantsUtil.ORDER_SYNC_QUEUE, true);
    }

    @Bean
    public Binding categoryBinding(Queue categoryQueue, TopicExchange categoryExchange) {
        return BindingBuilder.bind(categoryQueue).to(categoryExchange).with(ConstantsUtil.ROUTE_KEY_CATEGORY_CREATED);
    }

    @Bean
    public Binding productBinding(Queue productQueue, TopicExchange productExchange) {
        return BindingBuilder.bind(productQueue).to(productExchange).with(ConstantsUtil.ROUTE_KEY_PRODUCT_CREATED);
    }

    @Bean
    public Binding userBinding(Queue userQueue, TopicExchange userExchange) {
        return BindingBuilder.bind(userQueue).to(userExchange).with(ConstantsUtil.ROUTE_KEY_USER_CREATED);
    }

    @Bean
    public Binding orderBinding(Queue orderQueue, TopicExchange orderExchange) {
        return BindingBuilder.bind(orderQueue).to(orderExchange).with(ConstantsUtil.ROUTE_KEY_ORDER_CREATED);
    }


}
