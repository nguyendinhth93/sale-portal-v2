package com.tp.service;

import com.tp.dto.BoundCodeDTO;
import com.tp.dto.SaleFunnelCcaDTO;

import java.util.List;

public interface SaleFunnelService extends BaseSearchService<SaleFunnelCcaDTO> {

    public List<BoundCodeDTO> findAllBoundCode() throws Exception;

}