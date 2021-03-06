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
package com.mammb.jakartaee.starter.lib.repository;

import com.mammb.jakartaee.starter.lib.entity.Identifiable;

import java.io.Serializable;

public abstract class BaseQueryRepository <PK extends Serializable, T extends Identifiable<PK>> extends AbstractRepository<PK, T>
    implements GetByIdTrait<PK, T>, FindAllTrait<PK, T>, FindPageTrait<PK, T>, FindSliceTrait<PK, T> {

    public BaseQueryRepository(Class<T> entityClass, Class<PK> idClass) {
        super(entityClass, idClass);
    }

}
