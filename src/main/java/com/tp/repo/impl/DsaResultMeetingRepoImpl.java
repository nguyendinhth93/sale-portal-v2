package com.tp.repo.impl;

import com.tp.dto.*;
import com.tp.dto.admin.UserDTO;
import com.tp.dto.requestSearch.SaleFunnelCCASearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.model.DsaResultMeeting;
import com.tp.repo.DsaResultMeetingRepoCustom;
import com.tp.service.OptionSetValueService;
import com.tp.util.*;
import org.apache.log4j.Logger;
import org.decimal4j.util.DoubleRounder;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.MessageFormat;
import java.util.*;

public class DsaResultMeetingRepoImpl implements DsaResultMeetingRepoCustom {

    public static final Logger logger = Logger.getLogger(DsaResultMeetingRepoCustom.class);
    private final BaseMapper<DsaResultMeeting,DsaResultMeetingDTO> mapper = new BaseMapper<>(DsaResultMeeting.class,DsaResultMeetingDTO.class);

    @PersistenceContext(unitName = Const.PERSISTENCE.SALE_PORTAL)
    private EntityManager em;
    @PersistenceContext(unitName = Const.PERSISTENCE.CRM_HN)
    private EntityManager emHN;
    @PersistenceContext(unitName = Const.PERSISTENCE.CRM_HCM)
    private EntityManager emHCM;

    @Autowired
    OptionSetValueService optionSetValueService;

    @Override
    public ResponseSearchDTO<DsaResultMeetingDTO> searchDsaResultMeeting(DsaResultMeetingSearchDTO dsaResultMeetingSearchDTO) throws Exception {
        ResponseSearchDTO<DsaResultMeetingDTO> responseSearch = new ResponseSearchDTO<>();
        StringBuilder sql = new StringBuilder("SELECT {0} FROM DSA_RESULT_MEETING WHERE 1 = 1 ");
        List<String> params = new ArrayList<>(); //sometime should use map
        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getProvinceMeetingCode())) {
            sql.append(" AND PROVINCE_MEETING_CODE = :provinceId ");
        }
        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getDistrictMeetingCodes())) {
            sql.append(" AND DISTRICT_MEETING_CODE "+ DbUtil.createInQuery("districtId",dsaResultMeetingSearchDTO.getDistrictMeetingCodes())+ "");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getFromCallDate())) {
            sql.append(" AND DATE(CALL_DATE) >= :fromCallDate");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getToCallDate())) {
            sql.append(" AND DATE(CALL_DATE) <= :toCallDate");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getMeetDate())) {
            sql.append(" AND DATE(MEETING_DATE) = :meetingDate ");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getFromMeetDate())) {
            sql.append(" AND DATE(MEETING_DATE) >= :fromMeetDate ");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getToMeetDate())) {
            sql.append(" AND DATE(MEETING_DATE) <= :toMeetDate ");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getAssignDate())) {
            sql.append(" AND DATE(ASSIGNED_DATE) = :assignDate ");
        }

        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getDsaCodes())) {
            sql.append(" AND UPPER(DSA_CODE) "+ DbUtil.createInQuery("dsaCode",dsaResultMeetingSearchDTO.getDsaCodes())+ "");
        }

//        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getMeetingResults())) {
//            sql.append(" AND MEETING_RESULT "+ DbUtil.createInQuery("meetingResults",dsaResultMeetingSearchDTO.getMeetingResults())+ "");
//        }

        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getMeetingResults()) && dsaResultMeetingSearchDTO.getMeetingResults().contains("-1")) {
            sql.append(" AND (MEETING_RESULT "+ DbUtil.createInQuery("meetingResults",dsaResultMeetingSearchDTO.getMeetingResults())+ " OR MEETING_RESULT IS NULL) ");
        }else if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getMeetingResults())){
            sql.append(" AND MEETING_RESULT "+ DbUtil.createInQuery("meetingResults",dsaResultMeetingSearchDTO.getMeetingResults())+ "");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getAssigned())) {
            sql.append(" AND ASSIGNED = :assigned ");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getTypeMeeting()) && DataUtil.safeEqual(dsaResultMeetingSearchDTO.getTypeMeeting(),0)) {
            sql.append(" AND MEETING_RESULT is null ");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getTypeMeeting()) && DataUtil.safeEqual(dsaResultMeetingSearchDTO.getTypeMeeting(),1)) {
            sql.append(" AND MEETING_RESULT is not null ");
        }
        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getCustomerPhone())) {
            sql.append(" AND CUSTOMER_PHONE like :customerPhone ");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getAcceptStatus())) {
            sql.append(" AND accept_status = :acceptStatus ");
        }

        sql.append(" AND (accept_status = 1 OR accept_status = 5) ");
