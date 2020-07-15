package com.tp.repo.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.tp.dto.ApDomainDTO;
import com.tp.dto.requestSearch.ApDomainInputSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.model.ApDomain;
import com.tp.repo.ApDomainRepoCustom;
import com.tp.util.BaseMapper;
import com.tp.util.Const;
import com.tp.util.DataUtil;
import com.tp.util.QueryUtil;

/**
 * Created by hopnv on 21/07/2017.
 */
public class ApDomainRepoImpl implements ApDomainRepoCustom{

    @PersistenceContext(unitName = Const.PERSISTENCE.SALE_PORTAL)
    private EntityManager em;

    private BaseMapper<ApDomain, ApDomainDTO> mapper = new BaseMapper<>(ApDomain.class, ApDomainDTO.class);

    @Override
    public ResponseSearchDTO<ApDomainDTO> searchApDomain(ApDomainInputSearchDTO apDomainInputSearchDTO) throws Exception {
        ResponseSearchDTO<ApDomainDTO> responseSearch = new ResponseSearchDTO<>();
        StringBuilder sql = new StringBuilder("SELECT {0} FROM AP_DOMAIN WHERE 1 = 1 ");
        List<String> params = new ArrayList<>(); //sometime should use map
        if(!DataUtil.isNullOrEmpty(apDomainInputSearchDTO.getType())) {
            sql.append(" AND UPPER(TYPE) LIKE ? ");
            params.add(QueryUtil.createSafeLikeValue(apDomainInputSearchDTO.getType()));
        }
        if(!DataUtil.isNullOrEmpty(apDomainInputSearchDTO.getName())) {
            sql.append(" AND UPPER(NAME) LIKE ? ");
            params.add(QueryUtil.createSafeLikeValue(apDomainInputSearchDTO.getName()));
        }
        if(!DataUtil.isNullOrEmpty(apDomainInputSearchDTO.getCode())) {
            sql.append(" AND UPPER(code) LIKE ? ");
            params.add(QueryUtil.createSafeLikeValue(apDomainInputSearchDTO.getCode()));
        }
        if(!DataUtil.isNullOrEmpty(apDomainInputSearchDTO.getValue())) {
            sql.append(" AND UPPER(value) LIKE ? ");
            params.add(QueryUtil.createSafeLikeValue(apDomainInputSearchDTO.getValue()));
        }
        if(!DataUtil.isNullOrEmpty(apDomainInputSearchDTO.getDesc())) {
            sql.append(" AND UPPER(description) LIKE ? ");
            params.add(QueryUtil.createSafeLikeValue(apDomainInputSearchDTO.getDesc()));
        }
        if(apDomainInputSearchDTO.getStatus() != null) {
            sql.append(" AND status = ? ");
            params.add(apDomainInputSearchDTO.getStatus().toString());
        }

        if(DataUtil.isNullOrEmpty(apDomainInputSearchDTO.getSortField()))
            sql.append(" ORDER BY TYPE ");
        else {
            sql.append(" ORDER BY " + apDomainInputSearchDTO.getSortField() + " " + apDomainInputSearchDTO.getSortOrder());
        }

        String sqlCount = MessageFormat.format(sql.toString(), "count(*)");
        Query queryCount = em.createNativeQuery(sqlCount);
        String sqlSelect = MessageFormat.format(sql.toString(), " * ");
        Query querySelect = em.createNativeQuery(sqlSelect, ApDomain.class);
        querySelect.setFirstResult(apDomainInputSearchDTO.getFirst());
        querySelect.setMaxResults(apDomainInputSearchDTO.getPageSize());
        for(int i = 0 ; i < params.size() ; i++) {
            queryCount.setParameter(i + 1, params.get(i));
            querySelect.setParameter(i + 1, params.get(i));
        }

        responseSearch.setRowCount(DataUtil.safeToLong(queryCount.getSingleResult()));
        List<ApDomain> resultList = querySelect.getResultList();
        resultList.forEach(s -> s.setKey(s.getApDomainId().toString()));
        responseSearch.setData(mapper.toDtoBean(resultList));

        return responseSearch;
    }
}
