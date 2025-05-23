package com.example.UserService.configuration;


import com.example.UserService.constants.RabbitMQConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "user-notification-queue";
    public static final String EXCHANGE_NAME = "user-exchange";
    public static final String ROUTING_KEY = "user.created";

    @Bean
    public Queue userRequestQueue() {
        return new Queue(RabbitMQConstants.USER_REQUEST_QUEUE);
    }

    @Bean
    public Queue userResponseQueue() {
        return new Queue(RabbitMQConstants.USER_RESPONSE_QUEUE);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(RabbitMQConstants.USER_EXCHANGE);
    }

    @Bean
    public Binding userRequestBinding() {
        return BindingBuilder.bind(userRequestQueue())
                .to(exchange())
                .with(RabbitMQConstants.USER_ROUTING_KEY);
    }

    @Bean
    public Binding userResponseBinding() {
        return BindingBuilder.bind(userResponseQueue())
                .to(exchange())
                .with(RabbitMQConstants.USER_RESPONSE_ROUTING_KEY);
    }

}
