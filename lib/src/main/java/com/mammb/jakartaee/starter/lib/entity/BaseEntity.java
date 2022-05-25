/*
 * Copyright 2019-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mammb.jakartaee.starter.lib.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Version;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
public abstract class BaseEntity<PK, T> implements Identifiable<PK>, Serializable {

    @Version
    @Column(nullable = false)
    private Long version;

    private LocalDateTime createdOn;

    private LocalDateTime lastModifiedOn;


    @PrePersist
    public void prePersistBase() {
        createdOn = LocalDateTime.now();
        lastModifiedOn = createdOn;
    }

    @PreUpdate
    public void preUpdateBase() {
        lastModifiedOn = LocalDateTime.now();
    }

    @Override
    abstract public PK getId();

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public LocalDateTime getLastModifiedOn() {
        return lastModifiedOn;
    }

    public Long getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return String.format("Entity[%s, %s, %s]",
            this.getClass().getName(),
            Objects.isNull(getId()) ? null : getId().toString(),
            getVersion());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity<?, ?> that = (BaseEntity<?, ?>) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
