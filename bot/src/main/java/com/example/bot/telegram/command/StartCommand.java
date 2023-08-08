package com.example.bot.telegram.command;

import com.example.bot.service.ScrapperService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Order(3)
@RequiredArgsConstructor
public class StartCommand implements Command{

    private static final String COMMAND = "/start";
    private static final String WELCOME_MESSAGE = "Hello!";
    private final ScrapperService scrapperServiceImpl;

    @Override
    public SendMessage handle(@NotNull Message message) {
        scrapperServiceImpl.registrationChat(message.getChatId());
        return new SendMessage(message.getChatId().toString(), WELCOME_MESSAGE);
    }

    @Override
    public boolean supports(@NotNull Message message) {
        return message.getText().trim().startsWith(COMMAND);
    }
}
