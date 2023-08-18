package com.example.scrapper.client.githubClient;

import com.example.scrapper.dto.response.GithubApiResponse;

import java.util.List;

public interface GithubWebClient {
    List<GithubApiResponse> fetchRepository(String username, String repository);
}
