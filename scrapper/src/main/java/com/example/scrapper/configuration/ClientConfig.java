package com.example.scrapper.configuration;

import com.example.scrapper.client.botClient.BotClient;
import com.example.scrapper.client.botClient.BotClientImpl;
import com.example.scrapper.client.githubClient.GithubWebClient;
import com.example.scrapper.client.githubClient.GithubWebClientImpl;
import com.example.scrapper.client.stackoverflowClient.StackoverflowWebClient;
import com.example.scrapper.client.stackoverflowClient.StackoverflowWebClientImpl;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

@Configuration
public class ClientConfig {

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
    public GithubWebClient githubWebClient(WebClient webClient, @Value("${github.url}") String url){
        return new GithubWebClientImpl(webClient, url);
    }

    @Bean
    public StackoverflowWebClient stackoverflowWebClient(WebClient webClient, @Value("${stackoverflow.url}") String url){
        return new StackoverflowWebClientImpl(webClient, url);
    }

    @Bean
    public BotClient botClient(WebClient webClient, @Value("${bot.url.base}") String url){
        return new BotClientImpl(webClient, url);
    }

}
