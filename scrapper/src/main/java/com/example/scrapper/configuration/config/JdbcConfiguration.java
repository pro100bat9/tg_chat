package com.example.scrapper.configuration.config;

import com.example.scrapper.repository.jdbc.JdbcChatRepository;
import com.example.scrapper.repository.jdbc.JdbcLinkRepository;
import com.example.scrapper.repository.jdbc.JdbcSubscriptionRepository;
import com.example.scrapper.service.interfaces.ChatService;
import com.example.scrapper.service.interfaces.LinkService;
import com.example.scrapper.service.interfaces.SubscriptionService;
import com.example.scrapper.service.jdbc.JdbcChatService;
import com.example.scrapper.service.jdbc.JdbcLinkService;
import com.example.scrapper.service.jdbc.JdbcSubscriptionService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcConfiguration {

    @Bean
    public JdbcChatRepository jdbcChatRepository(JdbcTemplate jdbcTemplate){
        return new JdbcChatRepository(jdbcTemplate);
    }

    @Bean
    public JdbcLinkRepository jdbcLinkRepository(JdbcTemplate jdbcTemplate){
        return new JdbcLinkRepository(jdbcTemplate);
    }

    @Bean
    public JdbcSubscriptionRepository jdbcSubscriptionRepository(JdbcTemplate jdbcTemplate){
        return new JdbcSubscriptionRepository(jdbcTemplate);
    }

    @Bean
    public ChatService jdbcChatService(JdbcChatRepository jdbcChatRepository, JdbcLinkRepository jdbcLinkRepository){
        return new JdbcChatService(jdbcChatRepository, jdbcLinkRepository);
    }

    @Bean
    public LinkService jdbcLinkService(JdbcLinkRepository jdbcLinkRepository){
        return new JdbcLinkService(jdbcLinkRepository);
    }

    @Bean
    public SubscriptionService jdbcSubscriptionService(
            JdbcSubscriptionRepository jdbcSubscriptionRepository,
            JdbcLinkRepository jdbcLinkRepository,
            JdbcChatRepository jdbcChatRepository){
        return new JdbcSubscriptionService(jdbcChatRepository, jdbcLinkRepository, jdbcSubscriptionRepository);
    }
}
