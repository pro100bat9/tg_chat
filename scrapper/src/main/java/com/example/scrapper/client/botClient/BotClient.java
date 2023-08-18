package com.example.scrapper.client.botClient;

import com.example.scrapper.dto.request.LinkUpdateRequest;

public interface BotClient {
    void updatePosts(LinkUpdateRequest linkUpdateRequest);
}
