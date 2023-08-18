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
        List<GithubApiResponse> githubApiResponses = githubWebClient.fetchRepository(username, repository);

        if(githubApiResponses.isEmpty()){
            GithubApiResponse lastEvent = githubApiResponses.get(0);
            List<String> eventsInfo = githubApiResponses
                    .stream()
                    .filter(event -> event.getCreatedAt().isAfter(lastUpdateTime))
                    .map(event -> getEventType(event.getType()) + " time: " + event.getCreatedAt())
                    .toList();
            return new UpdateInfo(lastEvent.getCreatedAt(), eventsInfo);
        }
        return null;

    }

    public String getEventType(String event){
        try{
            return GitHubType.valueOf(event).getDescription();
        }
        catch (IllegalArgumentException ex){
            return GitHubType.UnknownEvent.getDescription();
        }
    }


}
