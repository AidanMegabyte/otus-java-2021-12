package ru.otus.service;

import ru.otus.model.document.SurveyTemplate;
import ru.otus.model.dto.SurveyFullDto;
import ru.otus.model.dto.SurveyRequest;
import ru.otus.model.entity.Survey;

import java.util.List;

public interface SurveyService {

    List<Survey> getList();

    SurveyFullDto getFull(long id);

    SurveyTemplate getTemplate(long id);

    SurveyFullDto save(SurveyRequest surveyRequest);

    void delete(long id);
}
