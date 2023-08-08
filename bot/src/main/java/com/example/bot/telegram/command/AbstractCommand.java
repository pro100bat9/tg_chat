package com.example.bot.telegram.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

@RequiredArgsConstructor
@Getter
public abstract class AbstractCommand implements Command {
    private final String command;
    private final String description;

    public BotCommand getBotCommand(){
        return new BotCommand(command, description);
    }
}
