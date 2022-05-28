package com.mammb.jakartaee.starter.app.example.entitymeta;

import jakarta.persistence.metamodel.EntityType;

import java.util.List;

public class Table {

    public final String name;
    public final String javaTypeName;
    public final List<Column> columns;

    public Table(EntityType<?> entity) {
        this.name = entity.getName();
        this.javaTypeName = entity.getJavaType().getName();
        this.columns = entity.getAttributes().stream().map(Column::new).toList();
    }

}
