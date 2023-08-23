package com.example.scrapper.repository.jdbc;

import com.example.scrapper.dto.model.SubscriptionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
public class JdbcSubscriptionRepository {
    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<SubscriptionDto> subscriptionMapper = new BeanPropertyRowMapper<>(SubscriptionDto.class);

    private final static String ADD_SUBSCRIPTION_QUERY = "insert into subscription (chat_id, link_id) values (?, ?)";
    private final static String REMOVE_SUBSCRIPTION_QUERY = "delete from subscription where chat_id = ? and link_id = ?";
    private final static String FIND_ALL_SUBSCRIPTIONS_QUERY = "select chat_id, link_id from subscription";
    private final static String COUNT_SUBSCRIPTION_QUERY = """
            select count(chat_id)
            from subscription
            where link_id = ?
            """;

    public Boolean addSubscription(Long tgChatId, Long linkId){
        int rowsAffected = jdbcTemplate.update(ADD_SUBSCRIPTION_QUERY, tgChatId, linkId);
        return rowsAffected > 0;

    }

    public Boolean removeSubscription(Long tgChatId, Long linkId){
        int rowsAffected = jdbcTemplate.update(REMOVE_SUBSCRIPTION_QUERY, tgChatId, linkId);
        return rowsAffected > 0;
    }

    public List<SubscriptionDto> findAllSubscriptions(){
        return jdbcTemplate.query(FIND_ALL_SUBSCRIPTIONS_QUERY, subscriptionMapper);
    }

    public Integer countSubscription(Long linkId){
        return jdbcTemplate.queryForObject(COUNT_SUBSCRIPTION_QUERY, Integer.class, linkId);
    }
}
