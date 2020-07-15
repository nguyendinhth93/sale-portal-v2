package com.tp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.tp.common.exception.LogicException;
import com.tp.dto.requestSearch.MenuRequestSearchDTO;
import com.tp.dto.requestSearch.RequestSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.dto.admin.MenuDTO;
import com.tp.model.admin.Menu;
import com.tp.model.admin.QMenu;
import com.tp.repo.MenuRepo;
import com.tp.service.MenuService;
import com.tp.util.BaseMapper;
import com.tp.util.Const;
import com.tp.util.DataUtil;

/**
 * Created by user on 24/08/2017.
 */
@Service
public class MenuServiceImpl  implements MenuService{
    @Autowired
    private MenuRepo menuRepo;

    private BaseMapper<Menu, MenuDTO> menuMapper = new BaseMapper<>(Menu.class, MenuDTO.class);

    @Override
    public ResponseSearchDTO<MenuDTO> load(RequestSearchDTO requestSearchDTO) throws Exception {
        MenuRequestSearchDTO menuRequestSearchDTO=(MenuRequestSearchDTO) requestSearchDTO;
        return menuRepo.searchMenu(menuRequestSearchDTO);
    }

    @Override
    public void delete(List<Long> menuIds, String username) throws  Exception {
        Date currentDate = new Date();
        List<Menu> menus = new ArrayList<>();
        for(Long  id : menuIds) {
            Menu menu = menuRepo.findOne(id);
            if (menu == null || DataUtil.safeEqual(menu.getStatus(), Const.STATUS.INACTIVCE)) {
                throw new LogicException("permission.msg.delete.fail");
            }
            menu.setStatus(Const.STATUS.INACTIVCE);
            menu.setUpdatedBy(username);
            menu.setUpdatedDate(currentDate);
            menus.add(menu);
        }

        menuRepo.save(menus);
    }

    @Override
    public List<MenuDTO> findByParentId(Long parentId, Integer status) throws Exception {
        BooleanExpression booleanExpression = QMenu.menu.status.eq(Integer.valueOf("1"));
        if (null == parentId) {
            booleanExpression = booleanExpression.and(QMenu.menu.parentId.isNull());
        } else {
            booleanExpression = booleanExpression.and(QMenu.menu.parentId.eq(parentId));
        }
        if (null != status) {
            booleanExpression = booleanExpression.and(QMenu.menu.status.eq(Integer.valueOf(status)));
        }
        return menuMapper.toDtoBean((List<Menu>) menuRepo.findAll(booleanExpression));
    }


    @Override
    public List<MenuDTO> getListMenu() throws Exception {
        List<MenuDTO> listMenu=menuMapper.toDtoBean(menuRepo.getAllMenus());
        return listMenu;
    }

    @Override
    public MenuDTO findByCodeAndName(String name, String code) throws Exception {
        BooleanExpression predicate = QMenu.menu.name.equalsIgnoreCase(name);
        predicate = predicate.or(QMenu.menu.code.equalsIgnoreCase(code));
        predicate = predicate.and(QMenu.menu.status.eq(Const.STATUS.ACTIVCE));

        List<Menu> menus = Lists.newArrayList(menuRepo.findAll(predicate));
        if(DataUtil.isNullOrEmpty(menus))
            return null;
        return menuMapper.toDtoBean(menus.get(0));
    }

    @Override
    public MenuDTO saveOrUpdate(MenuDTO menuDTO, String username) throws Exception {
        boolean isCreate = menuDTO.getId() == null ? true : false;
        Date currentDate = new Date();
        if(isCreate){
            menuDTO.setCreatedDate(currentDate);
            menuDTO.setCreatedBy(username);
        } else {
            menuDTO.setUpdatedDate(currentDate);
            menuDTO.setUpdatedBy(username);
        }

        return menuMapper.toDtoBean(menuRepo.saveAndFlush(menuMapper.toPersistenceBean(menuDTO)));
    }

    @Override
    public List<MenuDTO> getListMenuFlowApp(String username, String appCode) throws Exception {
        return menuMapper.toDtoBean(menuRepo.getListMenu(username, appCode));
    }

    @Override
    public MenuDTO findByCode(String code) throws Exception {
        BooleanExpression predicate = QMenu.menu.code.equalsIgnoreCase(code);
        predicate = predicate.and(QMenu.menu.status.eq(Const.STATUS.ACTIVCE));

        List<Menu> menus = Lists.newArrayList(menuRepo.findAll(predicate));
        if(DataUtil.isNullOrEmpty(menus))
            return null;
        return menuMapper.toDtoBean(menus.get(0));
    }

}
