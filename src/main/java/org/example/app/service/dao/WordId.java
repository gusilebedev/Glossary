package org.example.app.service.dao;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter

@Embeddable
public class WordId implements Serializable {
        private String name;
        @ManyToOne
        private Glossary glossary;
    }

