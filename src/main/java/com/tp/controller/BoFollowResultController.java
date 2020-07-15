package com.tp.controller;

import com.tp.common.custom.SnDataModel;
import com.tp.common.exception.LogicException;
import com.tp.dto.*;
import com.tp.dto.admin.UserDTO;
import com.tp.dto.requestSearch.BoCheckupResultSearchDTO;
import com.tp.dto.requestSearch.BoFollowResultSearchDTO;
import com.tp.service.*;
import com.tp.util.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by DINH NGUYEN on 20/07/2017.
 */
@ManagedBean
@Component
@Scope("view")
public class BoFollowResultController extends BaseController{

    final static Logger logger = LoggerFactory.getLogger(BoFollowResultController.class);

    private static final String BASE_FILE_NAME_EXPORT       = "boFollow";
    private static final String TEMPLATE_FILE_NAME          = "BO_FOLLOW.xlsx";

    @Autowired
    private BoFollowResultService boFollowResultService;
    @Autowired
    private OptionSetValueService optionSetValueService;
    @Autowired
    private UserService userService;
    @Autowired
    private AreaService areaService;

    private BoCheckupResultDTO currentBoCheckupResult = new BoCheckupResultDTO();
    private boolean isCreateState;
    private SnDataModel<BoCheckupResultDTO> lazyLoadBoFollowResult;

    private List<OptionSetValueDTO> statusDocVpbanks;
    private List<OptionSetValueDTO> groupStatusDocs;
    private List<OptionSetValueDTO> detailStatusDocs;
    private List<OptionSetValueDTO> statusReturnDocs;
    private List<OptionSetValueDTO> confirmErrorBos;
    private List<DsaResultMeetingLogDTO> dsaResultMeetingLogDTOs;
    private List<UserDTO> staffCodes;
    private String userLoginSystem;
    private String assigneUser;

    @Value("${DSA_PREFIX_FOLDER_SYSTEM_FILE_UPLOAD}")
    private String PREFIX_FOLDER_DSA;
    private StreamedContent downloadFile;
    private boolean renderBtnAssigne;
    private Map<String, String> listCheckBoxLabel = new ConcurrentHashMap<String, String>();
    private List<AreaDTO> lstProvinces;

    @PostConstruct
    @Override
    public void init(){
        try {
            BoFollowResultSearchDTO boFollowResultSearchDTO = new BoFollowResultSearchDTO();
            if(hasPermission("ROLE_BO_FOLLOW_ASSIGNE")){
                staffCodes = userService.findAllByUserTl(getUsername());
                renderBtnAssigne = true;
            }else{
                staffCodes = userService.findAllUserOnTeamByUser(getUsername());
                renderBtnAssigne = false;
            }
            lazyLoadBoFollowResult = new SnDataModel<>(boFollowResultService, boFollowResultSearchDTO);
            statusDocVpbanks = optionSetValueService.findByActionCode(Const.AP_DOMAIN.SALE_BO_FOLLOW_STATUS_DOC_VPBANK);
            statusReturnDocs = optionSetValueService.findByActionCode(Const.AP_DOMAIN.SALE_BO_FOLLOW_DOC_RETURN_STATUS);
            confirmErrorBos = optionSetValueService.findByActionCode(Const.AP_DOMAIN.SALE_BI_CONFIRM_ERROR_BO_CHECKUP);
            userLoginSystem = getUsername();
            lstProvinces = areaService.findAllProvince();
            listCheckBoxLabel.put(getText("distribute.meeting.province.label"), getText("common.label.choose"));
            listCheckBoxLabel.put(getText("boFollowResult.label.bo.follow"), getText("common.label.choose"));
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            reportErrorGrowl(null, "common.error.happened");
        }
    }

    public void prepareToShowAdd(){
        currentBoCheckupResult = new BoCheckupResultDTO();
    }

