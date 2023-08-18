package com.example.scrapper.service.jooq;

import com.example.scrapper.dto.entity.ChatEntity;
import com.example.scrapper.dto.entity.LinkEntity;
import com.example.scrapper.repository.jooq.JooqChatRepository;
import com.example.scrapper.repository.jooq.JooqLinkRepository;
import com.example.scrapper.repository.jooq.JooqSubscriptionRepository;
import com.example.scrapper.service.interfaces.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JooqSubscriptionService implements SubscriptionService {
    private final JooqChatRepository jooqChatRepository;
    private final JooqLinkRepository jooqLinkRepository;
    private final JooqSubscriptionRepository jooqSubscriptionRepository;

    @Override
    @Transactional
    public LinkEntity subscribe(Long chatId, String url) {
        try{
            LinkEntity linkEntity = jooqLinkRepository.findByUrl(url);
            jooqSubscriptionRepository.addSubscription(chatId, linkEntity.getId());
        }
        catch (DataAccessException ex){
            throw new IllegalArgumentException("Subscription already exists", ex);
        }
        return jooqLinkRepository.findByUrl(url);
    }

    @Override
    @Transactional
    public LinkEntity unSubscribe(Long chatId, String url) {
            LinkEntity link = jooqLinkRepository.findByUrl(url);
            if(link != null){
                jooqSubscriptionRepository.removeSubscription(chatId, link.getId());
                Integer count = jooqSubscriptionRepository.countSubscription(link.getId());
                if(count.equals(0)){
                    jooqLinkRepository.removeById(link.getId());
                }
                return link;
            }
            else {
                throw new IllegalArgumentException("link not found");
            }

    }

    @Override
    @Transactional(readOnly = true)
    public List<LinkEntity> getLinksFromChat(Long chatId) {
        return jooqLinkRepository.findLinksFromChat(chatId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatEntity> getChatFromLink(Long linkId) {
        return jooqChatRepository.findAllSubscribers(linkId);
    }
}
