package org.example.app.service;

import org.example.app.service.dao.Glossary;
import org.example.app.service.dao.Word;
import org.example.web.dto.GlossaryToView;
import org.example.web.dto.WordToView;

import java.util.List;

public interface Storage {
    Glossary searchListGloss(String name);

    boolean containsGrossList(String name);

    boolean setActiveByName(String name);

    boolean startGlossary();

    boolean addGlossary(GlossaryToView glossary);

    boolean delGlossary(String name);

    boolean addWordValue(WordToView word);

    boolean delWordValue(String line);

    Word getWordValue(String line);

    Glossary getActiveGlossary();

    List getAllGross();
}
