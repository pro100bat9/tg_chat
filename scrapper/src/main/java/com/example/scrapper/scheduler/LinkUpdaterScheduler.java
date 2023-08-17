package com.example.scrapper.scheduler;

import com.example.scrapper.service.UpdateLinkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LinkUpdaterScheduler {

    private final UpdateLinkService updateLinkService;

    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update(){
        log.info("updated!");
        updateLinkService.updateLinks();
    }
}
