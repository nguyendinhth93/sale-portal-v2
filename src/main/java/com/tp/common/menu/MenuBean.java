package com.tp.common.menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.tp.dto.admin.UserDTO;
import org.primefaces.context.RequestContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tp.config.SystemConfig;
import com.tp.controller.BaseController;
import com.tp.dto.admin.TreeMenuDTO;
import com.tp.util.Const;
import com.tp.util.DataUtil;

/**
 * Created by hopnv on 20/07/2017.
 */
@ManagedBean
@Component
@Scope("session")
public class MenuBean extends BaseController{

    private MenuModel mainMenu;
    private MenuModel profileMenu;
    private List<TreeMenuDTO> currentMenu;

    @Autowired
    private SystemConfig systemConfig;

    @PostConstruct
    public void init(){
        profileMenu = new DefaultMenuModel();
        DefaultMenuItem profile = new DefaultMenuItem(getText("menu.profile.profile"));
        profile.setIcon("ui-icon-person");
        DefaultMenuItem changePassword = new DefaultMenuItem(getText("menu.profile.changePassword"));
        changePassword.setIcon("ui-icon-lock-outline");
        changePassword.setUrl("/changePassword.jsf");
        DefaultMenuItem logout = new DefaultMenuItem(getText("menu.profile.logout"));
        logout.setIcon("ui-icon-power-settings-new");
        logout.setCommand("#{menuBean.logout}");
        profileMenu.addElement(profile);
        profileMenu.addElement(changePassword);
        profileMenu.addElement(logout);

        mainMenu = new DefaultMenuModel();
    }

    public void updateBreadCrumbViewByPage() throws IOException {
        String viewId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("viewId");
        updateBreadCrumbView(viewId.replaceFirst(getRequest().getContextPath(), ""));
    }

    public void updateBreadCrumbView(String viewId) throws IOException {
        /*RequestContext.getCurrentInstance().update("breadcrumb");*/
        /*if(systemConfig.getIgnoreRenderBreadcrumbs().contains(viewId)){
            return;
        }*/
        List<TreeMenuDTO> treeMenus = (List<TreeMenuDTO>) getSession().getAttribute(Const.MENU_TREE);
        for(TreeMenuDTO treeMenuDTO : treeMenus){
            currentMenu = new ArrayList<>();
            currentMenu.add(treeMenuDTO);
            if(!DataUtil.isNullOrEmpty(treeMenuDTO.getPath())&&treeMenuDTO.getPath().equals(viewId)
                    || DataUtil.isNullOrEmpty(treeMenuDTO.getChildMenus())
                    || findParentMenu(viewId, treeMenuDTO.getChildMenus(), currentMenu))
                break;
        }
        if(DataUtil.isNullOrEmpty(currentMenu) || currentMenu.size() < 2)
            return;
        for(MenuElement iElement : mainMenu.getElements()){
            DefaultSubMenu subMenu = (DefaultSubMenu) iElement;
            if(subMenu.getLabel().equals(getText(currentMenu.get(0).getCode()))){
                RequestContext.getCurrentInstance().execute("updateSelectedMenu('mainMenu_" + subMenu.getId() + "')");
            }
        }
    }

    public List<TreeMenuDTO> getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(List<TreeMenuDTO> currentMenu) {
        this.currentMenu = currentMenu;
    }

    private boolean findParentMenu(String viewId, List<TreeMenuDTO> treeMenus, List<TreeMenuDTO> currentMenu){
        for(TreeMenuDTO treeMenuDTO : treeMenus){
            currentMenu.add(treeMenuDTO);
            if(treeMenuDTO.getPath().equals(viewId))
                return true;
            if(!DataUtil.isNullOrEmpty(treeMenuDTO.getChildMenus())){
                if(findParentMenu(viewId, treeMenuDTO.getChildMenus(), currentMenu)) {
                    return true;
                }
                else currentMenu.remove(treeMenuDTO);
            } else currentMenu.remove(treeMenuDTO);
        }
        return false;
    }

    public void logout() throws IOException {
        getSession().invalidate();
        getContext().getExternalContext().redirect(getRequest().getContextPath() + systemConfig.getLoginUrl());
    }

    public MenuModel getMainMenu() {
        return mainMenu;
    }

    public void setMainMenu(MenuModel mainMenu) {
        this.mainMenu = mainMenu;
    }

    public MenuModel getProfileMenu() {
        return profileMenu;
    }

    public void setProfileMenu(MenuModel profileMenu) {
        this.profileMenu = profileMenu;
    }
}
