package com.example.scrapper.client.githubClient;


import com.example.scrapper.dto.response.GithubApiResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;

public class GithubWebClientImpl implements GithubWebClient {
    private final WebClient webClient;
    private final String url;
    private final static String BASE_URL = "https://api.github.com/repos/";

    public GithubWebClientImpl(WebClient webClient, String url) {
        this.webClient = webClient;
        this.url = url;
    }

    public GithubWebClientImpl(WebClient webClient) {
        this.webClient = webClient;
        this.url = BASE_URL;
    }

    @Override
    public List<GithubApiResponse> fetchRepository(String username, String repository) {
        return webClient
                .get()
                .uri(url + username + "/" + repository + "/events")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<GithubApiResponse>>() {})
                .blockOptional()
                .orElse(Collections.emptyList());
    }
}
