package com.tp.dwh.repo.impl;

import com.tp.dto.VoiceDetailReportSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.dwh.dto.CXBitCdrDTO;
import com.tp.dwh.model.CXBitCdr;
import com.tp.dwh.repo.CXBitCdrRepoCustom;
import com.tp.util.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CXBitCdrRepoImpl implements CXBitCdrRepoCustom {

    public static final Logger logger = Logger.getLogger(CXBitCdrRepoCustom.class);

    private BaseMapper<CXBitCdr, CXBitCdrDTO> mapper = new BaseMapper<>(CXBitCdr.class, CXBitCdrDTO.class);

    @PersistenceContext(unitName = Const.PERSISTENCE.DHW)
    EntityManager emDWH;

    @Override
    public ResponseSearchDTO<CXBitCdrDTO> searchCXBitCdr(VoiceDetailReportSearchDTO voiceDetailReportSearchDTO) throws Exception {
        ResponseSearchDTO<CXBitCdrDTO> responseSearch = new ResponseSearchDTO<>();
        StringBuilder sql = new StringBuilder("SELECT {0} FROM CX_BIT_CDR WHERE 1 = 1 ");
        List<String> params = new ArrayList<>(); //sometime should use map

        if(!DataUtil.isNullObject(voiceDetailReportSearchDTO.getFromDate())) {
            sql.append(" AND DATE(CALLDATE) >= ? ");
            params.add(DateUtil.date2String(voiceDetailReportSearchDTO.getFromDate()));
        }
        if(!DataUtil.isNullObject(voiceDetailReportSearchDTO.getToDate())) {
            sql.append(" AND DATE(CALLDATE) <= ? ");
            params.add(DateUtil.date2String(voiceDetailReportSearchDTO.getToDate()));
        }

        if(!DataUtil.isNullOrEmpty(voiceDetailReportSearchDTO.getVoiceCode())) {
            sql.append(" AND SRC = ? ");
            params.add(voiceDetailReportSearchDTO.getVoiceCode());
        }

        if(!DataUtil.isNullOrEmpty(voiceDetailReportSearchDTO.getVoiceCodes())) {
            sql.append(" AND SRC IN "+voiceDetailReportSearchDTO.getVoiceCodes().toString().replace("[", "(").replace("]", ")"));
        }

        if(!DataUtil.isNullOrEmpty(voiceDetailReportSearchDTO.getDst())) {
            sql.append(" AND dst = ? ");
            params.add(voiceDetailReportSearchDTO.getDst());
        }

        sql.append(" AND dcontext = ''vn-callout'' ");
//        params.add(QueryUtil.createSafeLikeValue("vn-callout"));

//        if(DataUtil.isNullOrEmpty(voiceDetailReportSearchDTO.getSortField()))
//            sql.append(" ORDER BY CALLDATE ASC ");
//        else {
//            sql.append(" ORDER BY " + voiceDetailReportSearchDTO.getSortField() + " " + voiceDetailReportSearchDTO.getSortOrder());
//        }

        String sqlCount = MessageFormat.format(sql.toString(), "count(*)");
        Query queryCount = emDWH.createNativeQuery(sqlCount);
        String sqlSelect = MessageFormat.format(sql.toString(), " * ");
        Query querySelect = emDWH.createNativeQuery(sqlSelect, CXBitCdr.class);
        querySelect.setFirstResult(voiceDetailReportSearchDTO.getFirst());
        querySelect.setMaxResults(voiceDetailReportSearchDTO.getPageSize());
        for(int i = 0 ; i < params.size() ; i++) {
            queryCount.setParameter(i + 1, params.get(i));
            querySelect.setParameter(i + 1, params.get(i));
        }

        responseSearch.setRowCount(DataUtil.safeToLong(queryCount.getSingleResult()));
        List<CXBitCdr> resultList = querySelect.getResultList();
        resultList.forEach(s -> s.setKey(s.getId().toString()));
        responseSearch.setData(mapper.toDtoBean(resultList));

        return responseSearch;
    }
}