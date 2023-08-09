package com.example.linkparser.parser;

import com.example.linkparser.model.NameWebsite;
import com.example.linkparser.model.answer.AbstractAnswer;
import com.example.linkparser.model.answer.StackoverflowAnswer;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class StackoverflowParser extends AbstractParser{
    public StackoverflowParser(NameWebsite website) {
        super(website);
    }

    @Override
    public AbstractAnswer getAnswer(String[] wordsFromUrl) {
        String domain = NameWebsite.stackoverflow + ".com";
        if(wordsFromUrl[3].matches("\\d+")
                && wordsFromUrl[2].matches("questions")
                && wordsFromUrl[1].matches(domain)){
                return new StackoverflowAnswer(wordsFromUrl[3]);
        }
        return null;
    }

}
