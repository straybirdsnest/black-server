package com.example.config;

import org.hibernate.cfg.ImprovedNamingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CustomNamingStrategy extends ImprovedNamingStrategy {
    public static final String SPLIT_REGEX = "(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])";
    static final Logger log = LoggerFactory.getLogger(CustomNamingStrategy.class);

    /**
     * 将驼峰法命名的类名 MyEntityClass，改成 T_MY_ENTITY_CLASS 这样的表名
     *
     * @param className
     * @return
     */
    @Override
    public String classToTableName(String className) {
        String[] words = className.split(SPLIT_REGEX);
        String tableName = "T_" + Arrays.stream(words).map(String::toUpperCase)
                .collect(Collectors.joining("_"));
        log.debug(tableName);
        return tableName;
    }

//    @Override
//    public String joinKeyColumnName(String joinedColumn, String joinedTable) {
//        String name = super.joinKeyColumnName(joinedColumn, joinedTable);
//        log.debug("Join Key Column Name: " + name);
//        return name;
//    }
//
//    @Override
//    public String foreignKeyColumnName(String propertyName, String propertyEntityName, String propertyTableName, String referencedColumnName) {
//        String name = super.foreignKeyColumnName(propertyName, propertyEntityName, propertyTableName, referencedColumnName);
//        log.debug("Foreign Key Column Name: " + name);
//        return "FK_" + name;
//    }
//
//    @Override
//    public String logicalCollectionTableName(String tableName, String ownerEntityTable, String associatedEntityTable, String propertyName) {
//        String name = super.logicalCollectionTableName(tableName, ownerEntityTable, associatedEntityTable, propertyName);
//        log.debug("Logical Collection Table Name: " + name);
//        return name;
//    }
//
//    @Override
//    public String propertyToColumnName(String propertyName) {
//        String name = super.propertyToColumnName(propertyName);
//        log.debug("Property to column name: " + name);
//        return name;
//    }
//
//    @Override
//    public String tableName(String tableName) {
//        String name = super.tableName(tableName);
//        log.debug("Table name: " + name);
//        return name;
//    }
//
//    @Override
//    public String columnName(String columnName) {
//        String name = super.columnName(columnName);
//        log.debug("Column name: " + name);
//        return name;
//    }
//
//    @Override
//    public String logicalColumnName(String columnName, String propertyName) {
//        String name = super.logicalColumnName(columnName, propertyName);
//        log.debug("Logical column name: " + name);
//        return name;
//    }
//
//    @Override
//    public String logicalCollectionColumnName(String columnName, String propertyName, String referencedColumn) {
//        String name = super.logicalCollectionColumnName(columnName, propertyName, referencedColumn);
//        log.debug("Logical collection column name: " + name);
//        return name;
//    }
//
//    @Override
//    public String collectionTableName(String ownerEntity, String ownerEntityTable, String associatedEntity, String associatedEntityTable, String propertyName) {
//        String name = super.collectionTableName(ownerEntity, ownerEntityTable, associatedEntity, associatedEntityTable, propertyName);
//        log.debug("Collection table name: " + name);
//        return name;
//    }
}
