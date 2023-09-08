package org.example.app.service.dao;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class WordId implements Serializable {

    private String name;
    @ManyToOne
    private GlossaryEntity glossary;
}

