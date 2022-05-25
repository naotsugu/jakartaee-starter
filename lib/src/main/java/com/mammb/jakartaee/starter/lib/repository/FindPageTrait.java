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

import com.mammb.jakartaee.starter.lib.page.Page;
import com.mammb.jakartaee.starter.lib.page.SliceRequest;
import com.mammb.jakartaee.starter.lib.criteria.Specification;
import jakarta.persistence.TypedQuery;

import java.io.Serializable;
import java.util.List;

public interface FindPageTrait<PK extends Serializable, T> extends EntityContext<PK, T> {

    default Page<T> findPage(SliceRequest<T> request, Specification<T> spec) {

        long count = executeCountQuery(Queries.getCountQuery(this, spec));
        if (count == 0) {
            return Page.of(List.of(), 0, request);
        }

        TypedQuery<T> query = Queries.getQuery(this, spec, request.getSortSpec());
        query.setFirstResult((int) request.getOffset());
        query.setMaxResults(request.getSize());
        return Page.of(query.getResultList(), count, request);
    }

    private static long executeCountQuery(TypedQuery<Long> query) {
        List<Long> totals = query.getResultList();
        long total = 0L;
        for (Long element : totals) {
            total += element == null ? 0 : element;
        }
        return total;
    }
}
