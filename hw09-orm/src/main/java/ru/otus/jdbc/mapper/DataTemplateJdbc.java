package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.exception.DataTemplateException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;

    private final EntitySQLMetaData entitySQLMetaData;

    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        var sql = entitySQLMetaData.getSelectByIdSql();
        return dbExecutor.executeSelect(connection, sql, List.of(id), resultSet -> {
            try {
                if (resultSet.next()) {
                    return mapRow(resultSet);
                }
                resultSet.close();
                return null;
            } catch (Exception e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        var sql = entitySQLMetaData.getSelectAllSql();
        return dbExecutor.executeSelect(connection, sql, Collections.emptyList(), resultSet -> {
            var result = new ArrayList<T>();
            try {
                while (resultSet.next()) {
                    result.add(mapRow(resultSet));
                }
                resultSet.close();
                return result;
            } catch (Exception e) {
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T object) {
        try {
            var sql = entitySQLMetaData.getInsertSql();
            var params = extractSqlParametersForEditing(object, false);
            return dbExecutor.executeStatement(connection, sql, params);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T object) {
        try {
            var sql = entitySQLMetaData.getUpdateSql();
            var params = extractSqlParametersForEditing(object, true);
            dbExecutor.executeStatement(connection, sql, params);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private T mapRow(ResultSet resultSet) throws InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {

        var result = entityClassMetaData.getConstructor().newInstance();

        var fields = entityClassMetaData.getAllFields();
        for (Field field : fields) {
            var fieldName = field.getName();
            var isAccessible = field.canAccess(result);
            field.setAccessible(true);
            field.set(result, resultSet.getObject(fieldName));
            if (!isAccessible) {
                field.setAccessible(false);
            }
        }

        return result;
    }

    private List<Object> extractSqlParametersForEditing(T object, boolean updating) throws IllegalAccessException {

        var result = new ArrayList<>();

        var fields = entityClassMetaData.getFieldsWithoutId();
        if (updating) {
            fields.add(entityClassMetaData.getIdField());
        }

        for (Field field : fields) {
            var isAccessible = field.canAccess(object);
            field.setAccessible(true);
            result.add(field.get(object));
            if (!isAccessible) {
                field.setAccessible(false);
            }
        }

        return result;
    }
}
