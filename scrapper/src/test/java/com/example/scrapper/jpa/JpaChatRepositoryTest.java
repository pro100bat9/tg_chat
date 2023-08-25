package com.example.scrapper.jpa;

import com.example.scrapper.IntegrationEnvironment;
import com.example.scrapper.ScrapperApplication;
import com.example.scrapper.configuration.TestConfiguration;
import com.example.scrapper.entity.Chat;
import com.example.scrapper.repository.jpa.JpaChatRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(classes = {ScrapperApplication.class, TestConfiguration.class})
public class JpaChatRepositoryTest extends IntegrationEnvironment {
    @Autowired
    JpaChatRepository jpaChatRepository;

    private static Chat createChat(){
        Chat chat = new Chat(1L);
        return chat;
    }

    @Test
    @Transactional
    @Rollback
    public void save(){
        Chat chat = createChat();


        jpaChatRepository.save(chat);

        Optional<Chat> chatOptional = jpaChatRepository.findById(chat.getId());

        assertAll(
                () -> AssertionsForClassTypes.assertThat(chatOptional).isNotEmpty(),
                () -> AssertionsForClassTypes.assertThat(chat.equals(chatOptional.get()))
        );
    }

    @Test
    @Transactional
    @Rollback
    @Sql("/sql/add_chat.sql")
    public void remove(){
        Optional<Chat> chatFromDbBefore = jpaChatRepository.findById(1L);
        jpaChatRepository.delete(chatFromDbBefore.get());
        Optional<Chat> chatFromDbAfter = jpaChatRepository.findById(1L);

        assertAll(
                () -> AssertionsForClassTypes.assertThat(chatFromDbBefore).isNotEmpty(),
                () -> AssertionsForClassTypes.assertThat(chatFromDbAfter).isEmpty()
        );
    }

}
