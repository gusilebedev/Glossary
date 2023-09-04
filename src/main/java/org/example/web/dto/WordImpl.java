package org.example.web.dto;

import jakarta.persistence.*;

@Entity
@Table(name = "words", schema = "glossary_box")
public class WordImpl implements Word{

    @EmbeddedId
    private WordId id;

    private String value;

    @Override
    public WordId getId() {
        return id;
    }
    @Override
    public void setId(WordId id) {
        this.id = id;
    }
    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return id.getName() + " - " + value;
    }
}
