package ru.otus.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
}
