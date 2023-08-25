package com.example.scrapper.service.jooq;

import com.example.scrapper.repository.jooq.JooqChatRepository;
import com.example.scrapper.repository.jooq.JooqLinkRepository;
import com.example.scrapper.service.interfaces.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class JooqChatService implements ChatService {
    private final JooqChatRepository jooqChatRepository;
    private final JooqLinkRepository jooqLinkRepository;

    @Override
    @Transactional
    public void register(Long id) {
        if(jooqChatRepository.findChatById(id) == null){
            jooqChatRepository.addChat(id);
        } else {
            throw new IllegalArgumentException("this chat already exists");
        }

    }

    @Override
    @Transactional
    public void unRegister(Long id) {
        jooqChatRepository.removeChat(id);
        jooqLinkRepository.removeLinkWithZeroChats();

    }
}
