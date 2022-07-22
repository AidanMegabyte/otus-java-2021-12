package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.SurveyRepository;
import ru.otus.dao.SurveyTemplateRepository;
import ru.otus.model.document.SurveyTemplate;
import ru.otus.model.dto.SurveyFullDto;
import ru.otus.model.dto.SurveyRequest;
import ru.otus.model.entity.Survey;
import ru.otus.service.SurveyService;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {

    private final SurveyRepository surveyRepository;

    private final SurveyTemplateRepository surveyTemplateRepository;

    private static final List<String> users = Arrays.asList("Вагон Героев", "Тояма Токанава", "Бздашек Западловский");

    @Override
    public List<Survey> getList() {
        return surveyRepository.findAll();
    }

    @Override
    public SurveyFullDto getFull(long id) {
        var survey = surveyRepository.findById(id).orElseThrow();
        return SurveyFullDto.builder()
                .id(survey.getId())
                .name(survey.getName())
                .dateTimeCreated(survey.getDateTimeCreated())
                .userCreated(survey.getUserCreated())
                .template(surveyTemplateRepository.findById(survey.getId()).orElseThrow())
                .build();
    }

    @Override
    public SurveyTemplate getTemplate(long id) {
        return surveyTemplateRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public SurveyFullDto save(SurveyRequest surveyRequest) {

        var id = surveyRequest.getId();

        var survey = id != null ? surveyRepository.findById(id).orElseThrow() : new Survey();
        survey.setName(surveyRequest.getName());
        if (id == null) {
            survey.setUserCreated(getRandomUser());
        }
        survey = surveyRepository.save(survey);

        var surveyTemplate = surveyRequest.getTemplate();
        surveyTemplate.setId(survey.getId());
        surveyTemplate = surveyTemplateRepository.save(surveyTemplate);

        return SurveyFullDto.builder()
                .id(survey.getId())
                .name(survey.getName())
                .dateTimeCreated(survey.getDateTimeCreated())
                .userCreated(survey.getUserCreated())
                .template(surveyTemplate)
                .build();
    }

    @Override
    @Transactional
    public void delete(long id) {
        surveyRepository.deleteById(id);
        surveyTemplateRepository.deleteById(id);
    }

    private String getRandomUser() {
        var idx = new Random().nextInt(users.size());
        return users.get(idx);
    }
}
