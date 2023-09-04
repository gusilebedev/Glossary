package org.example.web.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.util.Map;
import java.util.TreeMap;

@Entity
@Table(name = "glossaries", schema = "glossary_box")
public class GlossaryImpl implements Glossary {

    @Id
    private String nameGloss;
    private String regex;
    @Transient
    private Map<String, Word> map;

    public GlossaryImpl() {
        this.map = new TreeMap<>();
    }

    @Override
    public String getNameGloss() {
        return nameGloss;
    }

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public Map getGloss() {
        return map;
    }

    @Override
    public void setNameGloss(String name) {
        this.nameGloss = name;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public boolean addWord(Word word) {
        if (validatedLine(word.getId().getName())) {
            map.put(word.getId().getName(), word);
            return true;
        }
        return false;
    }

    @Override
    public boolean delWord(String word) {
        map.remove(word);
        return true;
    }

    @Override
    public Word getWord(String word) {
        return map.get(word);
    }

    @Override
    public boolean containsCheck(String word) {
        if (map.containsKey(word)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean validatedLine(String line) {
        String regex = getRegex();
        if (line.matches(regex)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return nameGloss + " " + regex;
    }
}

