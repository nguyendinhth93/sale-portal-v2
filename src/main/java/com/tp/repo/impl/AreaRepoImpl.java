package com.tp.repo.impl;
import com.tp.dto.AreaDTO;
import com.tp.dto.CampaignDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.model.Campaign;
import com.tp.repo.AreaRepoCustom;
import com.tp.util.Const;
import com.tp.util.DataUtil;
import com.tp.util.DateUtil;
import com.tp.util.QueryUtil;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class AreaRepoImpl implements AreaRepoCustom {

    public static final Logger logger = Logger.getLogger(AreaRepoCustom.class);

    @PersistenceContext(unitName = Const.PERSISTENCE.SALE_PORTAL)
    private EntityManager em;

    @Override
    public List<AreaDTO> findAllProvinceCRM() throws Exception {
        List<AreaDTO> areaDTOs = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT province_id, province_name FROM LST_PROVINCES WHERE 1 = 1 AND INACTIVE = 0 ");
        Query querySelect = em.createNativeQuery(sql.toString());
        List resultList = querySelect.getResultList();
        if (!DataUtil.isNullOrEmpty(resultList)){
            for (int i = 0; i < resultList.size(); i++) {
                Object[] obj = (Object[]) resultList.get(i);
                AreaDTO areaDTO = new AreaDTO();
                if (obj[0] != null) {
                    areaDTO.setCode(obj[0].toString());
                }
                if (obj[1] != null) {
                    areaDTO.setName(obj[1].toString());
                }
                areaDTOs.add(areaDTO);
            }
        }
        return areaDTOs;
    }

    @Override
    public List<AreaDTO> findAllDistrictCRMById(String provinceId) throws Exception {
        List<AreaDTO> areaDTOs = new ArrayList<>();
        StringBuilder sql = new StringBuilder(" SELECT DISTRICT_ID, DISTRICT_NAME FROM LST_DISTRICTS WHERE 1=1 AND INACTIVE = 0 ");
        if (!DataUtil.isNullOrEmpty(provinceId)){
            sql.append("AND PROVINCE_ID = :provinceId");
        }
        Query querySelect = em.createNativeQuery(sql.toString());
        querySelect.setParameter("provinceId",provinceId);
        List resultList = querySelect.getResultList();
        if (!DataUtil.isNullOrEmpty(resultList)){
            for (int i = 0; i < resultList.size(); i++) {
                Object[] obj = (Object[]) resultList.get(i);
                AreaDTO areaDTO = new AreaDTO();
                if (obj[0] != null) {
                    areaDTO.setCode(obj[0].toString());
                }
                if (obj[1] != null) {
                    areaDTO.setName(obj[1].toString());
                }
                areaDTOs.add(areaDTO);
            }
        }
        return areaDTOs;
    }

    @Override
    public AreaDTO findProvinceCRMById(String provinceId) throws Exception {
        AreaDTO areaDTO = new AreaDTO();
        StringBuilder sql = new StringBuilder("SELECT province_id, province_name FROM LST_PROVINCES WHERE 1 = 1 AND INACTIVE = 0 AND PROVINCE_ID = :provinceId ");
        Query querySelect = em.createNativeQuery(sql.toString());
        querySelect.setParameter("provinceId",provinceId);
        List resultList = querySelect.getResultList();
        if (!DataUtil.isNullOrEmpty(resultList)){
            Object[] obj = (Object[]) resultList.get(0);
            if (obj[0] != null) {
                areaDTO.setCode(obj[0].toString());
            }
            if (obj[1] != null) {
                areaDTO.setName(obj[1].toString());
            }
        }
        return areaDTO;
    }

    @Override
    public AreaDTO findDistrictCRMById(String districtId) throws Exception {
        AreaDTO areaDTO = new AreaDTO();
        StringBuilder sql = new StringBuilder("SELECT DISTRICT_ID, DISTRICT_NAME FROM LST_DISTRICTS WHERE 1=1 AND INACTIVE = 0 AND DISTRICT_ID = :districtId ");
        Query querySelect = em.createNativeQuery(sql.toString());
        querySelect.setParameter("districtId",districtId);
        List resultList = querySelect.getResultList();
        if (!DataUtil.isNullOrEmpty(resultList)){
            Object[] obj = (Object[]) resultList.get(0);
            if (obj[0] != null) {
                areaDTO.setCode(obj[0].toString());
            }
            if (obj[1] != null) {
                areaDTO.setName(obj[1].toString());
            }
        }
        return areaDTO;
    }
}