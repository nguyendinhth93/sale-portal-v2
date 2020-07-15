package com.tp.repo.impl;

import com.tp.dto.BoCheckupResultDTO;
import com.tp.dto.requestSearch.BoCheckupResultSearchDTO;
import com.tp.dto.requestSearch.BoFollowResultSearchDTO;
import com.tp.dto.requestSearch.CheckingMeetingResultSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.model.BoCheckupResult;
import com.tp.repo.BoCheckupResultRepoCustom;
import com.tp.util.*;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class BoCheckupResultRepoImpl implements BoCheckupResultRepoCustom {

    public static final Logger logger = Logger.getLogger(BoCheckupResultRepoCustom.class);

    @PersistenceContext(unitName = Const.PERSISTENCE.SALE_PORTAL)
    private EntityManager em;

    private BaseMapper<BoCheckupResult, BoCheckupResultDTO> mapper = new BaseMapper<>(BoCheckupResult.class, BoCheckupResultDTO.class);

    @Override
    public ResponseSearchDTO<BoCheckupResultDTO> searchBoCheckupResult(BoCheckupResultSearchDTO boCheckupResultInputSearchDTO) throws Exception {
        ResponseSearchDTO<BoCheckupResultDTO> responseSearch = new ResponseSearchDTO<>();
        StringBuilder sql = new StringBuilder("SELECT {0} FROM BO_CHECKUP_RESULT WHERE 1 = 1 ");
        List<String> params = new ArrayList<>(); //sometime should use map
        if(!DataUtil.isNullOrEmpty(boCheckupResultInputSearchDTO.getCustomerName())) {
            sql.append(" AND UPPER(CUSTOMER_NAME) LIKE ? ");
            params.add(QueryUtil.createSafeLikeValue(boCheckupResultInputSearchDTO.getCustomerName()));
        }
        if(!DataUtil.isNullOrEmpty(boCheckupResultInputSearchDTO.getCustomerPhone())) {
            sql.append(" AND UPPER(CUSTOMER_PHONE) LIKE ? ");
            params.add(QueryUtil.createSafeLikeValue(boCheckupResultInputSearchDTO.getCustomerPhone()));
        }
        if(!DataUtil.isNullOrEmpty(boCheckupResultInputSearchDTO.getNationalId())) {
            sql.append(" AND UPPER(NATIONAL_ID) LIKE ? ");
            params.add(QueryUtil.createSafeLikeValue(boCheckupResultInputSearchDTO.getNationalId()));
        }
        if(!DataUtil.isNullObject(boCheckupResultInputSearchDTO.getFromMeetDate())) {
            sql.append(" AND MEETING_DATE >= ? ");
            params.add(DateUtil.date2String(boCheckupResultInputSearchDTO.getFromMeetDate()));
        }
        if(!DataUtil.isNullObject(boCheckupResultInputSearchDTO.getToMeetDate())) {
            sql.append(" AND MEETING_DATE <= ? ");
            params.add(DateUtil.date2String(boCheckupResultInputSearchDTO.getToMeetDate()));
        }
        if(!DataUtil.isNullObject(boCheckupResultInputSearchDTO.getReceivedDsaDateFrom())) {
            sql.append(" AND RECEIVED_DSA_DATE >= ? ");
            params.add(DateUtil.date2String(boCheckupResultInputSearchDTO.getReceivedDsaDateFrom()));
        }
        if(!DataUtil.isNullObject(boCheckupResultInputSearchDTO.getReceivedDsaDateTo())) {
            sql.append(" AND RECEIVED_DSA_DATE <= ? ");
            params.add(DateUtil.date2String(boCheckupResultInputSearchDTO.getReceivedDsaDateTo()));
        }
        if(!DataUtil.isNullObject(boCheckupResultInputSearchDTO.getReturnDsaDateFrom())) {
            sql.append(" AND RETURN_DSA_DATE >= ? ");
            params.add(DateUtil.date2String(boCheckupResultInputSearchDTO.getReturnDsaDateFrom()));
        }
        if(!DataUtil.isNullObject(boCheckupResultInputSearchDTO.getReturnDsaDateTo())) {
            sql.append(" AND RETURN_DSA_DATE <= ? ");
            params.add(DateUtil.date2String(boCheckupResultInputSearchDTO.getReturnDsaDateTo()));
        }
        if(!DataUtil.isNullObject(boCheckupResultInputSearchDTO.getSendQdeDateFrom())) {
            sql.append(" AND SEND_QDE_DATE >= ? ");
            params.add(DateUtil.date2String(boCheckupResultInputSearchDTO.getSendQdeDateFrom()));
        }
        if(!DataUtil.isNullObject(boCheckupResultInputSearchDTO.getSendQdeDateTo())) {
            sql.append(" AND SEND_QDE_DATE <= ? ");
            params.add(DateUtil.date2String(boCheckupResultInputSearchDTO.getSendQdeDateTo()));
        }
        if(boCheckupResultInputSearchDTO.getCheckupStatus() != null) {
            sql.append(" AND CHECKUP_STATUS = ? ");
            params.add(boCheckupResultInputSearchDTO.getCheckupStatus().toString());
        }
        if(boCheckupResultInputSearchDTO.getQdeStatus() != null) {
            sql.append(" AND QDE_STATUS = ? ");
            params.add(boCheckupResultInputSearchDTO.getQdeStatus().toString());
        }
        if(!DataUtil.isNullOrEmpty(boCheckupResultInputSearchDTO.getStaffBoCheckup())) {
            sql.append(" AND UPPER(BO_CHECKUP_CODE) = ? ");
            params.add(DataUtil.safeToUpper(boCheckupResultInputSearchDTO.getStaffBoCheckup()));
        }
        if(!DataUtil.isNullOrEmpty(boCheckupResultInputSearchDTO.getStaffBoCheckup())) {
            sql.append(" AND UPPER(BO_CHECKUP_CODE) = ? ");
            params.add(DataUtil.safeToUpper(boCheckupResultInputSearchDTO.getStaffBoCheckup()));
        }
        if(!DataUtil.isNullObject(boCheckupResultInputSearchDTO.getSendToFollow())) {
            if (DataUtil.safeEqual(0,boCheckupResultInputSearchDTO.getSendToFollow())){
                sql.append(" AND (SEND_TO_FOLLOW IS NULL OR SEND_TO_FOLLOW = ? ) ");
                params.add(boCheckupResultInputSearchDTO.getSendToFollow());
            }else{
                sql.append(" AND SEND_TO_FOLLOW = ? ");
                params.add(boCheckupResultInputSearchDTO.getSendToFollow());
            }
        }
        if(!DataUtil.isNullObject(boCheckupResultInputSearchDTO.getAssigneStatus()) && DataUtil.safeEqual(1,boCheckupResultInputSearchDTO.getAssigneStatus())) {
            sql.append(" AND BO_CHECKUP_CODE IS NOT NULL ");
        }else if(!DataUtil.isNullObject(boCheckupResultInputSearchDTO.getAssigneStatus()) && DataUtil.safeEqual(0,boCheckupResultInputSearchDTO.getAssigneStatus())){
            sql.append(" AND BO_CHECKUP_CODE IS NULL ");
        }
        if(!DataUtil.isNullOrEmpty(boCheckupResultInputSearchDTO.getProvinceList())) {
            sql.append(" AND PROVINCE_CODE "+ DbUtil.createInQuery("provinceCode",boCheckupResultInputSearchDTO.getProvinceList())+ "");
        }
        if(!DataUtil.isNullOrEmpty(boCheckupResultInputSearchDTO.getBoCheckups())) {
            sql.append(" AND UPPER(BO_CHECKUP_CODE) "+ DbUtil.createInQuery("boCheckups",boCheckupResultInputSearchDTO.getBoCheckups())+ "");
        }
        sql.append(" AND STATUS = 1 ");
        if(DataUtil.isNullOrEmpty(boCheckupResultInputSearchDTO.getSortField()))
            sql.append(" ORDER BY UPDATED_DATE DESC ");
        else {
            sql.append(" ORDER BY " + boCheckupResultInputSearchDTO.getSortField() + " " + boCheckupResultInputSearchDTO.getSortOrder());
        }

        String sqlCount = MessageFormat.format(sql.toString(), "count(*)");
        Query queryCount = em.createNativeQuery(sqlCount);
        String sqlSelect = MessageFormat.format(sql.toString(), " * ");
        Query querySelect = em.createNativeQuery(sqlSelect, BoCheckupResult.class);
        querySelect.setFirstResult(boCheckupResultInputSearchDTO.getFirst());
        querySelect.setMaxResults(boCheckupResultInputSearchDTO.getPageSize());
        for(int i = 0 ; i < params.size() ; i++) {
            queryCount.setParameter(i + 1, params.get(i));
            querySelect.setParameter(i + 1, params.get(i));
        }
        if(!DataUtil.isNullOrEmpty(boCheckupResultInputSearchDTO.getProvinceList())) {
            DbUtil.setParamInQuery(queryCount,"provinceCode",boCheckupResultInputSearchDTO.getProvinceList());
            DbUtil.setParamInQuery(querySelect,"provinceCode",boCheckupResultInputSearchDTO.getProvinceList());
        }
        if(!DataUtil.isNullOrEmpty(boCheckupResultInputSearchDTO.getBoCheckups())) {
            DbUtil.setParamInQuery(queryCount,"boCheckups",boCheckupResultInputSearchDTO.getBoCheckups());
            DbUtil.setParamInQuery(querySelect,"boCheckups",boCheckupResultInputSearchDTO.getBoCheckups());
        }
        responseSearch.setRowCount(DataUtil.safeToLong(queryCount.getSingleResult()));
        List<BoCheckupResult> resultList = querySelect.getResultList();
        resultList.forEach(s -> s.setKey(s.getId().toString()));
        responseSearch.setData(mapper.toDtoBean(resultList));

        return responseSearch;
    }

    @Override
    public ResponseSearchDTO<BoCheckupResultDTO> searchBoFollowResult(BoFollowResultSearchDTO boFollowResultSearchDTO) throws Exception {
        ResponseSearchDTO<BoCheckupResultDTO> responseSearch = new ResponseSearchDTO<>();
        StringBuilder sql = new StringBuilder("SELECT {0} FROM BO_CHECKUP_RESULT WHERE 1 = 1 ");
        List<String> params = new ArrayList<>(); //sometime should use map
        if(!DataUtil.isNullOrEmpty(boFollowResultSearchDTO.getCustomerName())) {
            sql.append(" AND UPPER(CUSTOMER_NAME) LIKE ? ");
            params.add(QueryUtil.createSafeLikeValue(boFollowResultSearchDTO.getCustomerName()));
        }
        if(!DataUtil.isNullOrEmpty(boFollowResultSearchDTO.getCustomerPhone())) {
            sql.append(" AND UPPER(CUSTOMER_PHONE) LIKE ? ");
            params.add(QueryUtil.createSafeLikeValue(boFollowResultSearchDTO.getCustomerPhone()));
        }
        if(!DataUtil.isNullOrEmpty(boFollowResultSearchDTO.getNationalId())) {
            sql.append(" AND UPPER(NATIONAL_ID) LIKE ? ");
            params.add(QueryUtil.createSafeLikeValue(boFollowResultSearchDTO.getNationalId()));
        }
        if(!DataUtil.isNullOrEmpty(boFollowResultSearchDTO.getBoFollow())) {
            sql.append(" AND UPPER(BO_FOLLOW) = ? ");
            params.add(DataUtil.safeToUpper(boFollowResultSearchDTO.getBoFollow()));
        }
        if(!DataUtil.isNullObject(boFollowResultSearchDTO.getToLosDateFrom())) {
            sql.append(" AND TO_LOS_DATE >= ? ");
            params.add(DateUtil.date2String(boFollowResultSearchDTO.getToLosDateFrom()));
        }
        if(!DataUtil.isNullObject(boFollowResultSearchDTO.getToLosDateTo())) {
            sql.append(" AND TO_LOS_DATE <= ? ");
            params.add(DateUtil.date2String(boFollowResultSearchDTO.getToLosDateTo()));
        }
        if(!DataUtil.isNullObject(boFollowResultSearchDTO.getToFiDateFrom())) {
            sql.append(" AND TO_FI_DATE >= ? ");
            params.add(DateUtil.date2String(boFollowResultSearchDTO.getToFiDateFrom()));
        }
        if(!DataUtil.isNullObject(boFollowResultSearchDTO.getToFiDateTo())) {
            sql.append(" AND TO_FI_DATE <= ? ");
            params.add(DateUtil.date2String(boFollowResultSearchDTO.getToFiDateTo()));
        }
        if(!DataUtil.isNullObject(boFollowResultSearchDTO.getReturnQdeDateFrom())) {
            sql.append(" AND RETURN_RESULT_QDE_DATE >= ? ");
            params.add(DateUtil.date2String(boFollowResultSearchDTO.getReturnQdeDateFrom()));
        }
        if(!DataUtil.isNullObject(boFollowResultSearchDTO.getReturnQdeDateTo())) {
            sql.append(" AND RETURN_RESULT_QDE_DATE <= ? ");
            params.add(DateUtil.date2String(boFollowResultSearchDTO.getReturnQdeDateTo()));
        }
        if(!DataUtil.isNullObject(boFollowResultSearchDTO.getAssigneDateFrom())) {
            sql.append(" AND ASSIGNEE_BO_FOLLOW_DATE >= ? ");
            params.add(DateUtil.date2String(boFollowResultSearchDTO.getAssigneDateFrom()));
        }
        if(!DataUtil.isNullObject(boFollowResultSearchDTO.getAssigneDateTo())) {
            sql.append(" AND ASSIGNEE_BO_FOLLOW_DATE <= ? ");
            params.add(DateUtil.date2String(boFollowResultSearchDTO.getAssigneDateTo()));
        }
        if(!DataUtil.isNullObject(boFollowResultSearchDTO.getStatusDocVpBank())) {
            if (DataUtil.safeEqual(-1,boFollowResultSearchDTO.getStatusDocVpBank())){
                sql.append(" AND STATUS_DOC_VPBANK IS NULL ");
            }else {
                sql.append(" AND STATUS_DOC_VPBANK = ? ");
                params.add(boFollowResultSearchDTO.getStatusDocVpBank());
            }
        }
        if(!DataUtil.isNullObject(boFollowResultSearchDTO.getAssigneStatus()) && DataUtil.safeEqual(1,boFollowResultSearchDTO.getAssigneStatus())) {
            sql.append(" AND BO_FOLLOW IS NOT NULL ");
        }else if(!DataUtil.isNullObject(boFollowResultSearchDTO.getAssigneStatus()) && DataUtil.safeEqual(0,boFollowResultSearchDTO.getAssigneStatus())){
            sql.append(" AND BO_FOLLOW IS NULL ");
        }
        if(!DataUtil.isNullOrEmpty(boFollowResultSearchDTO.getProvinceList())) {
            sql.append(" AND PROVINCE_CODE "+ DbUtil.createInQuery("provinceCode",boFollowResultSearchDTO.getProvinceList())+ "");
        }
        if(!DataUtil.isNullOrEmpty(boFollowResultSearchDTO.getBoFollows())) {
            sql.append(" AND UPPER(BO_FOLLOW) "+ DbUtil.createInQuery("boFollows",boFollowResultSearchDTO.getBoFollows())+ "");
        }
        sql.append(" AND STATUS = 1 ");
        sql.append(" AND SEND_TO_FOLLOW = 1 ");
        if(DataUtil.isNullOrEmpty(boFollowResultSearchDTO.getSortField()))
            sql.append(" ORDER BY MEETING_DATE DESC ");
        else {
            sql.append(" ORDER BY " + boFollowResultSearchDTO.getSortField() + " " + boFollowResultSearchDTO.getSortOrder());
        }

        String sqlCount = MessageFormat.format(sql.toString(), "count(*)");
        Query queryCount = em.createNativeQuery(sqlCount);
        String sqlSelect = MessageFormat.format(sql.toString(), " * ");
        Query querySelect = em.createNativeQuery(sqlSelect, BoCheckupResult.class);
        querySelect.setFirstResult(boFollowResultSearchDTO.getFirst());
        querySelect.setMaxResults(boFollowResultSearchDTO.getPageSize());
        for(int i = 0 ; i < params.size() ; i++) {
            queryCount.setParameter(i + 1, params.get(i));
            querySelect.setParameter(i + 1, params.get(i));
        }
        if(!DataUtil.isNullOrEmpty(boFollowResultSearchDTO.getProvinceList())) {
            DbUtil.setParamInQuery(queryCount,"provinceCode",boFollowResultSearchDTO.getProvinceList());
            DbUtil.setParamInQuery(querySelect,"provinceCode",boFollowResultSearchDTO.getProvinceList());
        }
        if(!DataUtil.isNullOrEmpty(boFollowResultSearchDTO.getBoFollows())) {
            DbUtil.setParamInQuery(queryCount,"boFollows",boFollowResultSearchDTO.getBoFollows());
            DbUtil.setParamInQuery(querySelect,"boFollows",boFollowResultSearchDTO.getBoFollows());
        }
        responseSearch.setRowCount(DataUtil.safeToLong(queryCount.getSingleResult()));
        List<BoCheckupResult> resultList = querySelect.getResultList();
        resultList.forEach(s -> s.setKey(s.getId().toString()));
        responseSearch.setData(mapper.toDtoBean(resultList));

        return responseSearch;
    }

    @Override
    public ResponseSearchDTO<BoCheckupResultDTO> searchCheckingMeetingResult(CheckingMeetingResultSearchDTO checkingMeetingResultSearchDTO) throws Exception {
        ResponseSearchDTO<BoCheckupResultDTO> responseSearch = new ResponseSearchDTO<>();
        StringBuilder sql = new StringBuilder("SELECT {0} FROM BO_CHECKUP_RESULT WHERE 1 = 1 ");
        List<String> params = new ArrayList<>(); //sometime should use map
        if(!DataUtil.isNullOrEmpty(checkingMeetingResultSearchDTO.getCustomerName())) {
            sql.append(" AND UPPER(CUSTOMER_NAME) LIKE ? ");
            params.add(QueryUtil.createSafeLikeValue(checkingMeetingResultSearchDTO.getCustomerName()));
        }
        if(!DataUtil.isNullOrEmpty(checkingMeetingResultSearchDTO.getCustomerPhone())) {
            sql.append(" AND UPPER(CUSTOMER_PHONE) LIKE ? ");
            params.add(QueryUtil.createSafeLikeValue(checkingMeetingResultSearchDTO.getCustomerPhone()));
        }
        if(!DataUtil.isNullOrEmpty(checkingMeetingResultSearchDTO.getNationalId())) {
            sql.append(" AND UPPER(NATIONAL_ID) LIKE ? ");
            params.add(QueryUtil.createSafeLikeValue(checkingMeetingResultSearchDTO.getNationalId()));
        }
        // Query For CCA
        if(!DataUtil.isNullOrEmpty(checkingMeetingResultSearchDTO.getTlCcaCode())) {
            sql.append(" AND UPPER(TL_CCA_CODE) = ? ");
            params.add(DataUtil.safeToUpper(checkingMeetingResultSearchDTO.getTlCcaCode()));
        }

        if(!DataUtil.isNullOrEmpty(checkingMeetingResultSearchDTO.getCcaCode())) {
            sql.append(" AND UPPER(CCA_CODE) = ? ");
            params.add(DataUtil.safeToUpper(checkingMeetingResultSearchDTO.getCcaCode()));
        }
        // Query For DSA
        if(!DataUtil.isNullOrEmpty(checkingMeetingResultSearchDTO.getTlDsaCode())) {
            sql.append(" AND UPPER(TL_DSA_CODE) = ? ");
            params.add(DataUtil.safeToUpper(checkingMeetingResultSearchDTO.getTlDsaCode()));
        }

        if(!DataUtil.isNullOrEmpty(checkingMeetingResultSearchDTO.getDsaCode())) {
            sql.append(" AND UPPER(DSA_CODE) = ? ");
            params.add(DataUtil.safeToUpper(checkingMeetingResultSearchDTO.getDsaCode()));
        }


        if(!DataUtil.isNullObject(checkingMeetingResultSearchDTO.getFromMeetDate())) {
            sql.append(" AND MEETING_DATE >= ? ");
            params.add(DateUtil.date2String(checkingMeetingResultSearchDTO.getFromMeetDate()));
        }
        if(!DataUtil.isNullObject(checkingMeetingResultSearchDTO.getToMeetDate())) {
            sql.append(" AND MEETING_DATE <= ? ");
            params.add(DateUtil.date2String(checkingMeetingResultSearchDTO.getToMeetDate()));
        }
        if(!DataUtil.isNullObject(checkingMeetingResultSearchDTO.getReceivedDsaDateFrom())) {
            sql.append(" AND RECEIVED_DSA_DATE >= ? ");
            params.add(DateUtil.date2String(checkingMeetingResultSearchDTO.getReceivedDsaDateFrom()));
        }
        if(!DataUtil.isNullObject(checkingMeetingResultSearchDTO.getReceivedDsaDateTo())) {
            sql.append(" AND RECEIVED_DSA_DATE <= ? ");
            params.add(DateUtil.date2String(checkingMeetingResultSearchDTO.getReceivedDsaDateTo()));
        }
        if(!DataUtil.isNullObject(checkingMeetingResultSearchDTO.getReturnDsaDateFrom())) {
            sql.append(" AND RETURN_DSA_DATE >= ? ");
            params.add(DateUtil.date2String(checkingMeetingResultSearchDTO.getReturnDsaDateFrom()));
        }
        if(!DataUtil.isNullObject(checkingMeetingResultSearchDTO.getReturnDsaDateTo())) {
            sql.append(" AND RETURN_DSA_DATE <= ? ");
            params.add(DateUtil.date2String(checkingMeetingResultSearchDTO.getReturnDsaDateTo()));
        }
        if(!DataUtil.isNullObject(checkingMeetingResultSearchDTO.getSendQdeDateFrom())) {
            sql.append(" AND SEND_QDE_DATE >= ? ");
            params.add(DateUtil.date2String(checkingMeetingResultSearchDTO.getSendQdeDateFrom()));
        }
        if(!DataUtil.isNullObject(checkingMeetingResultSearchDTO.getSendQdeDateTo())) {
            sql.append(" AND SEND_QDE_DATE <= ? ");
            params.add(DateUtil.date2String(checkingMeetingResultSearchDTO.getSendQdeDateTo()));
        }
        if(checkingMeetingResultSearchDTO.getCheckupStatus() != null) {
            sql.append(" AND CHECKUP_STATUS = ? ");
            params.add(checkingMeetingResultSearchDTO.getCheckupStatus().toString());
        }
        if(checkingMeetingResultSearchDTO.getQdeStatus() != null) {
            sql.append(" AND QDE_STATUS = ? ");
            params.add(checkingMeetingResultSearchDTO.getQdeStatus().toString());
        }
        if(!DataUtil.isNullOrEmpty(checkingMeetingResultSearchDTO.getStatusDocVpBank())) {
            sql.append(" AND STATUS_DOC_VPBANK = ? ");
            params.add(checkingMeetingResultSearchDTO.getStatusDocVpBank());
        }
        if(!DataUtil.isNullOrEmpty(checkingMeetingResultSearchDTO.getDocReturnStatus())) {
            sql.append(" AND DOC_RETURN_STATUS = ? ");
            params.add(checkingMeetingResultSearchDTO.getDocReturnStatus());
        }
        if(!DataUtil.isNullObject(checkingMeetingResultSearchDTO.getSendToFollow())) {
            if (DataUtil.safeEqual(0,checkingMeetingResultSearchDTO.getSendToFollow())){
                sql.append(" AND (SEND_TO_FOLLOW IS NULL OR SEND_TO_FOLLOW = ? ) ");
                params.add(checkingMeetingResultSearchDTO.getSendToFollow());
            }else{
                sql.append(" AND SEND_TO_FOLLOW = ? ");
                params.add(checkingMeetingResultSearchDTO.getSendToFollow());
            }
        }
        if(!DataUtil.isNullOrEmpty(checkingMeetingResultSearchDTO.getProvinceList())) {
            sql.append(" AND PROVINCE_CODE "+ DbUtil.createInQuery("provinceCode",checkingMeetingResultSearchDTO.getProvinceList())+ "");
        }
        sql.append(" AND STATUS = 1 ");
        if(DataUtil.isNullOrEmpty(checkingMeetingResultSearchDTO.getSortField()))
            sql.append(" ORDER BY UPDATED_DATE DESC ");
        else {
            sql.append(" ORDER BY " + checkingMeetingResultSearchDTO.getSortField() + " " + checkingMeetingResultSearchDTO.getSortOrder());
        }

        String sqlCount = MessageFormat.format(sql.toString(), "count(*)");
        Query queryCount = em.createNativeQuery(sqlCount);
        String sqlSelect = MessageFormat.format(sql.toString(), " * ");
        Query querySelect = em.createNativeQuery(sqlSelect, BoCheckupResult.class);
        querySelect.setFirstResult(checkingMeetingResultSearchDTO.getFirst());
        querySelect.setMaxResults(checkingMeetingResultSearchDTO.getPageSize());
        for(int i = 0 ; i < params.size() ; i++) {
            queryCount.setParameter(i + 1, params.get(i));
            querySelect.setParameter(i + 1, params.get(i));
        }
        if(!DataUtil.isNullOrEmpty(checkingMeetingResultSearchDTO.getProvinceList())) {
            DbUtil.setParamInQuery(queryCount,"provinceCode",checkingMeetingResultSearchDTO.getProvinceList());
            DbUtil.setParamInQuery(querySelect,"provinceCode",checkingMeetingResultSearchDTO.getProvinceList());
        }
        responseSearch.setRowCount(DataUtil.safeToLong(queryCount.getSingleResult()));
        List<BoCheckupResult> resultList = querySelect.getResultList();
        resultList.forEach(s -> s.setKey(s.getId().toString()));
        responseSearch.setData(mapper.toDtoBean(resultList));

        return responseSearch;
    }
}