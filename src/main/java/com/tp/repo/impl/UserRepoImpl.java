package com.tp.repo.impl;

import com.tp.dto.admin.UserDTO;
import com.tp.dto.requestSearch.UserInputSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.model.admin.User;
import com.tp.repo.UserRepoCustom;
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
 * Created by user on 21/08/2017.
 */
public class UserRepoImpl implements UserRepoCustom {

    private BaseMapper<User, UserDTO> mapper = new BaseMapper<>(User.class, UserDTO.class);

    @PersistenceContext(unitName = Const.PERSISTENCE.SALE_PORTAL)
    private EntityManager em;


    @Override
    public ResponseSearchDTO<UserDTO> searchUser(UserInputSearchDTO userInputSearchDTO) throws Exception {
        ResponseSearchDTO<UserDTO> responseSearch= new ResponseSearchDTO<>();
        StringBuilder sql=new StringBuilder("SELECT DISTINCT {0} FROM USER u WHERE 1=1 ");
        List<String> params = new ArrayList<>();

        if(!DataUtil.isNullOrEmpty(userInputSearchDTO.getUserName())){
            sql.append(" AND UPPER(u.USER_NAME) LIKE ? ");
            params.add(QueryUtil.createSafeLikeValue(userInputSearchDTO.getUserName()));
        }
        if(!DataUtil.isNullOrEmpty(userInputSearchDTO.getFullName())){
            sql.append(" AND UPPER(u.FULL_NAME) LIKE ? ");
            params.add(QueryUtil.createSafeLikeValue(userInputSearchDTO.getFullName()));
        }
        if(!DataUtil.isNullObject(userInputSearchDTO.getStatus())){
            sql.append(" AND u.STATUS = ? ");
            params.add(DataUtil.safeToString(userInputSearchDTO.getStatus()));
        }
        if(DataUtil.isNullOrEmpty(userInputSearchDTO.getSortField())){
            sql.append(" ORDER BY u.USER_NAME ");
        }else{
            sql.append(" ORDER BY u." + userInputSearchDTO.getSortField() + " " + userInputSearchDTO.getSortOrder());
        }
        String sqlCount= MessageFormat.format(sql.toString()," count(DISTINCT u.user_name) ");
        Query queryCount=em.createNativeQuery(sqlCount);
        String sqlSelect= MessageFormat.format(sql.toString()," u.* ");
        Query querySelect=em.createNativeQuery(sqlSelect,User.class);
        querySelect.setFirstResult(userInputSearchDTO.getFirst());
        querySelect.setMaxResults(userInputSearchDTO.getPageSize());
        for(int i = 0 ; i < params.size() ; i++) {
            queryCount.setParameter(i + 1, params.get(i));
            querySelect.setParameter(i + 1, params.get(i));
        }
        responseSearch.setRowCount(DataUtil.safeToLong(queryCount.getSingleResult()));
        List<User> users = querySelect.getResultList();
        users.forEach(s -> s.setKey(s.getUserId().toString()));
        responseSearch.setData(mapper.toDtoBean(users));
        return responseSearch;
    }

    @Override
    public List<User> findDSAByUser(String userName) throws Exception {
        StringBuilder sql = new StringBuilder(" SELECT u.* FROM USER u, DSA_ROLE dr WHERE 1=1 ");
        sql.append("and u.user_name = dr.user_name ");
        sql.append("and dr.role = 'DSA' ");
        sql.append("and dr.status = 1 ");
        if (!DataUtil.isNullOrEmpty(userName)){
            sql.append("and dr.lead_user_id = (select id from USER where UPPER(user_name) = :userName )");
        }
        Query querySelect = em.createNativeQuery(sql.toString(), User.class);
        querySelect.setParameter("userName",userName);
        List<User> users = querySelect.getResultList();
        return users;
    }

    @Override
    public List<User> findTlDSAByUser(String userName) throws Exception {
        StringBuilder sql = new StringBuilder(" SELECT u.* FROM USER u, DSA_ROLE dr WHERE 1=1 ");
        sql.append("and u.user_name = dr.user_name ");
        sql.append("and dr.role = 'TL/JTL-DSA' ");
        sql.append("and dr.status = 1 ");
        if (!DataUtil.isNullOrEmpty(userName)){
            sql.append("and dr.lead_user_id = (select id from USER where UPPER(user_name) = :userName )");
        }
        Query querySelect = em.createNativeQuery(sql.toString(), User.class);
        querySelect.setParameter("userName",userName);
        List<User> users = querySelect.getResultList();
        return users;
    }

