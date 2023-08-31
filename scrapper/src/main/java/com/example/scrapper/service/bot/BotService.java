package com.example.scrapper.service.bot;

import com.example.scrapper.client.botClient.BotClient;
import com.example.scrapper.dto.request.LinkUpdateRequest;
import com.example.scrapper.service.sender.Sender;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false", matchIfMissing = true)
public class BotService implements Sender {

    private final BotClient botClient;

    @Override
    public void send(LinkUpdateRequest update) {
        botClient.updatePosts(update);
    }
}
