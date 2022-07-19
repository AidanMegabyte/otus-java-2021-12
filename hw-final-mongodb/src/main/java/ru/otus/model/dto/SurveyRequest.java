package ru.otus.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nonnull;

@Getter
@Setter
public class SurveyRequest {

    @Nonnull
    private String name;
}
