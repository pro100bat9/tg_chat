package com.example.linkparser;

import com.example.linkparser.model.NameWebsite;
import com.example.linkparser.parser.AbstractParser;
import com.example.linkparser.parser.GithubParser;
import com.example.linkparser.parser.StackoverflowParser;

import java.util.ArrayList;
import java.util.List;

public class FilterChainInit {

    public static void iter(List<AbstractParser> abstractParsers, String url){
        for (AbstractParser abstractParser : abstractParsers.subList(0, abstractParsers.size()-1)){
            for(AbstractParser abstractParser1 : abstractParsers.subList(1, abstractParsers.size())){
                abstractParser.setNextAbstractParser(abstractParser1);
            }
        }
        abstractParsers.get(0).parserManager(url);
    }

    public static void init(String url){
        List<AbstractParser> abstractParsers = new ArrayList<>();
        GithubParser githubParser = new GithubParser(NameWebsite.github);
        StackoverflowParser stackoverflowParser = new StackoverflowParser(NameWebsite.stackoverflow);
        abstractParsers.add(githubParser);
        abstractParsers.add(stackoverflowParser);
        iter(abstractParsers, url);
    }
}
