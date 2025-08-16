package com.nihal.demo.listener;

import com.nihal.demo.leaderelection.LeaderElection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

import java.io.IOException;

@Component
public class RabbitMQListener implements ChannelAwareMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQListener.class);

    @Override
    public void onMessage(Message message,Channel channel) {
        try {
            String messageBody = new String(message.getBody());
            logger.info("Leader instance processing message from RabbitMQ: {}", messageBody);
            assert channel != null;
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            logger.error("Error processing message: {}", e.getMessage(), e);
        }
    }
}
