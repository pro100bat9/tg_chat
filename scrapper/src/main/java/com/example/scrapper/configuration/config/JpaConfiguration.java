package com.example.scrapper.configuration.config;

import com.example.scrapper.repository.jpa.JpaChatRepository;
import com.example.scrapper.repository.jpa.JpaLinkRepository;
import com.example.scrapper.repository.jpa.JpaSubscriptionRepository;
import com.example.scrapper.service.interfaces.ChatService;
import com.example.scrapper.service.interfaces.LinkService;
import com.example.scrapper.service.interfaces.SubscriptionService;
import com.example.scrapper.service.jpa.JpaChatService;
import com.example.scrapper.service.jpa.JpaLinkService;
import com.example.scrapper.service.jpa.JpaSubscriptionService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaConfiguration {

    @Bean
    public ChatService chatService(JpaChatRepository jpaChatRepository, JpaLinkRepository jpaLinkRepository){
        return new JpaChatService(jpaLinkRepository, jpaChatRepository);
    }

    @Bean
    public LinkService linkService(JpaLinkRepository jpaLinkRepository){
        return new JpaLinkService(jpaLinkRepository);
    }

    @Bean
    public SubscriptionService subscriptionService(
            JpaSubscriptionRepository jpaSubscriptionRepository,
            JpaLinkRepository jpaLinkRepository,
            JpaChatRepository jpaChatRepository){
        return new JpaSubscriptionService(jpaLinkRepository, jpaChatRepository, jpaSubscriptionRepository);
    }
}
