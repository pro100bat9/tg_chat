package com.example.scrapper.service.jpa;

import com.example.scrapper.entity.Chat;
import com.example.scrapper.repository.jpa.JpaChatRepository;
import com.example.scrapper.repository.jpa.JpaLinkRepository;
import com.example.scrapper.service.interfaces.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JpaChatService implements ChatService {
    private final JpaLinkRepository jpaLinkRepository;
    private final JpaChatRepository jpaChatRepository;


    @Override
    @Transactional
    public void register(Long id) {
        if(jpaChatRepository.existsById(id)){
            throw new IllegalArgumentException("chat already exist");
        }
        jpaChatRepository.saveAndFlush(new Chat(id));
    }

    @Override
    @Transactional
    public void unRegister(Long id) {
        if(!jpaChatRepository.existsById(id)){
            throw new IllegalArgumentException("chat not found");
        }
        jpaChatRepository.deleteById(id);
        jpaLinkRepository.deleteWithZeroChats();
    }
}
