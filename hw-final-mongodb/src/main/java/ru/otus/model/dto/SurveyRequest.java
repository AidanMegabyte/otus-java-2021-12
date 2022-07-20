package ru.otus.model.dto;

import lombok.Getter;
import lombok.Setter;
import ru.otus.model.document.SurveyTemplate;

import javax.annotation.Nonnull;

@Getter
@Setter
public class SurveyRequest {

    @Nonnull
    private String name;

    @Nonnull
    private SurveyTemplate template;
}
