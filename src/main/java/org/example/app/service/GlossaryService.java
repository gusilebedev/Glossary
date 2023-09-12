package org.example.app.service;

import lombok.Getter;
import org.example.app.service.dao.GlossaryEntity;
import org.example.app.service.dao.RegexEntity;
import org.example.app.service.dao.WordEntity;
import org.example.app.service.dao.WordId;
import org.example.web.builders.GlossaryBuilder;
import org.example.web.builders.WordBuilder;
import org.example.web.dto.GlossaryDto;
import org.example.web.dto.WordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Getter
@Service
@Transactional

public class GlossaryService {


    private final GlossariesRepository glossariesRepository;


    private final WordsRepository wordsRepository;


    private final RegexRepository regexRepository;

    public GlossaryService(GlossariesRepository glossariesRepository, WordsRepository wordsRepository, RegexRepository regexRepository) {
        this.glossariesRepository = glossariesRepository;
        this.wordsRepository = wordsRepository;
        this.regexRepository = regexRepository;
    }


    public boolean saveGlossary(GlossaryDto glossaryDto) {
        GlossaryEntity glossaryEntity = new GlossaryEntity();
        glossaryEntity.setName(glossaryDto.getName().trim().toLowerCase());
        RegexEntity regexEntity = new RegexEntity();
        regexEntity.setRegex(glossaryDto.getRegex().trim());
        regexRepository.save(regexEntity);
        glossaryEntity.setRegexEntity(regexEntity);
        glossariesRepository.save(glossaryEntity);
        return true;
    }

    public List<GlossaryDto> listAllGlossaries() {
        List <GlossaryDto> glossaries = new LinkedList<>();
        glossariesRepository.findAll().forEach(glossaryEntity -> {
            glossaries.add(new GlossaryBuilder().build(glossaryEntity));
        });
        return glossaries;
    }

    public GlossaryEntity getGlossary(String glossaryToView) {
        return glossariesRepository.findById(glossaryToView.trim().toLowerCase()).get();
    }

    public GlossaryDto getGlossaryDto(String glossary) {
        return new GlossaryBuilder().build(glossariesRepository.findById(glossary.trim().toLowerCase()).get());
    }

    public boolean deleteGlossary(GlossaryDto glossaryDto) {
        int id = getGlossary(glossaryDto.getName()).getRegexEntity().getId();
        glossariesRepository.deleteById(glossaryDto.getName().trim().toLowerCase());
        regexRepository.deleteById(id);
        return true;
    }

    public boolean saveWord(WordDto wordDto) {
        WordId wordId = new WordId();
        wordId.setName(wordDto.getName().trim().toLowerCase());
        wordId.setGlossary(getGlossary(wordDto.getGlossary().trim().toLowerCase()));
        WordEntity wordEntity = new WordEntity();
        wordEntity.setId(wordId);
        wordEntity.setValue(wordDto.getValue().trim().toLowerCase());
        wordsRepository.save(wordEntity);
        return true;
    }

    public List<WordDto> listAllWords(String glossary) {
        List <WordDto> words = new LinkedList<>();
        wordsRepository.findAll().forEach(wordEntity -> {
            if (wordEntity.getId().getGlossary().getName().equals(glossary)) {
                words.add(new WordBuilder().build(wordEntity));
            }
        });
        return words;
    }

    public WordDto getWordDto(WordDto wordDto) {
        WordId wordId = new WordId();
        wordId.setName(wordDto.getName().trim().toLowerCase());
        wordId.setGlossary(getGlossary(wordDto.getGlossary().trim().toLowerCase()));
        return new WordBuilder().build(wordsRepository.findById(wordId).get());
    }

    public boolean deleteWord(WordDto wordDto) {
        WordId wordId = new WordId();
        wordId.setName(wordDto.getName().trim().toLowerCase());
        wordId.setGlossary(getGlossary(wordDto.getGlossary().trim().toLowerCase()));
        wordsRepository.deleteById(wordId);
        return true;
    }
}
