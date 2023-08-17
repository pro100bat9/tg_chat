package com.example.scrapper.service.jdbc;


import com.example.scrapper.repository.JdbcChatRepository;
import com.example.scrapper.repository.JdbcLinkRepository;
import com.example.scrapper.service.interfaces.ChatService;
import lombok.RequiredArgsConstructor;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatServiceJdbc implements ChatService {

    private final JdbcChatRepository jdbcChatRepository;
    private final JdbcLinkRepository jdbcLinkRepository;


    @Override
    @Transactional
    public void register(Long id) {
        try{
            jdbcChatRepository.addChat(id);
        }
        catch (DuplicateKeyException e){
            throw new IllegalArgumentException("chat with this id already exists", e);
        }

    }

    @Override
    @Transactional
    public void unRegister(Long id) {
        jdbcChatRepository.removeChat(id);
        jdbcLinkRepository.removeLinkWithZeroChats();

    }
}
