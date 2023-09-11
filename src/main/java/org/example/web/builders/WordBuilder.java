package org.example.web.builders;

import org.example.app.service.dao.WordEntity;
import org.example.web.dto.WordDto;
import org.springframework.stereotype.Component;

@Component
public class WordBuilder {
    public WordDto build(WordEntity wordEntity) {
        return WordDto.builder()
                .name(wordEntity.getId().getName())
                .value(wordEntity.getValue())
                .glossary(wordEntity.getId().getGlossary().getName())
                .build();
    }
}
