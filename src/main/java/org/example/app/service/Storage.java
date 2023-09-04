package org.example.app.service;

import org.example.web.dto.Glossary;
import org.example.web.dto.GlossaryImpl;
import org.example.web.dto.Word;

import java.util.List;

public interface Storage {
    Glossary searchListGloss(String name);

    boolean containsGrossList(String name);

    boolean setActiveByName(String name);

    boolean startGlossary();

    boolean addGlossary(GlossaryImpl glossary);

    boolean delGlossary(String name);

    boolean addWordValue(String line);

    boolean delWordValue(String line);

    Word getWordValue(String line);

    Glossary getActiveGlossary();

    List getAllGross();
}
