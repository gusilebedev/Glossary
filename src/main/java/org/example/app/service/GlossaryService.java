package org.example.app.service;

import lombok.Getter;
import org.example.app.service.dao.GlossaryEntity;
import org.example.app.service.dao.WordEntity;
import org.example.app.service.dao.WordId;
import org.example.web.dto.GlossaryDto;
import org.example.web.dto.WordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Getter
@Service
@Transactional
public class GlossaryService {

    @Autowired
    GlossariesRepository glossariesRepository;

    @Autowired
    WordsRepository wordsRepository;

    public GlossaryService(GlossariesRepository glossariesRepository, WordsRepository wordsRepository) {
        this.glossariesRepository = glossariesRepository;
        this.wordsRepository = wordsRepository;
    }


    public boolean saveGlossary(GlossaryDto glossaryDto) {
        GlossaryEntity glossaryEntity = new GlossaryEntity();
        glossaryEntity.setName(glossaryDto.getName().trim().toLowerCase());
        glossaryEntity.setRegex(glossaryDto.getRegex().trim());
        glossariesRepository.save(glossaryEntity);
        return true;
    }

    public List<GlossaryDto> listAllGlossaries() {
        List <GlossaryDto> glossaries = new ArrayList<>();
        glossariesRepository.findAll().forEach(glossaryEntity -> {
            glossaries.add(new GlossaryDto().builder()
                        .name(glossaryEntity.getName())
                        .regex(glossaryEntity.getRegex()).build());
        });
        return glossaries;
    }

    public GlossaryEntity getGlossary(String glossaryToView) {
        return glossariesRepository.findById(glossaryToView.trim().toLowerCase()).get();
    }

    public GlossaryDto getGlossaryDto(String glossary) {
        return new GlossaryDto().builder()
                .name(glossariesRepository.findById(glossary).get().getName())
                .regex(glossariesRepository.findById(glossary).get().getRegex()).build();
    }

    public boolean deleteGlossary(GlossaryDto glossaryDto) {
        glossariesRepository.deleteById(glossaryDto.getName().trim().toLowerCase());
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

    public List<WordDto> listAllWords() {
        List <WordDto> words = new ArrayList<>();
        wordsRepository.findAll().forEach(wordEntity -> {
            words.add(new WordDto().builder()
                    .name(wordEntity.getId().getName())
                    .value(wordEntity.getValue())
                    .glossary(wordEntity.getId().getGlossary().getName()).build());
        });
        return words;
    }

    public WordDto getWordDto(WordDto wordDto) {
        WordId wordId = new WordId();
        wordId.setName(wordDto.getName().trim().toLowerCase());
        wordId.setGlossary(getGlossary(wordDto.getGlossary().trim().toLowerCase()));
        return WordDto.builder()
                .name(wordsRepository.findById(wordId).get().getId().getName())
                .value(wordsRepository.findById(wordId).get().getValue())
                .glossary(wordsRepository.findById(wordId).get().getId().getGlossary().getName()).build();
    }

    public boolean deleteWord(WordDto wordDto) {
        WordId wordId = new WordId();
        wordId.setName(wordDto.getName().trim().toLowerCase());
        wordId.setGlossary(getGlossary(wordDto.getGlossary().trim().toLowerCase()));
        wordsRepository.deleteById(wordId);
        return true;
    }
}
