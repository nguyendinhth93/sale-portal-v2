package com.tp.repo.impl;

import com.tp.config.lang.LanguageBean;
import com.tp.dto.admin.MenuDTO;
import com.tp.dto.requestSearch.MenuRequestSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.model.admin.Menu;
import com.tp.repo.MenuRepoCustom;
import com.tp.util.BaseMapper;
import com.tp.util.Const;
import com.tp.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.tp.config.lang.Utf8ResourceBundle.UTF8_CONTROL;


/**
 * Created by user on 23/08/2017.
 */
public class MenuRepoImpl implements MenuRepoCustom{

    protected ResourceBundle bundle;
    @Autowired
    private LanguageBean languageBean;

    @PersistenceContext(unitName = Const.PERSISTENCE.SALE_PORTAL)
    private EntityManager em;

    @Autowired
    private MessageSource messageSource;

    private BaseMapper<Menu, MenuDTO> mapper = new BaseMapper<>(Menu.class, MenuDTO.class);
    @Override
    public List<Menu> getAllMenus() {
        String sql = "SELECT * FROM MENU WHERE 1 = 1 AND STATUS =1";
        Query querySelect = em.createNativeQuery(sql, Menu.class);
        List<Menu> resultList = querySelect.getResultList();
        return resultList;
    }

    @Override
    public Menu getMenuByMenuId(Long id) {
        String sql="SELECT DISTINCT m.* FROM  MENU m WHERE m.status = 1 and m.id="+id;
        Query query=em.createNativeQuery(sql,Menu.class);
        return query.getResultList().isEmpty()? null : (Menu) query.getSingleResult();
    }


    @Override
    public ResponseSearchDTO<MenuDTO> searchMenu(MenuRequestSearchDTO menuRequestSearchDTO) throws Exception {
        ResponseSearchDTO<MenuDTO> responseSearch = new ResponseSearchDTO<>();
        StringBuilder sql = new StringBuilder("SELECT {0} FROM MENU WHERE 1 = 1 AND STATUS = 1 ");
        List<String> params = new ArrayList<>();
        if(menuRequestSearchDTO.getParentId() != null) {
            sql.append(" AND PARENTID= ? ");
            params.add(menuRequestSearchDTO.getParentId().toString());
        }else{
            sql.append(" AND PARENTID is null ");
        }

        if(DataUtil.isNullOrEmpty(menuRequestSearchDTO.getSortField()))
            sql.append(" ORDER BY CODE ");
        else {
            sql.append(" ORDER BY " + menuRequestSearchDTO.getSortField() + " " + menuRequestSearchDTO.getSortOrder());
        }

        String sqlCount = MessageFormat.format(sql.toString(), "count(*)");
        Query queryCount = em.createNativeQuery(sqlCount);
        String sqlSelect = MessageFormat.format(sql.toString(), " * ");
        Query querySelect = em.createNativeQuery(sqlSelect, Menu.class);
        querySelect.setFirstResult(menuRequestSearchDTO.getFirst());
        querySelect.setMaxResults(menuRequestSearchDTO.getPageSize());
        for(int i = 0 ; i < params.size() ; i++) {
            queryCount.setParameter(i + 1, params.get(i));
            querySelect.setParameter(i + 1, params.get(i));
        }

        responseSearch.setRowCount(DataUtil.safeToLong(queryCount.getSingleResult()));
        List<Menu> resultList = querySelect.getResultList();
        resultList.forEach(s -> s.setKey(s.getId().toString()));
        responseSearch.setData(mapper.toDtoBean(resultList));

        return responseSearch;
    }

    public String getText(String key) {
        try {
            if (bundle == null || (bundle.getLocale().getLanguage().equals(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()))) {
                bundle = ResourceBundle.getBundle(Const.LANGUAGE.BUNDLE_NAME,
                        languageBean == null ? FacesContext.getCurrentInstance().getViewRoot().getLocale() : languageBean.getLocale(), UTF8_CONTROL);
            }
            return bundle.getString(key);
        } catch (Exception e) {
            return key;
        }
    }

    @Override
    public List<Menu> getListMenu(String username, String appCode) {
        StringBuilder sql= new StringBuilder("");
        sql.append(" SELECT DISTINCT m.* from menu m INNER JOIN group_menu gm INNER JOIN ");
        sql.append("        (SELECT DISTINCT p.* from (SELECT * from user where UPPER(user_name)=:userName AND status=1) u ");
        sql.append("        INNER JOIN user_group ug INNER JOIN p_group p ");
        sql.append("        ON u.id= ug.user_id AND ug.group_id=p.id WHERE  p.status=1 ) p ");
        sql.append("        on m.id =gm.menu_id AND gm.group_id=p.id WHERE m.status=1 and gm.status = 1 ");
        Query query=em.createNativeQuery(sql.toString(), Menu.class);
        query.setParameter("userName",username.toUpperCase());
        List<Menu> menus= query.getResultList();
        return menus;
    }
}
