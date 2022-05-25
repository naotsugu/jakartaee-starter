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

import com.mammb.jakartaee.starter.lib.page.Slice;

import java.util.List;
import java.util.function.Function;

public class PageDataModel<T> implements LoadablePagination {

    private Slice<T> slice;

    private Function<Integer, Slice<T>> loader;

    protected PageDataModel(Function<Integer, Slice<T>> loader) {
        this.loader = loader;
        load(0);
    }

    public static <T> PageDataModel<T> of(Function<Integer, Slice<T>> loader) {
        return new PageDataModel<>(loader);
    }

    @Override
    public void load(int pageIndex) {
        this.slice = loader.apply(pageIndex);
    }

    public List<T> getContent() {
        return slice.getContent();
    }

    @Override
    public Slice<T> getSlice() {
        return slice;
    }

}
