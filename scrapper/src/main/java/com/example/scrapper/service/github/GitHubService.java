package com.example.scrapper.service.github;

import com.example.scrapper.client.githubClient.GithubWebClient;
import com.example.scrapper.dto.UpdateInfo;
import com.example.scrapper.dto.response.GithubApiResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GitHubService {
    private final GithubWebClient githubWebClient;

    public @Nullable UpdateInfo fetchUpdate(String username, String repository, OffsetDateTime lastUpdateTime){
        GithubApiResponse githubApiResponses = githubWebClient.fetchRepository(username, repository);

        if(githubApiResponses != null && githubApiResponses.updatedAt().isAfter(lastUpdateTime)){
            return new UpdateInfo(githubApiResponses.updatedAt());
        }
        return null;

    }
}
