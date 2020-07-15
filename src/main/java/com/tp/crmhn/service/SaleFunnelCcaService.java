package com.tp.crmhn.service;

import com.tp.dto.BoundCodeDTO;
import com.tp.dto.SaleFunnelCcaDTO;
import com.tp.dto.requestSearch.SaleFunnelCCASearchDTO;
import com.tp.service.BaseSearchService;

import java.util.List;

public interface SaleFunnelCcaService extends BaseSearchService<SaleFunnelCcaDTO> {

    public List<BoundCodeDTO> findAllBoundCode() throws Exception;

}