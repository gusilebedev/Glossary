package org.example.app.service.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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
    private String nameGloss;
    private String regex;
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

