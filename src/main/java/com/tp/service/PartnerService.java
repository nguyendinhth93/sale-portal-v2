package com.tp.service;

import com.tp.dto.PartnerDTO;

import java.util.List;

public interface PartnerService {

    public PartnerDTO findOne(Long id) throws Exception;

    public List<PartnerDTO> findAllPartner() throws Exception;

}