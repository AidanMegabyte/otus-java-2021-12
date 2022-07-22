package ru.otus;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.otus.dao.SurveyRepository;
import ru.otus.dao.SurveyTemplateRepository;
import ru.otus.model.common.QuestionType;
import ru.otus.model.document.Answer;
import ru.otus.model.document.Question;
import ru.otus.model.document.SurveyTemplate;
import ru.otus.model.dto.SurveyRequest;
import ru.otus.service.SurveyService;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@Testcontainers
public class SurveyServiceTests {

    @Container
    private static final PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:12")
            .withDatabaseName("testDb")
            .withUsername("testUser")
            .withPassword("testPassword");

    @Container
    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0.9");

    @TestConfiguration
    static class DbConfiguration {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder
                    .create()
                    .url(postgresqlContainer.getJdbcUrl())
                    .username(postgresqlContainer.getUsername())
                    .password(postgresqlContainer.getPassword())
                    .build();
        }

        @Bean
        public MongoClient mongo() {
            return MongoClients.create(mongoDBContainer.getConnectionString());
        }
    }

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private SurveyTemplateRepository surveyTemplateRepository;

    @AfterEach
    public void tearDown() {
        surveyRepository.deleteAll();
        surveyTemplateRepository.deleteAll();
    }

    @Test
    @DisplayName("Создание опроса")
    public void testCreateSurvey() {
        var template = createSurveyTemplate();
        var request = createSurveyRequest("абырвалг", template);
        var survey = surveyService.save(request);
        assertThat(surveyRepository.count()).isEqualTo(1);
        assertThat(surveyTemplateRepository.count()).isEqualTo(1);
        assertThat(survey.getId()).isEqualTo(survey.getTemplate().getId());
    }

    @Test
    @DisplayName("Изменение опроса")
    public void testEditSurvey() {
        var newName = "главрыба";
        var newQuestionRequired = false;
        var template = createSurveyTemplate();
        var request = createSurveyRequest("абырвалг", template);
        var survey = surveyService.save(request);
        template.getQuestions().get(0).setRequired(newQuestionRequired);
        request.setId(survey.getId());
        request.setName(newName);
        survey = surveyService.save(request);
        assertThat(surveyRepository.count()).isEqualTo(1);
        assertThat(surveyTemplateRepository.count()).isEqualTo(1);
        assertThat(survey.getId()).isEqualTo(survey.getTemplate().getId());
        assertThat(survey.getName()).isEqualTo(newName);
        assertThat(survey.getTemplate().getQuestions().get(0).isRequired()).isEqualTo(newQuestionRequired);
    }

    @Test
    @DisplayName("Удаление опроса")
    public void testDeleteSurvey() {
        var template = createSurveyTemplate();
        var request = createSurveyRequest("абырвалг", template);
        var survey = surveyService.save(request);
        surveyService.delete(survey.getId());
        assertThat(surveyRepository.count()).isEqualTo(0);
        assertThat(surveyTemplateRepository.count()).isEqualTo(0);
    }

    @Test
    @DisplayName("Откат \"сквозной\" транзакции: ошибка при сохранении в Postgresql")
    public void testRollbackIfPostgresqlSaveError() {
        try {
            surveyService.save(createSurveyRequest(null, createSurveyTemplate()));
        } catch (DataIntegrityViolationException e) {
            assertThat(surveyRepository.count()).isEqualTo(0);
            assertThat(surveyTemplateRepository.count()).isEqualTo(0);
        }
    }

    @Test
    @DisplayName("Откат \"сквозной\" транзакции: ошибка при сохранении в MongoDB")
    public void testRollbackIfMongoDbSaveError() {
        try {
            surveyService.save(createSurveyRequest("абырвалг", null));
        } catch (NullPointerException e) {
            assertThat(surveyRepository.count()).isEqualTo(0);
            assertThat(surveyTemplateRepository.count()).isEqualTo(0);
        }
    }

    private SurveyRequest createSurveyRequest(String name, SurveyTemplate template) {

        var result = new SurveyRequest();

        Optional.ofNullable(name).ifPresent(result::setName);
        Optional.ofNullable(template).ifPresent(result::setTemplate);

        return result;
    }

    private SurveyTemplate createSurveyTemplate() {

        var result = new SurveyTemplate();

        result.setTitle("ахалай");
        result.setSubtitle("махалай");
        result.setQuestions(List.of(new Question(
                QuestionType.RADIO,
                "ляськи",
                true,
                List.of(new Answer("масяськи", QuestionType.RADIO))
        )));

        return result;
    }
}
