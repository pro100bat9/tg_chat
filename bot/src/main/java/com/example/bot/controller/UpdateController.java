package com.example.bot.controller;

import com.example.bot.dto.request.LinkUpdate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class UpdateController {

    @PostMapping(
            path = "/updates",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updates(@RequestBody LinkUpdate linkUpdate){
//TODO доделать
        return ResponseEntity.ok().body(null);
    }

}