    @Override
    public List<User> findTlDSA() throws Exception {
        StringBuilder sql = new StringBuilder(" SELECT u.* FROM USER u, DSA_ROLE dr WHERE 1=1 ");
        sql.append("and u.user_name = dr.user_name ");
        sql.append("and u.status = 1 ");
        sql.append("and dr.role = 'TL/JTL-DSA' ");
        Query querySelect = em.createNativeQuery(sql.toString(), User.class);
        List<User> users = querySelect.getResultList();
        return users;
    }

    @Override
    public List<User> findAllCCa() throws Exception {
        StringBuilder sql = new StringBuilder(" SELECT u.* FROM USER u where 1=1 ");
        sql.append("and u.status = 1 ");
        sql.append("and u.role = 'CCA' ");
        Query querySelect = em.createNativeQuery(sql.toString(), User.class);
        List<User> users = querySelect.getResultList();
        return users;
    }

    @Override
    public List<User> findAllStaffCodeByTeam(String teamCode) throws Exception {
        StringBuilder sql = new StringBuilder(" SELECT u.* FROM user u, cca_role cr, team t WHERE 1=1 ");
        sql.append("AND u.id = cr.user_id ");
        sql.append("AND cr.team_id = t.id ");
        sql.append("AND cr.status = 1 AND u.status = 1 AND t.status = 1 ");
        if (!DataUtil.isNullOrEmpty(teamCode)){
            sql.append("AND t.team_code = :teamCode ");
        }
        Query querySelect = em.createNativeQuery(sql.toString(), User.class);
        querySelect.setParameter("teamCode",teamCode);
        List<User> users = querySelect.getResultList();
        return users;
    }

    @Override
    public List<User> findAllStaffCodeByListTeam(List<String> teamCodes) throws Exception {
        List<String> params = new ArrayList<>(); //sometime should use map
        StringBuilder sql = new StringBuilder(" SELECT u.* FROM user u, cca_role cr, team t WHERE 1=1 ");
        sql.append("AND u.id = cr.user_id ");
        sql.append("AND cr.team_id = t.id ");
        sql.append("AND cr.status = 1 AND u.status = 1 AND t.status = 1 ");
        if (!DataUtil.isNullOrEmpty(teamCodes)){
            sql.append("AND t.team_code IN :teamCodes ");
        }
        Query querySelect = em.createNativeQuery(sql.toString(), User.class);
        querySelect.setParameter("teamCodes",teamCodes);
        List<User> users = querySelect.getResultList();
        return users;
    }

    @Override
    public List<User> findAllByUserTl(String userNameTl) throws Exception {
        StringBuilder sql = new StringBuilder(" SELECT u.* FROM USER u, team t, CCA_ROLE dr WHERE 1=1 ");
        sql.append("and u.id = dr.user_id ");
        sql.append("and dr.team_id = t.id ");
        if (!DataUtil.isNullOrEmpty(userNameTl)){
            sql.append("and t.team_lead_id = (select id from USER where UPPER(user_name) = :userName )");
        }
        Query querySelect = em.createNativeQuery(sql.toString(), User.class);
        querySelect.setParameter("userName",userNameTl);
        List<User> users = querySelect.getResultList();
        return users;
    }

    @Override
    public List<User> findAllByTeamId(Long teamId) throws Exception {
        StringBuilder sql = new StringBuilder(" SELECT u.* FROM USER u, CCA_ROLE dr WHERE 1=1 ");
        sql.append("and u.id = dr.user_id ");
        if (!DataUtil.isNullObject(teamId)){
            sql.append("and dr.team_id = :teamId ");
        }
        Query querySelect = em.createNativeQuery(sql.toString(), User.class);
        querySelect.setParameter("teamId",teamId);
        List<User> users = querySelect.getResultList();
        return users;
    }

    @Override
    public User findTlByUserName(String userName) throws Exception {
        List<String> params = new ArrayList<>(); //sometime should use map
        StringBuilder sql = new StringBuilder(" SELECT u1.* FROM user u1 WHERE u1.id = ( ");
        sql.append(" SELECT t.team_lead_id FROM user u, cca_role cr, team t WHERE 1=1 ");
        sql.append(" AND t.id = cr.team_id ");
        sql.append(" AND cr.user_id = u.id ");
        if (!DataUtil.isNullOrEmpty(userName)){
            sql.append(" AND UPPER(u.user_name) = :userName ");
        }
        sql.append(" ) ");
        Query querySelect = em.createNativeQuery(sql.toString(), User.class);
        if (!DataUtil.isNullOrEmpty(userName)) {
            querySelect.setParameter("userName", DataUtil.safeToUpper(userName));
        }
        List<User> users = querySelect.getResultList();
        if (DataUtil.isNullOrEmpty(users)){
            return null;
        }
        return users.get(0);
    }
}
