package com.example.scrapper.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;

@EnableAutoConfiguration(exclude = {LiquibaseAutoConfiguration.class })
public class TestConfiguration {
}
