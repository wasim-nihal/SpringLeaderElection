package com.nihal.demo.listener;

import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitListenerConfig {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    @Qualifier("rabbitMQListener")
    private MessageListener messageListener;

    @Value("${rabbitmq.queue.name:myQueue}")
    String queueName;

    @Bean
    public SimpleMessageListenerContainer myListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(messageListener);
        container.setAutoStartup(false);
        container.setAcknowledgeMode(org.springframework.amqp.core.AcknowledgeMode.MANUAL);
        return container;
    }
}
