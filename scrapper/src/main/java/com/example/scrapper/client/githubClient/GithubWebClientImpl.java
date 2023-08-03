package com.example.scrapper.client.githubClient;


import com.example.scrapper.dto.response.GithubApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public class GithubWebClientImpl implements GithubWebClient {
    private final WebClient webClient;
    private final String url;

    @Override
    public GithubApiResponse fetchRepository(String userName, String repository) {
        return webClient
                .get()
                .uri(url + userName + "/" + repository)
                .retrieve()
                .bodyToMono(GithubApiResponse.class)
                .block();
    }
}
