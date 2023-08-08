package com.example.bot.configuration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.validation.annotation.Validated;
import org.telegram.telegrambots.starter.TelegramBotStarterConfiguration;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
@Import(TelegramBotStarterConfiguration.class)
public record ApplicationConfig(
        @NotNull String test,
        Bot Bot,
        Client client
) {

    @Validated
    public record Bot(@NotBlank String name, @NotBlank String token){
    }

    @Validated
    public record Client(@NotBlank String baseUrl){

    }

}
