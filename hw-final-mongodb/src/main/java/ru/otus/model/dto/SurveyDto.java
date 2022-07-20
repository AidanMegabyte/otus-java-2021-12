package ru.otus.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.otus.model.document.SurveyTemplate;

import javax.annotation.Nonnull;
import java.util.Date;

@Getter
@Setter
@Builder
public class SurveyDto {

    private long id;

    @Nonnull
    private String name;

    @Nonnull
    private Date dateTimeCreated;

    @Nonnull
    private String userCreated;

    @Nonnull
    private SurveyTemplate template;
}
