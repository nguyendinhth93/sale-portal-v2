package com.tp.common.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tp.dto.BaseDTO;
import com.tp.dto.requestSearch.RequestSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.service.BaseSearchService;
import com.tp.util.Const;
import com.tp.util.DataUtil;

/**
 * Created by hopnv on 23/07/2017.
 */
public class SnDataModel<T extends BaseDTO> extends LazyDataModel<T> {

    protected List<T> selectedList;
    protected BaseSearchService<T> baseSearchService;
    protected RequestSearchDTO requestSearchDTO;

    final static Logger logger = LoggerFactory.getLogger(SnDataModel.class);

    public SnDataModel(BaseSearchService<T> baseSearchService, RequestSearchDTO requestSearchDTO) {
        this.baseSearchService = baseSearchService;
        this.requestSearchDTO = requestSearchDTO;
        selectedList = new ArrayList<T>();
    }

    @Override
    public T getRowData(String rowKey) {
        List<T> data = (List<T>) getWrappedData();
        for (T t : data) {
            if (DataUtil.safeEqual(t.getKey(),rowKey))
                return t;
        }

        return null;
    }

    @Override
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
        try {
            requestSearchDTO.setFirst(first);
            requestSearchDTO.setPageSize(pageSize);
            requestSearchDTO.setSortField(sortField);
            if(sortOrder != null)
                requestSearchDTO.setSortOrder(Const.SORT.ASCENDING.equals(sortOrder.name()) ? Const.SORT.ASC : Const.SORT.DESC);
            ResponseSearchDTO<T> responseSearch = DataUtil.defaultIfNull(baseSearchService.load(requestSearchDTO), new ResponseSearchDTO<T>());
            setRowCount(responseSearch.getRowCount().intValue());
            return responseSearch.getData();
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            setRowCount(0);
            return new ArrayList<T>();
        }
    }

    @Override
    public Object getRowKey(T object) {
        return object.getKey();
    }

    public List<T> getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(List<T> selectedList) {
        this.selectedList = selectedList;
    }

    public RequestSearchDTO getRequestSearchDTO() {
        return requestSearchDTO;
    }

    public void setRequestSearchDTO(RequestSearchDTO requestSearchDTO) {
        this.requestSearchDTO = requestSearchDTO;
    }
}
