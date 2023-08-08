package com.example.bot.telegram.command;

import com.example.bot.service.ScrapperService;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Order(5)
public class UntrackCommand extends AbstractCommand{

    private static final String COMMAND = "/untrack";
    private static final String DESCRIPTION = "stop tracking link";
    private static final Pattern PATTERN = Pattern.compile("^/untrack (\\S+)\\s*$");
    private static final String SUCCESS_RESPONSE = "Removed link from your tacking list";
    private static final String WRONG_FORMAT_RESPONSE = "Use correct format";

    private final ScrapperService scrapperService;

    public UntrackCommand(ScrapperService scrapperService) {
        super(COMMAND, DESCRIPTION);
        this.scrapperService = scrapperService;
    }

    @Override
    public SendMessage handle(@NotNull Message message) {
        Matcher matcher = PATTERN.matcher(message.getText());
        if(matcher.matches()){
            String url = matcher.group(1);
            scrapperService.addLinks(message.getChatId(), url);
            return new SendMessage(message.getChatId().toString(), SUCCESS_RESPONSE);
        }
        return new SendMessage(message.getChatId().toString(), WRONG_FORMAT_RESPONSE);
    }

    @Override
    public boolean supports(Message message) {
        return message.getText().trim().startsWith(COMMAND);
    }
}
