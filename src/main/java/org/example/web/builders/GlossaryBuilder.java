package org.example.web.builders;

import lombok.Builder;
import org.example.app.service.dao.GlossaryEntity;
import org.example.web.dto.GlossaryDto;
import org.springframework.stereotype.Component;

@Component
public class GlossaryBuilder {

    public GlossaryDto build(GlossaryEntity glossaryEntity) {
        return GlossaryDto.builder()
                .name(glossaryEntity.getName())
                .regex(glossaryEntity.getRegexEntity().getRegex())
                .build();
    }
}
