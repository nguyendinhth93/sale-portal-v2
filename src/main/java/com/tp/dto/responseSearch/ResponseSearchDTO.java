package com.tp.dto.responseSearch;

import java.util.List;

/**
 * Created by hopnv on 24/07/2017.
 */
public class ResponseSearchDTO<T> {

    protected Long rowCount;
    protected List<T> data;

    public Long getRowCount() {
        return rowCount;
    }

    public void setRowCount(Long rowCount) {
        this.rowCount = rowCount;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
