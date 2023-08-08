package com.example.bot.telegram;

import com.example.bot.configuration.ApplicationConfig;
import com.example.bot.handler.MessageHandler;
import com.example.bot.telegram.command.AbstractCommand;
import com.example.bot.telegram.command.Command;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class BotDispatcher extends TelegramLongPollingBot {
    private final ApplicationConfig applicationConfig;
    private final MessageHandler messageHandler;
    private final List<AbstractCommand> commands;

    public BotDispatcher(ApplicationConfig applicationConfig, MessageHandler messageHandler, List<AbstractCommand> commands) {
        super(applicationConfig.Bot().token());
        this.applicationConfig = applicationConfig;
        this.messageHandler = messageHandler;
        this.commands = commands;
    }

    @PostConstruct
    private void initCommands(){
        List<BotCommand> botCommands = commands
                .stream()
                .map(AbstractCommand::getBotCommand)
                .toList();
        SetMyCommands setMyCommands = new SetMyCommands();
        setMyCommands.setCommands(botCommands);
        try{
            this.execute(setMyCommands);
        } catch (TelegramApiException e) {
            System.out.println("happen something wrong" + e.getMessage());
        }


    }


    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()){
            Message message = update.getMessage();
            SendMessage sendMessage = messageHandler.handle(message);
            try{
                execute(sendMessage);
            } catch (TelegramApiException e) {
                System.out.println("happen something wrong" + e.getMessage());
            }
        }

    }

    @Override
    public String getBotUsername() {
        return applicationConfig.Bot().name();
    }
}
