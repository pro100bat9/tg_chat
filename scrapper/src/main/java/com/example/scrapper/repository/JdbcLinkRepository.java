package com.example.scrapper.repository;

import com.example.scrapper.dto.entity.LinkEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcLinkRepository {
    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<LinkEntity> linkMapper = new BeanPropertyRowMapper<>(LinkEntity.class);

    private final static String ADD_LINK_QUERY = "insert into link (url) values (?)";

    private final static String FIND_BY_ID_QUERY = """
            select id, url, last_check_time, updated_at
            from link
            where id = ?
            """;
    private final static String FIND_BY_URL_QUERY = """
            select id, url, last_check_time, updated_at
            from link
            where url = ?
            """;
    private final static String FIND_ALL_QUERY = "select id, url, last_check_time, updated_at from link";
    private final static String FIND_LINKS_FROM_CHAT_QUERY = """
            select id, url, last_check_time, updated_at
            from link 
            join subscription s on link.id = s.link_id
            where chat_id = ?
            """;
    private final static String UPDATE_LAST_TIME_CHECK_QUERY = """
            update link
            set last_check_time = now()
            where ? > last_check_time
            returning id, url, last_check_time, updated_at from link
            """;
    private final static String UPDATE_LAST_UPDATE_TIME_QUERY = "update link set updated_at = ? where id = ? ";
    private final static String REMOVE_BY_ID_QUERY = "delete from link where id = ?";

    private final static String REMOVE_BY_URL_QUERY = "delete from link where url = ?";

    private final static String REMOVE_LINKS_WITH_ZERO_CHATS_QUERY = """
            delete from link
            where link.id in
            (select id from link
            left outer join subscription s on link.id = s.link_id
            where s.chat_id is NULL)
            """;

    public Long addLink(String url){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(ADD_LINK_QUERY, new String[] {"id"});
            ps.setString(1, url);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();

    }

    public LinkEntity findByUrl(String url){
        return jdbcTemplate.queryForObject(FIND_BY_URL_QUERY, linkMapper, url);

    }

    public LinkEntity findById(Long id){
        return jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, linkMapper, id);

    }

    public List<LinkEntity> findAll(){
        return jdbcTemplate.query(FIND_ALL_QUERY, linkMapper);

    }

    public List<LinkEntity> findLinksFromChat(Long id){
        return jdbcTemplate.query(FIND_LINKS_FROM_CHAT_QUERY, linkMapper, id);

    }

    public List<LinkEntity> updateLastTimeCheck(OffsetDateTime date){
        return jdbcTemplate.query(UPDATE_LAST_TIME_CHECK_QUERY, linkMapper, date);

    }

    public Boolean updateLastUpdateTime(OffsetDateTime date, Long id){
        int rowsAffected = jdbcTemplate.update(UPDATE_LAST_UPDATE_TIME_QUERY, date, id);
        return rowsAffected > 0;
    }

    public Boolean removeById(Long id){
        int rowsAffected = jdbcTemplate.update(REMOVE_BY_ID_QUERY, id);
        return rowsAffected > 0;
    }

    public Boolean removeByUrl(String url){
        int rowsAffected = jdbcTemplate.update(REMOVE_BY_URL_QUERY, url);
        return rowsAffected > 0;
    }

    public Boolean removeLinkWithZeroChats(){
        int rowsAffected = jdbcTemplate.update(REMOVE_LINKS_WITH_ZERO_CHATS_QUERY);
        return rowsAffected > 0;

    }


}
