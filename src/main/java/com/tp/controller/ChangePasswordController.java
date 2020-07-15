package com.tp.controller;

import com.tp.common.exception.LogicException;
import com.tp.dto.admin.UserDTO;
import com.tp.service.UserService;
import com.tp.util.DataUtil;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import java.util.Date;

/**
 * Created by DINH NGUYEN on 20/07/2017.
 */
@ManagedBean
@Component
@Scope("view")
public class ChangePasswordController extends BaseController{

    final static Logger logger = LoggerFactory.getLogger(ChangePasswordController.class);

    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    private UserDTO currentUserDTO;
    private String oldPassword;
    private String newPassword;
    private String confirrmNewPassword;

    @PostConstruct
    @Override
    public void init(){
        try {
            currentUserDTO = userService.findByUserName(getUsername());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            reportErrorGrowl(null, "common.error.happened");
        }
    }

    public boolean validateBeforeSave() throws Exception {
        if (DataUtil.isNullOrEmpty(newPassword)) {
            reportErrorGrowl(null, "chang.password.old.password.req.mess");
            return false;
        }

        if (DataUtil.isNullOrEmpty(newPassword)) {
            reportErrorGrowl(null, "chang.password.new.password.req.mess");
            return false;
        }

        if (DataUtil.isNullOrEmpty(newPassword)) {
            reportErrorGrowl(null, "chang.password.new.password.confirm.req.mess");
            return false;
        }

        boolean validPassword = passwordEncoder.matches(oldPassword, currentUserDTO.getPassWord());
        if (!validPassword) {
            reportErrorGrowl(null, "chang.password.old.password.not.match.lbl");
            return false;
        }

        if (DataUtil.isNullOrWhiteSpace(newPassword)) {
            reportErrorGrowl(null, "chang.password.new.password.using.space.error");
            return false;
        }

        if (DataUtil.isNullOrWhiteSpace(confirrmNewPassword)) {
            reportErrorGrowl(null, "chang.password.confirm.new.password.using.space.error");
            return false;
        }

        if (!DataUtil.safeEqual(newPassword,confirrmNewPassword)) {
            reportErrorGrowl(null, "chang.password.new.password.not.match.lbl");
            return false;
        }

        return true;
    }

    public void saveOrUpdate(){
        try {
            if(!validateBeforeSave()){
                return;
            }
            currentUserDTO.setPassWord(passwordEncoder.encode(newPassword));
            currentUserDTO.setUpdatedBy(getUsername());
            currentUserDTO.setUpdatedDate(new Date());
            UserDTO userDTO = userService.saveOrUpdate(currentUserDTO,getUsername());
            reportSuccessGrowl(null, "chang.password.update.detail.mess.success",userDTO.getUserName());
        } catch (LogicException ex){
            reportError(null, ex.getKeyMsg(), ex.getParams());
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }
    }

    public void reWork() throws Exception {
        oldPassword = "";
        newPassword = "";
        confirrmNewPassword = "";
    }

    public UserDTO getCurrentUserDTO() {
        return currentUserDTO;
    }

    public void setCurrentUserDTO(UserDTO currentUserDTO) {
        this.currentUserDTO = currentUserDTO;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getConfirrmNewPassword() {
        return confirrmNewPassword;
    }

    public void setConfirrmNewPassword(String confirrmNewPassword) {
        this.confirrmNewPassword = confirrmNewPassword;
    }
}
