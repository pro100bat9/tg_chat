package com.example.linkparser;

import com.example.linkparser.model.NameWebsite;
import com.example.linkparser.parser.AbstractParser;
import com.example.linkparser.parser.GithubParser;
import com.example.linkparser.parser.StackoverflowParser;

import java.util.ArrayList;
import java.util.List;

public class LinkParserApplication {

    public static void main(String[] args) {
        List<AbstractParser> abstractParsers = new ArrayList<>();
        GithubParser githubParser = new GithubParser(NameWebsite.github);
        StackoverflowParser stackoverflowParser = new StackoverflowParser(NameWebsite.stackoverflow);
        abstractParsers.add(githubParser);
        abstractParsers.add(stackoverflowParser);
        FilterChainInit filterChainInit = new FilterChainInit(abstractParsers);
        filterChainInit.init("https://stackoverflow.com/search?q=unsupported%20link");
        filterChainInit.init("https://stackoverflow.com/search?q=unsupported%20link");
        filterChainInit.init("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c");
        filterChainInit.init("https://github.com/sanyarnd/tinkoff-java-course-2022/");
    }

}
