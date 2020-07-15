package com.tp.controller;

import com.tp.common.custom.SnDataModel;
import com.tp.common.exception.LogicException;
import com.tp.dto.*;
import com.tp.dto.admin.UserDTO;
import com.tp.dto.requestSearch.BoCheckupResultSearchDTO;
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
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by DINH NGUYEN on 20/07/2017.
 */
@ManagedBean
@Component
@Scope("view")
public class BoCheckupResultController extends BaseController{

    final static Logger logger = LoggerFactory.getLogger(BoCheckupResultController.class);

    private static final String BASE_FILE_NAME_EXPORT       = "boCheckUp";
    private static final String TEMPLATE_FILE_NAME          = "BO_CHECKUP.xlsx";

    @Autowired
    private BoCheckupResultService checkupResultService;
    @Autowired
    private OptionSetValueService optionSetValueService;
    @Autowired
    private DsaResultMeetingFileService dsaResultMeetingFileService;
    @Autowired
    private UserService userService;
    @Autowired
    private AreaService areaService;

    private BoCheckupResultDTO currentBoCheckupResult = new BoCheckupResultDTO();
    private boolean isCreateState;
    private SnDataModel<BoCheckupResultDTO> lazyLoadBoCheckupResult;
    private List<OptionSetValueDTO> statusTSAndRisks;
    private List<OptionSetValueDTO> statusDocCheckUps;
    private List<OptionSetValueDTO> statusDocQDEs;
    private List<OptionSetValueDTO> reasonCheckUpLists;
    private List<OptionSetValueDTO> sendToFollows;
    private List<OptionSetValueDTO> boundCodeDsas;
    private List<DsaResultMeetingLogDTO> dsaResultMeetingLogDTOs;
    private List<UserDTO> staffCodes;
    private String userLoginSystem;
    private String assigneUser;
    private Map<String, String> listCheckBoxLabel = new ConcurrentHashMap<String, String>();
    private List<AreaDTO> lstProvinces;

    @Value("${DSA_PREFIX_FOLDER_SYSTEM_FILE_UPLOAD}")
    private String PREFIX_FOLDER_DSA;
    private StreamedContent downloadFile;
    private boolean renderBtnAssigne;

