//package com.example.scrapper.jpa;
//
//import com.example.scrapper.IntegrationEnvironment;
//import com.example.scrapper.ScrapperApplication;
//import com.example.scrapper.entity.Link;
//import com.example.scrapper.repository.jpa.JpaLinkRepository;
//import org.assertj.core.api.AssertionsForClassTypes;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertAll;
//
//@SpringBootTest(classes = ScrapperApplication.class)
//public class JpaLinkRepositoryTest extends IntegrationEnvironment {
//
//    @Autowired
//    JpaLinkRepository jpaLinkRepository;
//
//    private static Link makeLink(){
//        return new Link("testUrl.com");
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    public void find_Link_By_Url(){
//        Link link = makeLink();
//        jpaLinkRepository.save(link);
//
//        Optional<Link> linkFromDb = jpaLinkRepository.findLinkByUrl(link.getUrl());
//
//        assertAll(
//                () -> AssertionsForClassTypes.assertThat(linkFromDb).isNotEmpty(),
//                () -> AssertionsForClassTypes.assertThat(linkFromDb.get().getUrl()).isEqualTo(link.getUrl())
//        );
////        assertEquals(link, linkFromDb);
//    }
//
//}
