package com.example.scrapper.dto.response;


import java.util.List;

public record ListLinksResponse(
        List<LinkResponse> links,
        Integer size
) {
}
