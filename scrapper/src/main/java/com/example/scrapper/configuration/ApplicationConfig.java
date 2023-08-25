package com.example.scrapper.configuration;

import com.example.linkparser.ParserConfig;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
@Import(ParserConfig.class)
public record ApplicationConfig(
        @NotNull
        String test,
        @NotNull
        Scheduler scheduler,
        @NotNull
        AccessType databaseAccessType
) {

    public record Scheduler(Duration interval){
    }

    public enum AccessType {
        JDBC,
        JPA,
        JOOQ
    }
}
