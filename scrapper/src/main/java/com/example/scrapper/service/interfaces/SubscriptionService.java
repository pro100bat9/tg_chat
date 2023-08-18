package com.example.scrapper.service.interfaces;

import com.example.scrapper.dto.entity.ChatEntity;
import com.example.scrapper.dto.entity.LinkEntity;

import java.util.List;

public interface SubscriptionService {
    LinkEntity subscribe(Long chatId, String url);
    LinkEntity unSubscribe(Long chatId, String url);
    List<LinkEntity> getLinksFromChat(Long chatId);
    List<ChatEntity> getChatFromLink(Long linkId);
    default List<Long> getChatId(Long linkId){
        return getChatFromLink(linkId)
                .stream()
                .map(ChatEntity::getId)
                .toList();
    }
}
