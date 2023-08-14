package repository;

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

    public boolean addChat(Long id){
        int rowsAffected = jdbcTemplate.update(ADD_CHAT_QUERY, id);
        return rowsAffected > 0;
    }

    public boolean removeChat(Long id){
        int rowsAffected = jdbcTemplate.update(REMOVE_CHAT_QUERY);
        return rowsAffected > 0;
    }

    public List<ChatEntity> findAllChats(){
        return jdbcTemplate.query(FIND_ALL_CHATS_QUERY, chatMapper);


    }

    public List<ChatEntity> findChatById(Long id){
        return jdbcTemplate.query(FIND_CHAT_BY_ID_QUERY, chatMapper, id);
    }





}
