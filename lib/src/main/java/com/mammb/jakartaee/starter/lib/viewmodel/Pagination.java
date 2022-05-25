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
package com.mammb.jakartaee.starter.lib.viewmodel;

import com.mammb.jakartaee.starter.lib.page.Page;
import com.mammb.jakartaee.starter.lib.page.Slice;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface Pagination {

    int MAX_LINKS = 9;

    Slice<?> getSlice();

    default int getCurrentPage() {
        return getSlice().getNumber();
    }

    default boolean isFirstPageEnable() {
        return getCurrentPage() > 0;
    }

    default boolean isLastPageEnable() {
        return Objects.nonNull(getTotalPages()) && getTotalPages() > getCurrentPage() + 1;
    }

    default Integer getTotalPages() {
        if (getSlice() instanceof Page) {
            return ((Page<?>) getSlice()).getTotalPages();
        } else {
            return null;
        }
    }

    default int startPages() {
        if (Objects.nonNull(getTotalPages())) {
            var start = Math.max(0, getCurrentPage() - MAX_LINKS / 2);
            if (start + MAX_LINKS > getTotalPages() - 1) {
                return Math.max(0, getTotalPages() - MAX_LINKS);
            } else {
                return start;
            }
        } else {
            return Math.max(0, getCurrentPage() - MAX_LINKS);
        }
    }

    default int endPages() {
        if (Objects.nonNull(getTotalPages())) {
            return Math.min(startPages() + MAX_LINKS, getTotalPages());
        } else {
            return getCurrentPage() + 1;
        }
    };

    default boolean isPervPageEnable() {
        return getSlice().hasPrevious();
    }
    default boolean isNextPageEnable() {
        return getSlice().hasNext();
    }

    default long getElementsTop() {
        return getSlice().getOffset();
    }
    default long getElementsBottom() {
        return getElementsTop() + getSlice().getContent().size();
    }

    default Long getTotalElements() {
        if (getSlice() instanceof Page) {
            return ((Page<?>) getSlice()).getTotalElements();
        } else {
            return null;
        }
    }

    default boolean isTotalPagesEnable() {
        return Objects.nonNull(getTotalPages());
    }

    default boolean isTotalElementsEnable() {
        return Objects.nonNull(getTotalElements());
    }

    default List<Integer> getLinks() {
        return IntStream.range(startPages(), endPages()).boxed().collect(Collectors.toList());
    }

}
