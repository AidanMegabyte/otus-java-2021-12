package ru.otus.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.model.document.SurveyTemplate;

public interface SurveyTemplateRepository extends MongoRepository<SurveyTemplate, Long> {
}
