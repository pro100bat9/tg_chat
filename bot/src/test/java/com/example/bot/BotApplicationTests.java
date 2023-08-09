package com.example.bot;

import com.example.bot.handler.MessageHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "spring.main.lazy-initialization=true")
class BotApplicationTests {

    @Autowired
    private MessageHandler messageHandler;

    @Test
    void checkUnknownCommand() {
        SendMessage sendMessage = messageHandler.handle(createMessage("UnknownCommand"));

        assertEquals(sendMessage.getText(), "Unknown command, use /help to get list of commands");
    }

    private Message createMessage(String text){
        Message message = new Message();
        message.setChat(new Chat(1L, "private"));
        message.setText(text);
        return message;
    }

}
