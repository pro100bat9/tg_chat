package com.example.scrapper.repository.jdbc;

import com.example.scrapper.dto.entity.ChatEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcChatRepository {
    private final JdbcTemplate jdbcTemplate;

    private final BeanPropertyRowMapper<ChatEntity> chatMapper = new BeanPropertyRowMapper<>(ChatEntity.class);

    private final static String ADD_CHAT_QUERY = "insert into tg_chat (id) values (?)";
    private final static String REMOVE_CHAT_QUERY = "delete from tg_chat where id = ?";
    private final static String FIND_ALL_CHATS_QUERY = "select id from tg_chat";
    private final static String FIND_CHAT_BY_ID_QUERY = "select id from tg_chat where id = ?";
    private final static String FIND_ALL_SUBSCRIBERS_QUERY = """
            select id
            from tg_chat t
            join subscription s on t.id = s.chat_id
            where s.link_id = ?
            """;

    public boolean addChat(Long chatId){
        int rowsAffected = jdbcTemplate.update(ADD_CHAT_QUERY, chatId);
        return rowsAffected > 0;
    }

    public boolean removeChat(Long chatId){
        int rowsAffected = jdbcTemplate.update(REMOVE_CHAT_QUERY, chatId);
        return rowsAffected > 0;
    }

    public List<ChatEntity> findAllChats(){
        return jdbcTemplate.query(FIND_ALL_CHATS_QUERY, chatMapper);

    }

    public List<ChatEntity> findChatById(Long chatId){
        return jdbcTemplate.query(FIND_CHAT_BY_ID_QUERY, chatMapper, chatId);
    }

    public List<ChatEntity> findAllSubscribers(Long linkId){
        return jdbcTemplate.query(FIND_ALL_SUBSCRIBERS_QUERY, chatMapper, linkId);

    }


}
