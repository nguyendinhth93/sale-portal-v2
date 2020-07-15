package com.tp.dto.requestSearch;

import com.tp.dto.BaseDTO;

/**
 * Created by hopnv on 24/07/2017.
 */
public abstract class RequestSearchDTO extends BaseDTO {

    protected int first;
    protected int pageSize;
    protected String sortField;
    protected String sortOrder;

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

}
