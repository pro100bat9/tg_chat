package com.example.bot.client;

import com.example.bot.dto.request.AddLinkRequest;
import com.example.bot.dto.request.RemoveLinkRequest;
import com.example.bot.dto.response.LinkResponse;
import com.example.bot.dto.response.ListLinksResponse;
import com.example.bot.exception.ScrapperClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@RequiredArgsConstructor
public class ScrapperClientImpl implements ScrapperClient {

    private final WebClient webClient;


    @Override
    public LinkResponse addLinks(Long id, AddLinkRequest addLinkRequest) {
        return webClient
         .post()
         .uri(baseUrl -> URI.create(baseUrl.build() + "/links" + id))
         .exchangeToMono(r -> {
             if(r.statusCode().equals(HttpStatus.NOT_FOUND)){
                 throw new ScrapperClientException("Chat with this id not registered");
             }
             else if(r.statusCode().equals(HttpStatus.BAD_REQUEST)){
                 throw new ScrapperClientException("Link with this id already exists");
             }
             else if(r.statusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)){
                 throw new ScrapperClientException("On server something went wrong, please try again later");
             }
             return r.bodyToMono(LinkResponse.class);
         }).block();
    }

    @Override
    public ListLinksResponse getLinks(Long id) {
        return webClient
                .get()
                .uri(baseUrl -> URI.create(baseUrl.build() + "/links" + id))
                .exchangeToMono(r -> {
                    if(r.statusCode().equals(HttpStatus.NOT_FOUND)){
                        throw new ScrapperClientException("Chat with this id not registered");
                    }
                    else if(r.statusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)){
                        throw new ScrapperClientException("On server something went wrong, please try again later");
                    }
                    return r.bodyToMono(ListLinksResponse.class);
                }).block();
    }

    @Override
    public LinkResponse deleteLink(Long id, RemoveLinkRequest removeLinkRequest) {
        return webClient
                .get()
                .uri(baseUrl -> URI.create(baseUrl.build() + "/links" + id))
                .exchangeToMono(r -> {
                    if(r.statusCode().equals(HttpStatus.NOT_FOUND)){
                        throw new ScrapperClientException("Link with this URL not found or chat with this id not registered");
                    }
                    else if(r.statusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)){
                        throw new ScrapperClientException("On server something went wrong, please try again later");
                    }
                    else if(r.statusCode().equals(HttpStatus.BAD_REQUEST)){
                        throw new ScrapperClientException("Invalid link");
                    }
                    return r.bodyToMono(LinkResponse.class);
                }).block();
    }

    @Override
    public void registrationChat(Long id) {
             webClient
                .post()
                .uri(baseUrl -> URI.create(baseUrl.build() + "/tg-chat" + id))
                .exchangeToMono(r -> {
                    if(r.statusCode().equals(HttpStatus.BAD_REQUEST)){
                        throw new ScrapperClientException("Chat with this id already exists or invalid id");
                    }
                    else if(r.statusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)){
                        throw new ScrapperClientException("On server something went wrong, please try again later");
                    }
                    return Mono.empty();
                }).block();
    }

    @Override
    public void deleteChat(Long id) {
        webClient
                .post()
                .uri(baseUrl -> URI.create(baseUrl.build() + "/tg-chat" + id))
                .exchangeToMono(r -> {
                    if(r.statusCode().equals(HttpStatus.BAD_REQUEST)){
                        throw new ScrapperClientException("Invalid id");
                    }
                    else if(r.statusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)){
                        throw new ScrapperClientException("On server something went wrong, please try again later");
                    }
                    else if(r.statusCode().equals(HttpStatus.NOT_FOUND)){
                        throw new ScrapperClientException("Chat with this id not found");
                    }
                    return Mono.empty();
                }).block();
    }
}
