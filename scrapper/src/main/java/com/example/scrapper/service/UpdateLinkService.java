package com.example.scrapper.service;

import com.example.linkparser.FilterChainInit;
import com.example.linkparser.model.answer.AbstractAnswer;
import com.example.linkparser.model.answer.GitHubAnswer;
import com.example.linkparser.model.answer.StackoverflowAnswer;
import com.example.scrapper.client.botClient.BotClient;
import com.example.scrapper.configuration.ApplicationConfig;
import com.example.scrapper.dto.UpdateInfo;
import com.example.scrapper.dto.model.LinkDto;
import com.example.scrapper.dto.request.LinkUpdateRequest;
import com.example.scrapper.service.github.GitHubService;
import com.example.scrapper.service.interfaces.LinkService;
import com.example.scrapper.service.interfaces.SubscriptionService;
import com.example.scrapper.service.sender.SenderService;
import com.example.scrapper.service.stackoverflow.StackoverflowService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateLinkService {
    private final ApplicationConfig config;
    private final GitHubService gitHubService;
    private final StackoverflowService stackoverflowService;
    private final LinkService linkService;
    private final FilterChainInit filterChain;
    private final SubscriptionService subscriptionService;
    private final SenderService senderService;


    public void updateLinks(){
        getUncheckedLinks().forEach(link ->{
            UpdateInfo updateInfo = fetchUpdate(link);

            boolean canSendUpdate = updateInfo != null && (link.getLastCheckTime() == null ||
                    link.getLastCheckTime().isBefore(updateInfo.dateTime()));
            if(canSendUpdate){
                sendUpdates(link, updateInfo);
            }
        });

    }

    public List<LinkDto> getUncheckedLinks(){
        return linkService.updateLastCheckedTime(
                config.scheduler().interval()
        );
    }

    public @Nullable UpdateInfo fetchUpdate(LinkDto link){
        AbstractAnswer abstractAnswer = filterChain.init(link.getUrl().toString());
        return switch (abstractAnswer) {
                    case null -> throw new InternalError("link was null");
                    case GitHubAnswer answer -> gitHubService.fetchUpdate(answer.username(),
                            answer.repositoryName(), link.getLastCheckTime());
                    case StackoverflowAnswer answer -> stackoverflowService.fetchUpdate(answer.questionId());
                };
    }

    public void sendUpdates(LinkDto link, UpdateInfo updateInfo){
        linkService.updateLink(link, updateInfo.dateTime());
        senderService.sendUpdate(new LinkUpdateRequest(link.getId(), link.getUrl(), updateInfo.toString(),
                subscriptionService.getChatId(link.getId())));
    }
}
