package com.example.linkparser.parser;

import com.example.linkparser.model.NameWebsite;
import com.example.linkparser.model.answer.AbstractAnswer;

public abstract class AbstractParser {
    private AbstractParser nextAbstractParser;
    private final NameWebsite website;

    public AbstractParser(NameWebsite website) {
        this.website = website;
    }

    public void setNextAbstractParser(AbstractParser nextAbstractParser) {
        this.nextAbstractParser = nextAbstractParser;
    }

    public void parserManager(String url){
        String substringUrl = url.substring("https://".length() - 1);
        String[] wordsFromUrl = substringUrl.split("/");
        String[] nameWebsite = wordsFromUrl[1].split("\\.");
        if(nameWebsite[0].equals(website.toString())){
            getAnswer(wordsFromUrl);
        }
        if(nextAbstractParser != null){
            nextAbstractParser.parserManager(url);
        }


    }
    public abstract AbstractAnswer getAnswer(String[] wordsFromUrl);



}
