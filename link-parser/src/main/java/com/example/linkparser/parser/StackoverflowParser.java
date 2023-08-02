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
        try {
        if(wordsFromUrl[3].matches("\\d+")){
                return new StackoverflowAnswer(wordsFromUrl[3]);
        }
        }
        catch (ArrayIndexOutOfBoundsException ex){
            System.out.println("array out of bounds");
        }
        return null;
    }

}
