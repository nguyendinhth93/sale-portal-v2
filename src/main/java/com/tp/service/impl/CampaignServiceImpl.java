package com.tp.service.impl;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.tp.common.exception.LogicException;
import com.tp.dto.CampaignDTO;
import com.tp.dto.requestSearch.CampaignSearchDTO;
import com.tp.dto.requestSearch.RequestSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.model.ApDomain;
import com.tp.model.Campaign;
import com.tp.model.QCampaign;
import com.tp.repo.CampaignRepo;
import com.tp.service.CampaignService;
import com.tp.util.BaseMapper;
import com.tp.util.Const;
import com.tp.util.DataUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CampaignServiceImpl extends BaseServiceImpl implements CampaignService {

    private final BaseMapper<Campaign,CampaignDTO> mapper = new BaseMapper<>(Campaign.class,CampaignDTO.class);

    @Autowired
    private CampaignRepo repo;
    public static final Logger logger = Logger.getLogger(CampaignService.class);


    @Override
    public CampaignDTO saveOrUpdate(CampaignDTO campaignDTO, String username) throws Exception {
        boolean isCreate = campaignDTO.getId() == null ? true : false;
        Date currentDate = new Date();
        if(isCreate){
            campaignDTO.setCreatedDate(currentDate);
            campaignDTO.setCreatedBy(username);
        } else {
            campaignDTO.setUpdatedDate(currentDate);
            campaignDTO.setUpdatedBy(username);
        }
        return mapper.toDtoBean(repo.saveAndFlush(mapper.toPersistenceBean(campaignDTO)));
    }

    public CampaignDTO findOne(Long id) throws Exception {
        return mapper.toDtoBean(repo.findOne(id));
    }

    @Override
    public CampaignDTO findByCode(String programCode) throws Exception {
        BooleanExpression predicate = QCampaign.campaign.programCode.equalsIgnoreCase(programCode);
        predicate = predicate.and(QCampaign.campaign.status.eq(Const.STATUS.ACTIVCE));

        List<Campaign> campaigns = Lists.newArrayList(repo.findAll(predicate));
        if(DataUtil.isNullOrEmpty(campaigns))
            return null;
        return mapper.toDtoBean(campaigns.get(0));
    }

    @Override
    public List<CampaignDTO> findAllCampaignActive() throws Exception {
        BooleanExpression predicate = QCampaign.campaign.status.eq(Const.STATUS.ACTIVCE);
        List<Campaign> campaigns = Lists.newArrayList(repo.findAll(predicate));
        return mapper.toDtoBean(campaigns);
    }

    @Override
    public void delete(List<Long> campaignIds, String username) throws LogicException, Exception {
        Date currentDate = new Date();
        List<Campaign> campaigns = new ArrayList<>();
        for(Long campaignId : campaignIds) {
            Campaign campaign = repo.findOne(campaignId);
            if (campaign == null || DataUtil.safeEqual(campaign.getStatus(), Const.STATUS.INACTIVCE)) {
                throw new LogicException("apConfig.msg.delete.fail");
            }
            campaign.setStatus(Const.STATUS.INACTIVCE);
            campaign.setUpdatedBy(username);
            campaign.setUpdatedDate(currentDate);
            campaigns.add(campaign);
        }
        repo.save(campaigns);
    }

    @Override
    public ResponseSearchDTO<CampaignDTO> load(RequestSearchDTO requestSearchDTO) throws Exception {
        CampaignSearchDTO campaignSearchDTO = (CampaignSearchDTO) requestSearchDTO;
        return repo.searchCampaign(campaignSearchDTO);
    }
}
