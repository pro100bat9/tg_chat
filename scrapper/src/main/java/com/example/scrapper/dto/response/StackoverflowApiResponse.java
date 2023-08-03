package com.example.scrapper.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record StackoverflowApiResponse(
        @JsonProperty("items")
        List<StackoverflowApiResponseItem> items
) {
}
