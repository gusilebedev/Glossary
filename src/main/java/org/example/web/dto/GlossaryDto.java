package org.example.web.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GlossaryDto {
    private String name;
    private String regex;
}
