package com.tp.controller;

import com.tp.common.custom.SnDataModel;
import com.tp.common.exception.LogicException;
import com.tp.dto.DsaResultMeetingDTO;
import com.tp.dto.DsaResultMeetingSearchDTO;
import com.tp.dto.OptionSetValueDTO;
import com.tp.dto.admin.UserDTO;
import com.tp.dto.requestSearch.UserInputSearchDTO;
import com.tp.service.OptionSetValueService;
import com.tp.service.UserService;
import com.tp.util.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by DINH NGUYEN on 20/07/2017.
 */
@ManagedBean
@Component
@Scope("view")
public class UserController extends BaseController{

    final static Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String TEMPLATE_FILE_NAME          = "USER.xlsx";
    private static final String BASE_FILE_NAME_EXPORT       = "user";

    @Autowired
    private UserService userService;
    @Autowired
    private OptionSetValueService optionSetValueService;

    private UserDTO currentUser;
    private boolean isCreateState;
    private SnDataModel<UserDTO> lazyLoadUser;
    private List<OptionSetValueDTO> roles;

    @PostConstruct
    @Override
    public void init(){
        try {
            lazyLoadUser = new SnDataModel<>(userService, new UserInputSearchDTO());
            roles = optionSetValueService.findByActionCode(Const.AP_DOMAIN.SALE_USER_ROLE);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            reportErrorGrowl(null, "common.error.happened");
        }
    }

    public void prepareToShowAdd(){
        currentUser = new UserDTO();
    }

    public void prepareToShowUpdate(UserDTO userDTO){
        currentUser = new UserDTO();
        try {
            BeanUtils.copyProperties(currentUser, userDTO);
            currentUser.setUserName(userDTO.getUserName());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }
    }

    public boolean validateBeforeSave() throws Exception {
        if(DataUtil.safeEqual(currentUser.getUserName(), currentUser.getUserName()))
            return true;

        UserDTO userDTO = userService.findById(currentUser.getUserId());
        if(userDTO != null){
            reportErrorGrowl(null, "user.manage.msg.add.fail.detail", currentUser.getUserName());
            return false;
        }
        return true;
    }

    public void saveOrUpdate(){

        try {
            if(!validateBeforeSave()){
                return;
            }

            UserDTO userDTO = userService.saveOrUpdate(currentUser, getUsername());
            if(isCreateState)
                reportSuccessGrowl(null, "user.manage.msg.add.success.detail", userDTO.getUserName());
            else
                reportSuccessGrowl(null, "user.manage.msg.add.update.detail");
            RequestContext.getCurrentInstance().execute("PF('dlgUser').hide()");
        } catch (LogicException ex){
            reportError(null, ex.getKeyMsg(), ex.getParams());
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }

    }

    public void exportData() {
        try {
            String location = "exportData() >";
            logger.info(location + " START");
            FileExportBean bean = new FileExportBean();
            bean.setTemplatePath(FileUtil.getTemplatePath() +BASE_FILE_NAME_EXPORT+"/"+ File.separator);

            UserInputSearchDTO inputSearchUI = (UserInputSearchDTO)lazyLoadUser.getRequestSearchDTO();

            UserInputSearchDTO inputSearchExport = new UserInputSearchDTO();
            org.springframework.beans.BeanUtils.copyProperties(inputSearchUI, inputSearchExport);

            inputSearchExport.setFirst(0);
            inputSearchExport.setPageSize(Integer.MAX_VALUE);
            List<UserDTO> lstData = userService.load(inputSearchExport).getData();

            logger.info(location + " size = " + lstData.size());

            bean.setTemplateName(TEMPLATE_FILE_NAME);
            bean.putValue("lstData", lstData);
            Workbook workbook = FileUtil.exportWorkBook(bean);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String exportFileName = BASE_FILE_NAME_EXPORT + "_" + DateUtil.parseDateToString(new Date(), "yyyyMMddHHmmSS") + ".xlsx";
            logger.info(location + " exportFileName = " + exportFileName);
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=" + exportFileName);
            externalContext.setResponseContentType("application/vnd.ms-excel");
            workbook.write(externalContext.getResponseOutputStream());
            facesContext.getResponseComplete();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void validateBeforeDelete(){
        if(DataUtil.isNullOrEmpty(lazyLoadUser.getSelectedList())){
            reportErrorGrowl(null, "apConfig.msg.delete.empty");
        }
    }

    public void prepareDeleteOne(UserDTO userDTO){
        userDTO.setStatus(Const.STATUS.INACTIVCE);
        try {
            userService.saveOrUpdate(userDTO,getUsername());
            lazyLoadUser.getSelectedList().clear();
            lazyLoadUser.getSelectedList().add(userDTO);
            reportErrorGrowl(null, "user.manage.msg.delete.user.success",userDTO.getUserName());
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
            for (UserDTO userDTO:lazyLoadUser.getSelectedList()){
                userDTO.setStatus(Const.STATUS.INACTIVCE);
                userService.saveOrUpdate(userDTO,getUsername());
            }
            reportSuccessGrowl(null, "user.manage.msg.delete.success", lazyLoadUser.getSelectedList().size() + "");
            lazyLoadUser.getSelectedList().clear();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }
    }

    public String getHeaderDialog() {
        return isCreateState ? getText("user.manage.title.dlg.add") : getText("user.manage.title.dlg.update");
    }

    public boolean isCreateState() {
        return isCreateState;
    }

    public void setCreateState(boolean createState) {
        isCreateState = createState;
    }

    public UserDTO getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserDTO currentUser) {
        this.currentUser = currentUser;
    }

    public SnDataModel<UserDTO> getLazyLoadUser() {
        return lazyLoadUser;
    }

    public void setLazyLoadUser(SnDataModel<UserDTO> lazyLoadUser) {
        this.lazyLoadUser = lazyLoadUser;
    }

    public List<OptionSetValueDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<OptionSetValueDTO> roles) {
        this.roles = roles;
    }
}
