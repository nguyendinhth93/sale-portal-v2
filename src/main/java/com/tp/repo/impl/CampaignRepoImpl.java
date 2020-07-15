package com.tp.repo.impl;

import com.tp.dto.CampaignDTO;
import com.tp.dto.requestSearch.CampaignSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.model.Campaign;
import com.tp.repo.CampaignRepoCustom;
import com.tp.util.*;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class CampaignRepoImpl implements CampaignRepoCustom {

    public static final Logger logger = Logger.getLogger(CampaignRepoCustom.class);
    private final BaseMapper<Campaign,CampaignDTO> mapper = new BaseMapper<>(Campaign.class,CampaignDTO.class);

    @PersistenceContext(unitName = Const.PERSISTENCE.SALE_PORTAL)
    private EntityManager em;

    @Override
    public ResponseSearchDTO<CampaignDTO> searchCampaign(CampaignSearchDTO campaignSearchDTO) throws Exception {
        ResponseSearchDTO<CampaignDTO> responseSearch = new ResponseSearchDTO<>();
        StringBuilder sql = new StringBuilder("SELECT {0} FROM CAMPAIGN WHERE 1 = 1 ");
        List<String> params = new ArrayList<>(); //sometime should use map
        if(!DataUtil.isNullOrEmpty(campaignSearchDTO.getProgramCode())) {
            sql.append(" AND UPPER(PROGRAM_CODE) LIKE ? ");
            params.add(QueryUtil.createSafeLikeValue(campaignSearchDTO.getProgramCode()));
        }
        if(!DataUtil.isNullOrEmpty(campaignSearchDTO.getProductName())) {
            sql.append(" AND UPPER(PRODUCT_NAME) LIKE ? ");
            params.add(QueryUtil.createSafeLikeValue(campaignSearchDTO.getProductName()));
        }
        if(!DataUtil.isNullOrEmpty(campaignSearchDTO.getProvince())) {
            sql.append(" AND UPPER(PROVINCE) LIKE ? ");
            params.add(QueryUtil.createSafeLikeValue(campaignSearchDTO.getProvince()));
        }
        if(!DataUtil.isNullOrEmpty(campaignSearchDTO.getScores())) {
            sql.append(" AND UPPER(SCORES) LIKE ? ");
            params.add(QueryUtil.createSafeLikeValue(campaignSearchDTO.getScores()));
        }
        if(!DataUtil.isNullObject(campaignSearchDTO.getFromDate())) {
            sql.append(" AND FROM_DATE LIKE >= ? ");
            params.add(DateUtil.date2String(campaignSearchDTO.getFromDate()));
        }
        if(!DataUtil.isNullObject(campaignSearchDTO.getToDate())) {
            sql.append(" AND TO_DATE LIKE <= ? ");
            params.add(DateUtil.date2String(campaignSearchDTO.getToDate()));
        }

        if(DataUtil.isNullOrEmpty(campaignSearchDTO.getSortField()))
            sql.append(" ORDER BY PROGRAM_CODE ");
        else {
            sql.append(" ORDER BY " + campaignSearchDTO.getSortField() + " " + campaignSearchDTO.getSortOrder());
        }

        String sqlCount = MessageFormat.format(sql.toString(), "count(*)");
        Query queryCount = em.createNativeQuery(sqlCount);
        String sqlSelect = MessageFormat.format(sql.toString(), " * ");
        Query querySelect = em.createNativeQuery(sqlSelect, Campaign.class);
        querySelect.setFirstResult(campaignSearchDTO.getFirst());
        querySelect.setMaxResults(campaignSearchDTO.getPageSize());
        for(int i = 0 ; i < params.size() ; i++) {
            queryCount.setParameter(i + 1, params.get(i));
            querySelect.setParameter(i + 1, params.get(i));
        }

        responseSearch.setRowCount(DataUtil.safeToLong(queryCount.getSingleResult()));
        List<Campaign> resultList = querySelect.getResultList();
        resultList.forEach(s -> s.setKey(s.getId().toString()));
        responseSearch.setData(mapper.toDtoBean(resultList));

        return responseSearch;
    }
}