    @PostConstruct
    @Override
    public void init(){
        try {
            lazyLoadBoCheckupResult = new SnDataModel<>(checkupResultService, new BoCheckupResultSearchDTO());
            statusTSAndRisks = optionSetValueService.findByActionCode(Const.AP_DOMAIN.SALE_BO_LIMIT_TS_RISK);
            statusDocQDEs = optionSetValueService.findByActionCode(Const.AP_DOMAIN.SALE_BO_QDE_STATUS_CHECK_UP);
            statusDocCheckUps = optionSetValueService.findByActionCode(Const.AP_DOMAIN.SALE_BO_STATUS_CHECK_UP);
            sendToFollows = optionSetValueService.findByActionCode(Const.AP_DOMAIN.SALE_BO_SEND_TO_FOLLOW_STATUS);
            boundCodeDsas = optionSetValueService.findByActionCode(Const.AP_DOMAIN.SALE_BO_CHECKUP_BOUND_CODE_DSA);
            if (hasPermission("ROLE_BO_FOLLOW_ASSIGNE")) {
                staffCodes = userService.findAllByUserTl(getUsername());
                renderBtnAssigne = true;
            }else{
                staffCodes = userService.findAllUserOnTeamByUser(getUsername());
                renderBtnAssigne = false;
            }
            lstProvinces = areaService.findAllProvince();
            userLoginSystem = getUsername();
            listCheckBoxLabel.put(getText("distribute.meeting.province.label"), getText("common.label.choose"));
            listCheckBoxLabel.put(getText("boCheckupResult.label.bo.checkup"), getText("common.label.choose"));
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
            if (DataUtil.isNullObject(currentBoCheckupResult.getReceivedDsaDate())){
                currentBoCheckupResult.setReceivedDsaDate(new Date());
            }
            if (DataUtil.isNullObject(currentBoCheckupResult.getReturnDsaDate())){
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_MONTH,1);
                currentBoCheckupResult.setReturnDsaDate(cal.getTime());
            }

            if (DataUtil.isNullOrEmpty(currentBoCheckupResult.getBoCheckupCode())){
                currentBoCheckupResult.setBoCheckupCode(getUsername());
            }

            if (DataUtil.isNullOrEmpty(currentBoCheckupResult.getTlBoCheckupCode())){
                UserDTO userDTO = userService.findTlByUserName(userLoginSystem);
                if (!DataUtil.isNullObject(userDTO)){
                    currentBoCheckupResult.setTlBoCheckupCode(DataUtil.safeToUpper(userDTO.getUserName()));
                }
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
//        if (!DataUtil.safeEqual(currentBoCheckupResult.getLimitTsRisk(),1)){
//            if (!DataUtil.isNullObject(currentBoCheckupResult.getSendQdeDate())){
//                reportErrorGrowl(null, "boCheckupResult.msg.send.qde.null");
//                return false;
//            }
//            if (DataUtil.safeEqual(currentBoCheckupResult.getCheckupStatus(),1)){
//                reportErrorGrowl(null, "boCheckupResult.msg.checkup.status.valid.null");
//                return false;
//            }
//            if (DataUtil.isNullObject(currentBoCheckupResult.getReturnDsaDate())){
//                reportErrorGrowl(null, "boCheckupResult.msg.return.dsa.date.null");
//                return false;
//            }
//            if (DataUtil.isNullObject(currentBoCheckupResult.getCheckupReason())){
//                reportErrorGrowl(null, "boCheckupResult.msg.reason.valid.null");
//                return false;
//            }
//        }
        if (!DataUtil.isNullObject(currentBoCheckupResult.getReturnDsaDate())){
            if (!DataUtil.isNullObject(currentBoCheckupResult.getSendQdeDate())){
                reportErrorGrowl(null, "boCheckupResult.msg.send.qde.null");
                return false;
            }
//            if (!DataUtil.isNullObject(currentBoCheckupResult.getQdeStatus())){
//                reportErrorGrowl(null, "boCheckupResult.msg.send.qde.status.null");
//                return false;
//            }
            if (DataUtil.safeEqual(currentBoCheckupResult.getCheckupStatus(),1)){
                reportErrorGrowl(null, "boCheckupResult.msg.checkup.status.valid.null");
                return false;
            }
            if (DataUtil.isNullObject(currentBoCheckupResult.getCheckupReason())){
                reportErrorGrowl(null, "boCheckupResult.msg.reason.valid.null");
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
            currentBoCheckupResult.setUpdatedUser(getUsername());
            currentBoCheckupResult.setUpdatedDate(new Date());
            checkupResultService.createOrUpdate(currentBoCheckupResult);
            reportSuccessGrowl(null, "boCheckupResult.msg.update.success");
            RequestContext.getCurrentInstance().execute("PF('dlgBoCheckupResult').hide()");
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
            for (BoCheckupResultDTO boCheckupResultDTO:lazyLoadBoCheckupResult.getSelectedList()){

                boCheckupResultDTO.setBoCheckupCode(DataUtil.safeToUpper(assigneUser));
                boCheckupResultDTO.setTlBoCheckupCode(DataUtil.safeToUpper(tlUserDTO.getUserName()));
                boCheckupResultDTO.setUpdatedDate(new Date());
                boCheckupResultDTO.setUpdatedUser(DataUtil.safeToUpper(getUsername()));

                checkupResultService.createOrUpdate(boCheckupResultDTO);
            }
            reportSuccessGrowl(null, "boCheckupResult.msg.assigne.success", DataUtil.safeToUpper(assigneUser) ,lazyLoadBoCheckupResult.getSelectedList().size() + "");
            lazyLoadBoCheckupResult.getSelectedList().clear();
            RequestContext.getCurrentInstance().execute("PF('dlgAssigneBoCheckup').hide()");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }
    }

    public boolean validateBeforeAssigne() throws Exception {
        isCreateState = true;
        if(DataUtil.isNullOrEmpty(lazyLoadBoCheckupResult.getSelectedList())){
            reportErrorGrowl(null, "distribute.meeting.msg.assigne.empty");
            return false;
        }
        if (!DataUtil.isNullOrEmpty(staffCodes)){
            staffCodes.clear();
        }
        staffCodes = userService.findAllByUserTl(getUsername());
        return true;
    }

    public void validateBeforeSendFollow(){
        if(DataUtil.isNullOrEmpty(lazyLoadBoCheckupResult.getSelectedList())){
            reportErrorGrowl(null, "boCheckupResult.msg.send.empty");
        }else{
            for (BoCheckupResultDTO boCheckupResultDTO: lazyLoadBoCheckupResult.getSelectedList()){
                if (!DataUtil.safeEqual(boCheckupResultDTO.getCheckupStatus(),1)){
                    reportErrorGrowl(null, "boCheckupResult.msg.send.follow.customer.error",boCheckupResultDTO.getCustomerName(),boCheckupResultDTO.getCustomerPhone(),boCheckupResultDTO.getNationalId());
                    return;
                }
            }
        }
    }

    public void sendFollow(){
        validateBeforeSendFollow();
        if(FacesContext.getCurrentInstance().isValidationFailed())
            return;
        try {
            for (BoCheckupResultDTO boCheckupResultDTO: lazyLoadBoCheckupResult.getSelectedList()){
                boCheckupResultDTO.setStatusDocVpbank(null);
                boCheckupResultDTO.setGroupDocStatus(null);
                boCheckupResultDTO.setDetailStatusDocVpbank(null);
                boCheckupResultDTO.setSendToFollow(1l);
                boCheckupResultDTO.setUpdatedUser(getUsername());
                boCheckupResultDTO.setUpdatedDate(new Date());
                checkupResultService.createOrUpdate(boCheckupResultDTO);
            }
            reportSuccessGrowl(null, "boCheckupResult.msg.send.follow.success", lazyLoadBoCheckupResult.getSelectedList().size() + "");
            lazyLoadBoCheckupResult.getSelectedList().clear();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }
    }

    public void prepareCloneRecord(BoCheckupResultDTO boCheckupResultDTO){
        try {
            List<BoCheckupResultDTO> boCheckupResultDTOs = checkupResultService.findByNameAndPhoneNationalId(boCheckupResultDTO.getCustomerName(),boCheckupResultDTO.getCustomerPhone(),boCheckupResultDTO.getNationalId());
            if (!DataUtil.isNullOrEmpty(boCheckupResultDTOs) && boCheckupResultDTOs.size() > 1){
                reportErrorGrowl(null, "boCheckupResult.msg.duppicate.error",boCheckupResultDTO.getCustomerName(),boCheckupResultDTO.getCustomerPhone(), boCheckupResultDTO.getNationalId());
                return;
            }else{
                currentBoCheckupResult = DataUtil.cloneBean(boCheckupResultDTO);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }
    }

    public void cloneRecordForDupSell(){
        if(FacesContext.getCurrentInstance().isValidationFailed())
            return;
        try {
            BoCheckupResultDTO boCheckupResultDTO = new BoCheckupResultDTO();
            boCheckupResultDTO.setDsaResultMeetingId(currentBoCheckupResult.getDsaResultMeetingId());
            boCheckupResultDTO.setCustomerName(currentBoCheckupResult.getCustomerName());
            boCheckupResultDTO.setCustomerPhone(currentBoCheckupResult.getCustomerPhone());
            boCheckupResultDTO.setNationalId(currentBoCheckupResult.getNationalId());
            boCheckupResultDTO.setPartnerCode(currentBoCheckupResult.getPartnerCode());
            boCheckupResultDTO.setBoundCode(currentBoCheckupResult.getBoundCode());
            boCheckupResultDTO.setProvincePhone(currentBoCheckupResult.getProvincePhone());
            boCheckupResultDTO.setUplLimit(currentBoCheckupResult.getUplLimit());
            boCheckupResultDTO.setCcLimit(currentBoCheckupResult.getCcLimit());
            boCheckupResultDTO.setMeetingDate(currentBoCheckupResult.getMeetingDate());
            boCheckupResultDTO.setValidDate(currentBoCheckupResult.getMeetingDate());
            boCheckupResultDTO.setTlCcaCode(currentBoCheckupResult.getTlCcaCode());
            boCheckupResultDTO.setCcaCode(currentBoCheckupResult.getCcaCode());
            boCheckupResultDTO.setTlDsaCode(currentBoCheckupResult.getTlDsaCode());
            boCheckupResultDTO.setDsaCode(currentBoCheckupResult.getDsaCode());
            boCheckupResultDTO.setDsaSaleCode(currentBoCheckupResult.getDsaSaleCode());
            boCheckupResultDTO.setProvinceName(currentBoCheckupResult.getProvinceName());
            boCheckupResultDTO.setProvinceCode(currentBoCheckupResult.getProvinceCode());
            boCheckupResultDTO.setProvincePhone(currentBoCheckupResult.getProvincePhone());
            boCheckupResultDTO.setDistrictName(currentBoCheckupResult.getDistrictName());
            boCheckupResultDTO.setDistrictCode(currentBoCheckupResult.getDistrictCode());
            boCheckupResultDTO.setStatus(1l);
            boCheckupResultDTO.setCreatedDate(new Date());
            boCheckupResultDTO.setCreatedUser(getUsername());
            boCheckupResultDTO.setUpdatedDate(new Date());
            boCheckupResultDTO.setUpdatedUser(getUsername());
            checkupResultService.createOrUpdate(boCheckupResultDTO);
            reportSuccessGrowl(null, "boCheckupResult.msg.duppicate.success",boCheckupResultDTO.getCustomerName(), boCheckupResultDTO.getCustomerPhone());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }
    }

    public void validateBeforeDelete(){
        if(DataUtil.isNullOrEmpty(lazyLoadBoCheckupResult.getSelectedList())){
            reportErrorGrowl(null, "apConfig.msg.delete.empty");
        }
    }

    public void delete(){
        validateBeforeDelete();
        if(FacesContext.getCurrentInstance().isValidationFailed())
            return;
        try {
            if (!DataUtil.isNullOrEmpty(lazyLoadBoCheckupResult.getSelectedList())) {
                for (BoCheckupResultDTO boCheckupResultDTO: lazyLoadBoCheckupResult.getSelectedList()) {
                    boCheckupResultDTO.setStatus(0l);
                    checkupResultService.createOrUpdate(boCheckupResultDTO);
                }
                reportSuccessGrowl(null, "boCheckupResult.msg.delete.success", lazyLoadBoCheckupResult.getSelectedList().size() + "");
                lazyLoadBoCheckupResult.getSelectedList().clear();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }
    }

    public void prepareDeleteOne(BoCheckupResultDTO boCheckupResultDTO){
        lazyLoadBoCheckupResult.getSelectedList().clear();
        lazyLoadBoCheckupResult.getSelectedList().add(boCheckupResultDTO);
    }

    public void updateReasonByCheckupStatus(String checkupStatus){
        try {
            if (!DataUtil.isNullOrEmpty(reasonCheckUpLists)){
                reasonCheckUpLists.clear();
            }
            reasonCheckUpLists = optionSetValueService.findListByCodeAndActionCode(Const.AP_DOMAIN.SALE_BO_STATUS_REASON_CHECK_UP,checkupStatus);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }
    }

    public void downloadFileAtt(Long dsaResultMeetingId) {
        try {
            DsaResultMeetingFileDTO dsaResultMeetingFileDTO = dsaResultMeetingFileService.findLastByDsaResultMeeting(dsaResultMeetingId);
            Path path = Paths.get(PREFIX_FOLDER_DSA+dsaResultMeetingFileDTO.getPrefixFolder()+dsaResultMeetingFileDTO.getFileName());
            byte[] fileBytes = Files.readAllBytes(path);
            InputStream stream = new ByteArrayInputStream(fileBytes);
            downloadFile = new DefaultStreamedContent(stream, findTailFile(dsaResultMeetingFileDTO.getFileName()), dsaResultMeetingFileDTO.getFileName());
        } catch (Exception ex) {
            reportErrorGrowl(null, "common.error.happened");
        }
    }

    public void exportData() {
        try {
            String location = "exportData() >";
            logger.info(location + " START");
            FileExportBean bean = new FileExportBean();
            bean.setTemplatePath(FileUtil.getTemplatePath() +BASE_FILE_NAME_EXPORT+"/"+ File.separator);

            BoCheckupResultSearchDTO inputSearchUI = (BoCheckupResultSearchDTO)lazyLoadBoCheckupResult.getRequestSearchDTO();

            BoCheckupResultSearchDTO inputSearchExport = new BoCheckupResultSearchDTO();
            org.springframework.beans.BeanUtils.copyProperties(inputSearchUI, inputSearchExport);

            inputSearchExport.setFirst(0);
            inputSearchExport.setPageSize(Integer.MAX_VALUE);
            List<BoCheckupResultDTO> lstData = checkupResultService.load(inputSearchExport).getData();

            if (!DataUtil.isNullOrEmpty(lstData)){
                for (BoCheckupResultDTO boCheckupResultDTO: lstData){
                    if (!DataUtil.isNullObject(boCheckupResultDTO.getLimitTsRisk())){
                        boCheckupResultDTO.setLimitTsRiskStr(convertOptionSetByCodeAndValue(Const.AP_DOMAIN.SALE_BO_LIMIT_TS_RISK,DataUtil.safeToString(boCheckupResultDTO.getLimitTsRisk())));
                    }
                    if (!DataUtil.isNullObject(boCheckupResultDTO.getQdeStatus())){
                        boCheckupResultDTO.setQdeStatusStr(convertOptionSetByCodeAndValue(Const.AP_DOMAIN.SALE_BO_QDE_STATUS_CHECK_UP,DataUtil.safeToString(boCheckupResultDTO.getQdeStatus())));
                    }
                    if (!DataUtil.isNullObject(boCheckupResultDTO.getCheckupStatus())){
                        boCheckupResultDTO.setCheckupStatusStr(convertOptionSetByCodeAndValue(Const.AP_DOMAIN.SALE_BO_STATUS_CHECK_UP,DataUtil.safeToString(boCheckupResultDTO.getCheckupStatus())));
                    }
                    if (!DataUtil.isNullObject(boCheckupResultDTO.getCheckupReason())){
                        boCheckupResultDTO.setCheckupReasonStr(convertOptionSetByCodeAndValue(Const.AP_DOMAIN.SALE_BO_STATUS_REASON_CHECK_UP,DataUtil.safeToString(boCheckupResultDTO.getCheckupReason())));
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

    public void updateLabelSelectCheckbox(String key, List<String> values) {
        try {
            listCheckBoxLabel.put(key, (!DataUtil.isNullOrEmpty(values) ? values.size() + " " + getText("common.cbb.isSelected") : getText("common.label.choose")));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public SnDataModel<BoCheckupResultDTO> getLazyLoadBoCheckupResult() {
        return lazyLoadBoCheckupResult;
    }

    public void setLazyLoadBoCheckupResult(SnDataModel<BoCheckupResultDTO> lazyLoadBoCheckupResult) {
        this.lazyLoadBoCheckupResult = lazyLoadBoCheckupResult;
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

    public List<OptionSetValueDTO> getStatusTSAndRisks() {
        return statusTSAndRisks;
    }

    public void setStatusTSAndRisks(List<OptionSetValueDTO> statusTSAndRisks) {
        this.statusTSAndRisks = statusTSAndRisks;
    }

    public List<OptionSetValueDTO> getStatusDocCheckUps() {
        return statusDocCheckUps;
    }

    public void setStatusDocCheckUps(List<OptionSetValueDTO> statusDocCheckUps) {
        this.statusDocCheckUps = statusDocCheckUps;
    }

    public List<OptionSetValueDTO> getStatusDocQDEs() {
        return statusDocQDEs;
    }

    public void setStatusDocQDEs(List<OptionSetValueDTO> statusDocQDEs) {
        this.statusDocQDEs = statusDocQDEs;
    }

    public List<OptionSetValueDTO> getReasonCheckUpLists() {
        return reasonCheckUpLists;
    }

    public void setReasonCheckUpLists(List<OptionSetValueDTO> reasonCheckUpLists) {
        this.reasonCheckUpLists = reasonCheckUpLists;
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

    public List<OptionSetValueDTO> getSendToFollows() {
        return sendToFollows;
    }

    public void setSendToFollows(List<OptionSetValueDTO> sendToFollows) {
        this.sendToFollows = sendToFollows;
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

    public boolean isRenderBtnAssigne() {
        return renderBtnAssigne;
    }

    public void setRenderBtnAssigne(boolean renderBtnAssigne) {
        this.renderBtnAssigne = renderBtnAssigne;
    }

    public List<OptionSetValueDTO> getBoundCodeDsas() {
        return boundCodeDsas;
    }

    public void setBoundCodeDsas(List<OptionSetValueDTO> boundCodeDsas) {
        this.boundCodeDsas = boundCodeDsas;
    }
}
