package com.tp.repo.impl;

import com.tp.dto.CreditScoringDTO;
import com.tp.dto.requestSearch.CreditScoringSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.model.CreditScoring;
import com.tp.repo.CreditScoringRepoCustom;
import com.tp.util.BaseMapper;
import com.tp.util.Const;
import com.tp.util.DataUtil;
import com.tp.util.QueryUtil;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class CreditScoringRepoImpl implements CreditScoringRepoCustom {

    public static final Logger logger = Logger.getLogger(CreditScoringRepoCustom.class);
    private final BaseMapper<CreditScoring,CreditScoringDTO> mapper = new BaseMapper<>(CreditScoring.class,CreditScoringDTO.class);


    @PersistenceContext(unitName = Const.PERSISTENCE.SALE_PORTAL)
    private EntityManager em;

    @Override
    public ResponseSearchDTO<CreditScoringDTO> searchCreditScoring(CreditScoringSearchDTO creditScoringSearchDTO) throws Exception {
        ResponseSearchDTO<CreditScoringDTO> responseSearch = new ResponseSearchDTO<>();
        StringBuilder sql = new StringBuilder("SELECT {0} FROM CREDIT_SCORING WHERE 1 = 1 ");
        List<String> params = new ArrayList<>(); //sometime should use map
        if(!DataUtil.isNullOrEmpty(creditScoringSearchDTO.getProgramCode())) {
            sql.append(" AND UPPER(PROGRAM_CODE) LIKE ? ");
            params.add(QueryUtil.createSafeLikeValue(creditScoringSearchDTO.getProgramCode()));
        }
        if(!DataUtil.isNullOrEmpty(creditScoringSearchDTO.getIsdn())) {
            sql.append(" AND UPPER(ISDN) LIKE ? ");
            params.add(QueryUtil.createSafeLikeValue(creditScoringSearchDTO.getIsdn()));
        }
        if(!DataUtil.isNullOrEmpty(creditScoringSearchDTO.getCreditScoring())) {
            sql.append(" AND UPPER(CREDIT_SCORING) LIKE ? ");
            params.add(QueryUtil.createSafeLikeValue(creditScoringSearchDTO.getCreditScoring()));
        }

        if(DataUtil.isNullOrEmpty(creditScoringSearchDTO.getSortField()))
            sql.append(" ORDER BY ISDN ");
        else {
            sql.append(" ORDER BY " + creditScoringSearchDTO.getSortField() + " " + creditScoringSearchDTO.getSortOrder());
        }

        String sqlCount = MessageFormat.format(sql.toString(), "count(*)");
        Query queryCount = em.createNativeQuery(sqlCount);
        String sqlSelect = MessageFormat.format(sql.toString(), " * ");
        Query querySelect = em.createNativeQuery(sqlSelect, CreditScoring.class);
        querySelect.setFirstResult(creditScoringSearchDTO.getFirst());
        querySelect.setMaxResults(creditScoringSearchDTO.getPageSize());
        for(int i = 0 ; i < params.size() ; i++) {
            queryCount.setParameter(i + 1, params.get(i));
            querySelect.setParameter(i + 1, params.get(i));
        }

        responseSearch.setRowCount(DataUtil.safeToLong(queryCount.getSingleResult()));
        List<CreditScoring> resultList = querySelect.getResultList();
        resultList.forEach(s -> s.setKey(s.getId().toString()));
        responseSearch.setData(mapper.toDtoBean(resultList));

        return responseSearch;
    }
}