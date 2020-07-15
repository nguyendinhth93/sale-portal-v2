package com.tp.repo;

import java.util.List;

import com.tp.dto.requestSearch.MenuRequestSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.dto.admin.MenuDTO;
import com.tp.model.admin.Menu;

/**
 * Created by user on 23/08/2017.
 */
public interface MenuRepoCustom {
    List<Menu> getAllMenus();
    Menu getMenuByMenuId(Long id);
    ResponseSearchDTO<MenuDTO> searchMenu(MenuRequestSearchDTO menuRequestSearchDTO) throws Exception;
    public List<Menu> getListMenu(String username, String appCode);

}
