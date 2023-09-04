package org.example.web.dto;

import java.util.Map;


public interface Glossary {


    String getNameGloss();

    String getRegex();

    Map getGloss();

    void setNameGloss(String name);

    void setRegex(String regex);

    boolean addWord(Word word);

    boolean delWord(String word);

    Word getWord(String word);

    boolean containsCheck(String word);

    boolean validatedLine(String line);
}
