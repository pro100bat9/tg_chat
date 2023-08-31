package com.example.bot.listener;

import com.example.bot.dto.request.LinkUpdateRequest;
import com.example.bot.telegram.BotDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RequiredArgsConstructor
@RabbitListener(queues = "${app.rabbit-queue.queue-name}")
public class QueueListener {

    private final BotDispatcher botDispatcher;

    @RabbitHandler
    public void receiver(LinkUpdateRequest update) {
        botDispatcher.sendUpdates(update);
    }
}
