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

import com.mammb.jakartaee.starter.lib.page.SliceRequest;
import com.mammb.jakartaee.starter.lib.page.Slice;
import com.mammb.jakartaee.starter.lib.criteria.Specification;
import jakarta.persistence.TypedQuery;

import java.io.Serializable;
import java.util.List;

public interface FindSliceTrait<PK extends Serializable, T> extends EntityContext<PK, T> {

    default Slice<T> findSlice(SliceRequest<T> request, Specification<T> spec) {

        TypedQuery<T> query = Queries.getQuery(this, spec, request.getSortSpec());
        query.setFirstResult((int) request.getOffset());
        query.setMaxResults(request.getSize() + 1);

        List<T> result = query.getResultList();
        return (result.size() > request.getSize())
            ? Slice.of(result.subList(0, result.size() - 1), true, request)
            : Slice.of(result, false, request);
    }
}
