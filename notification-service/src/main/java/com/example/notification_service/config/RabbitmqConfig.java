package com.example.notification_service.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    @Bean
    public JacksonJsonMessageConverter jackson2JsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            JacksonJsonMessageConverter jacksonJsonMessageConverter
    ) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jacksonJsonMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue("notification_queue");
    }

}
