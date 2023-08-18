package com.example.scrapper.service.jdbc;


import com.example.scrapper.dto.entity.ChatEntity;
import com.example.scrapper.dto.entity.LinkEntity;
import com.example.scrapper.repository.jdbc.JdbcChatRepository;
import com.example.scrapper.repository.jdbc.JdbcLinkRepository;
import com.example.scrapper.repository.jdbc.JdbcSubscriptionRepository;
import com.example.scrapper.service.interfaces.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JdbcSubscriptionService implements SubscriptionService{
    private final JdbcChatRepository jdbcChatRepository;
    private final JdbcLinkRepository jdbcLinkRepository;
    private final JdbcSubscriptionRepository jdbcSubscriptionRepository;


    @Override
    @Transactional
    public LinkEntity subscribe(Long chatId, String url) {
        try{
            LinkEntity link = jdbcLinkRepository.findByUrl(url);
            jdbcSubscriptionRepository.addSubscription(chatId, link.getId());
        }
        catch (EmptyResultDataAccessException ex){
            Long linkId = jdbcLinkRepository.addLink(url);
            jdbcSubscriptionRepository.addSubscription(chatId, linkId);
        }
        return jdbcLinkRepository.findByUrl(url);
    }

    @Override
    @Transactional
    public LinkEntity unSubscribe(Long chatId, String url) {
        try{
            LinkEntity link = jdbcLinkRepository.findByUrl(url);
            jdbcSubscriptionRepository.removeSubscription(chatId, link.getId());
            Integer countSubscriptions = jdbcSubscriptionRepository.countSubscription(link.getId());
            if(countSubscriptions.equals(0)){
                jdbcLinkRepository.removeByUrl(url);
            }
            return link;
        }
        catch (EmptyResultDataAccessException ex){
            throw new IllegalArgumentException("link not found", ex);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<LinkEntity> getLinksFromChat(Long chatId) {
        return jdbcLinkRepository.findLinksFromChat(chatId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatEntity> getChatFromLink(Long linkId) {
        return jdbcChatRepository.findAllSubscribers(linkId);
    }
}
