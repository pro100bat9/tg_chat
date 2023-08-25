package com.example.scrapper.jpa;

import com.example.scrapper.IntegrationEnvironment;
import com.example.scrapper.ScrapperApplication;
import com.example.scrapper.configuration.TestConfiguration;
import com.example.scrapper.entity.Link;
import com.example.scrapper.repository.jpa.JpaLinkRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {ScrapperApplication.class, TestConfiguration.class})
public class JpaLinkRepositoryTest extends IntegrationEnvironment {

    @Autowired
    JpaLinkRepository jpaLinkRepository;

    private static Link makeLink(){
        Link link = new Link("testUrl.com");
        link.setUpdateAtTime(OffsetDateTime.now());
        link.setLastCheckTime(OffsetDateTime.now());
        return link;
    }

    @Test
    @Transactional
    @Rollback
    public void save(){
        Link link = makeLink();

        jpaLinkRepository.saveAndFlush(link);

        Optional<Link> linkFromDb = jpaLinkRepository.findLinkByUrl("testUrl.com");

        assertAll(
                () -> AssertionsForClassTypes.assertThat(linkFromDb).isNotEmpty(),
                () -> assertEquals(link, linkFromDb.get())
        );
    }

    @Test
    @Transactional
    @Rollback
    @Sql("/sql/add_links.sql")
    public void remove(){
        Optional<Link> linkFromDbBefore = jpaLinkRepository.findLinkByUrl("testUrl.com");
        jpaLinkRepository.delete(linkFromDbBefore.get());
        Optional<Link> linkFromDbAfter = jpaLinkRepository.findLinkByUrl("testUrl.com");

        assertAll(
                () -> AssertionsForClassTypes.assertThat(linkFromDbBefore).isNotEmpty(),
                () -> AssertionsForClassTypes.assertThat(linkFromDbAfter).isEmpty()
        );
    }

    @Test
    @Transactional
    @Rollback
    @Sql("/sql/add_links.sql")
    public void remove_with_zero_subscriptions(){

        Optional<Link> linkFromDbBefore = jpaLinkRepository.findLinkByUrl("testUrl.com");
        jpaLinkRepository.deleteWithZeroChats();
        Optional<Link> linkFromDbAfter = jpaLinkRepository.findLinkByUrl("testUrl.com");

        assertAll(
                () -> AssertionsForClassTypes.assertThat(linkFromDbBefore).isNotEmpty(),
                () -> AssertionsForClassTypes.assertThat(linkFromDbAfter).isEmpty()
        );
    }

}
