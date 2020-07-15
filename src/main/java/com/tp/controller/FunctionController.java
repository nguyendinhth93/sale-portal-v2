package com.tp.controller;

import com.tp.common.custom.SnDataModel;
import com.tp.common.exception.LogicException;
import com.tp.dto.admin.MenuDTO;
import com.tp.dto.admin.UserDTO;
import com.tp.dto.requestSearch.UserInputSearchDTO;
import com.tp.service.MenuService;
import com.tp.service.UserService;
import com.tp.util.Const;
import com.tp.util.DataUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 * Created by DINH NGUYEN on 20/07/2017.
 */
@ManagedBean
@Component
@Scope("view")
public class FunctionController extends BaseController{

    final static Logger logger = LoggerFactory.getLogger(FunctionController.class);

    @Autowired
    private MenuService menuService;

    private MenuDTO currentMenu;
    private boolean isCreateState;
    private SnDataModel<MenuDTO> lazyLoadMenu;

    @PostConstruct
    @Override
    public void init(){
        lazyLoadMenu = new SnDataModel<>(menuService, new UserInputSearchDTO());
    }

    public void prepareToShowAdd(){
        currentMenu = new MenuDTO();
    }

    public void prepareToShowUpdate(MenuDTO menuDTO){
        currentMenu = new MenuDTO();
        try {
            BeanUtils.copyProperties(currentMenu, menuDTO);
            currentMenu.setOldCode(menuDTO.getCode());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }
    }

    public boolean validateBeforeSave() throws Exception {
        if(DataUtil.safeEqual(currentMenu.getCode(), currentMenu.getOldCode()))
            return true;

        MenuDTO menuDTO = menuService.findByCode(currentMenu.getCode());
        if(menuDTO != null){
            reportErrorGrowl(null, "function.msg.add.fail.detail", currentMenu.getCode());
            return false;
        }
        return true;
    }

    public void saveOrUpdate(){

        try {
            if(!validateBeforeSave()){
                return;
            }

            MenuDTO menuDTO = menuService.saveOrUpdate(currentMenu, getUsername());
            if(isCreateState)
                reportSuccessGrowl(null, "function.msg.add.success.detail", menuDTO.getCode());
            else
                reportSuccessGrowl(null, "function.msg.add.update.detail");
            RequestContext.getCurrentInstance().execute("PF('dlgFunction').hide()");
        } catch (LogicException ex){
            reportError(null, ex.getKeyMsg(), ex.getParams());
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }

    }

    public void validateBeforeDelete(){
        if(DataUtil.isNullOrEmpty(lazyLoadMenu.getSelectedList())){
            reportErrorGrowl(null, "apConfig.msg.delete.empty");
        }
    }

    public void prepareDeleteOne(MenuDTO menuDTO){
        menuDTO.setStatus(Const.STATUS.INACTIVCE);
        try {
            menuService.saveOrUpdate(menuDTO,getUsername());
            lazyLoadMenu.getSelectedList().clear();
            lazyLoadMenu.getSelectedList().add(menuDTO);
            reportErrorGrowl(null, "function.msg.delete.user.success",menuDTO.getCode());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }
    }

    public void delete(){
        validateBeforeDelete();
        if(FacesContext.getCurrentInstance().isValidationFailed())
            return;
        try {
            for (MenuDTO menuDTO:lazyLoadMenu.getSelectedList()){
                menuDTO.setStatus(Const.STATUS.INACTIVCE);
                menuService.saveOrUpdate(menuDTO,getUsername());
            }
            reportSuccessGrowl(null, "user.manage.msg.delete.success", lazyLoadMenu.getSelectedList().size() + "");
            lazyLoadMenu.getSelectedList().clear();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }
    }

    public String getHeaderDialog() {
        return isCreateState ? getText("function.title.dlg.add") : getText("function.title.dlg.update");
    }

    public boolean isCreateState() {
        return isCreateState;
    }

    public void setCreateState(boolean createState) {
        isCreateState = createState;
    }

    public MenuDTO getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(MenuDTO currentMenu) {
        this.currentMenu = currentMenu;
    }

    public SnDataModel<MenuDTO> getLazyLoadMenu() {
        return lazyLoadMenu;
    }

    public void setLazyLoadMenu(SnDataModel<MenuDTO> lazyLoadMenu) {
        this.lazyLoadMenu = lazyLoadMenu;
    }
}
