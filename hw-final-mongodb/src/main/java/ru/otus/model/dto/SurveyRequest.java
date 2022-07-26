package ru.otus.model.dto;

import lombok.*;
import ru.otus.model.document.SurveyTemplate;

import javax.annotation.Nonnull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyRequest {

    private Long id;

    @Nonnull
    private String name;

    @Nonnull
    private SurveyTemplate template;
}
