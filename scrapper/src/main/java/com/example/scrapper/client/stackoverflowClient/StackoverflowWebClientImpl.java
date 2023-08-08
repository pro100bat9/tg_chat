package com.example.scrapper.client.stackoverflowClient;

import com.example.scrapper.dto.response.StackoverflowApiResponse;
import com.example.scrapper.dto.response.StackoverflowApiResponseItem;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

public class StackoverflowWebClientImpl implements StackoverflowWebClient {
    private final WebClient webClient;
    private final String url;
    private static final String BASE_URL = "https://api.stackexchange.com/2.3/questions/";
    @Value("${stackoverflow.mandatory-request-params}")
    private String STACKOVERFLOW_MANDATORY_REQUEST_PARAMS;

    public StackoverflowWebClientImpl(WebClient webClient, String url) {
        this.webClient = webClient;
        this.url = url;
    }

    public StackoverflowWebClientImpl(WebClient webClient) {
        this.webClient = webClient;
        this.url = BASE_URL;
    }

    @Override
    public StackoverflowApiResponse fetchQuestion(String id) {
        return webClient
                .get()
                .uri(url + id + STACKOVERFLOW_MANDATORY_REQUEST_PARAMS)
                .retrieve()
                .bodyToMono(StackoverflowApiResponse.class)
                .block();
    }
}
