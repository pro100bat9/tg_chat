package com.example.scrapper.configuration.config;


import com.example.scrapper.repository.jooq.JooqChatRepository;
import com.example.scrapper.repository.jooq.JooqLinkRepository;
import com.example.scrapper.repository.jooq.JooqSubscriptionRepository;
import com.example.scrapper.service.jooq.JooqChatService;
import com.example.scrapper.service.jooq.JooqLinkService;
import com.example.scrapper.service.jooq.JooqSubscriptionService;
import org.jooq.DSLContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
public class JooqConfiguration {
    @Bean
    public JooqChatRepository jooqChatRepository(DSLContext dslContext){
        return new JooqChatRepository(dslContext);
    }

    @Bean
    public JooqLinkRepository jooqLinkRepository(DSLContext dslContext){
        return new JooqLinkRepository(dslContext);
    }

    @Bean
    public JooqSubscriptionRepository jooqSubscriptionRepository(DSLContext dslContext){
        return new JooqSubscriptionRepository(dslContext);
    }

    @Bean
    public JooqChatService jooqChatService(JooqChatRepository jooqChatRepository, JooqLinkRepository jooqLinkRepository){
        return new JooqChatService(jooqChatRepository, jooqLinkRepository);
    }

    @Bean
    public JooqLinkService jooqLinkService(JooqLinkRepository jooqLinkRepository){
        return new JooqLinkService(jooqLinkRepository);
    }

    @Bean
    public JooqSubscriptionService jooqSubscriptionService(
            JooqSubscriptionRepository jooqSubscriptionRepository,
            JooqLinkRepository jooqLinkRepository,
            JooqChatRepository jooqChatRepository){
        return new JooqSubscriptionService(jooqChatRepository, jooqLinkRepository, jooqSubscriptionRepository);
    }
}
