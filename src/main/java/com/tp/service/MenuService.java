package com.tp.service;

import java.util.List;

import com.tp.dto.admin.MenuDTO;

/**
 * Created by user on 24/08/2017.
 */
public interface MenuService extends BaseSearchService<MenuDTO> {
    void delete(List<Long> id, String username) throws  Exception;
    List<MenuDTO> findByParentId(Long parentId,Integer status) throws Exception;
    List<MenuDTO> getListMenu() throws Exception;
    MenuDTO findByCodeAndName(String name, String code) throws Exception;
    MenuDTO saveOrUpdate(MenuDTO menuDTO, String username) throws Exception;
    public List<MenuDTO> getListMenuFlowApp(String username, String appCode) throws Exception;
    MenuDTO findByCode(String code) throws Exception;
}
