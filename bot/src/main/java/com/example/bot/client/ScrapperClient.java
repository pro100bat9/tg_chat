package com.example.bot.client;

import com.example.bot.dto.request.AddLinkRequest;
import com.example.bot.dto.request.RemoveLinkRequest;
import com.example.bot.dto.response.LinkResponse;
import com.example.bot.dto.response.ListLinksResponse;

public interface ScrapperClient {
    LinkResponse addLinks(Long id, AddLinkRequest addLinkRequest);
    ListLinksResponse getLinks(Long id);
    LinkResponse deleteLink(Long id, RemoveLinkRequest removeLinkRequest);
    void registrationChat(Long id);
    void deleteChat(Long id);
}
