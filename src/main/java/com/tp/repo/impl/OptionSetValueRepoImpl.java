package com.tp.repo.impl;

import com.google.common.collect.Lists;
import com.tp.dto.OptionSetInputSearchDTO;
import com.tp.dto.OptionSetValueDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.model.OptionSetValue;
import com.tp.repo.OptionSetValueRepoCustom;
import com.tp.util.BaseMapper;
import com.tp.util.Const;
import com.tp.util.DataUtil;
import com.tp.util.QueryUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hopnv on 28/07/2017.
 */
public class OptionSetValueRepoImpl implements OptionSetValueRepoCustom {

    @PersistenceContext(unitName = Const.PERSISTENCE.SALE_PORTAL)
    private EntityManager em;

    private BaseMapper<OptionSetValue, OptionSetValueDTO> mapper = new BaseMapper<>(OptionSetValue.class, OptionSetValueDTO.class);

    @Override
    public ResponseSearchDTO<OptionSetValueDTO> searchOptionSetValue(OptionSetInputSearchDTO optionSetInputSearchDTO) throws Exception {
        ResponseSearchDTO<OptionSetValueDTO> responseSearch = new ResponseSearchDTO<>();
        StringBuilder sql = new StringBuilder("SELECT {0} FROM OPTION_SET_VALUE osv, OPTION_SET os WHERE osv.STATUS = 1 AND os.STATUS = 1 AND osv.OPTION_SET_ID = os.ID AND UPPER(os.CODE) = ? ");
        List<String> params = new ArrayList<>(); //sometime should use map
        params.add(optionSetInputSearchDTO.getActionCode().toUpperCase());
        if(!DataUtil.isNullOrEmpty(optionSetInputSearchDTO.getName())) {
            sql.append(" AND UPPER(osv.NAME) LIKE ? ");
            params.add(QueryUtil.createSafeLikeValue(optionSetInputSearchDTO.getName()));
        }
        if(!DataUtil.isNullOrEmpty(optionSetInputSearchDTO.getCode())) {
            sql.append(" AND UPPER(osv.code) LIKE ? ");
            params.add(QueryUtil.createSafeLikeValue(optionSetInputSearchDTO.getCode()));
        }
        if(!DataUtil.isNullOrEmpty(optionSetInputSearchDTO.getValue())) {
            sql.append(" AND UPPER(osv.value) LIKE ? ");
            params.add(QueryUtil.createSafeLikeValue(optionSetInputSearchDTO.getValue()));
        }
        if(!DataUtil.isNullOrEmpty(optionSetInputSearchDTO.getDescription())) {
            sql.append(" AND UPPER(osv.description) LIKE ? ");
            params.add(QueryUtil.createSafeLikeValue(optionSetInputSearchDTO.getDescription()));
        }

        if(DataUtil.isNullOrEmpty(optionSetInputSearchDTO.getSortField()))
            sql.append(" ORDER BY osv.NAME ");
        else {
            sql.append(" ORDER BY osv." + optionSetInputSearchDTO.getSortField() + " " + optionSetInputSearchDTO.getSortOrder());
        }

        String sqlCount = MessageFormat.format(sql.toString(), "count(*)");
        Query queryCount = em.createNativeQuery(sqlCount);
        String sqlSelect = MessageFormat.format(sql.toString(), " osv.* ");
        Query querySelect = em.createNativeQuery(sqlSelect, OptionSetValue.class);
        querySelect.setFirstResult(optionSetInputSearchDTO.getFirst());
        querySelect.setMaxResults(optionSetInputSearchDTO.getPageSize());
        for(int i = 0 ; i < params.size() ; i++) {
            queryCount.setParameter(i + 1, params.get(i));
            querySelect.setParameter(i + 1, params.get(i));
        }

        responseSearch.setRowCount(DataUtil.safeToLong(queryCount.getSingleResult()));
        List<OptionSetValue> resultList = querySelect.getResultList();
        resultList.forEach(s -> s.setKey(s.getId().toString()));
        responseSearch.setData(mapper.toDtoBean(resultList));

        return responseSearch;

    }

