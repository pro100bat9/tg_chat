package com.example.scrapper.controller;

import com.example.scrapper.annotation.ScrapperHandler;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/tg-chat")
@ScrapperHandler
public class TelegramChatController {

    @PostMapping(
            path = "/{id}"
    )
    public ResponseEntity<?> registrationChat(@PathVariable Long id){
//TODO доделать
        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping(
            path = "/{id}"
    )
    public ResponseEntity<?> deleteChat(@PathVariable Long id){
//TODO доделать
        return ResponseEntity.ok().body(null);

    }
}