//        sql.append(" AND DATE(VALID_DATE) >= DATE(SYSDATE()) ");

        if(DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getSortField()))
            sql.append(" ORDER BY FIELD(meeting_result,'2','3','4','5','6','7','1'), meeting_date desc, cca_meeting_date desc ");
        else {
            sql.append(" ORDER BY " + dsaResultMeetingSearchDTO.getSortField() + " " + dsaResultMeetingSearchDTO.getSortOrder());
        }

        String sqlCount = MessageFormat.format(sql.toString(), "count(*)");
        Query queryCount = em.createNativeQuery(sqlCount);
        String sqlSelect = MessageFormat.format(sql.toString(), " * ");
        Query querySelect = em.createNativeQuery(sqlSelect, DsaResultMeeting.class);
        querySelect.setFirstResult(dsaResultMeetingSearchDTO.getFirst());
        querySelect.setMaxResults(dsaResultMeetingSearchDTO.getPageSize());

        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getProvinceMeetingCode())) {
            queryCount.setParameter("provinceId", dsaResultMeetingSearchDTO.getProvinceMeetingCode());
            querySelect.setParameter("provinceId", dsaResultMeetingSearchDTO.getProvinceMeetingCode());
        }
        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getDistrictMeetingCodes())) {
            DbUtil.setParamInQuery(queryCount,"districtId",dsaResultMeetingSearchDTO.getDistrictMeetingCodes());
            DbUtil.setParamInQuery(querySelect,"districtId",dsaResultMeetingSearchDTO.getDistrictMeetingCodes());
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getFromCallDate())) {
            queryCount.setParameter("fromCallDate", DateUtil.date2String(dsaResultMeetingSearchDTO.getFromCallDate()));
            querySelect.setParameter("fromCallDate", DateUtil.date2String(dsaResultMeetingSearchDTO.getFromCallDate()));
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getToCallDate())) {
            queryCount.setParameter("toCallDate", DateUtil.date2String(dsaResultMeetingSearchDTO.getToCallDate()));
            querySelect.setParameter("toCallDate", DateUtil.date2String(dsaResultMeetingSearchDTO.getToCallDate()));
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getMeetDate())) {
            queryCount.setParameter("meetingDate", DateUtil.date2String(dsaResultMeetingSearchDTO.getMeetDate()));
            querySelect.setParameter("meetingDate", DateUtil.date2String(dsaResultMeetingSearchDTO.getMeetDate()));
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getAssignDate())) {
            queryCount.setParameter("assignDate", DateUtil.date2String(dsaResultMeetingSearchDTO.getAssignDate()));
            querySelect.setParameter("assignDate", DateUtil.date2String(dsaResultMeetingSearchDTO.getAssignDate()));
        }

        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getDsaCodes())) {
            DbUtil.setParamInQuery(queryCount,"dsaCode",dsaResultMeetingSearchDTO.getDsaCodes());
            DbUtil.setParamInQuery(querySelect,"dsaCode",dsaResultMeetingSearchDTO.getDsaCodes());
        }

        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getMeetingResults())) {
            DbUtil.setParamInQuery(queryCount,"meetingResults",dsaResultMeetingSearchDTO.getMeetingResults());
            DbUtil.setParamInQuery(querySelect,"meetingResults",dsaResultMeetingSearchDTO.getMeetingResults());
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getAssigned())) {
            queryCount.setParameter("assigned", dsaResultMeetingSearchDTO.getAssigned());
            querySelect.setParameter("assigned", dsaResultMeetingSearchDTO.getAssigned());
        }

        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getCustomerPhone())) {
            queryCount.setParameter("customerPhone", "%"+dsaResultMeetingSearchDTO.getCustomerPhone()+"%");
            querySelect.setParameter("customerPhone", "%"+dsaResultMeetingSearchDTO.getCustomerPhone()+"%");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getAcceptStatus())) {
            queryCount.setParameter("acceptStatus", dsaResultMeetingSearchDTO.getAcceptStatus());
            querySelect.setParameter("acceptStatus", dsaResultMeetingSearchDTO.getAcceptStatus());
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getFromMeetDate())) {
            queryCount.setParameter("fromMeetDate", DateUtil.date2String(dsaResultMeetingSearchDTO.getFromMeetDate()));
            querySelect.setParameter("fromMeetDate", DateUtil.date2String(dsaResultMeetingSearchDTO.getFromMeetDate()));
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getToMeetDate())) {
            queryCount.setParameter("toMeetDate", DateUtil.date2String(dsaResultMeetingSearchDTO.getToMeetDate()));
            querySelect.setParameter("toMeetDate", DateUtil.date2String(dsaResultMeetingSearchDTO.getToMeetDate()));
        }

        responseSearch.setRowCount(DataUtil.safeToLong(queryCount.getSingleResult()));
        List<DsaResultMeeting> resultList = querySelect.getResultList();
        resultList.forEach(s -> s.setKey(s.getId().toString()));
        List<DsaResultMeetingDTO> dsaResultMeetingDTOs = mapper.toDtoBean(resultList);
        for (DsaResultMeetingDTO dsaResultMeetingDTO: dsaResultMeetingDTOs){
            if (!DataUtil.isNullObject(dsaResultMeetingDTO.getAcceptStatus())){
                OptionSetValueDTO optionSetValueDTO = optionSetValueService.findByCodeAndValue("SALE_CCA_ACCEPT_STATUS",DataUtil.safeToString(dsaResultMeetingDTO.getAcceptStatus()));
                if (!DataUtil.isNullObject(optionSetValueDTO)){
                    dsaResultMeetingDTO.setAcceptStatusName(optionSetValueDTO.getName());
                }
            }
            if (DataUtil.isNullObject(dsaResultMeetingDTO.getReturnCca()) || DataUtil.safeEqual(dsaResultMeetingDTO.getReturnCca(),1)){
                dsaResultMeetingDTO.setReturnCcaName("CCA");
            }else{
                dsaResultMeetingDTO.setReturnCcaName("DSA");
            }
        }
        responseSearch.setData(dsaResultMeetingDTOs);

        return responseSearch;
    }

    @Override
    public ResponseSearchDTO<DsaResultMeetingDTO> searchDsaResultMeetingForDsa(DsaResultMeetingSearchDTO dsaResultMeetingSearchDTO) throws Exception {
        ResponseSearchDTO<DsaResultMeetingDTO> responseSearch = new ResponseSearchDTO<>();
        StringBuilder sql = new StringBuilder("SELECT {0} FROM DSA_RESULT_MEETING WHERE 1 = 1 ");
        List<String> params = new ArrayList<>(); //sometime should use map

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getDsaCode())) {
            sql.append(" AND UPPER(DSA_CODE) = ? ");
            params.add(DataUtil.safeToUpper(dsaResultMeetingSearchDTO.getDsaCode()));
        }
        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getCustomerPhone())) {
            sql.append(" AND CUSTOMER_PHONE like ? ");
            params.add("%"+dsaResultMeetingSearchDTO.getCustomerPhone()+"%");
        }
        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getDistrictMeetingCode())) {
            sql.append(" AND district_meeting_code = ? ");
            params.add(dsaResultMeetingSearchDTO.getDistrictMeetingCode());
        }
        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getAcceptStatus()) && DataUtil.safeEqual(dsaResultMeetingSearchDTO.getAcceptStatus(), 1)) {
            sql.append(" AND accept_status = 1 ");
        }
        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getAcceptStatus()) && DataUtil.safeEqual(dsaResultMeetingSearchDTO.getAcceptStatus(), 0)) {
            sql.append(" AND (accept_status = 0 OR accept_status is null ) ");
        }
        sql.append(" AND ((MEETING_RESULT IS NOT NULL AND MEETING_RESULT IN (1,2,5,6,7)) OR MEETING_RESULT IS NULL) ");

        sql.append(" AND ((meeting_result is null and (date(meeting_date) >= DATE(SUBDATE(SYSDATE(),3)) OR date(cca_meeting_date) >= DATE(SUBDATE(SYSDATE(),3)) OR date(assigned_date) = date(SYSDATE()))) ");
        sql.append("      OR ((meeting_result = 1) and (date(meeting_date) >= DATE(SUBDATE(SYSDATE(),3)) OR date(cca_meeting_date) >= DATE(SUBDATE(SYSDATE(),3)))) ");
        sql.append("      OR (meeting_result <> 1)) ");
        sql.append(" ORDER BY FIELD(accept_status,1,5,0,null,2,3,4),FIELD(meeting_result,'2','3','4','5','6','7','1'), meeting_date desc, cca_meeting_date desc ");

        String sqlCount = MessageFormat.format(sql.toString(), "count(*)");
        Query queryCount = em.createNativeQuery(sqlCount);
        String sqlSelect = MessageFormat.format(sql.toString(), " * ");
        Query querySelect = em.createNativeQuery(sqlSelect, DsaResultMeeting.class);
        querySelect.setFirstResult(dsaResultMeetingSearchDTO.getFirst());
        querySelect.setMaxResults(dsaResultMeetingSearchDTO.getPageSize());
        for(int i = 0 ; i < params.size() ; i++) {
            queryCount.setParameter(i + 1, params.get(i));
            querySelect.setParameter(i + 1, params.get(i));
        }

        responseSearch.setRowCount(DataUtil.safeToLong(queryCount.getSingleResult()));
        List<DsaResultMeeting> resultList = querySelect.getResultList();
        resultList.forEach(s -> s.setKey(s.getId().toString()));

        List<DsaResultMeetingDTO> dsaResultMeetingDTOs = mapper.toDtoBean(resultList);
        for (DsaResultMeetingDTO dsaResultMeetingDTO:dsaResultMeetingDTOs){
            if (!DataUtil.isNullObject(dsaResultMeetingDTO.getKeepStatus()) && DataUtil.safeEqual(dsaResultMeetingDTO.getKeepStatus(),1)){
                dsaResultMeetingDTO.setKeepStatusValue(true);
            }else{
                dsaResultMeetingDTO.setKeepStatusValue(false);
            }
            if (!DataUtil.isNullObject(dsaResultMeetingDTO.getReturnCca()) && DataUtil.safeEqual(dsaResultMeetingDTO.getReturnCca(),1)){
                dsaResultMeetingDTO.setReturnCcaValue(true);
            }else{
                dsaResultMeetingDTO.setReturnCcaValue(false);
            }
        }
        responseSearch.setData(dsaResultMeetingDTOs);

        return responseSearch;
    }

    @Override
    public ResponseSearchDTO<DsaResultMeetingDTO> searchDsaResultMeetingForDsaSort(DsaResultMeetingSearchDTO dsaResultMeetingSearchDTO) throws Exception {
        ResponseSearchDTO<DsaResultMeetingDTO> responseSearch = new ResponseSearchDTO<>();
        List<String> params = new ArrayList<>(); //sometime should use map
        StringBuilder sql = new StringBuilder("SELECT {0} FROM ( ");
        sql.append(" (SELECT * FROM DSA_RESULT_MEETING WHERE 1 = 1 ");

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getDsaCode())) {
            sql.append(" AND UPPER(DSA_CODE) = ? ");
            params.add(DataUtil.safeToUpper(dsaResultMeetingSearchDTO.getDsaCode()));
        }
        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getCustomerPhone())) {
            sql.append(" AND CUSTOMER_PHONE like ? ");
            params.add("%"+dsaResultMeetingSearchDTO.getCustomerPhone()+"%");
        }
        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getDistrictMeetingCode())) {
            sql.append(" AND district_meeting_code = ? ");
            params.add(dsaResultMeetingSearchDTO.getDistrictMeetingCode());
        }
        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getAcceptStatus())) {
            sql.append(" AND accept_status = ? ");
            params.add(DataUtil.safeToString(dsaResultMeetingSearchDTO.getAcceptStatus()));
        }
        sql.append(" AND (accept_status = 1 OR accept_status = 5 OR accept_status is null) ");

        sql.append(" AND (MEETING_RESULT = 5 OR MEETING_RESULT IS NULL) ");

        sql.append(" AND (DATE(meeting_date) = DATE(SYSDATE()) OR DATE(cca_meeting_date) = DATE(SYSDATE())) ");
        sql.append(" AND accept_status IN (1,5) ");
        sql.append(" AND (return_cca = 0 OR return_cca is null) ");
        sql.append(" ORDER BY FIELD(accept_status,1,5,0,null,2,3,4),FIELD(meeting_result,'2','3','4','5','6','7','1'), meeting_date desc, cca_meeting_date desc ) ");
        sql.append(" UNION ");
        sql.append(" (SELECT * FROM DSA_RESULT_MEETING WHERE 1 = 1 ");

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getDsaCode())) {
            sql.append(" AND UPPER(DSA_CODE) = ? ");
            params.add(DataUtil.safeToUpper(dsaResultMeetingSearchDTO.getDsaCode()));
        }
        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getCustomerPhone())) {
            sql.append(" AND CUSTOMER_PHONE like ? ");
            params.add("%"+dsaResultMeetingSearchDTO.getCustomerPhone()+"%");
        }
        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getDistrictMeetingCode())) {
            sql.append(" AND district_meeting_code = ? ");
            params.add(dsaResultMeetingSearchDTO.getDistrictMeetingCode());
        }
        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getAcceptStatus())) {
            sql.append(" AND accept_status = ? ");
            params.add(DataUtil.safeToString(dsaResultMeetingSearchDTO.getAcceptStatus()));
        }
        sql.append(" AND (accept_status = 1 OR accept_status = 5 OR accept_status is null) ");
        sql.append(" AND (MEETING_RESULT = 5 OR MEETING_RESULT IS NULL) ");

        sql.append(" AND (DATE(meeting_date) = DATE(SYSDATE()) OR DATE(cca_meeting_date) = DATE(SYSDATE())) ");
        sql.append(" AND accept_status IN (null,0,2,3,4) ");
        sql.append(" AND (return_cca = 0 OR return_cca is null) ");
        sql.append(" ORDER BY FIELD(accept_status,1,5,0,null,2,3,4),FIELD(meeting_result,'2','3','4','5','6','7','1'), meeting_date desc, cca_meeting_date desc )) DSA_INPUT_MEETING ");

        String sqlCount = MessageFormat.format(sql.toString(), "count(*)");
        Query queryCount = em.createNativeQuery(sqlCount);
        String sqlSelect = MessageFormat.format(sql.toString(), " * ");
        Query querySelect = em.createNativeQuery(sqlSelect, DsaResultMeeting.class);
        querySelect.setFirstResult(dsaResultMeetingSearchDTO.getFirst());
        querySelect.setMaxResults(dsaResultMeetingSearchDTO.getPageSize());
        for(int i = 0 ; i < params.size() ; i++) {
            queryCount.setParameter(i + 1, params.get(i));
            querySelect.setParameter(i + 1, params.get(i));
        }

        responseSearch.setRowCount(DataUtil.safeToLong(queryCount.getSingleResult()));
        List<DsaResultMeeting> resultList = querySelect.getResultList();
        resultList.forEach(s -> s.setKey(s.getId().toString()));

        List<DsaResultMeetingDTO> dsaResultMeetingDTOs = mapper.toDtoBean(resultList);
        for (DsaResultMeetingDTO dsaResultMeetingDTO:dsaResultMeetingDTOs){
            if (!DataUtil.isNullObject(dsaResultMeetingDTO.getKeepStatus()) && DataUtil.safeEqual(dsaResultMeetingDTO.getKeepStatus(),1)){
                dsaResultMeetingDTO.setKeepStatusValue(true);
            }else{
                dsaResultMeetingDTO.setKeepStatusValue(false);
            }

            if (!DataUtil.isNullObject(dsaResultMeetingDTO.getReturnCca()) && DataUtil.safeEqual(dsaResultMeetingDTO.getReturnCca(),1)){
                dsaResultMeetingDTO.setReturnCcaValue(true);
            }else{
                dsaResultMeetingDTO.setReturnCcaValue(false);
            }
        }
        responseSearch.setData(dsaResultMeetingDTOs);

        return responseSearch;
    }

    @Override
    public ResponseSearchDTO<DsaResultMeetingDTO> searchDsaResultMeetingForCca(DsaResultMeetingSearchDTO dsaResultMeetingSearchDTO) throws Exception {
        ResponseSearchDTO<DsaResultMeetingDTO> responseSearch = new ResponseSearchDTO<>();
        StringBuilder sql = new StringBuilder("SELECT {0} FROM DSA_RESULT_MEETING WHERE 1 = 1 ");
        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getProvinceMeetingCode())) {
            sql.append(" AND PROVINCE_MEETING_CODE = :provinceId ");
        }
        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getDistrictMeetingCodes())) {
            sql.append(" AND DISTRICT_MEETING_CODE "+ DbUtil.createInQuery("districtId",dsaResultMeetingSearchDTO.getDistrictMeetingCodes())+ "");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getFromCallDate())) {
            sql.append(" AND DATE(CALL_DATE) >= :fromCallDate");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getToCallDate())) {
            sql.append(" AND DATE(CALL_DATE) <= :toCallDate");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getMeetDate())) {
            sql.append(" AND (DATE(CCA_MEETING_DATE) = :meetingDate OR DATE(MEETING_DATE) = :meetingDate) ");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getFromMeetDate())) {
            sql.append(" AND (DATE(CCA_MEETING_DATE) >= :fromMeetDate OR DATE(MEETING_DATE) >= :fromMeetDate) ");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getToMeetDate())) {
            sql.append(" AND (DATE(CCA_MEETING_DATE) <= :toMeetDate OR DATE(MEETING_DATE) <= :toMeetDate) ");
        }

        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getMeetingResults())) {
            sql.append(" AND MEETING_RESULT "+ DbUtil.createInQuery("meetingResults",dsaResultMeetingSearchDTO.getMeetingResults())+ "");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getAssigned())) {
            sql.append(" AND ASSIGNED = :assigned ");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getTypeMeeting()) && DataUtil.safeEqual(dsaResultMeetingSearchDTO.getTypeMeeting(),0)) {
            sql.append(" AND MEETING_RESULT is null ");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getTypeMeeting()) && DataUtil.safeEqual(dsaResultMeetingSearchDTO.getTypeMeeting(),1)) {
            sql.append(" AND MEETING_RESULT is not null ");
        }
        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getCustomerPhone())) {
            sql.append(" AND CUSTOMER_PHONE like :customerPhone ");
        }

        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getJtlCcaCode())) {
            sql.append(" AND UPPER(JTL_CCA_CODE) = :jtlCcaCode ");
        }

        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getCcaCode())) {
            sql.append(" AND UPPER(CCA_CODE) = :ccaCode ");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getAcceptStatus()) && DataUtil.safeEqual(dsaResultMeetingSearchDTO.getAcceptStatus(), -1)) {
            sql.append(" AND ACCEPT_STATUS IS NULL ");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getAcceptStatus()) && !DataUtil.safeEqual(dsaResultMeetingSearchDTO.getAcceptStatus(), -1)) {
            sql.append(" AND ACCEPT_STATUS = :acceptStatus ");
        }

        sql.append(" AND DATE(SUBDATE(VALID_DATE, INTERVAL 5 DAY)) >= DATE(SYSDATE()) ");

        if(DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getSortField()))
            sql.append(" ORDER BY FIELD(return_cca,'1','0',null),FIELD(accept_status,null,'5','1','2','3','4'), call_date desc, meeting_date desc, cca_meeting_date desc ");
        else {
            sql.append(" ORDER BY " + dsaResultMeetingSearchDTO.getSortField() + " " + dsaResultMeetingSearchDTO.getSortOrder());
        }

        String sqlCount = MessageFormat.format(sql.toString(), "count(*)");
        Query queryCount = em.createNativeQuery(sqlCount);
        String sqlSelect = MessageFormat.format(sql.toString(), " * ");
        Query querySelect = em.createNativeQuery(sqlSelect, DsaResultMeeting.class);
        querySelect.setFirstResult(dsaResultMeetingSearchDTO.getFirst());
        querySelect.setMaxResults(dsaResultMeetingSearchDTO.getPageSize());

        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getProvinceMeetingCode())) {
            queryCount.setParameter("provinceId", dsaResultMeetingSearchDTO.getProvinceMeetingCode());
            querySelect.setParameter("provinceId", dsaResultMeetingSearchDTO.getProvinceMeetingCode());
        }
        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getDistrictMeetingCodes())) {
            DbUtil.setParamInQuery(queryCount,"districtId",dsaResultMeetingSearchDTO.getDistrictMeetingCodes());
            DbUtil.setParamInQuery(querySelect,"districtId",dsaResultMeetingSearchDTO.getDistrictMeetingCodes());
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getFromCallDate())) {
            queryCount.setParameter("fromCallDate", DateUtil.date2String(dsaResultMeetingSearchDTO.getFromCallDate()));
            querySelect.setParameter("fromCallDate", DateUtil.date2String(dsaResultMeetingSearchDTO.getFromCallDate()));
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getToCallDate())) {
            queryCount.setParameter("toCallDate", DateUtil.date2String(dsaResultMeetingSearchDTO.getToCallDate()));
            querySelect.setParameter("toCallDate", DateUtil.date2String(dsaResultMeetingSearchDTO.getToCallDate()));
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getMeetDate())) {
            queryCount.setParameter("meetingDate", DateUtil.date2String(dsaResultMeetingSearchDTO.getMeetDate()));
            querySelect.setParameter("meetingDate", DateUtil.date2String(dsaResultMeetingSearchDTO.getMeetDate()));
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getFromMeetDate())) {
            queryCount.setParameter("fromMeetDate", DateUtil.date2String(dsaResultMeetingSearchDTO.getFromMeetDate()));
            querySelect.setParameter("fromMeetDate", DateUtil.date2String(dsaResultMeetingSearchDTO.getFromMeetDate()));
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getToMeetDate())) {
            queryCount.setParameter("toMeetDate", DateUtil.date2String(dsaResultMeetingSearchDTO.getToMeetDate()));
            querySelect.setParameter("toMeetDate", DateUtil.date2String(dsaResultMeetingSearchDTO.getToMeetDate()));
        }

        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getMeetingResults())) {
            DbUtil.setParamInQuery(queryCount,"meetingResults",dsaResultMeetingSearchDTO.getMeetingResults());
            DbUtil.setParamInQuery(querySelect,"meetingResults",dsaResultMeetingSearchDTO.getMeetingResults());
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getAssigned())) {
            queryCount.setParameter("assigned", dsaResultMeetingSearchDTO.getAssigned());
            querySelect.setParameter("assigned", dsaResultMeetingSearchDTO.getAssigned());
        }

        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getCustomerPhone())) {
            queryCount.setParameter("customerPhone", "%"+dsaResultMeetingSearchDTO.getCustomerPhone()+"%");
            querySelect.setParameter("customerPhone", "%"+dsaResultMeetingSearchDTO.getCustomerPhone()+"%");
        }

        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getJtlCcaCode())) {
            queryCount.setParameter("jtlCcaCode", DataUtil.safeToUpper(dsaResultMeetingSearchDTO.getJtlCcaCode()));
            querySelect.setParameter("jtlCcaCode", DataUtil.safeToUpper(dsaResultMeetingSearchDTO.getJtlCcaCode()));
        }

        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getCcaCode())) {
            queryCount.setParameter("ccaCode", DataUtil.safeToUpper(dsaResultMeetingSearchDTO.getCcaCode()));
            querySelect.setParameter("ccaCode", DataUtil.safeToUpper(dsaResultMeetingSearchDTO.getCcaCode()));
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getAcceptStatus()) && !DataUtil.safeEqual(dsaResultMeetingSearchDTO.getAcceptStatus(), -1)) {
            queryCount.setParameter("acceptStatus", DataUtil.safeToString(dsaResultMeetingSearchDTO.getAcceptStatus()));
            querySelect.setParameter("acceptStatus", DataUtil.safeToString(dsaResultMeetingSearchDTO.getAcceptStatus()));
        }

        responseSearch.setRowCount(DataUtil.safeToLong(queryCount.getSingleResult()));
        List<DsaResultMeeting> resultList = querySelect.getResultList();
        resultList.forEach(s -> s.setKey(s.getId().toString()));
        responseSearch.setData(mapper.toDtoBean(resultList));

        return responseSearch;
    }

    @Override
    public ResponseSearchDTO<DsaResultMeetingDTO> searchDsaResultMeetingForCcaSort(DsaResultMeetingSearchDTO dsaResultMeetingSearchDTO) throws Exception {
        ResponseSearchDTO<DsaResultMeetingDTO> responseSearch = new ResponseSearchDTO<>();
        StringBuilder sql = new StringBuilder("SELECT {0} FROM ( ");
        sql.append(" (SELECT *, 1 as filter FROM DSA_RESULT_MEETING WHERE 1 = 1 ");
        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getProvinceMeetingCode())) {
            sql.append(" AND PROVINCE_MEETING_CODE = :provinceId ");
        }
        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getDistrictMeetingCodes())) {
            sql.append(" AND DISTRICT_MEETING_CODE "+ DbUtil.createInQuery("districtId",dsaResultMeetingSearchDTO.getDistrictMeetingCodes())+ "");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getFromCallDate())) {
            sql.append(" AND DATE(CALL_DATE) >= :fromCallDate");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getToCallDate())) {
            sql.append(" AND DATE(CALL_DATE) <= :toCallDate");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getMeetDate())) {
            sql.append(" AND (DATE(CCA_MEETING_DATE) = :meetingDate OR DATE(MEETING_DATE) = :meetingDate) ");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getFromMeetDate())) {
//            sql.append(" AND (DATE(CCA_MEETING_DATE) >= :fromMeetDate OR DATE(MEETING_DATE) >= :fromMeetDate) ");
            sql.append(" AND ((DATE(cca_meeting_date) >= :fromMeetDate AND meeting_date is null) OR (DATE(meeting_date) >= :fromMeetDate AND meeting_date is not null)) ");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getToMeetDate())) {
//            sql.append(" AND (DATE(CCA_MEETING_DATE) <= :toMeetDate OR DATE(MEETING_DATE) <= :toMeetDate) ");
            sql.append(" AND ((DATE(cca_meeting_date) <= :toMeetDate AND meeting_date is null) OR (DATE(meeting_date) <= :toMeetDate and meeting_date is not null)) ");
        }

        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getMeetingResults())) {
            sql.append(" AND MEETING_RESULT "+ DbUtil.createInQuery("meetingResults",dsaResultMeetingSearchDTO.getMeetingResults())+ "");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getAssigned())) {
            sql.append(" AND ASSIGNED = :assigned ");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getTypeMeeting()) && DataUtil.safeEqual(dsaResultMeetingSearchDTO.getTypeMeeting(),0)) {
            sql.append(" AND MEETING_RESULT is null ");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getTypeMeeting()) && DataUtil.safeEqual(dsaResultMeetingSearchDTO.getTypeMeeting(),1)) {
            sql.append(" AND MEETING_RESULT is not null ");
        }
        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getCustomerPhone())) {
            sql.append(" AND CUSTOMER_PHONE like :customerPhone ");
        }

        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getJtlCcaCode())) {
            sql.append(" AND UPPER(JTL_CCA_CODE) = :jtlCcaCode ");
        }

        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getCcaCode())) {
            sql.append(" AND UPPER(CCA_CODE) like :ccaCode ");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getAcceptStatus()) && DataUtil.safeEqual(dsaResultMeetingSearchDTO.getAcceptStatus(), -1)) {
            sql.append(" AND ACCEPT_STATUS IS NULL ");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getAcceptStatus()) && !DataUtil.safeEqual(dsaResultMeetingSearchDTO.getAcceptStatus(), -1)) {
            sql.append(" AND ACCEPT_STATUS = :acceptStatus ");
        }

        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getPartnerCode())) {
            sql.append(" AND UPPER(PARTNER_CODE) = :partnerCode ");
        }

