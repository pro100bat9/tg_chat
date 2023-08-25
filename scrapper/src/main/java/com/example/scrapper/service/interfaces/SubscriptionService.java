package com.example.scrapper.service.interfaces;

import com.example.scrapper.dto.model.ChatDto;
import com.example.scrapper.dto.model.LinkDto;

import java.util.List;

public interface SubscriptionService {
    LinkDto subscribe(Long chatId, String url);
    LinkDto unSubscribe(Long chatId, String url);
    List<LinkDto> getLinksFromChat(Long chatId);
    List<ChatDto> getChatFromLink(Long linkId);
    default List<Long> getChatId(Long linkId){
        return getChatFromLink(linkId)
                .stream()
                .map(ChatDto::getId)
                .toList();
    }
}
