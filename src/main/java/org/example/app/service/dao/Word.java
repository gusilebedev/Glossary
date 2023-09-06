package org.example.app.service.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "words", schema = "glossary_box")
public class Word {

    @EmbeddedId
    private WordId id;

    private String value;

    @Override
    public String toString() {
        return id.getName() + " - " + value;
    }
}
