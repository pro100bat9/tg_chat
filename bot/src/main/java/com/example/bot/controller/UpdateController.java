package com.example.bot.controller;

import com.example.bot.dto.request.LinkUpdateRequest;
import com.example.bot.telegram.BotDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
public class UpdateController {
    private final BotDispatcher botDispatcher;

    @PostMapping(
            path = "/updates",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void updates(@RequestBody LinkUpdateRequest linkUpdate){
        botDispatcher.sendUpdates(linkUpdate);
    }

}
