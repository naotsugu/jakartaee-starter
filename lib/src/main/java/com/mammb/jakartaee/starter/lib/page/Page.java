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
package com.mammb.jakartaee.starter.lib.page;

import java.util.List;

public interface Page<T> extends Slice<T> {

    long getTotalElements();

    default int getTotalPages() {
        return getSize() == 0 ? 1 : (int) Math.ceil((double) getTotalElements() / (double) getSize());
    }

    default boolean hasNext() {
        return getNumber() + 1 < getTotalPages();
    }

    static <T> Page<T> of(List<T> content, long totalElements, SlicePoint slicePoint) {

        return new Page<>() {

            private final List<T> list = List.copyOf(content);

            @Override
            public long getTotalElements() {
                return totalElements;
            }

            @Override
            public List<T> getContent() {
                return list;
            }

            @Override
            public int getNumber() {
                return slicePoint.getNumber();
            }

            @Override
            public int getSize() {
                return slicePoint.getSize();
            }

        };
    }
}
