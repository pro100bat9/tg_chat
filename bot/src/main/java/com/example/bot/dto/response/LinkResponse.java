package com.example.bot.dto.response;

import java.net.URI;

public record LinkResponse(
        Integer id,
        URI url
) {
}
