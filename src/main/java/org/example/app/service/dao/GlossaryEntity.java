package org.example.app.service.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.TreeMap;

@Getter
@Setter
@Entity
@Table(name = "glossaries", schema = "glossary_box")

public class GlossaryEntity {

    @Id
    private String name;

    @OneToOne
    @JoinColumn(name = "regex")
    private RegexEntity regexEntity;

    @Transient
    private Map<String, WordEntity> map;

    public GlossaryEntity() {
        this.map = new TreeMap<>();
    }

    public boolean addWord(WordEntity wordEntity) {
        if (validatedLine(wordEntity.getId().getName())) {
            map.put(wordEntity.getId().getName(), wordEntity);
            return true;
        }
        return false;
    }

    public boolean delWord(String word) {
        map.remove(word);
        return true;
    }


    public WordEntity getWord(String word) {
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
        String regex = getRegexEntity().getRegex();;
        if (line.matches(regex)) {
            return true;
        }
        return false;
    }


    public String toString() {
        return name + " " + regexEntity.getRegex();
    }
}

