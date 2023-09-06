package org.example.app.service.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "words", schema = "glossary_box")
public class WordImpl {

    @EmbeddedId
    private WordId id;

    private String value;

    @Override
    public String toString() {
        return id.getName() + " - " + value;
    }
}
