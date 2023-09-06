package org.example.app.service;

import org.example.app.service.dao.GlossaryImpl;
import org.example.app.service.dao.WordImpl;
import org.example.web.dto.GlossaryToView;
import org.example.web.dto.WordToView;

import java.util.List;

public interface Storage {
    GlossaryImpl searchListGloss(String name);

    boolean containsGrossList(String name);

    boolean setActiveByName(String name);

    boolean startGlossary();

    boolean addGlossary(GlossaryToView glossary);

    boolean delGlossary(String name);

    boolean addWordValue(WordToView word);

    boolean delWordValue(String line);

    WordImpl getWordValue(String line);

    GlossaryImpl getActiveGlossary();

    List getAllGross();
}
