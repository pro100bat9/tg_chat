package com.example.bot.telegram.command;

import com.example.bot.dto.response.LinkResponse;
import com.example.bot.dto.response.ListLinksResponse;
import com.example.bot.service.ScrapperService;
import com.example.bot.service.ScrapperServiceImpl;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.net.URI;
import java.util.List;

@Component
@Order(2)
public class ListCommand extends AbstractCommand{
    private final ScrapperService scrapperService;
    private static final String COMMAND = "/list";
    private static final String DESCRIPTION = "show a list of tracked links";
    private static final String EMPTY_LINKS_LIST_MESSAGE = "You don't have tracked links";

    public ListCommand(ScrapperServiceImpl scrapperServiceImpl) {
        super(COMMAND, DESCRIPTION);
        this.scrapperService =scrapperServiceImpl;
    }

    @Override
    public SendMessage handle(@NotNull Message message) {
        ListLinksResponse listLinksResponse = scrapperService.getLinks(message.getChatId());
        String response = listLinksResponse.size() == 0 ? EMPTY_LINKS_LIST_MESSAGE : getLinks(listLinksResponse);
        return new SendMessage(message.getChatId().toString(), response);
    }

    @Override
    public boolean supports(@NotNull Message message) {
        return message.getText().trim().startsWith(COMMAND);
    }

    private String getLinks(ListLinksResponse listLinksResponse){
        List<String> response = listLinksResponse
                .links()
                .stream()
                .map(LinkResponse::url)
                .map(URI::toString)
                .toList();
        return "List of your tracked links" + response.toString();
    }
}
