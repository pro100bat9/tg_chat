package com.example.scrapper.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record GithubApiResponse(
        @JsonProperty("id")
        long id,

        @JsonProperty("name")
        String repositoryName,

        @JsonProperty("updated_at")
        OffsetDateTime updatedAt,

        @JsonProperty("created_at")
        OffsetDateTime createdAt,

        @JsonProperty("pushed_at")
        OffsetDateTime pushedAt
        ) {
}