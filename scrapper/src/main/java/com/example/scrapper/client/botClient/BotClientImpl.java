package com.example.scrapper.client.botClient;

import com.example.scrapper.dto.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


public class BotClientImpl implements BotClient{

    private final static String BASE_URL = "http://localhost:8081";
    private final static String MAPPING = "/updates";

    private final WebClient webClient;
    private final String baseUrl;

    public BotClientImpl(WebClient webClient, String baseUrl) {
        this.webClient = webClient;
        this.baseUrl = baseUrl;
    }

    public BotClientImpl(WebClient webClient) {
        this.webClient = webClient;
        this.baseUrl = BASE_URL;
    }

    @Override
    public void updatePosts(LinkUpdateRequest linkUpdateRequest) {
        webClient
                .post()
                .uri(baseUrl + MAPPING)
                .body(BodyInserters.fromValue(linkUpdateRequest))
                .retrieve()
                .toBodilessEntity()
                .onErrorResume((ex) -> Mono.empty())
                .block();
    }
}
