package com.tp.service;

import com.tp.dto.requestSearch.RequestSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;

/**
 * Created by hopnv on 24/07/2017.
 */
public interface BaseSearchService<T> {
    ResponseSearchDTO<T> load(RequestSearchDTO requestSearchDTO) throws Exception;
}
