package com.example.linkparser.parser;

import com.example.linkparser.model.NameWebsite;
import com.example.linkparser.model.answer.AbstractAnswer;
import com.example.linkparser.model.answer.GitHubAnswer;

public class GithubParser extends AbstractParser{
    public GithubParser(NameWebsite website) {
        super(website);
    }

    @Override
    public AbstractAnswer getAnswer(String[] wordsFromUrl) {
        return new GitHubAnswer(wordsFromUrl[2], wordsFromUrl[3]);
    }
}
