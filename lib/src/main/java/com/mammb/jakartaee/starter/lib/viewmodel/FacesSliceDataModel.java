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
import jakarta.faces.model.DataModel;
import jakarta.faces.model.FacesDataModel;
import java.util.Objects;

@FacesDataModel(forClass = Slice.class)
public class FacesSliceDataModel<T> extends DataModel<T> {

    private Slice<T> slice;
    private int rowIndex;

    @Override
    public boolean isRowAvailable() {
        return rowIndex >= 0 && rowIndex < getRowCount();
    }

    @Override
    public int getRowCount() {
        return Objects.isNull(slice) ? 0 : slice.getContent().size();
    }

    @Override
    public T getRowData() {
        return Objects.isNull(slice) ? null : slice.getContent().get(rowIndex);
    }

    @Override
    public int getRowIndex() {
        return rowIndex;
    }

    @Override
    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    @Override
    public Slice<T> getWrappedData() {
        return slice;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setWrappedData(Object data) {
        if (data instanceof Slice<?> slice) {
            this.slice = (Slice<T>) slice;
        }
    }
}
