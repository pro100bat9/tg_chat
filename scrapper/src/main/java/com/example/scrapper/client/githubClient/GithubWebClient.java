package com.example.scrapper.client.githubClient;

import com.example.scrapper.dto.response.GithubApiResponse;

public interface GithubWebClient {
    GithubApiResponse fetchRepository(String username, String repository);
}
