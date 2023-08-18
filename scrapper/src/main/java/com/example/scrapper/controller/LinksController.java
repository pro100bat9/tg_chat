package com.example.scrapper.controller;

import com.example.scrapper.annotation.ScrapperHandler;
import com.example.scrapper.dto.entity.LinkEntity;
import com.example.scrapper.dto.request.AddLinkRequest;
import com.example.scrapper.dto.request.RemoveLinkRequest;
import com.example.scrapper.dto.response.LinkResponse;
import com.example.scrapper.dto.response.ListLinksResponse;
import com.example.scrapper.service.interfaces.LinkService;
import com.example.scrapper.service.interfaces.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@ScrapperHandler
@RequestMapping("/links")
@RequiredArgsConstructor
public class LinksController {

    private final SubscriptionService subscriptionService;

    @PostMapping(
//            path = "/links",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<LinkResponse> addLinks(@RequestParam Long id, @RequestBody AddLinkRequest addLinkRequest){
        LinkEntity linkEntity = subscriptionService.subscribe(id, addLinkRequest.link());
        return ResponseEntity.ok().body(new LinkResponse(linkEntity.getId(), linkEntity.getUrl()));
    }

    @GetMapping(
//            path = "/links",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ListLinksResponse> getLinks(@RequestParam Long id){
        List<LinkResponse> linkResponses = subscriptionService.getLinksFromChat(id)
                .stream()
                .map(linkEntity -> new LinkResponse(linkEntity.getId(), linkEntity.getUrl()))
                .toList();
        return ResponseEntity.ok().body(new ListLinksResponse(linkResponses, linkResponses.size()));
    }

    @DeleteMapping(
//            path = "/links",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<LinkResponse> deleteLinks(@RequestParam Long id, @RequestBody RemoveLinkRequest removeLinkRequest){
        LinkEntity linkEntity = subscriptionService.unSubscribe(id, removeLinkRequest.url());
        return ResponseEntity.ok().body(new LinkResponse(id, linkEntity.getUrl()));
    }
}
