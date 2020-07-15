package com.tp.service;

import com.tp.common.exception.LogicException;
import com.tp.dto.CampaignDTO;

import java.util.List;

public interface CampaignService extends BaseSearchService<CampaignDTO>{

    CampaignDTO saveOrUpdate(CampaignDTO campaignDTO, String username) throws Exception;

    public CampaignDTO findOne(Long id) throws Exception;

    CampaignDTO findByCode(String code) throws Exception;

    List<CampaignDTO> findAllCampaignActive() throws Exception;

    public void delete(List<Long> campaignIds, String username) throws LogicException, Exception;

}