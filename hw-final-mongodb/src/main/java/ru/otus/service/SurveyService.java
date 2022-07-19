package ru.otus.service;

import ru.otus.model.dto.SurveyDto;
import ru.otus.model.dto.SurveyRequest;

import java.util.List;

public interface SurveyService {

    List<SurveyDto> getList();

    SurveyDto get(long id);

    SurveyDto save(Long id, SurveyRequest surveyRequest);

    void delete(long id);
}
