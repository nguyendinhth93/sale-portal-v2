package com.tp.dwh.service;

import com.tp.dwh.dto.CXBitCdrDTO;
import com.tp.service.BaseSearchService;

public interface CXBitCdrService extends BaseSearchService<CXBitCdrDTO> {

    public CXBitCdrDTO findOne(Long id) throws Exception;

}