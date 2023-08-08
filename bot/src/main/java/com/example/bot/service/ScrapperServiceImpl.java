package com.example.bot.service;

import com.example.bot.client.ScrapperClient;
import com.example.bot.dto.request.AddLinkRequest;
import com.example.bot.dto.request.RemoveLinkRequest;
import com.example.bot.dto.response.LinkResponse;
import com.example.bot.dto.response.ListLinksResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ScrapperServiceImpl implements ScrapperService{

    private final ScrapperClient scrapperClient;
    @Override
    public LinkResponse addLinks(Long id, String addLinkRequest) {
        return scrapperClient.addLinks(id, new AddLinkRequest(addLinkRequest));
    }

    @Override
    public ListLinksResponse getLinks(Long id) {
        return scrapperClient.getLinks(id);
    }

    @Override
    public LinkResponse deleteLink(Long id, String removeLinkRequest) {
        return scrapperClient.deleteLink(id, new RemoveLinkRequest(removeLinkRequest));
    }

    @Override
    public void registrationChat(Long id) {
        scrapperClient.registrationChat(id);
    }

    @Override
    public void deleteChat(Long id) {
        scrapperClient.deleteChat(id);
    }
}
