package com.example.scrapper.client.githubClient;

import com.example.scrapper.dto.response.GithubApiResponse;
import org.springframework.web.reactive.function.client.WebClient;

public interface GithubWebClient {
    GithubApiResponse fetchRepository(String userName, String repository);
}
