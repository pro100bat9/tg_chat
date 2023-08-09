package com.example.bot.commands;

import com.example.bot.dto.response.LinkResponse;
import com.example.bot.dto.response.ListLinksResponse;
import com.example.bot.service.ScrapperService;
import com.example.bot.service.ScrapperServiceImpl;
import com.example.bot.telegram.command.ListCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.net.URI;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListCommandTest {

    @Mock
    private ScrapperServiceImpl scrapperServiceImpl;

    @InjectMocks
    private ListCommand listCommand;

    @Test
    void listLinksEmpty(){
        when(scrapperServiceImpl.getLinks(anyLong())).thenReturn(returnListLinkResponse(0));

        SendMessage sendMessage = listCommand.handle(createMessage());

        assertEquals(sendMessage.getText(), "You don't have tracked links");
    }

    @Test
    void listLinksIsNotEmpty(){

        int size = 2;

        when(scrapperServiceImpl.getLinks(1L)).thenReturn(returnListLinkResponse(size));

        SendMessage sendMessage = listCommand.handle(createMessage());
        assertTrue(sendMessage.getText().startsWith("List of your tracked links:"));
        assertEquals(sendMessage.getText().split(",").length, size);
    }

    private ListLinksResponse returnListLinkResponse(int size){
        List<LinkResponse> listLinksResponses = Stream.iterate(0, i -> i+1)
                .map(id -> new LinkResponse(id, URI.create("https://mockLink/")))
                .limit(size)
                .toList();
        return new ListLinksResponse(listLinksResponses, size);
    }



    private Message createMessage(){
        Message message = new Message();
        message.setChat(new Chat(1L, "private"));
        message.setText("/list");
        return message;
    }
}
