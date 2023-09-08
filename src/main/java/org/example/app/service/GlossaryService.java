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
        glossaryEntity.setNameGloss(glossaryDto.getName().trim().toLowerCase());
        glossaryEntity.setRegex(glossaryDto.getRegex().trim());
        glossariesRepository.save(glossaryEntity);
        return true;
    }

    public List<GlossaryEntity> listAllGlossaries() {
        return (List<GlossaryEntity>) glossariesRepository.findAll();
    }

    public GlossaryEntity getGlossary(String glossaryToView) {
        return glossariesRepository.findById(glossaryToView.trim().toLowerCase()).get();
    }

    public boolean deleteGlossary(GlossaryDto glossaryDto) {
        glossariesRepository.deleteById(glossaryDto.getName().trim().toLowerCase());
        return true;
    }

    public boolean saveWord(WordDto wordDto) {
        WordId wordId = new WordId();
        wordId.setName(wordDto.getName().trim().toLowerCase());
        wordId.setGlossaryName(getGlossary(wordDto.getGlossaryName().trim().toLowerCase()));
        WordEntity wordEntity = new WordEntity();
        wordEntity.setId(wordId);
        wordEntity.setValue(wordDto.getValue().trim().toLowerCase());
        wordsRepository.save(wordEntity);
        return true;
    }

    public List<WordEntity> listAllWords() {
        return (List<WordEntity>) wordsRepository.findAll();
    }

    public WordEntity getWord(WordDto wordDto) {
        WordId wordId = new WordId();
        wordId.setName(wordDto.getName().trim().toLowerCase());
        wordId.setGlossaryName(getGlossary(wordDto.getGlossaryName().trim().toLowerCase()));
        return wordsRepository.findById(wordId).get();
    }

    public boolean deleteWord(WordDto wordDto) {
        WordId wordId = new WordId();
        wordId.setName(wordDto.getName().trim().toLowerCase());
        wordId.setGlossaryName(getGlossary(wordDto.getGlossaryName().trim().toLowerCase()));
        wordsRepository.deleteById(wordId);
        return true;
    }
}
