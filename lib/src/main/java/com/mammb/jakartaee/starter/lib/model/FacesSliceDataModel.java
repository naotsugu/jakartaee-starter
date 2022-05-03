package com.mammb.jakartaee.starter.lib.model;

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
