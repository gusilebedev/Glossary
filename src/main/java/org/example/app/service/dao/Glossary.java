package org.example.app.service.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.TreeMap;

@Getter
@Setter
@Entity
@Table(name = "glossaries", schema = "glossary_box")

public class Glossary {

    @Id
    private String nameGloss;
    private String regex;
    @Transient
    private Map<String, Word> map;

    public Glossary() {
        this.map = new TreeMap<>();
    }

    public boolean addWord(Word word) {
        if (validatedLine(word.getId().getName())) {
            map.put(word.getId().getName(), word);
            return true;
        }
        return false;
    }

    public boolean delWord(String word) {
        map.remove(word);
        return true;
    }


    public Word getWord(String word) {
        return map.get(word);
    }
    public Map getGloss() {
        return map;
    }


    public boolean containsCheck(String word) {
        if (map.containsKey(word)) {
            return true;
        }
        return false;
    }


    public boolean validatedLine(String line) {
        String regex = getRegex();
        if (line.matches(regex)) {
            return true;
        }
        return false;
    }


    public String toString() {
        return nameGloss + " " + regex;
    }
}

