package com.example.scrapper.jpa;

import com.example.scrapper.IntegrationEnvironment;
import com.example.scrapper.ScrapperApplication;
import com.example.scrapper.configuration.TestConfiguration;
import com.example.scrapper.entity.Chat;
import com.example.scrapper.entity.Link;
import com.example.scrapper.entity.Subscription;
import com.example.scrapper.repository.jpa.JpaChatRepository;
import com.example.scrapper.repository.jpa.JpaLinkRepository;
import com.example.scrapper.repository.jpa.JpaSubscriptionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@SpringBootTest(classes = {ScrapperApplication.class, TestConfiguration.class})
public class JpaSubscriptionRepositoryTest extends IntegrationEnvironment {
    @Autowired
    JpaSubscriptionRepository jpaSubscriptionRepository;

    @Autowired
    JpaChatRepository jpaChatRepository;

    @Autowired
    JpaLinkRepository jpaLinkRepository;


    @Test
    @Transactional
    @Rollback
    @Sql("/sql/add_chat_and_links.sql")
    public void save(){
        Link linkFromDbBefore = jpaLinkRepository.findLinkByUrl("testUrl.com").get();
        Chat chatFromDbBefore = jpaChatRepository.findById(1L).get();
        Subscription subscription = new Subscription(chatFromDbBefore.getId(), linkFromDbBefore.getId());

        int countBefore = jpaSubscriptionRepository.findAll().size();
        jpaSubscriptionRepository.saveAndFlush(subscription);
        int countAfter = jpaSubscriptionRepository.findAll().size();

        Assertions.assertEquals(countBefore + 1, countAfter);
    }

    @Test
    @Transactional
    @Rollback
    @Sql("/sql/add_chat_and_links.sql")
    public void remove(){
        Link link = jpaLinkRepository.findLinkByUrl("testUrl.com").get();
        Chat chat = jpaChatRepository.findById(1L).get();
        Subscription subscription = jpaSubscriptionRepository.save(
                new Subscription(chat.getId(), link.getId()));
        int countBefore = jpaSubscriptionRepository.findAll().size();
        jpaSubscriptionRepository.delete(subscription);
        int countAfter = jpaSubscriptionRepository.findAll().size();
        Assertions.assertEquals(countBefore - 1 , countAfter);
    }

    @Test
    @Transactional
    @Rollback
    @Sql("/sql/add_chat_and_links.sql")
    void check_count_subscriber(){
        Link link = jpaLinkRepository.findLinkByUrl("testUrl.com").get();
        Chat chat = jpaChatRepository.findById(1L).get();
        Subscription subscription = jpaSubscriptionRepository.save(
                new Subscription(chat.getId(), link.getId()));

        int count = jpaSubscriptionRepository.countByLinkId(link.getId());

        assertEquals(count, 1);
    }

    @Test
    @Transactional
    @Rollback
    @Sql("/sql/add_chat_and_links.sql")
    void check_count_subscriber_nothing(){
        Link link = jpaLinkRepository.findLinkByUrl("testUrl.com").get();
        Chat chat = jpaChatRepository.findById(1L).get();


        int count = jpaSubscriptionRepository.countByLinkId(link.getId());

        assertEquals(count, 1);
    }
}
