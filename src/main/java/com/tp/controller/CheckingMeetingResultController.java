package com.tp.controller;

import com.tp.common.custom.SnDataModel;
import com.tp.common.exception.LogicException;
import com.tp.dto.*;
import com.tp.dto.admin.UserDTO;
import com.tp.dto.requestSearch.BoCheckupResultSearchDTO;
import com.tp.dto.requestSearch.CheckingMeetingResultSearchDTO;
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
public class CheckingMeetingResultController extends BaseController{

    final static Logger logger = LoggerFactory.getLogger(CheckingMeetingResultController.class);

    private static final String BASE_FILE_NAME_EXPORT       = "boCheckUp";
    private static final String TEMPLATE_FILE_NAME          = "BO_CHECKUP.xlsx";

    @Autowired
    private CheckingMeetingResultService checkingMeetingResultService;
    @Autowired
    private OptionSetValueService optionSetValueService;
    @Autowired
    private DsaResultMeetingFileService dsaResultMeetingFileService;
    @Autowired
    private AreaService areaService;

    private BoCheckupResultDTO currentBoCheckupResult = new BoCheckupResultDTO();
    private boolean isCreateState;
    private SnDataModel<BoCheckupResultDTO> lazyLoadBoCheckupResult;
    private List<OptionSetValueDTO> statusDocCheckUps;
    private List<OptionSetValueDTO> statusDocQDEs;
    private List<OptionSetValueDTO> sendToFollows;
    private List<OptionSetValueDTO> statusDocVpbanks;
    private List<OptionSetValueDTO> statusReturnDocs;
    private List<DsaResultMeetingLogDTO> dsaResultMeetingLogDTOs;
    private String userLoginSystem;
    private Map<String, String> listCheckBoxLabel = new ConcurrentHashMap<String, String>();
    private List<AreaDTO> lstProvinces;

    @Value("${DSA_PREFIX_FOLDER_SYSTEM_FILE_UPLOAD}")
    private String PREFIX_FOLDER_DSA;
    private StreamedContent downloadFile;

    @PostConstruct
    @Override
    public void init(){
        try {
            CheckingMeetingResultSearchDTO checkingMeetingResultSearchDTO = new CheckingMeetingResultSearchDTO();
            if (hasPermission("ROLE_TL_CCA")){
                checkingMeetingResultSearchDTO.setTlCcaCode(getUsername());
            }else if(hasPermission("ROLE_CCA")){
                checkingMeetingResultSearchDTO.setCcaCode(getUsername());
            }
            if (hasPermission("ROLE_TL_DSA")){
                checkingMeetingResultSearchDTO.setTlDsaCode(getUsername());
            }else if(hasPermission("ROLE_DSA")){
                checkingMeetingResultSearchDTO.setDsaCode(getUsername());
            }

            if ((hasPermission("ROLE_STL_CCA") && hasPermission("ROLE_TL_DSA"))
                    ||(hasPermission("ROLE_TL_CCA") && hasPermission("ROLE_DSA"))){
                checkingMeetingResultSearchDTO.setTlCcaCode("");
                checkingMeetingResultSearchDTO.setCcaCode("");
                checkingMeetingResultSearchDTO.setTlDsaCode("");
                checkingMeetingResultSearchDTO.setDsaCode("");
            }

            Calendar calFirstMonth = Calendar.getInstance();
            calFirstMonth.set(Calendar.DAY_OF_MONTH,1);
            checkingMeetingResultSearchDTO.setReceivedDsaDateFrom(calFirstMonth.getTime());
            checkingMeetingResultSearchDTO.setReceivedDsaDateTo(new Date());
            lazyLoadBoCheckupResult = new SnDataModel<>(checkingMeetingResultService, checkingMeetingResultSearchDTO);
            statusDocQDEs = optionSetValueService.findByActionCode(Const.AP_DOMAIN.SALE_BO_QDE_STATUS_CHECK_UP);
            statusDocCheckUps = optionSetValueService.findByActionCode(Const.AP_DOMAIN.SALE_BO_STATUS_CHECK_UP);
            sendToFollows = optionSetValueService.findByActionCode(Const.AP_DOMAIN.SALE_BO_SEND_TO_FOLLOW_STATUS);
            statusDocVpbanks = optionSetValueService.findByActionCode(Const.AP_DOMAIN.SALE_BO_FOLLOW_STATUS_DOC_VPBANK);
            statusReturnDocs = optionSetValueService.findByActionCode(Const.AP_DOMAIN.SALE_BO_FOLLOW_DOC_RETURN_STATUS);
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

        } catch (Exception e) {
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

            CheckingMeetingResultSearchDTO inputSearchUI = (CheckingMeetingResultSearchDTO)lazyLoadBoCheckupResult.getRequestSearchDTO();

            CheckingMeetingResultSearchDTO inputSearchExport = new CheckingMeetingResultSearchDTO();
            org.springframework.beans.BeanUtils.copyProperties(inputSearchUI, inputSearchExport);

            inputSearchExport.setFirst(0);
            inputSearchExport.setPageSize(Integer.MAX_VALUE);
            List<BoCheckupResultDTO> lstData = checkingMeetingResultService.load(inputSearchExport).getData();

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

    public String getUserLoginSystem() {
        return userLoginSystem;
    }

    public void setUserLoginSystem(String userLoginSystem) {
        this.userLoginSystem = userLoginSystem;
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

    public List<OptionSetValueDTO> getStatusDocVpbanks() {
        return statusDocVpbanks;
    }

    public void setStatusDocVpbanks(List<OptionSetValueDTO> statusDocVpbanks) {
        this.statusDocVpbanks = statusDocVpbanks;
    }

    public List<OptionSetValueDTO> getStatusReturnDocs() {
        return statusReturnDocs;
    }

    public void setStatusReturnDocs(List<OptionSetValueDTO> statusReturnDocs) {
        this.statusReturnDocs = statusReturnDocs;
    }
}
