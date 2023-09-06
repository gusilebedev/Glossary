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

@Repository
public class StorageImpl implements Storage {

    private AppImpl app;
    private List<GlossaryImpl> glossaries;
    private GlossaryImpl activeGlossary;

    @Autowired
    public StorageImpl(AppImpl app) {
        this.app = app;
        this.glossaries = new ArrayList<>();
    }


    @Override
    @PostConstruct
    public boolean startGlossary() {
        try {
            Session session = app.session();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery query = builder.createQuery(GlossaryImpl.class);
            Root<GlossaryImpl> root = query.from(GlossaryImpl.class);
            query.select(root);
            List<GlossaryImpl> glossaryImplList = session.createQuery(query).getResultList();
            for (GlossaryImpl glossaryImpl : glossaryImplList) {
                parseGloss(glossaryImpl);
                glossaries.add(glossaryImpl);
            }
            session.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public void parseGloss(GlossaryImpl glossary) {
        try {
            Session session = app.session();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery query = builder.createQuery(WordImpl.class);
            Root<WordImpl> root = query.from(WordImpl.class);
            query.select(root);
            List<WordImpl> wordImplList = session.createQuery(query).getResultList();
            for (WordImpl word : wordImplList) {
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
                GlossaryImpl glossaryImpl = new GlossaryImpl();
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
                GlossaryImpl glossaryImpl = session.get(GlossaryImpl.class, name);
                session.remove(glossaryImpl);
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
                WordImpl wordImpl = new WordImpl();
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
                WordImpl wordImpl = session.get(WordImpl.class, activeGlossary.getWord(line).getId());
                session.remove(wordImpl);
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
    public GlossaryImpl searchListGloss(String name) {
        for (GlossaryImpl glossary : glossaries) {
            if (glossary.getNameGloss().equals(name)) {
                return glossary;
            }
        }
        return null;
    }

    @Override
    public boolean containsGrossList(String name) {
        for (GlossaryImpl glossary : glossaries) {
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
    public WordImpl getWordValue(String line) {
        if (activeGlossary.containsCheck(line)) {
            return activeGlossary.getWord(line);
        }
        return null;
    }

    @Override
    public GlossaryImpl getActiveGlossary() {
        return activeGlossary;
    }

    @Override
    public List getAllGross() {
        return glossaries;
    }
}
