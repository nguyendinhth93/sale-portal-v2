package com.tp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;

import com.tp.common.exception.LogicException;
import com.tp.dto.admin.*;
import com.tp.service.MenuService;
import com.tp.service.UserService;
import org.primefaces.json.JSONObject;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.tp.common.menu.MenuBean;
import com.tp.config.SystemConfig;
import com.tp.util.Const;
import com.tp.util.DataUtil;

/**
 * Created by hopnv on 22/07/2017.
 */
@ManagedBean
@Component
@Scope("view")
@PropertySource(value = {"classpath:system-config.properties"})
public class LoginController extends BaseController {

    private String username;
    private String password;
    private String msgError;

    final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

//    @Autowired
//    private StaffService staffService;

    @PostConstruct
    @Override
    public void init() throws IOException {
        Boolean isAuthenticated = (Boolean) getSession().getAttribute(Const.AUTHENTICATED);
        if (isAuthenticated != null && isAuthenticated) {
            getContext().getExternalContext().redirect(getRequest().getContextPath() + systemConfig.getIndexUrl());
        }
        //insert test data
        try {
            if (userService.findByUserName("admintp") == null) {
                UserDTO userDTO = new UserDTO();
                userDTO.setPassWord("123456");
                userDTO.setFullName("Nguyễn Văn A");
                userDTO.setUserName("admintp");
                userDTO.setStatus(1);
                userService.saveOrUpdate(userDTO, "admin-generated");
            }
        } catch (Exception e) {

        }
    }


    public void login() {
        try {
            msgError = "";
            UserDTO userDTO = userService.login(username, password);

            getSession().setAttribute(Const.USER_INFO, userDTO);
            getSession().setAttribute(Const.AUTHENTICATED, true);
            List<MenuDTO> menuDTOs = menuService.getListMenuFlowApp(username, systemConfig.getAppCode());
            List<MenuDTO> menuModules = new ArrayList<>();
            menuDTOs.forEach(m -> {
                if (m.getType().equals(Const.MENU.MODULE)) menuModules.add(m);
            });
            List<MenuDTO> menuComponents = new ArrayList<>();
            menuDTOs.forEach(m -> {
                if (m.getType().equals(Const.MENU.COMPONENT)) menuComponents.add(m);
            });
            menuModules.sort((p1, p2) -> DataUtil.safeToInt(p1.getStt()) > DataUtil.safeToInt(p2.getStt()) ? 1 : 0);

            getSession().setAttribute(Const.ROLE_MODULE, menuModules.stream().map(m -> m.getPath()).collect(Collectors.toList()));
            getSession().setAttribute(Const.ROLE_COMPONENT, menuComponents.stream().map(m -> m.getCode()).collect(Collectors.toList()));
            MenuBean menuBean = applicationContext.getBean(MenuBean.class);
            if (menuBean != null)
                menuBean.setMainMenu(buildMainMenu(menuModules));


            Cookie cookie11 = new Cookie("ultima_expandeditems", null); //clear history menu
            cookie11.setValue(null);
            cookie11.setMaxAge(0);
            cookie11.setPath("/");
            getResponse().addCookie(cookie11);

            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect(context.getRequestContextPath() + systemConfig.getIndexUrl());
            return;
        } catch (LogicException ex) {
            logger.error(ex.getMessage(), ex);
            msgError = getText(ex.getKeyMsg());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            msgError = getText("common.error.happened.againAction");
        }
    }

    public String getMsgError() {
        return msgError;
    }

    public void setMsgError(String msgError) {
        this.msgError = msgError;
    }

    private MenuModel buildMainMenu(List<MenuDTO> listMenu) throws IOException {

        MenuModel mainMenu = new DefaultMenuModel();

        TreeMenuDTO treeMenu = findChildMenuByParentId(listMenu, null); //build treemenu from root
        if (!DataUtil.isNullOrEmpty(treeMenu.getChildMenus())) { // order by stt (seq)
            Collections.sort(treeMenu.getChildMenus(), (m1, m2) -> m1.getStt().compareTo(m2.getStt()));
        }

        if (!DataUtil.isNullOrEmpty(treeMenu.getChildMenus())) { //gen menu
            encodeElements(treeMenu.getChildMenus(), mainMenu);
        }

        getSession().setAttribute(Const.MENU_TREE, treeMenu.getChildMenus());

        return mainMenu;
    }

