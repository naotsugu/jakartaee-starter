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

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

@StaticMetamodel(BaseEntity.class)
public abstract class BaseEntity_ {

    public static volatile SingularAttribute<BaseEntity<?, ?>, Long> version;
    public static volatile SingularAttribute<BaseEntity<?, ?>, LocalDateTime> createdOn;
    public static volatile SingularAttribute<BaseEntity<?, ?>, LocalDateTime> lastModifiedOn;

    public static final String VERSION = "version";
    public static final String CREATED_ON = "createdOn";
    public static final String LAST_MODIFIED_ON = "lastModifiedOn";

}
