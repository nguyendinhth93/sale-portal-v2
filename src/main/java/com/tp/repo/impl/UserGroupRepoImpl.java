package com.tp.repo.impl;
import com.tp.dto.BoCheckupResultDTO;
import com.tp.dto.admin.UserGroupDTO;
import com.tp.dto.requestSearch.UserGroupSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.model.BoCheckupResult;
import com.tp.repo.UserGroupRepoCustom;
import com.tp.util.*;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class UserGroupRepoImpl implements UserGroupRepoCustom {

    public static final Logger logger = Logger.getLogger(UserGroupRepoCustom.class);

    @PersistenceContext(unitName = Const.PERSISTENCE.SALE_PORTAL)
    private EntityManager em;

    @Override
    public ResponseSearchDTO<UserGroupDTO> searchUserGroup(UserGroupSearchDTO userGroupSearchDTO) throws Exception {
        ResponseSearchDTO<UserGroupDTO> responseSearch = new ResponseSearchDTO<>();
        List<UserGroupDTO> userGroupDTOs = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT {0} FROM USER u INNER JOIN USER_GROUP ug ON ug.USER_ID = u.ID WHERE 1 = 1 ");
        List<String> params = new ArrayList<>(); //sometime should use map
        if(!DataUtil.isNullOrEmpty(userGroupSearchDTO.getUserName())) {
            sql.append(" AND UPPER(u.USER_NAME) LIKE ? ");
            params.add(QueryUtil.createSafeLikeValue(userGroupSearchDTO.getUserName()));
        }
        if(!DataUtil.isNullObject(userGroupSearchDTO.getGroupId())) {
            sql.append(" AND ug.GROUP_ID = ? ");
            params.add(DataUtil.safeToString(userGroupSearchDTO.getGroupId()));
        }
        sql.append(" AND u.STATUS = 1 ");
        if(DataUtil.isNullOrEmpty(userGroupSearchDTO.getSortField()))
            sql.append(" ORDER BY u.USER_NAME ASC ");
        else {
            sql.append(" ORDER BY " + userGroupSearchDTO.getSortField() + " " + userGroupSearchDTO.getSortOrder());
        }

        String sqlCount = MessageFormat.format(sql.toString(), "count(*)");
        Query queryCount = em.createNativeQuery(sqlCount);
        String sqlSelect = MessageFormat.format(sql.toString(), " ug.id, u.user_name,  (select pg.name from p_group pg where pg.id = ug.group_id) group_name, ug.status, ug.group_id, ug.user_id ");
        Query querySelect = em.createNativeQuery(sqlSelect);
        querySelect.setFirstResult(userGroupSearchDTO.getFirst());
        querySelect.setMaxResults(userGroupSearchDTO.getPageSize());
        for(int i = 0 ; i < params.size() ; i++) {
            queryCount.setParameter(i + 1, params.get(i));
            querySelect.setParameter(i + 1, params.get(i));
        }
        responseSearch.setRowCount(DataUtil.safeToLong(queryCount.getSingleResult()));
        List resultList = querySelect.getResultList();
        if (!DataUtil.isNullOrEmpty(resultList)) {
            UserGroupDTO userGroupDTO;
            for (int i=0;i< resultList.size();i++) {
                Object[] obj = (Object[]) resultList.get(i);
                userGroupDTO = new UserGroupDTO();
                userGroupDTO.setKey(DataUtil.safeToString(i));
                if (!DataUtil.isNullObject(obj[0])) {
                    userGroupDTO.setId(DataUtil.safeToLong(obj[0].toString()));
                }
                if (!DataUtil.isNullObject(obj[1])) {
                    userGroupDTO.setUserName(obj[1].toString());
                }
                if (!DataUtil.isNullObject(obj[2])) {
                    userGroupDTO.setGroupName(obj[2].toString());
                }
                if (!DataUtil.isNullObject(obj[3])) {
                    userGroupDTO.setStatus(DataUtil.safeToInt(obj[3].toString()));
                }
                if (!DataUtil.isNullObject(obj[4])) {
                    userGroupDTO.setGroupId(DataUtil.safeToLong(obj[4].toString()));
                }
                if (!DataUtil.isNullObject(obj[5])) {
                    userGroupDTO.setUserId(DataUtil.safeToLong(obj[5].toString()));
                }
                userGroupDTOs.add(userGroupDTO);
            }
        }
        responseSearch.setData(userGroupDTOs);

        return responseSearch;
    }
}