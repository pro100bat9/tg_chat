package com.example.scrapper.service.jooq;

import com.example.scrapper.dto.model.ChatDto;
import com.example.scrapper.dto.model.LinkDto;
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
    public LinkDto subscribe(Long chatId, String url) {
        try{
            LinkDto linkEntity = jooqLinkRepository.findByUrl(url);
            jooqSubscriptionRepository.addSubscription(chatId, linkEntity.getId());
        }
        catch (DataAccessException ex){
            throw new IllegalArgumentException("Subscription already exists", ex);
        }
        return jooqLinkRepository.findByUrl(url);
    }

    @Override
    @Transactional
    public LinkDto unSubscribe(Long chatId, String url) {
            LinkDto link = jooqLinkRepository.findByUrl(url);
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
    public List<LinkDto> getLinksFromChat(Long chatId) {
        return jooqLinkRepository.findLinksFromChat(chatId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatDto> getChatFromLink(Long linkId) {
        return jooqChatRepository.findAllSubscribers(linkId);
    }
}
