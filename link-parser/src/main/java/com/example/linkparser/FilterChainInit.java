package com.example.linkparser;


import com.example.linkparser.model.answer.AbstractAnswer;
import com.example.linkparser.parser.AbstractParser;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class FilterChainInit {

    private final List<AbstractParser> abstractParsers;

    public @Nullable AbstractAnswer init(@NotNull String url){
        return abstractParsers.stream()
                .map(abstractParser -> abstractParser.parserManager(url))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }



}