    @Override
    public OptionSetValue findByCodeAndActionCode(String actionCode, String code) {

        StringBuilder sql = new StringBuilder("SELECT osv.* FROM OPTION_SET_VALUE osv, OPTION_SET os ");
        sql.append("                                WHERE osv.OPTION_SET_ID = os.ID AND UPPER(os.CODE) = :type ");
        sql.append("                                AND UPPER(osv.CODE) = :code AND osv.STATUS = 1 AND os.STATUS = 1 ");

        Query query = em.createNativeQuery(sql.toString(), OptionSetValue.class);
        query.setParameter("type", actionCode.toUpperCase());
        query.setParameter("code", code.toUpperCase());
        List<OptionSetValue> optionSetValues = Lists.newArrayList(query.getResultList());
        if(DataUtil.isNullOrEmpty(optionSetValues))
            return null;
        return optionSetValues.get(0);
    }

    @Override
    public List<OptionSetValue> findListByCodeAndActionCode(String actionCode, String code) throws Exception {
        StringBuilder sql = new StringBuilder("SELECT osv.* FROM OPTION_SET_VALUE osv, OPTION_SET os ");
        sql.append("                                WHERE osv.OPTION_SET_ID = os.ID AND UPPER(os.CODE) = :type ");
        sql.append("                                AND UPPER(osv.CODE) = :code AND osv.STATUS = 1 AND os.STATUS = 1 ");

        Query query = em.createNativeQuery(sql.toString(), OptionSetValue.class);
        query.setParameter("type", actionCode.toUpperCase());
        query.setParameter("code", code.toUpperCase());
        List<OptionSetValue> optionSetValues = Lists.newArrayList(query.getResultList());
        return optionSetValues;
    }

    @Override
    public OptionSetValue findByName( String actionCode,String name) {

            StringBuilder sql = new StringBuilder("SELECT osv.* FROM OPTION_SET_VALUE osv, OPTION_SET os ");
            sql.append("                                WHERE osv.OPTION_SET_ID = os.ID AND UPPER(os.CODE) = :type ");
            sql.append("                                AND UPPER(osv.NAME) = :name AND osv.STATUS = 1 AND os.STATUS = 1 ");

            Query query = em.createNativeQuery(sql.toString(), OptionSetValue.class);
            query.setParameter("type", actionCode.toUpperCase());
            query.setParameter("name", name.toUpperCase());
            List<OptionSetValue> optionSetValues = Lists.newArrayList(query.getResultList());
            if(DataUtil.isNullOrEmpty(optionSetValues))
                return null;
            return optionSetValues.get(0);
    }

    @Override
    public OptionSetValue findByCodeAndNameAndActionCode(String actionCode, String code, String name) {

        StringBuilder sql = new StringBuilder("SELECT osv.* FROM OPTION_SET_VALUE osv, OPTION_SET os ");
        sql.append("                                WHERE osv.OPTION_SET_ID = os.ID AND UPPER(os.CODE) = :type ");
        sql.append("                               AND ( UPPER(osv.CODE) = :code OR UPPER(osv.NAME)= :name ) AND osv.STATUS = 1 AND os.STATUS = 1 ");

        Query query = em.createNativeQuery(sql.toString(), OptionSetValue.class);
        query.setParameter("type", actionCode.toUpperCase());
        query.setParameter("code", code.toUpperCase());
        query.setParameter("name", name.toUpperCase());
        List<OptionSetValue> optionSetValues = Lists.newArrayList(query.getResultList());
        if(DataUtil.isNullOrEmpty(optionSetValues))
            return null;
        return optionSetValues.get(0);
    }

    @Override
    public OptionSetValue findByCodeAndValue(String code, String value) throws Exception {
        StringBuilder sql = new StringBuilder("SELECT osv.* FROM OPTION_SET_VALUE osv, OPTION_SET os ");
        sql.append("                                WHERE osv.OPTION_SET_ID = os.ID AND UPPER(os.CODE) = :code ");
        sql.append("                               AND UPPER(osv.VALUE) = :value AND osv.STATUS = 1 AND os.STATUS = 1 ");

        Query query = em.createNativeQuery(sql.toString(), OptionSetValue.class);
        query.setParameter("code", code.toUpperCase());
        query.setParameter("value", value.toUpperCase());
        List<OptionSetValue> optionSetValues = Lists.newArrayList(query.getResultList());
        if(DataUtil.isNullOrEmpty(optionSetValues))
            return null;
        return optionSetValues.get(0);
    }

