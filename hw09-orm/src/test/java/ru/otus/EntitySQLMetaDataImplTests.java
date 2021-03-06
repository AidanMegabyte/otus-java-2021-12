package ru.otus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;
import ru.otus.jdbc.mapper.impl.EntityClassMetaDataImpl;
import ru.otus.jdbc.mapper.impl.EntitySQLMetaDataImpl;
import ru.otus.model.TestModel;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class EntitySQLMetaDataImplTests {

    @Test
    @DisplayName("Проверка корректности формирования SQL-запросов")
    public void testSqlQueriesValidity() {

        EntityClassMetaData<TestModel> entityClassMetaData = new EntityClassMetaDataImpl<>(TestModel.class);
        EntitySQLMetaData entitySQLMetaData = new EntitySQLMetaDataImpl(entityClassMetaData);
        var selectAllSql = "select * from testmodel";
        var selectByIdSql = "select * from testmodel where a = ?";
        var insertSql = "insert into testmodel(b, c, d) values(?, ?, ?)";
        var updateSql = "update testmodel set b = ?, c = ?, d = ? where a = ?";

        assertThat(entitySQLMetaData.getSelectAllSql()).isEqualTo(selectAllSql);
        assertThat(entitySQLMetaData.getSelectByIdSql()).isEqualTo(selectByIdSql);
        assertThat(entitySQLMetaData.getInsertSql()).isEqualTo(insertSql);
        assertThat(entitySQLMetaData.getUpdateSql()).isEqualTo(updateSql);
    }

    @Test
    @DisplayName("Проверка обработки отсутствия метаданных")
    public void testNoEntitySqlMetaDataError() {
        assertThatThrownBy(() -> new EntitySQLMetaDataImpl(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