    public void prepareToShowUpdate(BoCheckupResultDTO boCheckupResultDTO){
        currentBoCheckupResult = new BoCheckupResultDTO();
        try {
            BeanUtils.copyProperties(currentBoCheckupResult, boCheckupResultDTO);
            if (!DataUtil.isNullObject(currentBoCheckupResult.getStatusDocVpbank())){
                updateGroupByDocStatus(DataUtil.safeToString(currentBoCheckupResult.getStatusDocVpbank()));
            }
            if (!DataUtil.isNullObject(currentBoCheckupResult.getGroupDocStatus())){
                updateDetailByDocGroup(DataUtil.safeToString(currentBoCheckupResult.getGroupDocStatus()));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }
    }

    public boolean validateBeforeSave() throws Exception {
        return true;
    }

    public boolean validateSave() throws Exception {
        if (!DataUtil.isNullObject(currentBoCheckupResult.getReturnResultQdeDate())
                &&!DataUtil.isNullObject(currentBoCheckupResult.getToLosDate())
                &&currentBoCheckupResult.getReturnResultQdeDate().before(currentBoCheckupResult.getToLosDate())){
            reportErrorGrowl(null, "boFollowResult.msg.return.qde.date.after.to.los.date.err");
            return false;
        }
        if (!DataUtil.isNullObject(currentBoCheckupResult.getActiveDate())
                &&!DataUtil.isNullObject(currentBoCheckupResult.getToLosDate())
                &&currentBoCheckupResult.getActiveDate().before(currentBoCheckupResult.getToLosDate())){
            reportErrorGrowl(null, "boFollowResult.msg.active.date.after.to.los.date.err");
            return false;
        }
        if (!DataUtil.isNullObject(currentBoCheckupResult.getReturnResultQdeDate())
                &&!DataUtil.isNullObject(currentBoCheckupResult.getToFiDate())
                &&currentBoCheckupResult.getReturnResultQdeDate().before(currentBoCheckupResult.getToFiDate())){
            reportErrorGrowl(null, "boFollowResult.msg.return.qde.date.after.to.fi.date.err");
            return false;
        }
        if (!DataUtil.isNullObject(currentBoCheckupResult.getActiveDate())
                &&!DataUtil.isNullObject(currentBoCheckupResult.getToFiDate())
                &&currentBoCheckupResult.getActiveDate().before(currentBoCheckupResult.getToFiDate())){
            reportErrorGrowl(null, "boFollowResult.msg.active.date.after.to.fi.date.err");
            return false;
        }
        if (!DataUtil.isNullObject(currentBoCheckupResult.getActiveDate())){
            if (!DataUtil.safeEqual(currentBoCheckupResult.getStatusDocVpbank(),6)){
                reportErrorGrowl(null, "boFollowResult.msg.active.date.req.approve.status.vpbank");
                return false;
            }
            if (!DataUtil.safeEqual(currentBoCheckupResult.getGroupDocStatus(),42)){
                reportErrorGrowl(null, "boFollowResult.msg.active.date.req.end.status.group.vpbank");
                return false;
            }
            if (!DataUtil.isNullObject(currentBoCheckupResult.getReturnResultQdeDate())&&currentBoCheckupResult.getActiveDate().before(currentBoCheckupResult.getReturnResultQdeDate())){
                reportErrorGrowl(null, "boFollowResult.msg.active.date.after.return.qde.date.err");
                return false;
            }
        }
        if (DataUtil.safeEqual(currentBoCheckupResult.getStatusDocVpbank(),4)
                ||DataUtil.safeEqual(currentBoCheckupResult.getStatusDocVpbank(),5)
                ||DataUtil.safeEqual(currentBoCheckupResult.getStatusDocVpbank(),6)){
            if (DataUtil.isNullObject(currentBoCheckupResult.getReturnResultQdeDate())){
                reportErrorGrowl(null, "boFollowResult.msg.qde.date.req.err");
                return false;
            }
        }
        if (!DataUtil.safeEqual(currentBoCheckupResult.getStatusDocVpbank(),1)){
            if (DataUtil.isNullOrEmpty(currentBoCheckupResult.getLosCode())){
                reportErrorGrowl(null, "boFollowResult.msg.los.code.req.err");
                return false;
            }
        }
        if (!DataUtil.isNullOrEmpty(currentBoCheckupResult.getBoundCodeDsa()) && currentBoCheckupResult.getBoundCodeDsa().contains("UPL")){
            if ((!DataUtil.safeEqual(currentBoCheckupResult.getStatusDocVpbank(),2) && !DataUtil.safeEqual(currentBoCheckupResult.getStatusDocVpbank(),3)) && DataUtil.isNullObject(currentBoCheckupResult.getLimitApproval())){
                reportErrorGrowl(null, "boFollowResult.msg.limit.approval.req.err");
                return false;
            }
            if (DataUtil.isNullObject(currentBoCheckupResult.getReturnResultQdeDate())){
                reportErrorGrowl(null, "boFollowResult.msg.qde.date.req.err");
                return false;
            }
        }
        return true;
    }

    public void saveOrUpdate(){
        try {
            if(!validateSave()){
                return;
            }
            boFollowResultService.createOrUpdate(currentBoCheckupResult);
            reportSuccessGrowl(null, "boCheckupResult.msg.update.success");
            RequestContext.getCurrentInstance().execute("PF('dlgBoFollowResult').hide()");
        } catch (LogicException ex){
            reportError(null, ex.getKeyMsg(), ex.getParams());
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }

    }

    public void assigne(){
        try {
            if (!validateBeforeAssigne()){
                return;
            }
            if(FacesContext.getCurrentInstance().isValidationFailed())
                return;
            UserDTO tlUserDTO = userService.findByUserName(getUsername());
            UserDTO dsaUserDTO = userService.findByUserName(assigneUser);
            if (DataUtil.isNullObject(tlUserDTO)){
                reportErrorGrowl(null, "boCheckupResult.msg.assigned.user.empty");
                return;
            }
            if (DataUtil.isNullObject(dsaUserDTO)){
                reportErrorGrowl(null, "boCheckupResult.msg.assigne.user.empty");
                return;
            }
            for (BoCheckupResultDTO boCheckupResultDTO:lazyLoadBoFollowResult.getSelectedList()){

                boCheckupResultDTO.setBoFollow(DataUtil.safeToUpper(assigneUser));
                boCheckupResultDTO.setTlBoFollow(DataUtil.safeToUpper(tlUserDTO.getUserName()));
                boCheckupResultDTO.setAssigneeBoFollowDate(new Date());
                boCheckupResultDTO.setUpdatedDate(new Date());
                boCheckupResultDTO.setUpdatedUser(DataUtil.safeToUpper(getUsername()));

                boFollowResultService.createOrUpdate(boCheckupResultDTO);
            }
            reportSuccessGrowl(null, "boCheckupResult.msg.assigne.success", DataUtil.safeToUpper(assigneUser) ,lazyLoadBoFollowResult.getSelectedList().size() + "");
            lazyLoadBoFollowResult.getSelectedList().clear();
            RequestContext.getCurrentInstance().execute("PF('dlgAssigneBoFollow').hide()");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }
    }

    public boolean validateBeforeAssigne() throws Exception {
        isCreateState = true;
        if(DataUtil.isNullOrEmpty(lazyLoadBoFollowResult.getSelectedList())){
            reportErrorGrowl(null, "distribute.meeting.msg.assigne.empty");
            return false;
        }
        if (!DataUtil.isNullOrEmpty(staffCodes)){
            staffCodes.clear();
        }
        staffCodes = userService.findAllByUserTl(getUsername());
        return true;
    }

    public void validateBeforeReturnCheckUp(){
        if(DataUtil.isNullOrEmpty(lazyLoadBoFollowResult.getSelectedList())){
            reportErrorGrowl(null, "boCheckupResult.msg.send.empty");
        }
    }

    public void returnCheckUp(){
        validateBeforeReturnCheckUp();
        if(FacesContext.getCurrentInstance().isValidationFailed())
            return;
        try {
            for (BoCheckupResultDTO boCheckupResultDTO: lazyLoadBoFollowResult.getSelectedList()){
                boCheckupResultDTO.setSendToFollow(2l);
                boCheckupResultDTO.setUpdatedUser(getUsername());
                boCheckupResultDTO.setUpdatedDate(new Date());
                boFollowResultService.createOrUpdate(boCheckupResultDTO);
            }
            reportSuccessGrowl(null, "boFollowResult.msg.return.checkup.success", lazyLoadBoFollowResult.getSelectedList().size() + "");
            lazyLoadBoFollowResult.getSelectedList().clear();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }
    }

    public void validateBeforeDelete(){
        if(DataUtil.isNullOrEmpty(lazyLoadBoFollowResult.getSelectedList())){
            reportErrorGrowl(null, "apConfig.msg.delete.empty");
        }
    }

    public void delete(){
        validateBeforeDelete();
        if(FacesContext.getCurrentInstance().isValidationFailed())
            return;
        try {
            if (!DataUtil.isNullOrEmpty(lazyLoadBoFollowResult.getSelectedList())) {
                for (BoCheckupResultDTO boCheckupResultDTO: lazyLoadBoFollowResult.getSelectedList()) {
                    boCheckupResultDTO.setStatus(0l);
                    boFollowResultService.createOrUpdate(boCheckupResultDTO);
                }
                reportSuccessGrowl(null, "boCheckupResult.msg.delete.success", lazyLoadBoFollowResult.getSelectedList().size() + "");
                lazyLoadBoFollowResult.getSelectedList().clear();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }
    }

    public void prepareDeleteOne(BoCheckupResultDTO boCheckupResultDTO){
        lazyLoadBoFollowResult.getSelectedList().clear();
        lazyLoadBoFollowResult.getSelectedList().add(boCheckupResultDTO);
    }

    public void updateGroupByDocStatus(String statusDoc){
        try {
            if (!DataUtil.isNullOrEmpty(groupStatusDocs)){
                groupStatusDocs.clear();
            }
            groupStatusDocs = optionSetValueService.findListByCodeAndActionCode(Const.AP_DOMAIN.SALE_BO_FOLLOW_GROUP_STATUS_DOC,statusDoc);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }
    }

    public void updateDetailByDocGroup(String groupStatus){
        try {
            if (!DataUtil.isNullOrEmpty(detailStatusDocs)){
                detailStatusDocs.clear();
            }
            detailStatusDocs = optionSetValueService.findListByCodeAndActionCode(Const.AP_DOMAIN.SALE_BO_FOLLOW_DETAIL_STATUS_DOC,groupStatus);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }
    }

    public void updateLabelSelectCheckbox(String key, List<String> values) {
        try {
            listCheckBoxLabel.put(key, (!DataUtil.isNullOrEmpty(values) ? values.size() + " " + getText("common.cbb.isSelected") : getText("common.label.choose")));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void exportData() {
        try {
            String location = "exportData() >";
            logger.info(location + " START");
            FileExportBean bean = new FileExportBean();
            bean.setTemplatePath(FileUtil.getTemplatePath() +BASE_FILE_NAME_EXPORT+"/"+ File.separator);

            BoFollowResultSearchDTO inputSearchUI = (BoFollowResultSearchDTO)lazyLoadBoFollowResult.getRequestSearchDTO();

            BoFollowResultSearchDTO inputSearchExport = new BoFollowResultSearchDTO();
            org.springframework.beans.BeanUtils.copyProperties(inputSearchUI, inputSearchExport);

            inputSearchExport.setFirst(0);
            inputSearchExport.setPageSize(Integer.MAX_VALUE);
            List<BoCheckupResultDTO> lstData = boFollowResultService.load(inputSearchExport).getData();

            if (!DataUtil.isNullOrEmpty(lstData)){
                for (BoCheckupResultDTO boCheckupResultDTO: lstData){
                    if (!DataUtil.isNullObject(boCheckupResultDTO.getLimitTsRisk())){
                        boCheckupResultDTO.setLimitTsRiskStr(convertOptionSetByCodeAndValue(Const.AP_DOMAIN.SALE_BO_LIMIT_TS_RISK,DataUtil.safeToString(boCheckupResultDTO.getLimitTsRisk())));
                    }
                    if (!DataUtil.isNullObject(boCheckupResultDTO.getQdeStatus())){
                        boCheckupResultDTO.setQdeStatusStr(convertOptionSetByCodeAndValue(Const.AP_DOMAIN.SALE_BO_QDE_STATUS_CHECK_UP,DataUtil.safeToString(boCheckupResultDTO.getQdeStatus())));
                    }
                    if (!DataUtil.isNullObject(boCheckupResultDTO.getConfirmErrorBo())){
                        boCheckupResultDTO.setConfirmErrorBoStr(convertOptionSetByCodeAndValue(Const.AP_DOMAIN.SALE_BI_CONFIRM_ERROR_BO_CHECKUP,DataUtil.safeToString(boCheckupResultDTO.getConfirmErrorBo())));
                    }
                    if (!DataUtil.isNullObject(boCheckupResultDTO.getStatusDocVpbank())){
                        boCheckupResultDTO.setStatusDocVpbankStr(convertOptionSetByCodeAndValue(Const.AP_DOMAIN.SALE_BO_FOLLOW_STATUS_DOC_VPBANK,DataUtil.safeToString(boCheckupResultDTO.getStatusDocVpbank())));
                    }
                    if (!DataUtil.isNullObject(boCheckupResultDTO.getGroupDocStatus())){
                        boCheckupResultDTO.setGroupDocStatusStr(convertOptionSetByCodeAndValue(Const.AP_DOMAIN.SALE_BO_FOLLOW_GROUP_STATUS_DOC,DataUtil.safeToString(boCheckupResultDTO.getGroupDocStatus())));
                    }
                    if (!DataUtil.isNullObject(boCheckupResultDTO.getDetailStatusDocVpbank())){
                        boCheckupResultDTO.setDetailStatusDocVpbankStr(convertOptionSetByCodeAndValue(Const.AP_DOMAIN.SALE_BO_FOLLOW_DETAIL_STATUS_DOC,DataUtil.safeToString(boCheckupResultDTO.getDetailStatusDocVpbank())));
                    }
                    if (!DataUtil.isNullObject(boCheckupResultDTO.getDocReturnStatus())){
                        boCheckupResultDTO.setDocReturnStatusStr(convertOptionSetByCodeAndValue(Const.AP_DOMAIN.SALE_BO_FOLLOW_DOC_RETURN_STATUS,DataUtil.safeToString(boCheckupResultDTO.getDocReturnStatus())));
                    }
                }
            }

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

    public SnDataModel<BoCheckupResultDTO> getLazyLoadBoFollowResult() {
        return lazyLoadBoFollowResult;
    }

    public void setLazyLoadBoFollowResult(SnDataModel<BoCheckupResultDTO> lazyLoadBoFollowResult) {
        this.lazyLoadBoFollowResult = lazyLoadBoFollowResult;
    }

    public String getHeaderDialog() {
        return isCreateState ? getText("boCheckupResult.title.dlg.add") : getText("boCheckupResult.title.dlg.update");
    }

    public boolean isCreateState() {
        return isCreateState;
    }

    public void setCreateState(boolean createState) {
        isCreateState = createState;
    }

    public BoCheckupResultDTO getCurrentBoCheckupResult() {
        return currentBoCheckupResult;
    }

    public void setCurrentBoCheckupResult(BoCheckupResultDTO currentBoCheckupResult) {
        this.currentBoCheckupResult = currentBoCheckupResult;
    }

    public List<OptionSetValueDTO> getStatusReturnDocs() {
        return statusReturnDocs;
    }

    public void setStatusReturnDocs(List<OptionSetValueDTO> statusReturnDocs) {
        this.statusReturnDocs = statusReturnDocs;
    }

    public List<OptionSetValueDTO> getGroupStatusDocs() {
        return groupStatusDocs;
    }

    public void setGroupStatusDocs(List<OptionSetValueDTO> groupStatusDocs) {
        this.groupStatusDocs = groupStatusDocs;
    }

    public List<DsaResultMeetingLogDTO> getDsaResultMeetingLogDTOs() {
        return dsaResultMeetingLogDTOs;
    }

    public void setDsaResultMeetingLogDTOs(List<DsaResultMeetingLogDTO> dsaResultMeetingLogDTOs) {
        this.dsaResultMeetingLogDTOs = dsaResultMeetingLogDTOs;
    }

    public StreamedContent getDownloadFile() {
        return downloadFile;
    }

    public void setDownloadFile(StreamedContent downloadFile) {
        this.downloadFile = downloadFile;
    }

    public List<UserDTO> getStaffCodes() {
        return staffCodes;
    }

    public void setStaffCodes(List<UserDTO> staffCodes) {
        this.staffCodes = staffCodes;
    }

    public String getUserLoginSystem() {
        return userLoginSystem;
    }

    public void setUserLoginSystem(String userLoginSystem) {
        this.userLoginSystem = userLoginSystem;
    }

    public String getAssigneUser() {
        return assigneUser;
    }

    public void setAssigneUser(String assigneUser) {
        this.assigneUser = assigneUser;
    }

    public List<OptionSetValueDTO> getStatusDocVpbanks() {
        return statusDocVpbanks;
    }

    public void setStatusDocVpbanks(List<OptionSetValueDTO> statusDocVpbanks) {
        this.statusDocVpbanks = statusDocVpbanks;
    }

    public List<OptionSetValueDTO> getDetailStatusDocs() {
        return detailStatusDocs;
    }

    public void setDetailStatusDocs(List<OptionSetValueDTO> detailStatusDocs) {
        this.detailStatusDocs = detailStatusDocs;
    }

    public boolean isRenderBtnAssigne() {
        return renderBtnAssigne;
    }

    public void setRenderBtnAssigne(boolean renderBtnAssigne) {
        this.renderBtnAssigne = renderBtnAssigne;
    }

    public List<OptionSetValueDTO> getConfirmErrorBos() {
        return confirmErrorBos;
    }

    public void setConfirmErrorBos(List<OptionSetValueDTO> confirmErrorBos) {
        this.confirmErrorBos = confirmErrorBos;
    }

    public Map<String, String> getListCheckBoxLabel() {
        return listCheckBoxLabel;
    }

    public void setListCheckBoxLabel(Map<String, String> listCheckBoxLabel) {
        this.listCheckBoxLabel = listCheckBoxLabel;
    }

    public List<AreaDTO> getLstProvinces() {
        return lstProvinces;
    }

    public void setLstProvinces(List<AreaDTO> lstProvinces) {
        this.lstProvinces = lstProvinces;
    }
}
