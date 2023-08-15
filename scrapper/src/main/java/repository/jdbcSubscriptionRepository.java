package repository;

import com.example.scrapper.dto.entity.ChatEntity;
import com.example.scrapper.dto.entity.SubscriptionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class jdbcSubscriptionRepository {
    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<SubscriptionEntity> subscriptionMapper = new BeanPropertyRowMapper<>(SubscriptionEntity.class);

    private final static String ADD_SUBSCRIPTION_QUERY = "insert into subscription (chat_id, link_id) values (?)";
    private final static String REMOVE_SUBSCRIPTION_QUERY = "delete from subscription where chat_id = ? and link_id = ?";
    private final static String FIND_ALL_SUBSCRIPTIONS_QUERY = "select chat_id, link_id from subscription";
    private final static String COUNT_SUBSCRIPTION_QUERY = """
            select count(chat_id)
            from subscription
            where link_id = ?
            """;

    public Boolean addSubscription(Long tgChatId, Long linkChatId){
        int rowsAffected = jdbcTemplate.update(ADD_SUBSCRIPTION_QUERY, tgChatId, linkChatId);
        return rowsAffected > 0;

    }

    public Boolean removeSubscription(Long tgChatId, Long linkChatId){
        int rowsAffected = jdbcTemplate.update(REMOVE_SUBSCRIPTION_QUERY, tgChatId, linkChatId);
        return rowsAffected > 0;
    }

    public List<SubscriptionEntity> findAllSubscriptions(){
        return jdbcTemplate.query(FIND_ALL_SUBSCRIPTIONS_QUERY, subscriptionMapper);
    }

    public Integer countSubscription(Long id){
        return jdbcTemplate.queryForObject(COUNT_SUBSCRIPTION_QUERY, Integer.class, id);
    }
}
