package com.example.scrapper.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RemoveLinkRequest(
        @NotBlank
        String url
)

{}
