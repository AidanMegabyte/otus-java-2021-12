package ru.otus.model.dto;

import lombok.*;
import ru.otus.model.document.SurveyTemplate;

import javax.annotation.Nonnull;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyFullDto {

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
