package org.example.app.service;

import lombok.Getter;
import org.example.app.service.dao.Glossary;
import org.example.app.service.dao.Word;
import org.example.app.service.dao.WordId;
import org.example.web.dto.GlossaryToView;
import org.example.web.dto.WordToView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Getter
@Service
@Transactional
public class GlossaryService {

    @Autowired
    GlossariesRepository glossariesRepository;

    @Autowired
    WordsRepository wordsRepository;

    private Glossary activeGlossary;

    public GlossaryService(GlossariesRepository glossariesRepository, WordsRepository wordsRepository) {
        this.glossariesRepository = glossariesRepository;
        this.wordsRepository = wordsRepository;
    }

    public boolean setActiveByName(GlossaryToView glossaryToView) {
        activeGlossary = getGlossary(glossaryToView);
        return true;
    }


    public boolean saveGlossary(GlossaryToView glossaryToView) {
        Glossary glossary = new Glossary();
        glossary.setNameGloss(glossaryToView.getName());
        glossary.setRegex(glossaryToView.getRegex());
        glossariesRepository.save(glossary);
        return true;
    }

    public List<Glossary> listAllGlossaries() {
        return (List<Glossary>) glossariesRepository.findAll();
    }

    public Glossary getGlossary(GlossaryToView glossaryToView) {
        return glossariesRepository.findById(glossaryToView.getName()).get();
    }

    public boolean deleteGlossary(GlossaryToView glossaryToView) {
        glossariesRepository.deleteById(glossaryToView.getName());
        return true;
    }

    public boolean saveWord(WordToView wordToView) {
        WordId wordId = new WordId();
        wordId.setName(wordToView.getName());
        wordId.setGlossary(getActiveGlossary());
        Word word = new Word();
        word.setId(wordId);
        word.setValue(wordToView.getValue());
        wordsRepository.save(word);
        return true;
    }

    public List<Word> listAllWords() {
        return (List<Word>) wordsRepository.findAll();
    }

    public Word getWord(WordToView wordToView) {
        WordId wordId = new WordId();
        wordId.setName(wordToView.getName());
        wordId.setGlossary(getActiveGlossary());
        return wordsRepository.findById(wordId).get();
    }

    public boolean deleteWord(WordToView wordToView) {
        WordId wordId = new WordId();
        wordId.setName(wordToView.getName());
        wordId.setGlossary(getActiveGlossary());
        wordsRepository.deleteById(wordId);
        return true;
    }
}
