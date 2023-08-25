package com.example.scrapper.service.jpa;

import com.example.scrapper.dto.model.ChatDto;
import com.example.scrapper.dto.model.LinkDto;
import com.example.scrapper.entity.Chat;
import com.example.scrapper.entity.Link;
import com.example.scrapper.entity.Subscription;
import com.example.scrapper.entity.pk.SubscriptionPk;
import com.example.scrapper.repository.jpa.JpaChatRepository;
import com.example.scrapper.repository.jpa.JpaLinkRepository;
import com.example.scrapper.repository.jpa.JpaSubscriptionRepository;
import com.example.scrapper.service.interfaces.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class JpaSubscriptionService implements SubscriptionService {
    private final JpaLinkRepository jpaLinkRepository;
    private final JpaChatRepository jpaChatRepository;
    private final JpaSubscriptionRepository jpaSubscriptionRepository;

    @Override
    @Transactional
    public LinkDto subscribe(Long chatId, String url) {
        if(!jpaChatRepository.existsById(chatId)){
            throw new IllegalArgumentException("Chat not found");
        }
        Optional<Link> linkOptional = jpaLinkRepository.findLinkByUrl(url);
        Link link = linkOptional.orElseGet(
                () -> jpaLinkRepository.saveAndFlush(new Link(url))
        );
        if(jpaSubscriptionRepository.existsById(new SubscriptionPk(chatId, link.getId()))){
            throw new IllegalArgumentException("chat already exist");
        }
        jpaSubscriptionRepository.saveAndFlush(new Subscription(chatId, link.getId()));
        return new LinkDto(link);
    }

    @Override
    @Transactional
    public LinkDto unSubscribe(Long chatId, String url) {
        if(!jpaChatRepository.existsById(chatId)){
            throw new IllegalArgumentException("Chat not found");
        }
        Optional<Link> linkOptional = jpaLinkRepository.findLinkByUrl(url);
        if(linkOptional.isEmpty()){
            throw new IllegalArgumentException("Link not found");
        }
        Link link = linkOptional.get();
        jpaSubscriptionRepository.deleteById(new SubscriptionPk(link.getId(), chatId));
        Integer countSubscription = jpaSubscriptionRepository.countByLinkId(link.getId());
        if(countSubscription.equals(0)){
            jpaLinkRepository.delete(link);
        }
        jpaLinkRepository.flush();
        jpaSubscriptionRepository.flush();
        return new LinkDto(link);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LinkDto> getLinksFromChat(Long chatId) {
        Optional<Chat> chatOptional = jpaChatRepository.findById(chatId);
        if(chatOptional.isEmpty()){
            throw new IllegalArgumentException("Chat not found");
        }
        Chat chat = chatOptional.get();
        return chat.getSubscription()
                .stream()
                .map(LinkDto::new)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)

    public List<ChatDto> getChatFromLink(Long linkId) {
        Optional<Link> linkOptional = jpaLinkRepository.findById(linkId);
        if(linkOptional.isEmpty()){
            throw new IllegalArgumentException("Link not found");
        }
        Link link = linkOptional.get();
        return link.getSubscription()
                .stream()
                .map(ChatDto::new)
                .toList();
    }
}
