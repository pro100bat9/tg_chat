package com.example.bot.configuration;

import com.example.bot.client.ScrapperClient;
import com.example.bot.client.ScrapperClientImpl;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
public class ClientConfig {

    private final ApplicationConfig applicationConfig;

    @Value("${webclient.timeout}")
    private int timeout;

    @Bean
    public WebClient webClient() {
        final HttpClient httpClient = HttpClient
                .create()
                .compress(true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeout)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(timeout, TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(timeout, TimeUnit.MILLISECONDS));
                });

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @Bean
    public ScrapperClient scrapperClient(){
        return new ScrapperClientImpl(webClient());
    }

}
