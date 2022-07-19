package ru.otus.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.model.entity.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
}
