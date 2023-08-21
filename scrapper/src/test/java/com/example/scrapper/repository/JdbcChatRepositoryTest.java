package com.example.scrapper.repository;

import com.example.scrapper.ScrapperApplication;
import com.example.scrapper.configuration.TestConfiguration;
import com.example.scrapper.dto.model.ChatDto;
import com.example.scrapper.repository.jdbc.JdbcChatRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {ScrapperApplication.class, TestConfiguration.class})
public class JdbcChatRepositoryTest{


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcChatRepository jdbcChatRepository;

    @Test
    @Transactional
    @Rollback
    void add_One(){
        Long id = 1L;
        boolean chat = jdbcChatRepository.addChat(id);
        List<ChatDto> chats = getAllChats();
        assertTrue(chat);
        assertEquals(chats.size(), 1);
        assertEquals(chats.get(0).getId(), id);
    }

    @Test
    @Transactional
    @Rollback
    void already_exist(){
        Long id = 1L;
        boolean firstChat = jdbcChatRepository.addChat(id);

        assertTrue(firstChat);
        assertThrows(DuplicateKeyException.class, () -> jdbcChatRepository.addChat(id));
    }

    @Test
    @Transactional
    @Rollback
    void remove_one(){
        Long id = 1L;
        createChat(id);

        boolean remove = jdbcChatRepository.removeChat(id);
        List<ChatDto> chatEntities = getAllChats();

        assertEquals(chatEntities.size(), 0);
        assertTrue(remove);
    }

    @Test
    @Transactional
    @Rollback
    void nothing_remove(){
        Long id = 1L;

        boolean remove = jdbcChatRepository.removeChat(id);

        assertFalse(remove);
    }

    @Test
    @Transactional
    @Rollback
    void getAll_nothing(){
        List<ChatDto> chatEntities = jdbcChatRepository.findAllChats();

        assertEquals(chatEntities.size(), 0);
    }

    @Test
    @Transactional
    @Rollback
    void getAll_one(){
        Long id = 1L;
        createChat(id);

        List<ChatDto> chatEntities = jdbcChatRepository.findAllChats();

        assertEquals(chatEntities.size(), 1);
    }




    private List<ChatDto> getAllChats(){
        return jdbcTemplate.query("select * from tg_chat", new BeanPropertyRowMapper<>(ChatDto.class));
    }

    private void createChat(Long id){
        jdbcTemplate.update("insert into tg_chat (id) values (?)", id);
    }

}
