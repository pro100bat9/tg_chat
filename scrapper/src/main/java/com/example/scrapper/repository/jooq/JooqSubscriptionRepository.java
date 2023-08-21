package com.example.scrapper.repository.jooq;

import com.example.scrapper.domain.jooq.tables.Subscription;
import com.example.scrapper.dto.model.SubscriptionDto;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JooqSubscriptionRepository {
    private final DSLContext dslContext;
    private final Subscription subscription = Subscription.SUBSCRIPTION;

    public void addSubscription(Long tgChatId, Long linkId){
        dslContext.insertInto(subscription)
                .set(subscription.CHAT_ID, tgChatId)
                .set(subscription.LINK_ID, linkId)
                .execute();
    }

    public void removeSubscription(Long tgChatId, Long linkId){
        dslContext.delete(subscription)
                .where(subscription.CHAT_ID.eq(tgChatId).and(subscription.LINK_ID.eq(linkId)))
                .execute();

    }

    public List<SubscriptionDto> findAllSubscriptions(){
        return dslContext.select(subscription.fields())
                .from(subscription)
                .fetchInto(SubscriptionDto.class);
    }

    public Integer countSubscription(Long linkId){
        return dslContext.fetchCount(subscription, subscription.LINK_ID.eq(linkId));
    }
}
