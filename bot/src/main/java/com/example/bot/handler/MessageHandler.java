package com.example.bot.handler;

import com.example.bot.telegram.command.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MessageHandler {
    private final List<Command> commands;

    public SendMessage handle(Message message){
        return commands
                .stream()
                .filter(command -> command.supports(message))
                .map(command -> command.handle(message))
                .findFirst()
                .get();
    }

}
