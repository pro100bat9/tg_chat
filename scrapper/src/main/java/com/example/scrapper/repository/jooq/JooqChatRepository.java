package com.example.scrapper.repository.jooq;


import com.example.scrapper.domain.jooq.tables.Subscription;
import com.example.scrapper.domain.jooq.tables.TgChat;
import com.example.scrapper.dto.entity.ChatEntity;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class JooqChatRepository {
    private final DSLContext dslContext;
    private final TgChat tgChat = TgChat.TG_CHAT;
    private final Subscription subscription = Subscription.SUBSCRIPTION;

    public void addChat(Long chatId){
        dslContext.insertInto(tgChat)
                .set(tgChat.ID, chatId)
                .execute();
    }

    public void removeChat(Long chatId){
        dslContext.delete(tgChat)
                .where(tgChat.ID.eq(chatId))
                .execute();
    }

    public List<ChatEntity> findAllChats(){
        return dslContext.select(tgChat.ID)
                .from(tgChat)
                .fetchInto(ChatEntity.class);

    }

    public ChatEntity findChatById(Long chatId){
        return dslContext.select(tgChat.ID)
                .from(tgChat)
                .fetchOneInto(ChatEntity.class);
    }

    public List<ChatEntity> findAllSubscribers(Long linkId){
        return dslContext.select(tgChat.ID)
                .from(tgChat)
                .join(subscription)
                .on(tgChat.ID.eq(subscription.CHAT_ID))
                .where(subscription.LINK_ID.eq(linkId))
                .fetchInto(ChatEntity.class);

    }
}