    protected void encodeElements(List<TreeMenuDTO> elements, MenuModel mainMenu)
            throws IOException {
        for (TreeMenuDTO element : elements) {
            if ((DataUtil.isNullOrEmpty(element.getChildMenus()))) {
                DefaultMenuItem menuItem = new DefaultMenuItem();
                menuItem.setIcon(element.getIcon());
                menuItem.setValue(getText(element.getName()));
                menuItem.setHref(element.getPath());
                mainMenu.addElement(menuItem);
            } else {
                DefaultSubMenu submenu = new DefaultSubMenu();
                if (!DataUtil.isNullOrEmpty(element.getIcon()))
                    submenu.setIcon(element.getIcon());
                submenu.setLabel(getText(element.getName()));
                encodeSubmenu(element.getChildMenus(), submenu);
                mainMenu.addElement(submenu);
            }
        }
    }

    protected void encodeSubmenu(List<TreeMenuDTO> elements, DefaultSubMenu submenu)
            throws IOException {
        for (TreeMenuDTO element : elements) {
            if ((DataUtil.isNullOrEmpty(element.getChildMenus()))) {
                DefaultMenuItem menuItem = new DefaultMenuItem();
                menuItem.setIcon(element.getIcon());
                menuItem.setValue(getText(element.getName()));
                //String command = "#{menuBean.updateBreadCrumbView('" + element.getPath() + "')}";
                //menuItem.setCommand(command);
                menuItem.setUrl(element.getPath());
                submenu.addElement(menuItem);
            } else {
                DefaultSubMenu submenuTemp = new DefaultSubMenu();
                submenuTemp.setIcon(element.getIcon());
                submenuTemp.setLabel(getText(element.getName()));
                encodeSubmenu(element.getChildMenus(), submenuTemp);
                submenu.addElement(submenuTemp);
            }
        }
    }

    private TreeMenuDTO findChildMenuByParentId(List<MenuDTO> listMenu, Long parentId) {
        TreeMenuDTO treeMenu = new TreeMenuDTO();
        treeMenu.setChildMenus(new ArrayList<>());
        for (MenuDTO menuDTO : listMenu) {
            //if is children of node
            if ((parentId == null && menuDTO.getParentId() == null) || (menuDTO.getParentId() != null && parentId != null && menuDTO.getParentId().equals(parentId))) {
                TreeMenuDTO temp = findChildMenuByParentId(listMenu, menuDTO.getId()); //find children of child
                if (!DataUtil.isNullOrEmpty(temp.getCode()) || !DataUtil.isNullOrEmpty(temp.getChildMenus())) {
                    //is a menu
                    TreeMenuDTO treeMenuTemp = new TreeMenuDTO();
                    treeMenuTemp.setChildMenus(new ArrayList<>());
                    treeMenuTemp.setName(menuDTO.getName());
                    treeMenuTemp.setCode(menuDTO.getCode());
                    treeMenuTemp.setParentId(menuDTO.getParentId());
                    treeMenuTemp.setId(menuDTO.getId());
                    treeMenuTemp.setPath(menuDTO.getPath());
                    treeMenuTemp.setStt(menuDTO.getStt());
                    treeMenuTemp.setIcon(menuDTO.getIcon());
                    if (!DataUtil.isNullOrEmpty(temp.getChildMenus())) // if menu has child
                        treeMenuTemp.getChildMenus().addAll(temp.getChildMenus());
                    treeMenu.getChildMenus().add(treeMenuTemp);
                    Collections.sort(treeMenu.getChildMenus(), (m1, m2) -> m1.getStt().compareTo(m2.getStt()));
                } else { // if has nothing children then gather all into a list
                    TreeMenuDTO treeMenuTemp = new TreeMenuDTO();
                    treeMenuTemp.setName(menuDTO.getName());
                    treeMenuTemp.setCode(menuDTO.getCode());
                    treeMenuTemp.setParentId(menuDTO.getParentId());
                    treeMenuTemp.setId(menuDTO.getId());
                    treeMenuTemp.setPath(menuDTO.getPath());
                    treeMenuTemp.setStt(menuDTO.getStt());
                    treeMenuTemp.setIcon(menuDTO.getIcon());
                    treeMenu.getChildMenus().add(treeMenuTemp);
                    Collections.sort(treeMenu.getChildMenus(), (m1, m2) -> m1.getStt().compareTo(m2.getStt()));
                }
            }
        }
        return treeMenu; // nothing mapping menu or loop all list
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
