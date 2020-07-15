package com.tp.repo;

import com.tp.dto.CampaignDTO;
import com.tp.dto.requestSearch.CampaignSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;

public interface CampaignRepoCustom {

    public ResponseSearchDTO<CampaignDTO> searchCampaign(CampaignSearchDTO campaignSearchDTO) throws Exception;

}