//        sql.append(" AND VALID_DATE >= DATE(SYSDATE()) ");

        sql.append(" AND ACCEPT_STATUS IS NULL) ");
        sql.append(" UNION ");
        sql.append(" (SELECT *, 2 as filter FROM DSA_RESULT_MEETING WHERE 1 = 1 ");
        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getProvinceMeetingCode())) {
            sql.append(" AND PROVINCE_MEETING_CODE = :provinceId ");
        }
        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getDistrictMeetingCodes())) {
            sql.append(" AND DISTRICT_MEETING_CODE "+ DbUtil.createInQuery("districtId",dsaResultMeetingSearchDTO.getDistrictMeetingCodes())+ "");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getFromCallDate())) {
            sql.append(" AND DATE(CALL_DATE) >= :fromCallDate");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getToCallDate())) {
            sql.append(" AND DATE(CALL_DATE) <= :toCallDate");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getMeetDate())) {
            sql.append(" AND (DATE(CCA_MEETING_DATE) = :meetingDate OR DATE(MEETING_DATE) = :meetingDate) ");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getFromMeetDate())) {
//            sql.append(" AND (DATE(CCA_MEETING_DATE) >= :fromMeetDate OR DATE(MEETING_DATE) >= :fromMeetDate) ");
            sql.append(" AND ((DATE(cca_meeting_date) >= :fromMeetDate AND meeting_date is null) OR (DATE(meeting_date) >= :fromMeetDate AND meeting_date is not null)) ");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getToMeetDate())) {
//            sql.append(" AND (DATE(CCA_MEETING_DATE) <= :toMeetDate OR DATE(MEETING_DATE) <= :toMeetDate) ");
            sql.append(" AND ((DATE(cca_meeting_date) <= :toMeetDate AND meeting_date is null) OR (DATE(meeting_date) <= :toMeetDate and meeting_date is not null)) ");
        }

        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getMeetingResults())) {
            sql.append(" AND MEETING_RESULT "+ DbUtil.createInQuery("meetingResults",dsaResultMeetingSearchDTO.getMeetingResults())+ "");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getAssigned())) {
            sql.append(" AND ASSIGNED = :assigned ");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getTypeMeeting()) && DataUtil.safeEqual(dsaResultMeetingSearchDTO.getTypeMeeting(),0)) {
            sql.append(" AND MEETING_RESULT is null ");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getTypeMeeting()) && DataUtil.safeEqual(dsaResultMeetingSearchDTO.getTypeMeeting(),1)) {
            sql.append(" AND MEETING_RESULT is not null ");
        }
        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getCustomerPhone())) {
            sql.append(" AND CUSTOMER_PHONE like :customerPhone ");
        }

        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getJtlCcaCode())) {
            sql.append(" AND UPPER(JTL_CCA_CODE) = :jtlCcaCode ");
        }

        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getCcaCode())) {
            sql.append(" AND UPPER(CCA_CODE) like :ccaCode ");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getAcceptStatus()) && DataUtil.safeEqual(dsaResultMeetingSearchDTO.getAcceptStatus(), -1)) {
            sql.append(" AND ACCEPT_STATUS IS NULL ");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getAcceptStatus()) && !DataUtil.safeEqual(dsaResultMeetingSearchDTO.getAcceptStatus(), -1)) {
            sql.append(" AND ACCEPT_STATUS = :acceptStatus ");
        }

        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getPartnerCode())) {
            sql.append(" AND UPPER(PARTNER_CODE) = :partnerCode ");
        }

