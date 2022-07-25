package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.SurveyRepository;
import ru.otus.dao.SurveyTemplateRepository;
import ru.otus.exception.SurveyNotFoundException;
import ru.otus.model.document.SurveyTemplate;
import ru.otus.model.dto.SurveyFullDto;
import ru.otus.model.dto.SurveyRequest;
import ru.otus.model.entity.Survey;
import ru.otus.service.SurveyService;
import ru.otus.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {

    private final SurveyRepository surveyRepository;

    private final SurveyTemplateRepository surveyTemplateRepository;

    private final UserService userService;

    private static final String SURVEY_NOT_FOUND_ERROR_MESSAGE = "Survey not found!";

    @Override
    public List<Survey> getList() {
        return surveyRepository.findAll();
    }

    @Override
    public SurveyFullDto getFull(long id) {
        var survey = findSurveyById(id);
        var template = findTemplateById(id);
        return mapToFullDto(survey, template);
    }

    @Override
    public SurveyTemplate getTemplate(long id) {
        return findTemplateById(id);
    }

    @Override
    @Transactional
    public SurveyFullDto save(SurveyRequest surveyRequest) {

        var id = surveyRequest.getId();

        var survey = id != null ? findSurveyById(id) : new Survey();
        survey.setName(surveyRequest.getName());
        if (id == null) {
            survey.setUserCreated(userService.getRandomUser());
        }
        survey = surveyRepository.save(survey);

        var template = surveyRequest.getTemplate();
        template.setId(survey.getId());
        template = surveyTemplateRepository.save(template);

        return mapToFullDto(survey, template);
    }

    @Override
    @Transactional
    public void delete(long id) {
        surveyRepository.deleteById(id);
        surveyTemplateRepository.deleteById(id);
    }

    private SurveyFullDto mapToFullDto(Survey survey, SurveyTemplate template) {
        return SurveyFullDto.builder()
                .id(survey.getId())
                .name(survey.getName())
                .dateTimeCreated(survey.getDateTimeCreated())
                .userCreated(survey.getUserCreated())
                .template(template)
                .build();
    }

    private Survey findSurveyById(long id) {
        return findById(() -> surveyRepository.findById(id));
    }

    private SurveyTemplate findTemplateById(long id) {
        return findById(() -> surveyTemplateRepository.findById(id));
    }

    private <T> T findById(Supplier<Optional<T>> supplier) {
        return supplier.get().orElseThrow(() -> new SurveyNotFoundException(SURVEY_NOT_FOUND_ERROR_MESSAGE));
    }
}
