package com.example.linkparser;

import com.example.linkparser.model.NameWebsite;
import com.example.linkparser.model.answer.AbstractAnswer;
import com.example.linkparser.parser.AbstractParser;
import com.example.linkparser.parser.GithubParser;
import com.example.linkparser.parser.StackoverflowParser;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LinkParserApplicationTests {

    private final FilterChainInit filterChainInit;

    LinkParserApplicationTests() {
        List<AbstractParser> handlers = Arrays.asList(
                new GithubParser(NameWebsite.github),
                new StackoverflowParser(NameWebsite.stackoverflow)
        );
        filterChainInit = new FilterChainInit(handlers);
    }


    @ParameterizedTest
    @MethodSource({
            "getCorrect",
            "getWrongFormat"
    })
    void checkIncorrectLink(String url, Boolean correct) {
        AbstractAnswer answer = filterChainInit.init(url);
        assertEquals(answer != null, correct);
    }

    private Stream<Arguments> getCorrect(){
        return Stream.of(
                Arguments.of("https://stackoverflow.com/questions/64237319/correlation-id-in-header-kafka", true),
                Arguments.of("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c", true),
                Arguments.of("https://stackoverflow.com/questions/44237829/spring-kafkatemplate-correlation-id", true),
                Arguments.of("https://github.com/pro100bat9/WebProgramming", true),
                Arguments.of("https://github.com/pro100bat9/TECH_2022", true)
        );
    }

    private Stream<Arguments> getWrongFormat(){
        return Stream.of(
                Arguments.of("ijerwgow", false),
                Arguments.of("vk.com", false),
                Arguments.of("https://stackoverflow.fake/what/44237829/unsupported%20link", false),
                Arguments.of("https://github.fake/pro100bat9/Test",false),
                Arguments.of("https://stackoverflow.com/search?q=unsupported%20link", false),
                Arguments.of("https://github.com/fake/unsupported%20link/fake",false)
        );
    }




}
