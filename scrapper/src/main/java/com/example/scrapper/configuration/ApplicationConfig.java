package com.example.scrapper.configuration;

import com.example.scrapper.repository.JdbcChatRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
        @NotNull
        String test,
        @NotNull
        Scheduler scheduler
) {

    public record Scheduler(Duration interval){
    }
}
