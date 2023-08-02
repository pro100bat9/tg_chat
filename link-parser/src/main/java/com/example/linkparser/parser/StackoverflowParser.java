package com.example.linkparser.parser;

import com.example.linkparser.model.NameWebsite;
import com.example.linkparser.model.answer.AbstractAnswer;
import com.example.linkparser.model.answer.StackoverflowAnswer;

public class StackoverflowParser extends AbstractParser{
    public StackoverflowParser(NameWebsite website) {
        super(website);
    }

    @Override
    public AbstractAnswer getAnswer(String[] wordsFromUrl) {
        if(wordsFromUrl[3].matches("\\d+")){
            return new StackoverflowAnswer(wordsFromUrl[3]);
        }
        return null;
    }

}
