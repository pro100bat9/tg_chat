package com.example.bot.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RemoveLinkRequest(
        @NotBlank
        String url
) {
}
