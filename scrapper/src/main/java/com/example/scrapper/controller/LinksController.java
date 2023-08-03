package com.example.scrapper.controller;

import com.example.scrapper.annotation.ScrapperHandler;
import com.example.scrapper.dto.request.AddLinkRequest;
import com.example.scrapper.dto.request.RemoveLinkRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@ScrapperHandler
public class LinksController {

    @PostMapping(
            path = "/links",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> addLinks(@RequestParam Long id, @RequestBody AddLinkRequest addLinkRequest){
//        TODO доделать
        return ResponseEntity.ok().body(null);
    }

    @GetMapping(
            path = "/links",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getLinks(@RequestParam Long id){
        //        TODO доделать
        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping(
            path = "/links",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deleteLinks(@RequestParam Long id, @RequestBody RemoveLinkRequest removeLinkRequest){
        //        TODO доделать
        return ResponseEntity.ok().body(null);

    }
}
