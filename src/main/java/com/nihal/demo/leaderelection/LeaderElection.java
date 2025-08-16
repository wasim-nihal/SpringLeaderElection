package com.nihal.demo.leaderelection;

import com.nihal.demo.listener.RabbitMQListener;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.integration.leader.event.OnGrantedEvent;
import org.springframework.integration.leader.event.OnRevokedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class LeaderElection {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQListener.class);

    @Autowired
    SimpleMessageListenerContainer simpleMessageListenerContainer;

    private AtomicBoolean leader= new AtomicBoolean(false);

    @EventListener(OnGrantedEvent.class)
    public void onLeaderGranted(OnGrantedEvent event) {
        this.leader.set(true);
        logger.info("Leadership GRANTED to this instance. Role: {}", event.getRole());
        simpleMessageListenerContainer.start();
        logger.info("Message listener container started for leader instance.");
    }

    @EventListener(OnRevokedEvent.class)
    public void onLeaderRevoked(OnRevokedEvent event) {
        this.leader.set(false);
        logger.info("Leadership REVOKED from this instance. Role: {}", event.getRole());
        simpleMessageListenerContainer.stop();
        logger.info("Message listener container stopped for non-leader instance.");
    }

    public boolean getLeader() {
        return this.leader.get();
    }
}
