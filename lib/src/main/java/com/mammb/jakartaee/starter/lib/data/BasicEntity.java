package com.mammb.jakartaee.starter.lib.data;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

import java.io.Serializable;
import java.util.Objects;

@MappedSuperclass
public abstract class BasicEntity<T> implements Identifiable<Long>, Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    @Column(nullable = false)
    private Long version;

    @Override
    public Long getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return String.format("Entity[%s]-id[%s]", this.getClass().getName(), getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicEntity<?> that = (BasicEntity<?>) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
