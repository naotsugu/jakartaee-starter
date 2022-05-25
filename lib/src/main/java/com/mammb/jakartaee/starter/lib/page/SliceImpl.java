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

import java.util.Collections;
import java.util.List;

public class SliceImpl<T> implements Slice<T> {

    private final List<T> content;

    private final boolean hasNext;

    private final SlicePoint slicePoint;

    SliceImpl(List<T> content, boolean hasNext, SlicePoint slicePoint) {
        this.content = content;
        this.slicePoint = slicePoint;
        this.hasNext = hasNext;
    }

    @Override
    public List<T> getContent() {
        return Collections.unmodifiableList(content);
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public int getNumber() {
        return slicePoint.getNumber();
    }

    @Override
    public int getSize() {
        return slicePoint.getSize();
    }
}
