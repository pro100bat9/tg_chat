package com.example.bot.service;

import com.example.bot.dto.request.AddLinkRequest;
import com.example.bot.dto.request.RemoveLinkRequest;
import com.example.bot.dto.response.LinkResponse;
import com.example.bot.dto.response.ListLinksResponse;

public interface ScrapperService {
    LinkResponse addLinks(Long id, String addLinkRequest);
    ListLinksResponse getLinks(Long id);
    LinkResponse deleteLink(Long id, String removeLinkRequest);
    void registrationChat(Long id);
    void deleteChat(Long id);
}
