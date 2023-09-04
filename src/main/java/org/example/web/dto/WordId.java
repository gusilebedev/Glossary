package org.example.web.dto;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
@Embeddable
public class WordId implements Serializable {
        private String name;
        @ManyToOne
        private GlossaryImpl glossary;


        public String getName() {
            return name;
        }


        public void setName(String name) {
            this.name = name;
        }

        public GlossaryImpl getGlossary() {
            return glossary;
        }

        public void setGlossary(GlossaryImpl glossary) {
            this.glossary = glossary;
        }
    }

