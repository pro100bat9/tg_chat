package com.example.scrapper.repository.jpa;

import com.example.scrapper.entity.Link;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@ConditionalOnProperty(prefix = "app", name = "database-type", havingValue = "jpa")
public interface JpaLinkRepository {

    @Query("""
            delete from Link
            where Link.id in
            (select id from Link
            left outer join Subscription s on Link.id = s.linkId
            where s.chatId is NULL)
           """)
    @Modifying
    void deleteWithZeroLinks();

    @Query(value = """
              update link
              set last_check_time = now()
              where :time > last_check_time
              returning id, url, last_check_time, updated_at
            """, nativeQuery = true)
    @Modifying(clearAutomatically = true)
    List<Link> updateLastTimeCheck(@Param("time") OffsetDateTime date);

    Optional<Link> findLinkByUrl(String url);
}
