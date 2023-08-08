package com.example.bot.telegram.command;

import jakarta.validation.constraints.NotNull;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Component
@Order(1)
public class HelpCommand extends AbstractCommand{
    private static final String COMMAND = "/help";
    private static final String DESCRIPTION = "show list of commands";

    private final List<String> commands;

    public HelpCommand(List<AbstractCommand> abstractCommandList) {
        super(COMMAND, DESCRIPTION);
        commands = abstractCommandList
                .stream()
                .map(v -> v.getCommand() + ": " + v.getDescription())
                .toList();
    }

    @Override
    public SendMessage handle(@NotNull Message message) {
        return new SendMessage(message.getChatId().toString(), "Commands: \n" + commands.toString());
    }

    @Override
    public boolean supports(@NotNull Message message) {
        return message.getText().trim().startsWith(COMMAND);
    }
}
