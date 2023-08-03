package com.example.scrapper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerConfig {
    @Bean
    public long schedulerIntervalMs(ApplicationConfig config){
        return config.scheduler().interval().toMillis();
    }
}
