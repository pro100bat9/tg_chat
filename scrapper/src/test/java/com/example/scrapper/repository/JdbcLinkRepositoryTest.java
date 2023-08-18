package com.example.scrapper.repository;

import com.example.scrapper.ScrapperApplication;
import com.example.scrapper.configuration.TestConfiguration;
import com.example.scrapper.dto.entity.LinkEntity;
import com.example.scrapper.repository.jdbc.JdbcLinkRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest(classes = {ScrapperApplication.class, TestConfiguration.class})
public class JdbcLinkRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcLinkRepository jdbcLinkRepository;

    @Test
    @Transactional
    @Rollback
    void addOne(){
        String url = "https://github.com/pro100bat9/project";

        int before = getAll().size();
        jdbcLinkRepository.addLink(url);
        int after = getAll().size();

        assertEquals(before+1, after);

    }

    @Test
    @Transactional
    @Rollback
    void already_exist(){
        String url = "https://github.com/pro100bat9/project";

        jdbcLinkRepository.addLink(url);

        assertThrows(DuplicateKeyException.class, () -> jdbcLinkRepository.addLink(url));
    }

    @Test
    @Transactional
    @Rollback
    void find_one_byId(){
        String url = "https://github.com/pro100bat9/project";
        Long id = createLink(url);

        LinkEntity linkFromDb = jdbcLinkRepository.findById(id);

        assertEquals(id, linkFromDb.getId());
    }

    @Test
    @Transactional
    @Rollback
    void find_by_id_nothing(){
        Long id = 1L;

        assertThrows(EmptyResultDataAccessException.class, () -> jdbcLinkRepository.findById(id));
    }

    @Test
    @Transactional
    @Rollback
    void find_all_nothing_return(){
        List<LinkEntity> linkEntities = jdbcLinkRepository.findAll();

        assertEquals(linkEntities.size(), 0);
    }

    @Test
    @Transactional
    @Rollback
    void findAll_one_returned(){
        String url = "https://github.com/pro100bat9/project";
        createLink(url);

        List<LinkEntity> linkEntities = jdbcLinkRepository.findAll();

        assertEquals(linkEntities.size(), 1);
    }

    @Test
    @Transactional
    @Rollback
    void find_with_chat_subscription(){
        String url = "https://github.com/pro100bat9/project";
        Long id = 1L;
        createChat(id);
        Long idLink = createLink(url);
        createSubscription(id, idLink);

        List<LinkEntity> linkEntities = jdbcLinkRepository.findLinksFromChat(id);

        assertEquals(linkEntities.size(), 1);
    }

    @Test
    @Transactional
    @Rollback
    void find_with_chat_subscription_not_exist(){
        Long id = 1L;

        List<LinkEntity> linkEntities = jdbcLinkRepository.findLinksFromChat(id);

        assertEquals(linkEntities.size(), 0);
    }

    @Test
    @Transactional
    @Rollback
    void remove_one_by_URL(){
        String url = "https://github.com/pro100bat9/project";
        createLink(url);

        int before = getAll().size();
        boolean remove = jdbcLinkRepository.removeByUrl(url);
        int after = getAll().size();

        assertTrue(remove);
        assertEquals(before - 1, after);
    }

    @Test
    @Transactional
    @Rollback
    void dont_remove_one_byUrl(){
        String url = "https://github.com/pro100bat9/project";

        int before = getAll().size();
        boolean remove = jdbcLinkRepository.removeByUrl(url);
        int after = getAll().size();

        assertFalse(remove);
        assertEquals(before, after);
    }

    @Test
    @Transactional
    @Rollback
    void remove_one_by_Id() {
        String url = "https://github.com/pro100bat9/project";
        Long idLink = createLink(url);

        int before = getAll().size();
        boolean remove = jdbcLinkRepository.removeById(idLink);
        int after = getAll().size();

        assertTrue(remove);
        assertEquals(before - 1, after);
    }

    @Test
    @Transactional
    @Rollback
    void dont_remove_one_byId(){
        Long id = 1L;
        int before = getAll().size();
        boolean remove = jdbcLinkRepository.removeById(id);
        int after = getAll().size();

        assertFalse(remove);
        assertEquals(before, after);
    }

    @Test
    @Transactional
    @Rollback
    void remove_with_zero_subscriptions(){
        String url = "https://github.com/pro100bat9/project";
        createLink(url);

        int before = getAll().size();
        boolean remove = jdbcLinkRepository.removeLinkWithZeroChats();
        int after = getAll().size();

        assertTrue(remove);
        assertEquals(before - 1, after);
    }

    @Test
    @Transactional
    @Rollback
    void dont_remove_with_zero_subscriptions(){
        String url = "https://github.com/pro100bat9/project";
        Long id = 1L;
        createChat(id);
        Long idLink = createLink(url);
        createSubscription(id, idLink);

        int before = getAll().size();
        boolean remove = jdbcLinkRepository.removeLinkWithZeroChats();
        int after = getAll().size();

        assertFalse(remove);
        assertEquals(before, after);
    }








    private List<LinkEntity> getAll(){
        return jdbcTemplate.query("select * from link", new BeanPropertyRowMapper<>(LinkEntity.class));
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
