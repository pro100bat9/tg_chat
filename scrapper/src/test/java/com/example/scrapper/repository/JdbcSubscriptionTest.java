package com.example.scrapper.repository;

import com.example.scrapper.ScrapperApplication;
import com.example.scrapper.configuration.TestConfiguration;
import com.example.scrapper.dto.model.SubscriptionDto;
import com.example.scrapper.repository.jdbc.JdbcSubscriptionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(classes = {ScrapperApplication.class, TestConfiguration.class})
public class JdbcSubscriptionTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcSubscriptionRepository jdbcSubscriptionRepository;

    @Test
    @Transactional
    @Rollback
    void add_one_subscription(){
        Long chatId = 1L;
        String url = "https://github.com/pro100bat9/project";
        Long linkId = createLink(url);
        createChat(chatId);

        int before = getAll().size();
        boolean add = jdbcSubscriptionRepository.addSubscription(chatId, linkId);
        int after = getAll().size();

        assertAll(
                () -> assertTrue(add),
                () -> assertEquals(before + 1, after));
    }

    @Test
    @Transactional
    @Rollback
    void already_exist(){
        Long chatId = 1L;
        String url = "https://github.com/pro100bat9/project";
        Long linkId = createLink(url);
        createChat(chatId);
        boolean add = jdbcSubscriptionRepository.addSubscription(chatId, linkId);

        assertThrows(DuplicateKeyException.class, () -> jdbcSubscriptionRepository.addSubscription(chatId, linkId));
    }

    @Test
    @Transactional
    @Rollback
    void findAll_nothing(){
        int size = getAll().size();

        List<SubscriptionDto> linkEntities = jdbcSubscriptionRepository.findAllSubscriptions();

        assertAll(
                () -> assertEquals(size, 0),
                () -> assertEquals(linkEntities.size(), size));
    }


    @Test
    @Transactional
    @Rollback
    void findAll_one_returned(){
        Long chatId = 1L;
        String url = "https://github.com/pro100bat9/project";
        Long linkId = createLink(url);
        createChat(chatId);
        createSubscription(chatId, linkId);

        int size = getAll().size();
        List<SubscriptionDto> linkEntities = jdbcSubscriptionRepository.findAllSubscriptions();

        assertAll(
                () -> assertEquals(size, 1),
                () -> assertEquals(linkEntities.size(), size)
        );
    }

    @Test
    @Transactional
    @Rollback
    void remove_one(){
        Long chatId = 1L;
        String url = "https://github.com/pro100bat9/project";
        Long linkId = createLink(url);
        createChat(chatId);
        createSubscription(chatId, linkId);

        int before = getAll().size();
        boolean remove = jdbcSubscriptionRepository.removeSubscription(chatId, linkId);
        int after = getAll().size();

        assertAll(
                () -> assertTrue(remove),
                () -> assertEquals(before-1 , after));
    }



    @Test
    @Transactional
    @Rollback
    void remove_one_not_exist(){
        Long chatId = 1L;
        Long linkId = 1L;

        int before = getAll().size();
        boolean remove = jdbcSubscriptionRepository.removeSubscription(chatId, linkId);
        int after = getAll().size();

        assertAll(
                () -> assertFalse(remove),
                () -> assertEquals(before , after));
    }

    @Test
    @Transactional
    @Rollback
    void check_count_subscriber(){
        Long chatId = 1L;
        String url = "https://github.com/pro100bat9/project";
        Long linkId = createLink(url);
        createChat(chatId);
        createSubscription(chatId, linkId);

        int count = jdbcSubscriptionRepository.countSubscription(linkId);

        assertEquals(count, 1);
    }

    @Test
    @Transactional
    @Rollback
    void check_count_subscriber_nothing(){
        Long chatId = 1L;
        String url = "https://github.com/pro100bat9/project";
        Long linkId = createLink(url);
        createChat(chatId);

        int count = jdbcSubscriptionRepository.countSubscription(linkId);

        assertEquals(count, 0);
    }



    private List<SubscriptionDto> getAll(){
        return jdbcTemplate.query("select chat_id, link_id from subscription", new BeanPropertyRowMapper<>(SubscriptionDto.class));
    }

    private Long createLink(String url){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("insert into link (url) values (?)", new String[] {"id"});
            ps.setString(1, url);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    private void createChat(Long id){
        jdbcTemplate.update("insert into tg_chat (id) values (?)", id);
    }

    private void createSubscription(Long chatId, Long linkId){
        jdbcTemplate.update("insert into subscription (chat_id, link_id) values (?, ?)", chatId, linkId);
    }
}
