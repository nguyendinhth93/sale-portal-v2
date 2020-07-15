package com.tp.controller;

import com.tp.common.custom.SnDataModel;
import com.tp.common.exception.LogicException;
import com.tp.dto.OptionSetValueDTO;
import com.tp.dto.admin.GroupDTO;
import com.tp.dto.admin.UserDTO;
import com.tp.dto.admin.UserGroupDTO;
import com.tp.dto.requestSearch.UserGroupSearchDTO;
import com.tp.dto.requestSearch.UserInputSearchDTO;
import com.tp.service.GroupService;
import com.tp.service.OptionSetValueService;
import com.tp.service.UserGroupService;
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
public class UserGroupController extends BaseController{

    final static Logger logger = LoggerFactory.getLogger(UserGroupController.class);

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    private UserGroupDTO currentUserGroup;
    private boolean isCreateState;
    private SnDataModel<UserGroupDTO> lazyLoadUserGroup;
    private List<GroupDTO> groupDTOs;
    private List<UserDTO> userDTOs;

    @PostConstruct
    @Override
    public void init(){
        try {
            lazyLoadUserGroup = new SnDataModel<>(userGroupService, new UserGroupSearchDTO());
            groupDTOs = groupService.findAll();
            userDTOs = userService.findAllActive();
            currentUserGroup = new UserGroupDTO();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            reportErrorGrowl(null, "common.error.happened");
        }
    }

    public void prepareToShowAdd(){
        currentUserGroup = new UserGroupDTO();
    }

    public void prepareToShowUpdate(UserGroupDTO userGroupDTO){
        currentUserGroup = new UserGroupDTO();
        try {
            BeanUtils.copyProperties(currentUserGroup, userGroupDTO);
            currentUserGroup.setUserName(userGroupDTO.getUserName());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }
    }

    public boolean validateBeforeSave() throws Exception {
        UserGroupDTO userGroupDTO = userGroupService.findByGroupIdUserId(currentUserGroup.getGroupId(), DataUtil.safeToLong(currentUserGroup.getUserId()));
        if(userGroupDTO != null){
            UserDTO userDTO = userService.findById(currentUserGroup.getUserId());
            GroupDTO groupDTO = groupService.findOne(currentUserGroup.getGroupId());
            reportErrorGrowl(null, "user.group.msg.add.fail.detail", userDTO.getUserName(), groupDTO.getName());
            return false;
        }
        return true;
    }

    public void saveOrUpdate(){

        try {
            if(!validateBeforeSave()){
                return;
            }
            UserGroupDTO userGroupDTO = userGroupService.createOrUpdate(currentUserGroup,getUser());
            RequestContext.getCurrentInstance().execute("PF('dlgUserGroup').hide()");
        } catch (LogicException ex){
            reportError(null, ex.getKeyMsg(), ex.getParams());
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }

    }

    public void validateBeforeDelete(){
        if(DataUtil.isNullOrEmpty(lazyLoadUserGroup.getSelectedList())){
            reportErrorGrowl(null, "apConfig.msg.delete.empty");
        }
    }

    public void prepareDeleteOne(UserGroupDTO userGroupDTO){
        try {
            lazyLoadUserGroup.getSelectedList().clear();
            lazyLoadUserGroup.getSelectedList().add(userGroupDTO);
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
            for (UserGroupDTO userGroupDTO:lazyLoadUserGroup.getSelectedList()) {
                userGroupDTO.setStatus(Const.STATUS.INACTIVCE);
                userGroupService.createOrUpdate(userGroupDTO, getUser());
                reportSuccessGrowl(null, "user.group.msg.delete.user.success",userGroupDTO.getGroupName(),userGroupDTO.getUserName());
                return;
            }
            lazyLoadUserGroup.getSelectedList().clear();
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

    public UserGroupDTO getCurrentUserGroup() {
        return currentUserGroup;
    }

    public void setCurrentUserGroup(UserGroupDTO currentUserGroup) {
        this.currentUserGroup = currentUserGroup;
    }

    public SnDataModel<UserGroupDTO> getlazyLoadUserGroup() {
        return lazyLoadUserGroup;
    }

    public void setlazyLoadUserGroup(SnDataModel<UserGroupDTO> lazyLoadUserGroup) {
        this.lazyLoadUserGroup = lazyLoadUserGroup;
    }

    public List<GroupDTO> getGroupDTOs() {
        return groupDTOs;
    }

    public void setGroupDTOs(List<GroupDTO> groupDTOs) {
        this.groupDTOs = groupDTOs;
    }

    public List<UserDTO> getUserDTOs() {
        return userDTOs;
    }

    public void setUserDTOs(List<UserDTO> userDTOs) {
        this.userDTOs = userDTOs;
    }
}
