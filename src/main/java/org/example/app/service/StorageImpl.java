package org.example.app.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.app.service.dao.*;
import org.example.web.dto.GlossaryToView;
import org.example.web.dto.WordToView;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.List;


public class StorageImpl implements Storage {

    private AppImpl app;
    private List<Glossary> glossaries;
    private Glossary activeGlossary;


    public StorageImpl(AppImpl app) {
        this.app = app;
        this.glossaries = new ArrayList<>();
    }


    @Override
    public boolean startGlossary() {
        try {
            Session session = app.session();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery query = builder.createQuery(Glossary.class);
            Root<Glossary> root = query.from(Glossary.class);
            query.select(root);
            List<Glossary> glossaryList = session.createQuery(query).getResultList();
            for (Glossary glossary : glossaryList) {
                parseGloss(glossary);
                glossaries.add(glossary);
            }
            session.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public void parseGloss(Glossary glossary) {
        try {
            Session session = app.session();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery query = builder.createQuery(Word.class);
            Root<Word> root = query.from(Word.class);
            query.select(root);
            List<Word> wordList = session.createQuery(query).getResultList();
            for (Word word : wordList) {
                if (word.getId().getGlossary().getNameGloss().equals(glossary.getNameGloss())) {
                    glossary.addWord(word);
                }
            }
            session.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @Override
    public boolean addGlossary(GlossaryToView glossary) {
        try {
            String name = glossary.getName().trim();
            String regex = glossary.getRegex().trim();
            if (searchListGloss(name) == null) {
                Session session = app.session();
                Transaction transaction = session.beginTransaction();
                Glossary glossaryImpl = new Glossary();
                glossaryImpl.setNameGloss(name);
                glossaryImpl.setRegex(regex);
                session.save(glossaryImpl);
                transaction.commit();
                session.close();
                glossaries.add(glossaryImpl);
                return true;
            }
            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean delGlossary(String name) {
        try {
            if (searchListGloss(name) != null) {
                Session session = app.session();
                Transaction transaction = session.beginTransaction();
                Glossary glossary = session.get(Glossary.class, name);
                session.remove(glossary);
                transaction.commit();
                session.close();
                glossaries.remove(searchListGloss(name));
                return true;
            }
            return false;
        } catch (Exception ex) {
            return false;
        }
    }


    @Override
    public boolean addWordValue(WordToView word) {
        try {
            String name = word.getName().trim().toLowerCase();
            String value = word.getValue().toLowerCase();
            if (!activeGlossary.containsCheck(name)) {
                Word wordImpl = new Word();
                WordId wordId = new WordId();
                wordId.setName(name);
                wordId.setGlossary(activeGlossary);
                wordImpl.setId(wordId);
                wordImpl.setValue(value);
                boolean validated = activeGlossary.addWord(wordImpl);
                if (validated) {
                    Session session = app.session();
                    Transaction transaction = session.beginTransaction();
                    session.save(wordImpl);
                    transaction.commit();
                    session.close();
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
            return false;
        }
    }


    @Override
    public boolean delWordValue(String line) {
        try {
            if (activeGlossary.containsCheck(line)) {
                Session session = app.session();
                Transaction transaction = session.beginTransaction();
                Word word = session.get(Word.class, activeGlossary.getWord(line).getId());
                session.remove(word);
                transaction.commit();
                session.close();
                activeGlossary.delWord(line);
                return true;
            }
            return false;
        } catch (Exception ex) {
            return false;
        }
    }


    @Override
    public Glossary searchListGloss(String name) {
        for (Glossary glossary : glossaries) {
            if (glossary.getNameGloss().equals(name)) {
                return glossary;
            }
        }
        return null;
    }

    @Override
    public boolean containsGrossList(String name) {
        for (Glossary glossary : glossaries) {
            if (glossary.getNameGloss().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setActiveByName(String name) {
        activeGlossary = searchListGloss(name);
        return true;
    }

    @Override
    public Word getWordValue(String line) {
        if (activeGlossary.containsCheck(line)) {
            return activeGlossary.getWord(line);
        }
        return null;
    }

    @Override
    public Glossary getActiveGlossary() {
        return activeGlossary;
    }

    @Override
    public List getAllGross() {
        return glossaries;
    }
}
