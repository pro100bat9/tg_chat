package com.example.linkparser.parser;

import com.example.linkparser.model.NameWebsite;
import com.example.linkparser.model.answer.AbstractAnswer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractParser {

    private final NameWebsite website;

    public AbstractParser(NameWebsite website) {
        this.website = website;
    }

    public @Nullable AbstractAnswer parserManager(@NotNull String url){
        try {
        String substringUrl = url.substring("https://".length() - 1);
        String[] wordsFromUrl = substringUrl.split("/");
            String[] nameWebsite = wordsFromUrl[1].split("\\.");
            if(nameWebsite[0].equals(website.toString())){
                return getAnswer(wordsFromUrl);
            }
        }
        catch (StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException ex){
            System.out.println(ex.getMessage());

        }
        return null;

    }
    public abstract AbstractAnswer getAnswer(String[] wordsFromUrl);



}
