package com.example.linkparser;

import com.example.linkparser.model.NameWebsite;
import com.example.linkparser.parser.AbstractParser;
import com.example.linkparser.parser.GithubParser;
import com.example.linkparser.parser.StackoverflowParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ParserConfig {
    @Bean
    public FilterChainInit filterChainInit(List<AbstractParser> abstractParsers){
        return new FilterChainInit(abstractParsers);
    }

    @Bean
    public GithubParser githubParser(){
        return new GithubParser(NameWebsite.github);
    }

    @Bean
    public StackoverflowParser stackoverflowParser(){
        return new StackoverflowParser(NameWebsite.stackoverflow);
    }
}
