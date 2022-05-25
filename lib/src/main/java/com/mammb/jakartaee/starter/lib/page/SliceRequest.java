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

import com.mammb.jakartaee.starter.lib.sort.SortSpec;
import com.mammb.jakartaee.starter.lib.sort.Sortable;

public interface SliceRequest<T> extends SlicePoint, Sortable<T> {

    static <T> SliceRequestImpl<T> of(int number, int size, SortSpec<T> sortSpec) {
        return new SliceRequestImpl<>(number, size, sortSpec);
    }

    static <T> SliceRequestImpl<T> of(int number, int size) {
        return new SliceRequestImpl<>(number, size, null);
    }

    static <T> SliceRequestImpl<T> of(int number, SortSpec<T> sortSpec) {
        return new SliceRequestImpl<>(number, 10, sortSpec);
    }

}
