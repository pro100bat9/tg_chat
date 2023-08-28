package com.example.scrapper.service.queue;

import com.example.scrapper.configuration.ApplicationConfig;
import com.example.scrapper.dto.request.LinkUpdateRequest;
import com.example.scrapper.service.sender.Sender;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class ScrapperQueueProducer implements Sender {
    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;
    private final String routingKey;

    public ScrapperQueueProducer(RabbitTemplate rabbitTemplate, ApplicationConfig applicationConfig) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = applicationConfig.rabbitQueue().exchangeName();
        this.routingKey = applicationConfig.rabbitQueue().routingKey();
    }

    public void send(LinkUpdateRequest update) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, update);
    }
}