//        sql.append(" AND VALID_DATE >= DATE(SYSDATE()) ");
        sql.append(" AND ACCEPT_STATUS IS NOT NULL ");
        sql.append(" )) DSA_RESULT_CCA ");
        sql.append(" ORDER BY filter, FIELD(return_cca,1,0), FIELD(accept_status,null,5,1,2,3,4), call_date desc, meeting_date desc, cca_meeting_date desc ");


        String sqlCount = MessageFormat.format(sql.toString(), "count(*)");
        Query queryCount = em.createNativeQuery(sqlCount);
        String sqlSelect = MessageFormat.format(sql.toString(), " * ");
        Query querySelect = em.createNativeQuery(sqlSelect, DsaResultMeeting.class);
        querySelect.setFirstResult(dsaResultMeetingSearchDTO.getFirst());
        querySelect.setMaxResults(dsaResultMeetingSearchDTO.getPageSize());

        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getProvinceMeetingCode())) {
            queryCount.setParameter("provinceId", dsaResultMeetingSearchDTO.getProvinceMeetingCode());
            querySelect.setParameter("provinceId", dsaResultMeetingSearchDTO.getProvinceMeetingCode());
        }
        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getDistrictMeetingCodes())) {
            DbUtil.setParamInQuery(queryCount,"districtId",dsaResultMeetingSearchDTO.getDistrictMeetingCodes());
            DbUtil.setParamInQuery(querySelect,"districtId",dsaResultMeetingSearchDTO.getDistrictMeetingCodes());
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getFromCallDate())) {
            queryCount.setParameter("fromCallDate", DateUtil.date2String(dsaResultMeetingSearchDTO.getFromCallDate()));
            querySelect.setParameter("fromCallDate", DateUtil.date2String(dsaResultMeetingSearchDTO.getFromCallDate()));
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getToCallDate())) {
            queryCount.setParameter("toCallDate", DateUtil.date2String(dsaResultMeetingSearchDTO.getToCallDate()));
            querySelect.setParameter("toCallDate", DateUtil.date2String(dsaResultMeetingSearchDTO.getToCallDate()));
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getMeetDate())) {
            queryCount.setParameter("meetingDate", DateUtil.date2String(dsaResultMeetingSearchDTO.getMeetDate()));
            querySelect.setParameter("meetingDate", DateUtil.date2String(dsaResultMeetingSearchDTO.getMeetDate()));
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getFromMeetDate())) {
            queryCount.setParameter("fromMeetDate", DateUtil.date2String(dsaResultMeetingSearchDTO.getFromMeetDate()));
            querySelect.setParameter("fromMeetDate", DateUtil.date2String(dsaResultMeetingSearchDTO.getFromMeetDate()));
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getToMeetDate())) {
            queryCount.setParameter("toMeetDate", DateUtil.date2String(dsaResultMeetingSearchDTO.getToMeetDate()));
            querySelect.setParameter("toMeetDate", DateUtil.date2String(dsaResultMeetingSearchDTO.getToMeetDate()));
        }

        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getMeetingResults())) {
            DbUtil.setParamInQuery(queryCount,"meetingResults",dsaResultMeetingSearchDTO.getMeetingResults());
            DbUtil.setParamInQuery(querySelect,"meetingResults",dsaResultMeetingSearchDTO.getMeetingResults());
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getAssigned())) {
            queryCount.setParameter("assigned", dsaResultMeetingSearchDTO.getAssigned());
            querySelect.setParameter("assigned", dsaResultMeetingSearchDTO.getAssigned());
        }

        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getCustomerPhone())) {
            queryCount.setParameter("customerPhone", "%"+dsaResultMeetingSearchDTO.getCustomerPhone()+"%");
            querySelect.setParameter("customerPhone", "%"+dsaResultMeetingSearchDTO.getCustomerPhone()+"%");
        }

        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getJtlCcaCode())) {
            queryCount.setParameter("jtlCcaCode", DataUtil.safeToUpper(dsaResultMeetingSearchDTO.getJtlCcaCode()));
            querySelect.setParameter("jtlCcaCode", DataUtil.safeToUpper(dsaResultMeetingSearchDTO.getJtlCcaCode()));
        }

        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getCcaCode())) {
            queryCount.setParameter("ccaCode", "%"+DataUtil.safeToUpper(dsaResultMeetingSearchDTO.getCcaCode())+"%");
            querySelect.setParameter("ccaCode", "%"+DataUtil.safeToUpper(dsaResultMeetingSearchDTO.getCcaCode())+"%");
        }

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getAcceptStatus()) && !DataUtil.safeEqual(dsaResultMeetingSearchDTO.getAcceptStatus(), -1)) {
            queryCount.setParameter("acceptStatus", DataUtil.safeToString(dsaResultMeetingSearchDTO.getAcceptStatus()));
            querySelect.setParameter("acceptStatus", DataUtil.safeToString(dsaResultMeetingSearchDTO.getAcceptStatus()));
        }

        if(!DataUtil.isNullOrEmpty(dsaResultMeetingSearchDTO.getPartnerCode())) {
            queryCount.setParameter("partnerCode", DataUtil.safeToUpper(dsaResultMeetingSearchDTO.getPartnerCode()));
            querySelect.setParameter("partnerCode", DataUtil.safeToUpper(dsaResultMeetingSearchDTO.getPartnerCode()));
        }

        responseSearch.setRowCount(DataUtil.safeToLong(queryCount.getSingleResult()));
        List<DsaResultMeeting> resultList = querySelect.getResultList();
        resultList.forEach(s -> s.setKey(s.getId().toString()));
        List<DsaResultMeetingDTO> dsaResultMeetingDTOs = mapper.toDtoBean(resultList);
        for (DsaResultMeetingDTO dsaResultMeetingDTO: dsaResultMeetingDTOs){
            if (!DataUtil.isNullObject(dsaResultMeetingDTO.getAcceptStatus())){
                OptionSetValueDTO optionSetValueDTO = optionSetValueService.findByCodeAndValue("SALE_CCA_ACCEPT_STATUS",DataUtil.safeToString(dsaResultMeetingDTO.getAcceptStatus()));
                if (!DataUtil.isNullObject(optionSetValueDTO)){
                    dsaResultMeetingDTO.setAcceptStatusName(optionSetValueDTO.getName());
                }
            }
            if (DataUtil.isNullObject(dsaResultMeetingDTO.getReturnCca()) || DataUtil.safeEqual(dsaResultMeetingDTO.getReturnCca(),1)){
                dsaResultMeetingDTO.setReturnCcaName("CCA");
            }else{
                dsaResultMeetingDTO.setReturnCcaName("DSA");
            }
        }
        responseSearch.setData(dsaResultMeetingDTOs);

        return responseSearch;
    }

    @Override
    public ResponseSearchDTO<DsaResultMeetingDTO> searchDsaResultMeetingAccepted(DsaResultMeetingSearchDTO dsaResultMeetingSearchDTO) throws Exception {
        ResponseSearchDTO<DsaResultMeetingDTO> responseSearch = new ResponseSearchDTO<>();
        StringBuilder sql = new StringBuilder("SELECT {0} FROM DSA_RESULT_MEETING WHERE 1 = 1 ");
        List<String> params = new ArrayList<>(); //sometime should use map

        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getDsaCode())) {
            sql.append(" AND UPPER(DSA_CODE) = ? ");
            params.add(DataUtil.safeToUpper(dsaResultMeetingSearchDTO.getDsaCode()));
        }
        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getCustomerPhone())) {
            sql.append(" AND CUSTOMER_PHONE like ? ");
            params.add("%"+dsaResultMeetingSearchDTO.getCustomerPhone()+"%");
        }
        if(!DataUtil.isNullObject(dsaResultMeetingSearchDTO.getDistrictMeetingCode())) {
            sql.append(" AND district_meeting_code = ? ");
            params.add(dsaResultMeetingSearchDTO.getDistrictMeetingCode());
        }
        sql.append(" AND meeting_result = 1 AND date(meeting_date) >= DATE(SUBDATE(SYSDATE(),7)) ");
        sql.append(" ORDER BY meeting_date desc ");

        String sqlCount = MessageFormat.format(sql.toString(), "count(*)");
        Query queryCount = em.createNativeQuery(sqlCount);
        String sqlSelect = MessageFormat.format(sql.toString(), " * ");
        Query querySelect = em.createNativeQuery(sqlSelect, DsaResultMeeting.class);
        querySelect.setFirstResult(dsaResultMeetingSearchDTO.getFirst());
        querySelect.setMaxResults(dsaResultMeetingSearchDTO.getPageSize());
        for(int i = 0 ; i < params.size() ; i++) {
            queryCount.setParameter(i + 1, params.get(i));
            querySelect.setParameter(i + 1, params.get(i));
        }

        responseSearch.setRowCount(DataUtil.safeToLong(queryCount.getSingleResult()));
        List<DsaResultMeeting> resultList = querySelect.getResultList();
        resultList.forEach(s -> s.setKey(s.getId().toString()));

        List<DsaResultMeetingDTO> dsaResultMeetingDTOs = mapper.toDtoBean(resultList);
        responseSearch.setData(dsaResultMeetingDTOs);

        return responseSearch;
    }

    @Override
    public ResponseSearchDTO<RatioCollectMeetingDTO> searchRatioCollectMeeting(RatioCollectMeetingSearchDTO ratioCollectMeetingSearchDTO) throws Exception {
        ResponseSearchDTO<RatioCollectMeetingDTO> responseSearch = new ResponseSearchDTO<>();
        List<RatioCollectMeetingDTO> ratioCollectMeetingDTOs = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT {0} FROM ( ");
        List<String> params = new ArrayList<>(); //sometime should use map

        sql.append(" SELECT drm.province_meeting_code, drm.district_meeting_code, ");
        sql.append(" (SELECT name FROM area WHERE 1=1 AND type = ''PROVINCE'' AND code = drm.province_meeting_code) PROVINCE_NAME, ");
        sql.append(" (SELECT name FROM area WHERE 1=1 AND type = ''DISTRICT'' AND code = drm.district_meeting_code) DISTRICT_NAME, ");
        sql.append(" drm.tl_dsa_code, drm.dsa_code, ");
        sql.append(" (SELECT count(*) FROM dsa_result_meeting WHERE 1=1 ");
        sql.append("    AND  province_meeting_code = drm.province_meeting_code ");
        sql.append("    AND district_meeting_code = drm.district_meeting_code ");
        sql.append("    AND tl_dsa_code = drm.tl_dsa_code ");
        sql.append("    AND dsa_code = drm.dsa_code ");
        sql.append("    AND accept_status = 1 ");
        if (!DataUtil.isNullObject(ratioCollectMeetingSearchDTO.getFromDate())){
            sql.append(" AND DATE(return_cca_date) >= :fromDate ");
        }
        if (!DataUtil.isNullObject(ratioCollectMeetingSearchDTO.getToDate())){
            sql.append(" AND DATE(return_cca_date) <= :toDate ");
        }
        sql.append(" AND return_cca = 0 ");
        sql.append("    ) TOTAL_ACCEPT, ");
        sql.append(" (SELECT count(*) FROM dsa_result_meeting WHERE 1=1 ");
        sql.append("    AND  province_meeting_code = drm.province_meeting_code ");
        sql.append("    AND district_meeting_code = drm.district_meeting_code ");
        sql.append("    AND tl_dsa_code = drm.tl_dsa_code ");
        sql.append("    AND dsa_code = drm.dsa_code ");
        sql.append("    AND accept_status = 1 ");
        if (!DataUtil.isNullObject(ratioCollectMeetingSearchDTO.getFromDate())){
            sql.append(" AND DATE(return_cca_date) >= :fromDate ");
        }
        if (!DataUtil.isNullObject(ratioCollectMeetingSearchDTO.getToDate())){
            sql.append(" AND DATE(return_cca_date) <= :toDate ");
        }
        sql.append(" AND return_cca = 0 ");
        sql.append(" ) NOT_CONNECTED, ");
        sql.append(" (SELECT count(*) FROM dsa_result_meeting WHERE 1=1 ");
        sql.append("    AND  province_meeting_code = drm.province_meeting_code ");
        sql.append("    AND district_meeting_code = drm.district_meeting_code ");
        sql.append("    AND tl_dsa_code = drm.tl_dsa_code ");
        sql.append("    AND dsa_code = drm.dsa_code ");
        sql.append("    AND meeting_result = 1 ");
        if (!DataUtil.isNullObject(ratioCollectMeetingSearchDTO.getFromDate())){
            sql.append(" AND DATE(return_cca_date) >= :fromDate ");
        }
        if (!DataUtil.isNullObject(ratioCollectMeetingSearchDTO.getToDate())){
            sql.append(" AND DATE(return_cca_date) <= :toDate ");
        }
        sql.append(" AND return_cca = 0 ");
        sql.append("    ) FULL_DOCUMENT, ");
        sql.append(" (SELECT count(*) FROM dsa_result_meeting WHERE 1=1 ");
        sql.append("    AND  province_meeting_code = drm.province_meeting_code ");
        sql.append("    AND district_meeting_code = drm.district_meeting_code ");
        sql.append("    AND tl_dsa_code = drm.tl_dsa_code ");
        sql.append("    AND dsa_code = drm.dsa_code ");
        if (!DataUtil.isNullObject(ratioCollectMeetingSearchDTO.getFromDate())){
            sql.append(" AND DATE(return_cca_date) >= :fromDate ");
        }
        if (!DataUtil.isNullObject(ratioCollectMeetingSearchDTO.getToDate())){
            sql.append(" AND DATE(return_cca_date) <= :toDate ");
        }
        sql.append("    AND return_cca = 1 ");
        sql.append("    ) RETURN_CCA, ");
        sql.append(" (SELECT count(*) FROM dsa_result_meeting WHERE 1=1 ");
        sql.append("    AND  province_meeting_code = drm.province_meeting_code ");
        sql.append("    AND district_meeting_code = drm.district_meeting_code ");
        sql.append("    AND tl_dsa_code = drm.tl_dsa_code ");
        sql.append("    AND dsa_code = drm.dsa_code ");
        if (!DataUtil.isNullObject(ratioCollectMeetingSearchDTO.getFromDate())){
            sql.append(" AND DATE(return_cca_date) >= :fromDate ");
        }
        if (!DataUtil.isNullObject(ratioCollectMeetingSearchDTO.getToDate())){
            sql.append(" AND DATE(return_cca_date) <= :toDate ");
        }
        sql.append(" AND return_cca = 0 ");
        sql.append("    ) TOTAL_MEETING ");
        sql.append(" FROM dsa_result_meeting drm WHERE 1=1 ");
        sql.append(" AND drm.return_cca = 0 ");

        if (!DataUtil.isNullOrEmpty(ratioCollectMeetingSearchDTO.getProvinceCode())){
            sql.append(" AND drm.province_meeting_code = :provinceMeetingCode ");
        }
        if (!DataUtil.isNullOrEmpty(ratioCollectMeetingSearchDTO.getDistrictCode())){
            sql.append(" AND drm.district_meeting_code = :districtMeetingCode ");
        }
        if (!DataUtil.isNullObject(ratioCollectMeetingSearchDTO.getFromDate())){
            sql.append(" AND DATE(drm.return_cca_date) >= :fromDate ");
        }
        if (!DataUtil.isNullObject(ratioCollectMeetingSearchDTO.getToDate())){
            sql.append(" AND DATE(drm.return_cca_date) <= :toDate ");
        }
        if (!DataUtil.isNullOrEmpty(ratioCollectMeetingSearchDTO.getJtlDsaCode())) {
            sql.append(" AND UPPER(tl_dsa_code) = UPPER(:tlDsaCode) ");
        }
        sql.append(" AND tl_dsa_code is not null ");
        sql.append("GROUP BY drm.province_meeting_code,drm.district_meeting_code,drm.tl_dsa_code,drm.dsa_code) COLLECT_REPORT ");

        String sqlCount = MessageFormat.format(sql.toString(), "count(*)");
        Query queryCount = em.createNativeQuery(sqlCount);
        String sqlSelect = MessageFormat.format(sql.toString(), " * ");
        Query querySelect = em.createNativeQuery(sqlSelect);
        querySelect.setFirstResult(ratioCollectMeetingSearchDTO.getFirst());
        querySelect.setMaxResults(ratioCollectMeetingSearchDTO.getPageSize());
        if (!DataUtil.isNullObject(ratioCollectMeetingSearchDTO.getFromDate())) {
            queryCount.setParameter("fromDate", DateUtil.date2String(ratioCollectMeetingSearchDTO.getFromDate()));
            querySelect.setParameter("fromDate", DateUtil.date2String(ratioCollectMeetingSearchDTO.getFromDate()));
        }
        if (!DataUtil.isNullObject(ratioCollectMeetingSearchDTO.getToDate())) {
            queryCount.setParameter("toDate", DateUtil.date2String(ratioCollectMeetingSearchDTO.getToDate()));
            querySelect.setParameter("toDate", DateUtil.date2String(ratioCollectMeetingSearchDTO.getToDate()));
        }
        if (!DataUtil.isNullOrEmpty(ratioCollectMeetingSearchDTO.getProvinceCode())){
            queryCount.setParameter("provinceMeetingCode", ratioCollectMeetingSearchDTO.getProvinceCode());
            querySelect.setParameter("provinceMeetingCode", ratioCollectMeetingSearchDTO.getProvinceCode());
        }
        if (!DataUtil.isNullOrEmpty(ratioCollectMeetingSearchDTO.getDistrictCode())){
            queryCount.setParameter("districtMeetingCode", ratioCollectMeetingSearchDTO.getDistrictCode());
            querySelect.setParameter("districtMeetingCode", ratioCollectMeetingSearchDTO.getDistrictCode());
        }
        if (!DataUtil.isNullOrEmpty(ratioCollectMeetingSearchDTO.getJtlDsaCode())) {
            queryCount.setParameter("tlDsaCode", ratioCollectMeetingSearchDTO.getJtlDsaCode());
            querySelect.setParameter("tlDsaCode", ratioCollectMeetingSearchDTO.getJtlDsaCode());
        }

        responseSearch.setRowCount(DataUtil.safeToLong(queryCount.getSingleResult()));
        List resultList = querySelect.getResultList();
        if (!DataUtil.isNullOrEmpty(resultList)){
            RatioCollectMeetingDTO ratioCollectMeetingDTO;
            for (int i=0;i< resultList.size();i++) {
                Object[] obj = (Object[]) resultList.get(i);
                ratioCollectMeetingDTO = new RatioCollectMeetingDTO();
                ratioCollectMeetingDTO.setKey(DataUtil.safeToString(i));
                if (!DataUtil.isNullObject(obj[0])) {
                    ratioCollectMeetingDTO.setProvinceCode(obj[0].toString());
                }
                if (!DataUtil.isNullObject(obj[1])) {
                    ratioCollectMeetingDTO.setDistrictCode(obj[1].toString());
                }
                if (!DataUtil.isNullObject(obj[2])) {
                    ratioCollectMeetingDTO.setProvinceName(obj[2].toString());
                }
                if (!DataUtil.isNullObject(obj[3])) {
                    ratioCollectMeetingDTO.setDistrictName(obj[3].toString());
                }
                if (!DataUtil.isNullObject(obj[4])) {
                    ratioCollectMeetingDTO.setJtlDsaCode(obj[4].toString());
                }
                if (!DataUtil.isNullObject(obj[5])) {
                    ratioCollectMeetingDTO.setDsaCode(obj[5].toString());
                }
                if (!DataUtil.isNullObject(obj[6])) {
                    ratioCollectMeetingDTO.setTotalAccept(DataUtil.safeToLong(obj[6].toString()));
                }
                if (!DataUtil.isNullObject(obj[7])) {
                    ratioCollectMeetingDTO.setTotalNotConnected(DataUtil.safeToLong(obj[7].toString()));
                }
                if (!DataUtil.isNullObject(obj[8])) {
                    ratioCollectMeetingDTO.setTotalFullDocument(DataUtil.safeToLong(obj[8].toString()));
                }
                if (!DataUtil.isNullObject(obj[9])) {
                    ratioCollectMeetingDTO.setTotalReturnCca(DataUtil.safeToLong(obj[9].toString()));
                }
                if (!DataUtil.isNullObject(obj[10])) {
                    ratioCollectMeetingDTO.setTotalMeeting(DataUtil.safeToLong(obj[10].toString()));
                }

                if (!DataUtil.isNullOrZero(ratioCollectMeetingDTO.getTotalAccept())){
                    Double ratio = (double) ratioCollectMeetingDTO.getTotalFullDocument() /  ratioCollectMeetingDTO.getTotalAccept();
                    ratioCollectMeetingDTO.setRatioFullDocAndAccept((double)Math.round(ratio*10)/10 * 100);
                }else{
                    ratioCollectMeetingDTO.setRatioFullDocAndAccept((double) 0);
                }
                if (!DataUtil.isNullOrZero(ratioCollectMeetingDTO.getTotalNotConnected())){
                    Double ratio = (double) ratioCollectMeetingDTO.getTotalFullDocument() /  ratioCollectMeetingDTO.getTotalNotConnected();
                    ratioCollectMeetingDTO.setRatioFullDocAndNotConnected((double)Math.round(ratio*10)/10 * 100);
                }else{
                    ratioCollectMeetingDTO.setRatioFullDocAndNotConnected((double) 0);
                }
                if (!DataUtil.isNullOrZero(ratioCollectMeetingDTO.getTotalMeeting())){
                    Double ratio = (double) ratioCollectMeetingDTO.getTotalFullDocument() /  ratioCollectMeetingDTO.getTotalMeeting();
                    ratioCollectMeetingDTO.setRatioFullDocAndTotalMeeting((double)Math.round(ratio*10)/10 * 100);
                }else{
                    ratioCollectMeetingDTO.setRatioFullDocAndTotalMeeting((double) 0);
                }

                ratioCollectMeetingDTOs.add(ratioCollectMeetingDTO);
            }
        }
        responseSearch.setData(ratioCollectMeetingDTOs);

        return responseSearch;
    }

    @Override
    public List<AreaDTO> findAllDistrictByDsa(String dsaCode) throws Exception {
        List<AreaDTO> areaDTOs = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT DISTINCT district_meeting_code, district_meeting_name FROM DSA_RESULT_MEETING WHERE 1 = 1 ");
        List<String> params = new ArrayList<>(); //sometime should use map

        sql.append(" AND district_meeting_code is not null ");
        sql.append(" AND district_meeting_name is not null ");

        if(!DataUtil.isNullOrEmpty(dsaCode)) {
            sql.append(" AND UPPER(DSA_CODE) = ? ");
            params.add(DataUtil.safeToUpper(dsaCode));
        }
        sql.append(" AND ((meeting_result is null and (date(meeting_date) = date(SYSDATE()) OR date(cca_meeting_date) = date(SYSDATE()) OR date(assigned_date) = date(SYSDATE()))) ");
        sql.append("      OR ((meeting_result = 1) and (date(meeting_date) >= DATE(SUBDATE(SYSDATE(),3)) OR date(cca_meeting_date) >= DATE(SUBDATE(SYSDATE(),3)))) ");
        sql.append("      OR (meeting_result <> 1)) ");
        sql.append(" ORDER BY FIELD(meeting_result,'2','3','4','5','6','7','1'), meeting_date desc ");

        Query querySelect = em.createNativeQuery(sql.toString());
        for(int i = 0 ; i < params.size() ; i++) {
            querySelect.setParameter(i + 1, params.get(i));
        }
        List resultList = querySelect.getResultList();
        if (!DataUtil.isNullOrEmpty(resultList)){
            AreaDTO areaDTO;
            for (int i=0;i< resultList.size();i++) {
                Object[] obj = (Object[]) resultList.get(i);
                areaDTO = new AreaDTO();
                if (!DataUtil.isNullObject(obj[0])) {
                    areaDTO.setCode(obj[0].toString());
                }
                if (!DataUtil.isNullObject(obj[1])) {
                    areaDTO.setName(obj[1].toString());
                }
                areaDTOs.add(areaDTO);
            }
        }
        return areaDTOs;
    }

    @Override
    public List<AreaDTO> findAllDistrictByDsaAndDay(String dsaCode, Long dayQuery) throws Exception {
        List<AreaDTO> areaDTOs = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT DISTINCT district_meeting_code, district_meeting_name FROM DSA_RESULT_MEETING WHERE 1 = 1 ");
        List<String> params = new ArrayList<>(); //sometime should use map

        if(!DataUtil.isNullOrEmpty(dsaCode)) {
            sql.append(" AND UPPER(DSA_CODE) = ? ");
            params.add(DataUtil.safeToUpper(dsaCode));
        }
        sql.append(" AND meeting_result = 1 AND date(meeting_date) >= DATE(SUBDATE(SYSDATE(), ? )) ");
        params.add(DataUtil.safeToString(dayQuery));
        sql.append(" ORDER BY district_meeting_name asc ");

        Query querySelect = em.createNativeQuery(sql.toString());
        for(int i = 0 ; i < params.size() ; i++) {
            querySelect.setParameter(i + 1, params.get(i));
        }
        List resultList = querySelect.getResultList();
        if (!DataUtil.isNullOrEmpty(resultList)){
            AreaDTO areaDTO;
            for (int i=0;i< resultList.size();i++) {
                Object[] obj = (Object[]) resultList.get(i);
                areaDTO = new AreaDTO();
                if (!DataUtil.isNullObject(obj[0])) {
                    areaDTO.setCode(obj[0].toString());
                }
                if (!DataUtil.isNullObject(obj[1])) {
                    areaDTO.setName(obj[1].toString());
                }
                areaDTOs.add(areaDTO);
            }
        }
        return areaDTOs;
    }

    @Override
    public List<UserDTO> getAllDsaAndMeetingAvailiableByTlDsa(String tlDsaCode) throws Exception {
        List<UserDTO> userDTOs = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT u.user_name, u.full_name, ");
        List<String> params = new ArrayList<>(); //sometime should use map

        sql.append(" (SELECT count(*) FROM dsa_result_meeting WHERE 1=1 ");
        sql.append(" AND upper(dsa_code) = upper(u.user_name) ");
        sql.append(" AND (meeting_result IS NULL OR (meeting_result IS NOT NULL AND meeting_result IN(2,5)))) avaliable_meeting ");
        sql.append(" FROM USER u, DSA_ROLE dr WHERE 1=1 ");
        sql.append(" and u.user_name = dr.user_name ");
        sql.append(" and dr.role = 'DSA' ");
        sql.append(" and dr.status = 1 ");
        if(!DataUtil.isNullOrEmpty(tlDsaCode)) {
            sql.append(" and dr.lead_user_id = (select id from USER where UPPER(user_name) = ? ) ");
            params.add(DataUtil.safeToUpper(tlDsaCode));
        }
        Query querySelect = em.createNativeQuery(sql.toString());
        for(int i = 0 ; i < params.size() ; i++) {
            querySelect.setParameter(i + 1, params.get(i));
        }
        List resultList = querySelect.getResultList();
        if (!DataUtil.isNullOrEmpty(resultList)){
            UserDTO userDTO;
            for (int i=0;i< resultList.size();i++) {
                Object[] obj = (Object[]) resultList.get(i);
                userDTO = new UserDTO();
                if (!DataUtil.isNullObject(obj[0])) {
                    userDTO.setUserName(obj[0].toString());
                }
                if (!DataUtil.isNullObject(obj[1])) {
                    userDTO.setFullName(obj[1].toString());
                }
                if (!DataUtil.isNullObject(obj[2])) {
                    userDTO.setAvailiableMeeting(DataUtil.safeToLong(obj[2].toString()));
                }else{
                    userDTO.setAvailiableMeeting(0l);
                }
                userDTOs.add(userDTO);
            }
        }
        return userDTOs;
    }

    @Override
    public List<SaleFunnelCcaDTO> findSaleFunnelData(SaleFunnelCCASearchDTO saleFunnelCCASearchDTO) throws Exception {
        List<SaleFunnelCcaDTO> saleFunnelCcaDTOs = new ArrayList<>();
        String employeeId = "";
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getTlCcaCode())) {
            employeeId = findEmployeeIdByUser(saleFunnelCCASearchDTO.getTlCcaCode(), saleFunnelCCASearchDTO.getDataSource());
        }
        StringBuilder sql = new StringBuilder(" select ");
        sql.append(" (select SUBSTRING(email,0,CHARINDEX('@',email)) from lst_employees where employee_id =  vcch.cca_employee_id) email_cca_employee, ");
        sql.append(" vcch.cca_employee_id cca_id,   ");
        sql.append(" (select employee_name from lst_employees where employee_id =  vcch.cca_employee_id) cca_name, ");
        sql.append(" (select SUBSTRING(email,0,CHARINDEX('@',email)) from lst_employees where employee_id =  vcch.jtl_cca_employee_id) email_jtl_employee, ");
        sql.append(" vcch.jtl_cca_employee_id,  ");
        sql.append(" (select employee_name from lst_employees where employee_id =  vcch.jtl_cca_employee_id) jtl_cca_name,  ");
        sql.append(" (select count(*) from ( ");
        sql.append(" select distinct vcchtotal.customer_id from vch_crm_chance_headers vcchtotal ");
        sql.append(" left join cust_customers cctotal on vcchtotal.customer_id = cctotal.customer_id ");
        sql.append(" where 1=1 ");
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProjectCode())){
            sql.append(" and cctotal.partner_id = :partnerId ");
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getBoundCodes())){
            sql.append(" and cctotal.bound_code "+ DbUtil.createInQuery("boundCodes",saleFunnelCCASearchDTO.getBoundCodes())+ "");
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProvince())){
            sql.append(" and cctotal.province = :province ");
        }
        if (!DataUtil.isNullObject(saleFunnelCCASearchDTO.getFromDate())) {
            sql.append(" and vcchtotal.distributed_cca_on_date >= :fromDate ");
        }
        if (!DataUtil.isNullObject(saleFunnelCCASearchDTO.getToDate())) {
            sql.append(" and vcchtotal.distributed_cca_on_date <= :toDate ");
        }
        if (!DataUtil.isNullOrEmpty(employeeId)){
            sql.append(" and vcchtotal.jtl_cca_employee_id = :employeeId ");
        }
        sql.append(" and vcchtotal.cca_employee_id = vcch.cca_employee_id   ) total_customer_id ) total_received_lead, ");
        sql.append(" (select count(*) from ( ");
        sql.append(" select distinct vcchtotal.customer_id from vch_crm_chance_headers vcchtotal  ");
        sql.append(" left join cust_customers cctotal on vcchtotal.customer_id = cctotal.customer_id ");
        sql.append(" where 1=1 ");
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProjectCode())){
            sql.append(" and cctotal.partner_id = :partnerId ");
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getBoundCodes())){
            sql.append(" and cctotal.bound_code "+ DbUtil.createInQuery("boundCodes",saleFunnelCCASearchDTO.getBoundCodes())+ "");
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProvince())){
            sql.append(" and cctotal.province = :province ");
        }
        if (!DataUtil.isNullObject(saleFunnelCCASearchDTO.getFromDate())) {
            sql.append(" and vcchtotal.last_cca_contacted_on_date >= :fromDate ");
        }
        if (!DataUtil.isNullObject(saleFunnelCCASearchDTO.getToDate())) {
            sql.append(" and vcchtotal.last_cca_contacted_on_date <= :toDate ");
        }
        if (!DataUtil.isNullOrEmpty(employeeId)){
            sql.append(" and vcchtotal.jtl_cca_employee_id = :employeeId ");
        }
        sql.append(" and vcchtotal.cca_employee_id = vcch.cca_employee_id   ) total_customer_id ) total_lead, ");
        sql.append(" (select COUNT(*) from vch_crm_chance_headers vcchmeet  ");
        sql.append(" left join cust_customers ccmeet on vcchmeet.customer_id = ccmeet.customer_id  ");
        sql.append(" where 1=1 ");
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProjectCode())){
            sql.append(" and ccmeet.partner_id = :partnerId ");
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getBoundCodes())){
            sql.append(" and ccmeet.bound_code "+ DbUtil.createInQuery("boundCodes",saleFunnelCCASearchDTO.getBoundCodes())+ "");
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProvince())){
            sql.append(" and ccmeet.province = :province ");
        }
        if (!DataUtil.isNullObject(saleFunnelCCASearchDTO.getFromDate())) {
            sql.append(" and vcchmeet.last_cca_contacted_on_date >= :fromDate ");
        }
        if (!DataUtil.isNullObject(saleFunnelCCASearchDTO.getToDate())) {
            sql.append(" and vcchmeet.last_cca_contacted_on_date <= :toDate ");
        }
        if (!DataUtil.isNullOrEmpty(employeeId)){
            sql.append(" and vcchmeet.jtl_cca_employee_id = :employeeId ");
        }
        sql.append(" and vcchmeet.cca_employee_id = vcch.cca_employee_id ");
        sql.append(" and vcchmeet.jtl_cca_employee_id = vcch.jtl_cca_employee_id ");
        sql.append(" and (vcchmeet.cca_chance_status_id = '17T124281' OR vcchmeet.cca_chance_status_id = '18T18389168' OR vcchmeet.cca_chance_status_id = '18H7776456' ) ");
        sql.append(" and vcchmeet.cca_employee_id = vcch.cca_employee_id ) meeting, ");
        sql.append(" (select ccmeet.tel + ',' AS 'data()' ");
        sql.append("    from vch_crm_chance_headers vcchmeet ");
        sql.append("    left join cust_customers ccmeet on vcchmeet.customer_id = ccmeet.customer_id ");
        sql.append("    where 1 = 1 ");
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProjectCode())){
            sql.append(" and ccmeet.partner_id = :partnerId ");
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getBoundCodes())){
            sql.append(" and ccmeet.bound_code "+ DbUtil.createInQuery("boundCodes",saleFunnelCCASearchDTO.getBoundCodes())+ "");
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProvince())){
            sql.append(" and ccmeet.province = :province ");
        }
        if (!DataUtil.isNullObject(saleFunnelCCASearchDTO.getFromDate())) {
            sql.append(" and vcchmeet.last_cca_contacted_on_date >= :fromDate ");
        }
        if (!DataUtil.isNullObject(saleFunnelCCASearchDTO.getToDate())) {
            sql.append(" and vcchmeet.last_cca_contacted_on_date <= :toDate ");
        }
        if (!DataUtil.isNullOrEmpty(employeeId)){
            sql.append(" and vcchmeet.jtl_cca_employee_id = :employeeId ");
        }
        sql.append(" and vcchmeet.cca_employee_id = vcch.cca_employee_id ");
        sql.append(" and vcchmeet.jtl_cca_employee_id = vcch.jtl_cca_employee_id ");
        sql.append(" and (vcchmeet.cca_chance_status_id = '17T124281' OR vcchmeet.cca_chance_status_id = '18T18389168' OR vcchmeet.cca_chance_status_id = '18H7776456' ) ");
        sql.append(" and vcchmeet.cca_employee_id = vcch.cca_employee_id FOR XML PATH('') ) LIST_PHONES ");

        sql.append(" from vch_crm_chance_headers vcch ");
        sql.append(" left join cust_customers cc on vcch.customer_id = cc.customer_id  ");
        sql.append(" where 1=1  ");
        sql.append(" and (vcch.cca_employee_id is not null and vcch.cca_employee_id <> '') ");
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProvince())){
            sql.append(" and cc.province = :province ");
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProjectCode())){
            sql.append(" and cc.partner_id = :partnerId ");
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getBoundCodes())){
            sql.append(" and cc.bound_code "+ DbUtil.createInQuery("boundCodes",saleFunnelCCASearchDTO.getBoundCodes())+ "");
        }
        if (!DataUtil.isNullObject(saleFunnelCCASearchDTO.getFromDate())) {
            sql.append(" and vcch.last_cca_contacted_on_date >= :fromDate ");
        }
        if (!DataUtil.isNullObject(saleFunnelCCASearchDTO.getToDate())) {
            sql.append(" and vcch.last_cca_contacted_on_date <= :toDate ");
        }
        if (!DataUtil.isNullOrEmpty(employeeId)){
            sql.append(" and vcch.jtl_cca_employee_id = :employeeId ");
        }
        sql.append(" group by vcch.cca_employee_id,vcch.jtl_cca_employee_id ");

        Query querySelect;
        if (DataUtil.safeEqual(Const.PERSISTENCE.CRM_HN,saleFunnelCCASearchDTO.getDataSource())) {
            querySelect = emHN.createNativeQuery(sql.toString());
        }else{
            querySelect = emHCM.createNativeQuery(sql.toString());
        }
        querySelect.setFirstResult(saleFunnelCCASearchDTO.getFirst());
        querySelect.setMaxResults(saleFunnelCCASearchDTO.getPageSize());
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProjectCode())){
            String projectCode = "";
            if (DataUtil.safeEqual(saleFunnelCCASearchDTO.getProjectCode(),"VIETTEL")){
                projectCode = "17T120807";
            }else if(DataUtil.safeEqual(saleFunnelCCASearchDTO.getProjectCode(),"CTIN")){
                projectCode = "17T339974";
            }else if (DataUtil.safeEqual(saleFunnelCCASearchDTO.getProjectCode(),"VPBANK_TS")){
                projectCode = "19T19368857";
            }
            querySelect.setParameter("partnerId",projectCode);
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getBoundCodes())){
            DbUtil.setParamInQuery(querySelect,"boundCodes",saleFunnelCCASearchDTO.getBoundCodes());
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProvince())){
            String provinceCode = "";
            if(DataUtil.safeEqual(saleFunnelCCASearchDTO.getProjectCode(),"VIETTEL")){
                if (DataUtil.safeEqual(saleFunnelCCASearchDTO.getProvince(),"CTH")){
                    provinceCode = "Cần Thơ";
                }else if(DataUtil.safeEqual(saleFunnelCCASearchDTO.getProvince(),"HCM")){
                    provinceCode = "TP HCM";
                }else if (DataUtil.safeEqual(saleFunnelCCASearchDTO.getProvince(),"DNI")){
                    provinceCode = "Đồng Nai";
                }else if(DataUtil.safeEqual(saleFunnelCCASearchDTO.getProvince(),"DNA")){
                    provinceCode = "Đà Nẵng";
                }else if (DataUtil.safeEqual(saleFunnelCCASearchDTO.getProvince(),"HNO")){
                    provinceCode = "Hà Nội";
                }
                querySelect.setParameter("province",provinceCode);
            }else{
                querySelect.setParameter("province",saleFunnelCCASearchDTO.getProvince());
            }
        }

        if (!DataUtil.isNullOrEmpty(employeeId)){
            querySelect.setParameter("employeeId",employeeId);
        }

        if (!DataUtil.isNullObject(saleFunnelCCASearchDTO.getFromDate())) {
            querySelect.setParameter("fromDate",DateUtil.dateyyyyMMddHHmmSS(saleFunnelCCASearchDTO.getFromDate()));
        }
        if (!DataUtil.isNullObject(saleFunnelCCASearchDTO.getToDate())) {
            querySelect.setParameter("toDate",DateUtil.dateyyyyMMddHHmmSS(saleFunnelCCASearchDTO.getToDate()));
        }
        List resultList = querySelect.getResultList();
        if (!DataUtil.isNullOrEmpty(resultList)){
            SaleFunnelCcaDTO saleFunnelCcaDTO;
            for (int i=0;i< resultList.size();i++) {
                Object[] obj = (Object[]) resultList.get(i);
                saleFunnelCcaDTO = new SaleFunnelCcaDTO();
                saleFunnelCcaDTO.setKey(DataUtil.safeToString(i));
                if (!DataUtil.isNullObject(obj[0])) {
                    saleFunnelCcaDTO.setCcaCode(obj[0].toString());
                }
                if (!DataUtil.isNullObject(obj[1])) {
                    saleFunnelCcaDTO.setCcaId(obj[1].toString());
                }
                if (!DataUtil.isNullObject(obj[2])) {
                    saleFunnelCcaDTO.setCcaName(obj[2].toString());
                }
                if (!DataUtil.isNullObject(obj[3])) {
                    saleFunnelCcaDTO.setJtlCcaCode(obj[3].toString());
                }
                if (!DataUtil.isNullObject(obj[4])) {
                    saleFunnelCcaDTO.setJtlCcaId(obj[4].toString());
                }
                if (!DataUtil.isNullObject(obj[5])) {
                    saleFunnelCcaDTO.setJtlCcaName(obj[5].toString());
                }
                if (!DataUtil.isNullObject(obj[6])) {
                    saleFunnelCcaDTO.setTotalReceivedLeads(DataUtil.safeToLong(obj[6].toString()));
                }
                if (!DataUtil.isNullObject(obj[7])) {
                    saleFunnelCcaDTO.setTotalLeads(DataUtil.safeToLong(obj[7].toString()));
                }
                if (!DataUtil.isNullObject(obj[8])) {
                    saleFunnelCcaDTO.setTotalMeetings(DataUtil.safeToLong(obj[8].toString()));
                }
                if (!DataUtil.isNullObject(obj[9])) {
                    String listPhones = obj[9].toString().substring(0,obj[9].toString().length()-1);
                    saleFunnelCcaDTO.setListPhones(new ArrayList<String>(Arrays.asList(listPhones.split("\\s*,\\s*"))));
                }
                saleFunnelCcaDTOs.add(saleFunnelCcaDTO);
            }
        }
        return saleFunnelCcaDTOs;
    }


    @Override
    public List<SaleFunnelCcaDTO> findAllSaleFunnelData(SaleFunnelCCASearchDTO saleFunnelCCASearchDTO) throws Exception{
        List<SaleFunnelCcaDTO> saleFunnelCcaDTOs = new ArrayList<>();
        StringBuilder sql = new StringBuilder(" select ");
        sql.append(" UPPER(drm.cca_code) cca_code, ");
        sql.append(" UPPER(drm.jtl_cca_code) jtl_cca_code,    ");
        sql.append(" count(*) total_meeting, ");
        sql.append(" GROUP_CONCAT(drm.id) id_list ");
        sql.append(" from dsa_result_meeting drm  ");
        sql.append(" where 1=1  ");
//        sql.append("and drm.call_date >= :fromDate and drm.call_date <= :toDate ");
        sql.append("and ( ");
        sql.append(" (drm.call_date >= :fromDate ");
        sql.append(" and drm.call_date <= :toDate )");
        sql.append(" OR (drm.meeting_date >= :fromDate ");
        sql.append(" and drm.meeting_date <= :toDate) ");
        sql.append(" )");
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProjectCode())) {
            sql.append("and drm.partner_code = :partnerCode  ");
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProvince())) {
            sql.append("and drm.province_meeting_code = :provinceCode  ");
        }
        if(!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getBoundCodes())) {
            sql.append(" AND drm.bound_code "+ DbUtil.createInQuery("boundCodes",saleFunnelCCASearchDTO.getBoundCodes())+ "");
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getTlCcaCode())) {
            sql.append("    and UPPER(drm.jtl_cca_code) = :jtlCcaCode  ");
        }
        sql.append(" group by drm.cca_code, drm.jtl_cca_code ");

        Query querySelect = em.createNativeQuery(sql.toString());
        querySelect.setFirstResult(saleFunnelCCASearchDTO.getFirst());
        querySelect.setMaxResults(saleFunnelCCASearchDTO.getPageSize());
        if (!DataUtil.isNullObject(saleFunnelCCASearchDTO.getFromDate())) {
            querySelect.setParameter("fromDate",DateUtil.dateyyyyMMddHHmmSS(saleFunnelCCASearchDTO.getFromDate()));
        }
        if (!DataUtil.isNullObject(saleFunnelCCASearchDTO.getToDate())) {
            querySelect.setParameter("toDate",DateUtil.dateyyyyMMddHHmmSS(saleFunnelCCASearchDTO.getToDate()));
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProjectCode())) {
            querySelect.setParameter("partnerCode",saleFunnelCCASearchDTO.getProjectCode());
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProvince())) {
            querySelect.setParameter("provinceCode",saleFunnelCCASearchDTO.getProvince());
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getBoundCodes())){
            DbUtil.setParamInQuery(querySelect,"boundCodes",saleFunnelCCASearchDTO.getBoundCodes());
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getTlCcaCode())) {
            querySelect.setParameter("jtlCcaCode",saleFunnelCCASearchDTO.getTlCcaCode().toUpperCase());
        }
        List resultList = querySelect.getResultList();
        if (!DataUtil.isNullOrEmpty(resultList)){
            SaleFunnelCcaDTO saleFunnelCcaDTO;
            for (int i=0;i< resultList.size();i++) {
                Object[] obj = (Object[]) resultList.get(i);
                saleFunnelCcaDTO = new SaleFunnelCcaDTO();
                saleFunnelCcaDTO.setKey(DataUtil.safeToString(i));
                if (!DataUtil.isNullObject(obj[0])) {
                    saleFunnelCcaDTO.setCcaCode(obj[0].toString());
                }
                if (!DataUtil.isNullObject(obj[1])) {
                    saleFunnelCcaDTO.setJtlCcaCode(obj[1].toString());
                }
                if (!DataUtil.isNullObject(obj[2])) {
                    saleFunnelCcaDTO.setTotalMeetings(DataUtil.safeToLong(obj[2].toString()));
                }
                if (!DataUtil.isNullObject(obj[3])) {
                    saleFunnelCcaDTO.setListIds(new ArrayList<String>(Arrays.asList(obj[3].toString().split(","))));
                }
                saleFunnelCcaDTOs.add(saleFunnelCcaDTO);
            }
        }
        return saleFunnelCcaDTOs;
    }

    @Override
    public Long countAllSaleFunnelData(SaleFunnelCCASearchDTO saleFunnelCCASearchDTO) throws Exception{
        StringBuilder sql = new StringBuilder(" select count(*) from ( ");
        sql.append(" select ");
        sql.append(" UPPER(drm.cca_code) cca_code, ");
        sql.append(" UPPER(drm.jtl_cca_code) jtl_cca_code,    ");
        sql.append(" count(*) total_meeting ");
        sql.append(" from dsa_result_meeting drm  ");
        sql.append(" where 1=1  ");
        sql.append("and ( ");
        sql.append(" (drm.call_date >= :fromDate ");
        sql.append(" and drm.call_date <= :toDate )");
        sql.append(" OR (drm.meeting_date >= :fromDate ");
        sql.append(" and drm.meeting_date <= :toDate) ");
        sql.append(" )");
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProjectCode())) {
            sql.append("and drm.partner_code = :partnerCode  ");
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProvince())) {
            sql.append("and drm.province_meeting_code = :provinceCode  ");
        }
        if(!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getBoundCodes())) {
            sql.append(" AND drm.bound_code "+ DbUtil.createInQuery("boundCodes",saleFunnelCCASearchDTO.getBoundCodes())+ "");
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getTlCcaCode())) {
            sql.append("    and UPPER(drm.jtl_cca_code) = :jtlCcaCode  ");
        }
        sql.append(" group by drm.cca_code, drm.jtl_cca_code ) table_tmp_count ");

        Query querySelect = em.createNativeQuery(sql.toString());
        if (!DataUtil.isNullObject(saleFunnelCCASearchDTO.getFromDate())) {
            querySelect.setParameter("fromDate",DateUtil.dateyyyyMMddHHmmSS(saleFunnelCCASearchDTO.getFromDate()));
        }
        if (!DataUtil.isNullObject(saleFunnelCCASearchDTO.getToDate())) {
            querySelect.setParameter("toDate",DateUtil.dateyyyyMMddHHmmSS(saleFunnelCCASearchDTO.getToDate()));
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProjectCode())) {
            querySelect.setParameter("partnerCode",saleFunnelCCASearchDTO.getProjectCode());
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProvince())) {
            querySelect.setParameter("provinceCode",saleFunnelCCASearchDTO.getProvince());
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getBoundCodes())){
            DbUtil.setParamInQuery(querySelect,"boundCodes",saleFunnelCCASearchDTO.getBoundCodes());
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getTlCcaCode())) {
            querySelect.setParameter("jtlCcaCode",saleFunnelCCASearchDTO.getTlCcaCode().toUpperCase());
        }
        Object resultList = querySelect.getSingleResult();
        return DataUtil.safeToLong(resultList);
    }

    @Override
    public Long countSaleFunnel(SaleFunnelCCASearchDTO saleFunnelCCASearchDTO) throws Exception{
        String employeeId = "";
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getTlCcaCode())) {
            employeeId = findEmployeeIdByUser(saleFunnelCCASearchDTO.getTlCcaCode(), saleFunnelCCASearchDTO.getDataSource());
        }
        StringBuilder sql = new StringBuilder(" select count(*) from ( ");
        sql.append(" select ");
        sql.append(" (select SUBSTRING(email,0,CHARINDEX('@',email)) from lst_employees where employee_id =  vcch.cca_employee_id) email_cca_employee, ");
        sql.append(" vcch.cca_employee_id cca_id,   ");
        sql.append(" (select employee_name from lst_employees where employee_id =  vcch.cca_employee_id) cca_name, ");
        sql.append(" (select SUBSTRING(email,0,CHARINDEX('@',email)) from lst_employees where employee_id =  vcch.jtl_cca_employee_id) email_jtl_employee, ");
        sql.append(" vcch.jtl_cca_employee_id,  ");
        sql.append(" (select employee_name from lst_employees where employee_id =  vcch.jtl_cca_employee_id) jtl_cca_name  ");
        sql.append(" from vch_crm_chance_headers vcch ");
        sql.append(" left join cust_customers cc on vcch.customer_id = cc.customer_id  ");
        sql.append(" where 1=1  ");
        sql.append(" and (vcch.cca_employee_id is not null and vcch.cca_employee_id <> '') ");
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProvince())){
            sql.append(" and cc.province = :province ");
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProjectCode())){
            sql.append(" and cc.partner_id = :partnerId ");
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getBoundCodes())){
            sql.append(" and cc.bound_code "+ DbUtil.createInQuery("boundCodes",saleFunnelCCASearchDTO.getBoundCodes())+ "");
        }
        if (!DataUtil.isNullObject(saleFunnelCCASearchDTO.getFromDate())) {
            sql.append(" and vcch.last_cca_contacted_on_date >= :fromDate ");
        }
        if (!DataUtil.isNullObject(saleFunnelCCASearchDTO.getToDate())) {
            sql.append(" and vcch.last_cca_contacted_on_date <= :toDate ");
        }
        if (!DataUtil.isNullOrEmpty(employeeId)) {
            sql.append(" and vcch.jtl_cca_employee_id = :employeeId ");
        }
        sql.append(" group by vcch.cca_employee_id,vcch.jtl_cca_employee_id ) TERM_TABLE");

        Query querySelect;
        if (DataUtil.safeEqual(Const.PERSISTENCE.CRM_HN,saleFunnelCCASearchDTO.getDataSource())) {
            querySelect = emHN.createNativeQuery(sql.toString());
        }else{
            querySelect = emHCM.createNativeQuery(sql.toString());
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProjectCode())){
            String projectCode = "";
            if (DataUtil.safeEqual(saleFunnelCCASearchDTO.getProjectCode(),"VIETTEL")){
                projectCode = "17T120807";
            }else if(DataUtil.safeEqual(saleFunnelCCASearchDTO.getProjectCode(),"CTIN")){
                projectCode = "17T339974";
            }else if (DataUtil.safeEqual(saleFunnelCCASearchDTO.getProjectCode(),"VPBANK_TS")){
                projectCode = "19T19368857";
            }
            querySelect.setParameter("partnerId",projectCode);
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getBoundCodes())){
            DbUtil.setParamInQuery(querySelect,"boundCodes",saleFunnelCCASearchDTO.getBoundCodes());
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProvince())){
            String provinceCode = "";
            if(DataUtil.safeEqual(saleFunnelCCASearchDTO.getProjectCode(),"VIETTEL")){
                if (DataUtil.safeEqual(saleFunnelCCASearchDTO.getProvince(),"CTH")){
                    provinceCode = "Cần Thơ";
                }else if(DataUtil.safeEqual(saleFunnelCCASearchDTO.getProvince(),"HCM")){
                    provinceCode = "TP HCM";
                }else if (DataUtil.safeEqual(saleFunnelCCASearchDTO.getProvince(),"DNI")){
                    provinceCode = "Đồng Nai";
                }else if(DataUtil.safeEqual(saleFunnelCCASearchDTO.getProvince(),"DNA")){
                    provinceCode = "Đà Nẵng";
                }else if (DataUtil.safeEqual(saleFunnelCCASearchDTO.getProvince(),"HNO")){
                    provinceCode = "Hà Nội";
                }
                querySelect.setParameter("province",provinceCode);
            }else{
                querySelect.setParameter("province",saleFunnelCCASearchDTO.getProvince());
            }
        }
        if (!DataUtil.isNullObject(saleFunnelCCASearchDTO.getFromDate())) {
            querySelect.setParameter("fromDate",DateUtil.dateyyyyMMddHHmmSS(saleFunnelCCASearchDTO.getFromDate()));
        }
        if (!DataUtil.isNullObject(saleFunnelCCASearchDTO.getToDate())) {
            querySelect.setParameter("toDate",DateUtil.dateyyyyMMddHHmmSS(saleFunnelCCASearchDTO.getToDate()));
        }
        if (!DataUtil.isNullOrEmpty(employeeId)) {
            querySelect.setParameter("employeeId",employeeId);
        }
        Object resultList = querySelect.getSingleResult();
        return DataUtil.safeToLong(resultList);
    }

    @Override
    public SaleFunnelCcaDTO mergeDataSaleFunnel(SaleFunnelCcaDTO saleFunnelCcaDTO, SaleFunnelCCASearchDTO saleFunnelCCASearchDTO) {
        StringBuilder sql = new StringBuilder(" select ");
        sql.append(" (select count(*) accepted_meeting from dsa_result_meeting where 1=1  ");
        sql.append(" and accept_status = 1    ");
        if(!DataUtil.isNullObject(saleFunnelCcaDTO.getCcaCode())) {
            sql.append(" and cca_code = :ccaCode ");
        }
        if(!DataUtil.isNullObject(saleFunnelCcaDTO.getJtlCcaCode())) {
            sql.append(" and upper(jtl_cca_code) = :jtlCcaCode ");
        }
        if(!DataUtil.isNullOrEmpty(saleFunnelCcaDTO.getListIds())) {
            sql.append(" and id IN :listIds ");
        }
        if(!DataUtil.isNullOrEmpty(saleFunnelCcaDTO.getListPhones())) {
            sql.append(" and customer_phone IN :listPhones ");
        }
        if(!DataUtil.isNullObject(saleFunnelCCASearchDTO.getProvince())) {
            sql.append(" and province_meeting_code = :province ");
        }
        if(!DataUtil.isNullObject(saleFunnelCCASearchDTO.getFromDate())) {
            sql.append(" and meeting_date >= :fromDate ");
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProjectCode())){
            sql.append(" and partner_code = :partnerCode ");
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getBoundCodes())){
            sql.append(" and bound_code "+ DbUtil.createInQuery("boundCodes",saleFunnelCCASearchDTO.getBoundCodes())+ "");
        }
        sql.append(" )  accepted_meetings, ");

        sql.append(" (select count(*) accepted_meeting from dsa_result_meeting where 1=1  ");
        sql.append(" and accept_status = 5    ");
        if(!DataUtil.isNullObject(saleFunnelCcaDTO.getCcaCode())) {
            sql.append(" and cca_code = :ccaCode ");
        }
        if(!DataUtil.isNullObject(saleFunnelCcaDTO.getJtlCcaCode())) {
            sql.append(" and upper(jtl_cca_code) = :jtlCcaCode ");
        }
        if(!DataUtil.isNullOrEmpty(saleFunnelCcaDTO.getListIds())) {
            sql.append(" and id IN :listIds ");
        }
        if(!DataUtil.isNullOrEmpty(saleFunnelCcaDTO.getListPhones())) {
            sql.append(" and customer_phone IN :listPhones ");
        }
        if(!DataUtil.isNullObject(saleFunnelCCASearchDTO.getProvince())) {
            sql.append(" and province_meeting_code = :province ");
        }
        if(!DataUtil.isNullObject(saleFunnelCCASearchDTO.getFromDate())) {
            sql.append(" and meeting_date >= :fromDate ");
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProjectCode())){
            sql.append(" and partner_code = :partnerCode ");
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getBoundCodes())){
            sql.append(" and bound_code "+ DbUtil.createInQuery("boundCodes",saleFunnelCCASearchDTO.getBoundCodes())+ "");
        }
        sql.append(" )  total_not_contacted, ");

        sql.append(" (select count(*) full_doc_meeting from dsa_result_meeting where 1=1  ");
        sql.append(" and accept_status = 1    ");
        sql.append(" and meeting_result = 1    ");
        if(!DataUtil.isNullObject(saleFunnelCcaDTO.getCcaCode())) {
            sql.append(" and upper(cca_code) = :ccaCode ");
        }
        if(!DataUtil.isNullObject(saleFunnelCcaDTO.getJtlCcaCode())) {
            sql.append(" and upper(jtl_cca_code) = :jtlCcaCode ");
        }
        if(!DataUtil.isNullOrEmpty(saleFunnelCcaDTO.getListIds())) {
            sql.append(" and id IN :listIds ");
        }
        if(!DataUtil.isNullOrEmpty(saleFunnelCcaDTO.getListPhones())) {
            sql.append(" and customer_phone IN :listPhones ");
        }
        if(!DataUtil.isNullObject(saleFunnelCCASearchDTO.getProvince())) {
            sql.append(" and province_meeting_code = :province ");
        }
        if(!DataUtil.isNullObject(saleFunnelCCASearchDTO.getFromDate())) {
            sql.append(" and meeting_date >= :fromDate ");
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProjectCode())){
            sql.append(" and partner_code = :partnerCode ");
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getBoundCodes())){
            sql.append(" and bound_code "+ DbUtil.createInQuery("boundCodes",saleFunnelCCASearchDTO.getBoundCodes())+ "");

        }
        sql.append(" )  full_doc_meetings, ");

        sql.append(" (select count(*) full_doc_meeting from dsa_result_meeting where 1=1  ");
        sql.append(" and accept_status = 5    ");
        sql.append(" and meeting_result = 1    ");
        if(!DataUtil.isNullObject(saleFunnelCcaDTO.getCcaCode())) {
            sql.append(" and upper(cca_code) = :ccaCode ");
        }
        if(!DataUtil.isNullObject(saleFunnelCcaDTO.getJtlCcaCode())) {
            sql.append(" and upper(jtl_cca_code) = :jtlCcaCode ");
        }
        if(!DataUtil.isNullOrEmpty(saleFunnelCcaDTO.getListIds())) {
            sql.append(" and id IN :listIds ");
        }
        if(!DataUtil.isNullOrEmpty(saleFunnelCcaDTO.getListPhones())) {
            sql.append(" and customer_phone IN :listPhones ");
        }
        if(!DataUtil.isNullObject(saleFunnelCCASearchDTO.getProvince())) {
            sql.append(" and province_meeting_code = :province ");
        }
        if(!DataUtil.isNullObject(saleFunnelCCASearchDTO.getFromDate())) {
            sql.append(" and meeting_date >= :fromDate ");
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProjectCode())){
            sql.append(" and partner_code = :partnerCode ");
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getBoundCodes())){
            sql.append(" and bound_code "+ DbUtil.createInQuery("boundCodes",saleFunnelCCASearchDTO.getBoundCodes())+ "");

        }
        sql.append(" )  full_doc_not_contacted_meetings, ");

        sql.append(" (select count(*) total_checkup from bo_checkup_result where 1=1  ");
        if(!DataUtil.isNullObject(saleFunnelCcaDTO.getCcaCode())) {
            sql.append(" and upper(cca_code) = :ccaCode ");
        }
        if(!DataUtil.isNullObject(saleFunnelCcaDTO.getJtlCcaCode())) {
            sql.append(" and upper(tl_cca_code) = :jtlCcaCode ");
        }
        if(!DataUtil.isNullOrEmpty(saleFunnelCcaDTO.getListIds())) {
            sql.append(" and dsa_result_meeting_id IN :listIds ");
        }
        if(!DataUtil.isNullOrEmpty(saleFunnelCcaDTO.getListPhones())) {
            sql.append(" and customer_phone IN :listPhones ");
        }
        if(!DataUtil.isNullObject(saleFunnelCCASearchDTO.getProvince())) {
            sql.append(" and province_code = :province ");
        }
        if(!DataUtil.isNullObject(saleFunnelCCASearchDTO.getFromDate())) {
            sql.append(" and meeting_date >= :fromDate ");
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProjectCode())){
            sql.append(" and partner_code = :partnerCode ");
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getBoundCodes())){
            sql.append(" and bound_code "+ DbUtil.createInQuery("boundCodes",saleFunnelCCASearchDTO.getBoundCodes())+ "");
        }
        sql.append(" and checkup_status is not null ");
        sql.append(" )  total_checkups, ");

        sql.append(" (select count(*) total_QDE from bo_checkup_result where 1=1  ");
        sql.append(" and send_to_follow = 1    ");
        if(!DataUtil.isNullObject(saleFunnelCcaDTO.getCcaCode())) {
            sql.append(" and upper(cca_code) = :ccaCode ");
        }
        if(!DataUtil.isNullObject(saleFunnelCcaDTO.getJtlCcaCode())) {
            sql.append(" and upper(tl_cca_code) = :jtlCcaCode ");
        }
        if(!DataUtil.isNullOrEmpty(saleFunnelCcaDTO.getListIds())) {
            sql.append(" and dsa_result_meeting_id IN :listIds ");
        }
        if(!DataUtil.isNullOrEmpty(saleFunnelCcaDTO.getListPhones())) {
            sql.append(" and customer_phone IN :listPhones ");
        }
        if(!DataUtil.isNullObject(saleFunnelCCASearchDTO.getProvince())) {
            sql.append(" and province_code = :province ");
        }
        if(!DataUtil.isNullObject(saleFunnelCCASearchDTO.getFromDate())) {
            sql.append(" and meeting_date >= :fromDate ");
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProjectCode())){
            sql.append(" and partner_code = :partnerCode ");
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getBoundCodes())){
            sql.append(" and bound_code "+ DbUtil.createInQuery("boundCodes",saleFunnelCCASearchDTO.getBoundCodes())+ "");
        }
        sql.append(" )  total_QDEs, ");

        sql.append(" (select count(*) total_approve from bo_checkup_result where 1=1  ");
        sql.append(" and status_doc_vpbank = 6    ");
        if(!DataUtil.isNullObject(saleFunnelCcaDTO.getCcaCode())) {
            sql.append(" and upper(cca_code) = :ccaCode ");
        }
        if(!DataUtil.isNullObject(saleFunnelCcaDTO.getJtlCcaCode())) {
            sql.append(" and upper(tl_cca_code) = :jtlCcaCode ");
        }
        if(!DataUtil.isNullOrEmpty(saleFunnelCcaDTO.getListIds())) {
            sql.append(" and dsa_result_meeting_id IN :listIds ");
        }
        if(!DataUtil.isNullOrEmpty(saleFunnelCcaDTO.getListPhones())) {
            sql.append(" and customer_phone IN :listPhones ");
        }
        if(!DataUtil.isNullObject(saleFunnelCCASearchDTO.getProvince())) {
            sql.append(" and province_code = :province ");
        }
        if(!DataUtil.isNullObject(saleFunnelCCASearchDTO.getFromDate())) {
            sql.append(" and meeting_date >= :fromDate ");
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProjectCode())){
            sql.append(" and partner_code = :partnerCode ");
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getBoundCodes())){
            sql.append(" and bound_code "+ DbUtil.createInQuery("boundCodes",saleFunnelCCASearchDTO.getBoundCodes())+ "");
        }
        sql.append(" )  total_approves ");


        Query querySelect = em.createNativeQuery(sql.toString());
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProjectCode())){
            querySelect.setParameter("partnerCode",saleFunnelCCASearchDTO.getProjectCode());
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getBoundCodes())){
            DbUtil.setParamInQuery(querySelect,"boundCodes",saleFunnelCCASearchDTO.getBoundCodes());
        }
        if (!DataUtil.isNullOrEmpty(saleFunnelCCASearchDTO.getProvince())){
            querySelect.setParameter("province",saleFunnelCCASearchDTO.getProvince());
        }
        if (!DataUtil.isNullObject(saleFunnelCCASearchDTO.getFromDate())) {
            querySelect.setParameter("fromDate",DateUtil.dateToString(saleFunnelCCASearchDTO.getFromDate()));
        }
        if(!DataUtil.isNullObject(saleFunnelCcaDTO.getCcaCode())) {
            querySelect.setParameter("ccaCode",DataUtil.safeToUpper(saleFunnelCcaDTO.getCcaCode()));
        }
        if(!DataUtil.isNullObject(saleFunnelCcaDTO.getJtlCcaCode())) {
            querySelect.setParameter("jtlCcaCode",DataUtil.safeToUpper(saleFunnelCcaDTO.getJtlCcaCode()));
        }
        if(!DataUtil.isNullObject(saleFunnelCcaDTO.getListIds())) {
            querySelect.setParameter("listIds",saleFunnelCcaDTO.getListIds());
        }
        if(!DataUtil.isNullOrEmpty(saleFunnelCcaDTO.getListPhones())) {
            querySelect.setParameter("listPhones",saleFunnelCcaDTO.getListPhones());
        }
        List resultList = querySelect.getResultList();
        if (!DataUtil.isNullOrEmpty(resultList)){
            for (int i=0;i< resultList.size();i++) {
                Object[] obj = (Object[]) resultList.get(i);
                if (!DataUtil.isNullObject(obj[0])) {
                    saleFunnelCcaDTO.setTotalAcceptedMeetings(DataUtil.safeToLong(obj[0].toString()));
                }
                if (!DataUtil.isNullObject(obj[1])) {
                    saleFunnelCcaDTO.setTotalNotContactedMeetings(DataUtil.safeToLong(obj[1].toString()));
                }
                if (!DataUtil.isNullObject(obj[2])) {
                    saleFunnelCcaDTO.setTotalFullDocMeetings(DataUtil.safeToLong(obj[2].toString()));
                }
                if (!DataUtil.isNullObject(obj[3])) {
                    saleFunnelCcaDTO.setTotalFullDocNotContacted(DataUtil.safeToLong(obj[3].toString()));
                }
                if (!DataUtil.isNullObject(obj[4])) {
                    saleFunnelCcaDTO.setTotalCheckups(DataUtil.safeToLong(obj[4].toString()));
                }
                if (!DataUtil.isNullObject(obj[5])) {
                    saleFunnelCcaDTO.setTotalQDEs(DataUtil.safeToLong(obj[5].toString()));
                }
                if (!DataUtil.isNullObject(obj[6])) {
                    saleFunnelCcaDTO.setTotalApproves(DataUtil.safeToLong(obj[6].toString()));
                }
                saleFunnelCcaDTO.setTotalDocs(DataUtil.safeToLong(saleFunnelCcaDTO.getTotalFullDocMeetings()) + DataUtil.safeToLong(saleFunnelCcaDTO.getTotalFullDocNotContacted()));

                if (!DataUtil.safeEqual(saleFunnelCcaDTO.getTotalMeetings(),0)){
                    double percentDocs = ((double) saleFunnelCcaDTO.getTotalDocs()/saleFunnelCcaDTO.getTotalMeetings()) * 100;
                    saleFunnelCcaDTO.setPercentFullDocs(DataUtil.round(percentDocs, 2));
                }else{
                    saleFunnelCcaDTO.setPercentFullDocs(0d);
                }
                if (!DataUtil.safeEqual(saleFunnelCcaDTO.getTotalAcceptedMeetings(),0)){
                    double percentFullDocs = ((double) saleFunnelCcaDTO.getTotalFullDocMeetings()/saleFunnelCcaDTO.getTotalAcceptedMeetings()) * 100;
                    saleFunnelCcaDTO.setPercentFullDocsAccept(DataUtil.round(percentFullDocs, 2));
                }else{
                    saleFunnelCcaDTO.setPercentFullDocsAccept(0d);
                }
                if (!DataUtil.safeEqual(saleFunnelCcaDTO.getTotalNotContactedMeetings(),0)){
                    double percentFullDocsNotContacted = ((double) saleFunnelCcaDTO.getTotalFullDocNotContacted()/saleFunnelCcaDTO.getTotalNotContactedMeetings()) * 100;
                    saleFunnelCcaDTO.setPercentFullDocsNotContacted(DataUtil.round(percentFullDocsNotContacted, 2));
                }else{
                    saleFunnelCcaDTO.setPercentFullDocsNotContacted(0d);
                }
                if (!DataUtil.safeEqual(saleFunnelCcaDTO.getTotalDocs(),0)){
                    double percentCheckups = ((double) saleFunnelCcaDTO.getTotalCheckups()/saleFunnelCcaDTO.getTotalDocs()) * 100;
                    saleFunnelCcaDTO.setPercentCheckups(DataUtil.round(percentCheckups, 2));
                }else{
                    saleFunnelCcaDTO.setPercentCheckups(0d);
                }
                if (!DataUtil.safeEqual(saleFunnelCcaDTO.getTotalCheckups(),0)){
                    double percentQDEs = ((double) saleFunnelCcaDTO.getTotalQDEs()/saleFunnelCcaDTO.getTotalCheckups()) * 100;
                    saleFunnelCcaDTO.setPercentQDEs(DataUtil.round(percentQDEs, 2));
                }else{
                    saleFunnelCcaDTO.setPercentQDEs(0d);
                }
                if (!DataUtil.safeEqual(saleFunnelCcaDTO.getTotalQDEs(),0)){
                    double percentApproves = ((double) saleFunnelCcaDTO.getTotalApproves()/saleFunnelCcaDTO.getTotalQDEs()) * 100;
                    saleFunnelCcaDTO.setPercentApproves(DataUtil.round(percentApproves, 2));
                }else{
                    saleFunnelCcaDTO.setPercentApproves(0d);
                }
                saleFunnelCcaDTO.setTotalActives(0l);
            }
        }
        return saleFunnelCcaDTO;
    }

    @Override
    public List<BoundCodeDTO> findAllBoundCode() throws Exception {
        List<BoundCodeDTO> boundCodeDTOs = new ArrayList<>();
        String querySql = "SELECT distinct bound_code FROM cust_customers where bound_code <> ''";
        Query query = emHN.createNativeQuery(querySql);
        List lstTemp = query.getResultList();
        if (lstTemp != null && lstTemp.size() > 0) {
            for (int i = 0; i < lstTemp.size(); i++) {
                String obj = (String) lstTemp.get(i);
                BoundCodeDTO boundCodeDTO = new BoundCodeDTO();
                if (obj != null) {
                    boundCodeDTO.setBoundCodeId(obj.toString());
                    boundCodeDTO.setBoundCode(obj.toString());
                }
                boundCodeDTOs.add(boundCodeDTO);
            }
        }
        return boundCodeDTOs;
    }

    @Override
    public String findEmployeeIdByUser(String username, String dataSource) throws Exception {
        List<BoundCodeDTO> boundCodeDTOs = new ArrayList<>();
        String querySql = "SELECT TOP 1 employee_id FROM lst_employees WHERE UPPER(email) like :userName";
        Query query;
        if (DataUtil.safeEqual(dataSource,Const.PERSISTENCE.CRM_HN)){
            query = emHN.createNativeQuery(querySql);
        }else{
            query = emHCM.createNativeQuery(querySql);
        }
        query.setParameter("userName","%"+DataUtil.safeToUpper(username)+"%");
        Object lstTemp = query.getSingleResult();
        return DataUtil.safeToString(lstTemp);
    }
}