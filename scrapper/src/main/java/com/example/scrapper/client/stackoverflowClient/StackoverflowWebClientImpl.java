package com.example.scrapper.client.stackoverflowClient;

import com.example.scrapper.dto.response.StackoverflowApiResponse;
import com.example.scrapper.dto.response.StackoverflowApiResponseItem;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public class StackoverflowWebClientImpl implements StackoverflowWebClient {
    private final WebClient webClient;
    private final String url;

    @Value("${stackoverflow.mandatory-request-params}")
    private String STACKOVERFLOW_MANDATORY_REQUEST_PARAMS;
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
