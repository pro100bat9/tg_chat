package com.example.linkparser.parser;

import com.example.linkparser.model.NameWebsite;
import com.example.linkparser.model.answer.AbstractAnswer;
import com.example.linkparser.model.answer.GitHubAnswer;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class GithubParser extends AbstractParser{
    public GithubParser(NameWebsite website) {
        super(website);
    }

    private final static int LENGTH_URL = 4;

    @Override
    public AbstractAnswer getAnswer(String[] wordsFromUrl) {
        String domain = NameWebsite.github + ".com";
        if(wordsFromUrl[1].matches(domain) && wordsFromUrl.length == LENGTH_URL) {
            return new GitHubAnswer(wordsFromUrl[2], wordsFromUrl[3]);
        }
        return null;
    }
}
