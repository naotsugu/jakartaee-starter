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

import java.util.Objects;

public class SliceRequestImpl<T> implements SliceRequest<T> {

    private final int number;
    private final int size;
    private final SortSpec<T> sortSpec;

    SliceRequestImpl(int number, int size, SortSpec<T> sortSpec) {
        if (number < 0) {
            throw new IllegalArgumentException("Page number must not be less than zero!");
        }
        if (size < 1) {
            throw new IllegalArgumentException("Page size must not be less than one!");
        }
        this.number = number;
        this.size = size;
        this.sortSpec = Objects.isNull(sortSpec) ? SortSpec.empty() : sortSpec;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public SortSpec<T> getSortSpec() {
        return sortSpec;
    }
}
