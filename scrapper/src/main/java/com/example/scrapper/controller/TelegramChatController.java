package com.example.scrapper.controller;

import com.example.scrapper.annotation.ScrapperHandler;
import com.example.scrapper.service.interfaces.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/tg-chat")
@ScrapperHandler
@RequiredArgsConstructor
public class TelegramChatController {

    private final ChatService chatService;

    @PostMapping(
            path = "/{id}"
    )
    public void registrationChat(@PathVariable Long id){
        chatService.register(id);
    }

    @DeleteMapping(
            path = "/{id}"
    )
    public void deleteChat(@PathVariable Long id){
        chatService.unRegister(id);

    }
}