    @Override
    public List<OptionSetValue> findLikeByCodeOrNameAndActionCode(String actionCode, String code, String name) throws Exception {
        StringBuilder sql = new StringBuilder("SELECT osv.* FROM OPTION_SET_VALUE osv, OPTION_SET os ");
        sql.append("                                WHERE osv.OPTION_SET_ID = os.ID AND UPPER(os.CODE) = :type ");
        sql.append("                               AND ( UPPER(osv.CODE) LIKE :code OR UPPER(osv.NAME) LIKE :name ) AND osv.STATUS = 1 AND os.STATUS = 1 ");

        Query query = em.createNativeQuery(sql.toString(), OptionSetValue.class);
        query.setParameter("type", actionCode.toUpperCase());
        query.setParameter("code", QueryUtil.createSafeLikeValue(code));
        query.setParameter("name", QueryUtil.createSafeLikeValue(name));
        return Lists.newArrayList(query.getResultList());
    }


    @Override
    public List<OptionSetValue> findByActionCode(String actionCode) {
        StringBuilder sql = new StringBuilder("SELECT osv.* FROM OPTION_SET_VALUE osv, OPTION_SET os ");
        sql.append("                                WHERE osv.OPTION_SET_ID = os.ID AND UPPER(os.CODE) = :type ");
        sql.append("                                AND osv.STATUS = 1 AND os.STATUS = 1 ORDER BY osv.DISPLAY_ORDER, osv.NAME ");
        Query query = em.createNativeQuery(sql.toString(), OptionSetValue.class);
        query.setParameter("type", actionCode.toUpperCase());
        return Lists.newArrayList(query.getResultList());
    }
    @Override
    public Long getIdOptionSetValue(String actionCode, String name){
        StringBuilder sql = new StringBuilder("SELECT osv.* FROM OPTION_SET_VALUE osv, OPTION_SET os ");
        sql.append("                                WHERE osv.OPTION_SET_ID = os.ID AND UPPER(os.CODE) = :type ");
        sql.append("                                AND UPPER(osv.NAME) = :name AND osv.STATUS = 1 AND os.STATUS = 1 ");

        Query query = em.createNativeQuery(sql.toString(), OptionSetValue.class);
        query.setParameter("type", actionCode.toUpperCase());
        query.setParameter("name", name.toUpperCase());
        List<OptionSetValue> optionSetValues = Lists.newArrayList(query.getResultList());
        if(DataUtil.isNullOrEmpty(optionSetValues))
            return null;
        return optionSetValues.get(0).getId();
    }

    @Override
    public List<OptionSetValue> findSateAndQuantity(String actionCode, Long sparePartId,Long branchFromId) throws Exception {
        StringBuilder sql = new StringBuilder("SELECT osv.ID,osv.NAME, sspi.quantity FROM ");
        sql.append(" (SELECT osv.* from option_set_value osv, option_set op WHERE osv.STATUS=1 AND op.STATUS=1 AND osv.OPTION_SET_ID=op.ID AND op.CODE = :type) osv, statistic_spare_part_inventory sspi");
        sql.append("   WHERE osv.STATUS=1 AND sspi.status=1 AND sspi.spare_part_state=osv.ID AND sspi.spare_part_id= :id AND sspi.branch_id = :branchId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("type", actionCode.toUpperCase());
        query.setParameter("id", sparePartId);
        query.setParameter("branchId", branchFromId);
        List<Object[]> resultList = query.getResultList();
        List<OptionSetValue> list = new ArrayList<>();
        for(Object[] obj : resultList){
            OptionSetValue optionSetValue= new OptionSetValue();
            optionSetValue.setId(DataUtil.safeToLong(obj[0]));
            optionSetValue.setName(DataUtil.safeToString(obj[1]));
            optionSetValue.setValue(DataUtil.safeToString(obj[2]));
            list.add(optionSetValue);
        }
        return list;
    }

    @Override
    public List<OptionSetValue> findByType(String actionCode, String type) throws Exception {
        StringBuilder sql = new StringBuilder("SELECT osv.ID,osv.NAME, osv.VALUE ");
        sql.append(" FROM option_set_value osv, option_set op WHERE osv.STATUS=1 AND op.STATUS=1 AND osv.OPTION_SET_ID=op.ID AND op.CODE = :code");
        sql.append("  AND osv.VALUE= :type");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("code", actionCode.toUpperCase());
        query.setParameter("type", type);
        List<Object[]> resultList = query.getResultList();
        List<OptionSetValue> list = new ArrayList<>();
        for(Object[] obj : resultList){
            OptionSetValue optionSetValue= new OptionSetValue();
            optionSetValue.setId(DataUtil.safeToLong(obj[0]));
            optionSetValue.setName(DataUtil.safeToString(obj[1]));
            optionSetValue.setValue(DataUtil.safeToString(obj[2]));
            list.add(optionSetValue);
        }
        return list;
    }
}
