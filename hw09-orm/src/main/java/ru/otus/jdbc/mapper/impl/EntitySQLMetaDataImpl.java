package ru.otus.jdbc.mapper.impl;

import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData<?> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        if (entityClassMetaData == null) {
            throw new IllegalArgumentException("Entity meta cannot be null!");
        }
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return String.format(SqlQueryFormats.SELECT_ALL_SQL_FORMAT, entityClassMetaData.getName().toLowerCase());
    }

    @Override
    public String getSelectByIdSql() {
        var idFieldName = entityClassMetaData.getIdField().getName();
        return String.format(
                SqlQueryFormats.SELECT_BY_ID_SQL_FORMAT,
                entityClassMetaData.getName().toLowerCase(),
                idFieldName,
                idFieldName
        );
    }

    @Override
    public String getInsertSql() {
        var fieldNames = entityClassMetaData.getFieldsWithoutId().stream()
                .map(Field::getName)
                .sorted()
                .toList();
        var params = fieldNames.stream()
                .map(fieldName -> String.format(":%s", fieldName))
                .toList();
        return String.format(
                SqlQueryFormats.INSERT_SQL_FORMAT,
                entityClassMetaData.getName().toLowerCase(),
                String.join(", ", fieldNames),
                String.join(", ", params)
        );
    }

    @Override
    public String getUpdateSql() {
        var fieldsSetPartSql = entityClassMetaData.getFieldsWithoutId().stream()
                .map(field -> {
                    var fieldName = field.getName();
                    return String.format("%s = :%s", fieldName, fieldName);
                })
                .sorted()
                .collect(Collectors.joining(", "));
        var idFieldName = entityClassMetaData.getIdField().getName();
        return String.format(
                SqlQueryFormats.UPDATE_SQL_FORMAT,
                entityClassMetaData.getName().toLowerCase(),
                fieldsSetPartSql,
                idFieldName,
                idFieldName
        );
    }

    private static class SqlQueryFormats {
        public static final String SELECT_ALL_SQL_FORMAT = "select * from %s";
        public static final String SELECT_BY_ID_SQL_FORMAT = "select * from %s where %s = :%s";
        public static final String INSERT_SQL_FORMAT = "insert into %s(%s) values (%s)";
        public static final String UPDATE_SQL_FORMAT = "update %s set %s where %s = :%s";
    }
}
