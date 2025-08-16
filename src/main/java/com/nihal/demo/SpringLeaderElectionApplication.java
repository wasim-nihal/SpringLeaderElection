package com.nihal.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.leader.event.OnGrantedEvent;
import org.springframework.integration.leader.event.OnRevokedEvent;
import org.springframework.context.event.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class SpringLeaderElectionApplication {
    private static final Logger logger = LoggerFactory.getLogger(SpringLeaderElectionApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringLeaderElectionApplication.class, args);
    }


}
