package ru.otus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.otus.core.repository.executor.DbExecutorImpl;
import ru.otus.exception.DataTemplateException;
import ru.otus.jdbc.mapper.DataTemplateJdbc;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;
import ru.otus.jdbc.mapper.impl.EntityClassMetaDataImpl;
import ru.otus.jdbc.mapper.impl.EntitySQLMetaDataImpl;
import ru.otus.model.TestModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Testcontainers
public class DataTemplateJdbcTests {

    private final EntityClassMetaData<TestModel> entityClassMetaData =
            new EntityClassMetaDataImpl<>(TestModel.class);

    private final EntitySQLMetaData entitySQLMetaData =
            new EntitySQLMetaDataImpl(entityClassMetaData);

    private final DataTemplateJdbc<TestModel> dataTemplate =
            new DataTemplateJdbc<>(new DbExecutorImpl(), entitySQLMetaData, entityClassMetaData);

    @Container
    private final PostgreSQLContainer<?> postgresqlContainer =
            new PostgreSQLContainer<>("postgres:12-alpine")
                    .withDatabaseName("testDataBase")
                    .withUsername("owner")
                    .withPassword("secret")
                    .withClasspathResourceMapping(
                            "00_createTables.sql",
                            "/docker-entrypoint-initdb.d/00_createTables.sql",
                            BindMode.READ_ONLY
                    )
                    .withClasspathResourceMapping(
                            "01_insertData.sql",
                            "/docker-entrypoint-initdb.d/01_insertData.sql",
                            BindMode.READ_ONLY
                    );

    @Test
    @DisplayName("Проверка поиска по ID")
    public void testFindById() throws SQLException {
        var testModelExpected = new TestModel(1, true, "name1", 1.11);
        var testModelActualOpt = dataTemplate.findById(makeConnection(), testModelExpected.getA());
        assertThat(testModelActualOpt).isPresent();
        assertThat(testModelActualOpt.get()).isEqualTo(testModelExpected);
    }

    @Test
    @DisplayName("Проверка поиска по ID - запись отсутствует")
    public void testFindByIdNotFound() throws SQLException {
        var testModelActualOpt = dataTemplate.findById(makeConnection(), Long.MAX_VALUE);
        assertThat(testModelActualOpt).isNotPresent();
    }

    @Test
    @DisplayName("Проверка получения всех записей")
    public void testFindAll() throws SQLException {
        var testModelsExpected = List.of(
                new TestModel(1, true, "name1", 1.11),
                new TestModel(2, false, "name2", 2.22),
                new TestModel(3, true, "name3", 3.33),
                new TestModel(4, false, "name4", 4.44)
        );
        var testModelsActual = dataTemplate.findAll(makeConnection());
        assertThat(testModelsActual).isEqualTo(testModelsExpected);
    }

    @Test
    @DisplayName("Проверка вставки")
    public void testInsert() throws SQLException {
        var connection = makeConnection();
        var idExpected = 5;
        var testModel = new TestModel(0, true, "name5", 5.55);
        var testModelExpected = new TestModel(idExpected, testModel.isB(), testModel.getC(), testModel.getD());
        var idActual = dataTemplate.insert(connection, testModel);
        var testModelActualOpt = dataTemplate.findById(makeConnection(), idExpected);
        assertThat(idActual).isEqualTo(idExpected);
        assertThat(testModelActualOpt).isPresent();
        assertThat(testModelActualOpt.get()).isEqualTo(testModelExpected);
    }

    @Test
    @DisplayName("Проверка вставки - пустое поле \"name\"")
    public void testInsertEmptyName() throws SQLException {
        var connection = makeConnection();
        var sizeExpected = dataTemplate.findAll(connection).size();
        var testModel = new TestModel(0, true, null, 5.55);
        assertThatThrownBy(() -> dataTemplate.insert(connection, testModel))
                .isInstanceOf(DataTemplateException.class);
        assertThat(dataTemplate.findAll(connection).size()).isEqualTo(sizeExpected);
    }

    @Test
    @DisplayName("Проверка обновления")
    public void testUpdate() throws SQLException {
        var testModelExpected = new TestModel(4, false, "abyrvalg", 4.44);
        dataTemplate.update(makeConnection(), testModelExpected);
        var testModelActualOpt = dataTemplate.findById(makeConnection(), testModelExpected.getA());
        assertThat(testModelActualOpt).isPresent();
        assertThat(testModelActualOpt.get()).isEqualTo(testModelExpected);
    }

    @Test
    @DisplayName("Проверка обновления - пустое поле \"name\"")
    public void testUpdateEmptyName() throws SQLException {
        var connection = makeConnection();
        var testModel = new TestModel(4, false, null, 4.44);
        var testModelExpected = new TestModel(testModel.getA(), testModel.isB(), "name4", testModel.getD());
        assertThatThrownBy(() -> dataTemplate.update(connection, testModel))
                .isInstanceOf(DataTemplateException.class);
        assertThat(dataTemplate.findById(connection, testModel.getA()).get()).isEqualTo(testModelExpected);
    }

    private Properties getConnectionProperties() {
        Properties props = new Properties();
        props.setProperty("user", postgresqlContainer.getUsername());
        props.setProperty("password", postgresqlContainer.getPassword());
        props.setProperty("ssl", "false");
        return props;
    }

    private Connection makeConnection() throws SQLException {
        Connection connection =
                DriverManager.getConnection(postgresqlContainer.getJdbcUrl(), getConnectionProperties());
        connection.setAutoCommit(true);
        return connection;
    }
}
