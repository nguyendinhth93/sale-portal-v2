package com.tp.controller;

import com.google.common.collect.Lists;
import com.mchange.v2.beans.BeansUtils;
import com.tp.common.custom.SnDataModel;
import com.tp.common.excel.ExcellUtil;
import com.tp.common.exception.LogicException;
import com.tp.dto.*;
import com.tp.dto.admin.UserDTO;
import com.tp.dto.fw.BaseMessage;
import com.tp.dto.requestSearch.CreditScoringSearchDTO;
import com.tp.service.*;
import com.tp.util.*;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.omnifaces.util.Faces;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.annotation.Secured;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by DINH NGUYEN on 20/07/2017.
 */
@ManagedBean
@Component
@Scope("view")
public class DistributeMeetingController extends BaseController{

    final static Logger logger = LoggerFactory.getLogger(DistributeMeetingController.class);

    private final static String FOLDER_PREFIX = "inputMeeting/";
    private final static String FILE_NAME_INPUT_MEETING_REPORT = "INPUT_MEETING.xlsx";
    private static final String FILE_IMPORT_TEMPLATE_PATH = "IMPORT_DSA_TEMPLATE_MEETING_TEMPLATE.xls";
    private static final String TEMPLATE_FILE_NAME          = "DISTRIBUTE_MEETING_TEMPLATE.xlsx";
    private static final String BASE_FILE_NAME_EXPORT       = "resultMeeting";
    private static final Long MAX_ROW_IMPORT = 500l;

    @Autowired
    private DistributeMeetingService distributeMeetingService;
    @Autowired
    private DsaResultMeetingLogService dsaResultMeetingLogService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private OptionSetValueService optionSetValueService;
    @Autowired
    private UserService userService;
    @Autowired
    private BoCheckupResultService boCheckupResultService;

    @Value("${DSA_PREFIX_FOLDER_SYSTEM_FILE_UPLOAD}")
    private String PREFIX_FOLDER_DSA;

    private boolean isCreateState;
    private SnDataModel<DsaResultMeetingDTO> lazyLoadDsaResultMeeting;
    private List<DsaResultMeetingLogDTO> dsaResultMeetingLogDTOs;
    private List<AreaDTO> lstProvinces;
    private List<AreaDTO> lstDistricts;
    private List<OptionSetValueDTO> meetingResults;
    private List<OptionSetValueDTO> ccaAcceptStatus;
    private List<UserDTO> lstDSAs;
    private String assigneUser;
    private StreamedContent downloadFile;
    private Map<String, String> listCheckBoxLabel = new ConcurrentHashMap<String, String>();
    private Date meetingDateUpdate;
    private DsaResultMeetingDTO currentDsaResultMeetingDTO;
    private BoCheckupResultDTO boCheckupResultDTO;

    //Import Properties
    private String fileNameImport;
    private UploadedFile uploadedFileImport;
    private List<String[]> listError = Lists.newArrayList();
    private byte[] contentByteImport;
    private Workbook bookError;
    private StreamedContent downloadFileTemplate;
    private boolean renderBtnUpdate;
    private boolean renderBtnUpdateMeetingDate;
    private boolean renderBtnAssigne;


    @PostConstruct
    @Override
    public void init(){
        try {
            DsaResultMeetingSearchDTO dsaResultMeetingSearchDTO = new DsaResultMeetingSearchDTO();
            dsaResultMeetingSearchDTO.setFromMeetDate(new Date());
            dsaResultMeetingSearchDTO.setToMeetDate(new Date());
            lazyLoadDsaResultMeeting = new SnDataModel<>(distributeMeetingService, dsaResultMeetingSearchDTO);
            lstProvinces = areaService.findAllProvince();
            meetingResults = optionSetValueService.findByActionCode(Const.AP_DOMAIN.SALE_DSA_MEETING_RESULT);
            ccaAcceptStatus = optionSetValueService.findByActionCode(Const.AP_DOMAIN.SALE_CCA_ACCEPT_STATUS);
            if (!hasPermission(Const.ROLE_USER.SP_DSA)){
                lstDSAs = userService.findDSAByUser(getUsername());
            }else{
                lstDSAs = userService.findTlDSAByUser(getUsername());
            }
            renderBtnUpdate = hasPermission("DSA_RESULT_MEETING_UPDATE_ROLE");
            renderBtnUpdateMeetingDate = hasPermission("DSA_RESULT_MEETING_UPDATE_MEET_DATE_ROLE");
            renderBtnAssigne = hasPermission("DSA_RESULT_MEETING_ASSIGNE_ROLE");
            listCheckBoxLabel.put(getText("distribute.meeting.district.label"), getText("common.label.choose"));
            listCheckBoxLabel.put(getText("distribute.meeting.dsa.label"), getText("common.label.choose"));
            listCheckBoxLabel.put(getText("distribute.meeting.status.label"), getText("common.label.choose"));
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            reportErrorGrowl(null, "common.error.happened");
        }
    }

    public boolean validateBeforeAssigne() throws Exception {
        isCreateState = true;
        if(DataUtil.isNullOrEmpty(lazyLoadDsaResultMeeting.getSelectedList())){
            reportErrorGrowl(null, "distribute.meeting.msg.assigne.empty");
            return false;
        }
        if (!DataUtil.isNullOrEmpty(lstDSAs)){
            lstDSAs.clear();
        }
        lstDSAs = distributeMeetingService.getAllDsaAndMeetingAvailiableByTlDsa(getUsername());
        return true;
    }

    public boolean validateBeforeUpdateMeetingDate() throws Exception {
        if(DataUtil.isNullOrEmpty(lazyLoadDsaResultMeeting.getSelectedList())){
            reportErrorGrowl(null, "distribute.meeting.msg.update.meeting.date.empty");
            return false;
        }
        return true;
    }

    public boolean validateSaveUpdateMeetingDate() throws Exception {
        if(DataUtil.isNullOrEmpty(lazyLoadDsaResultMeeting.getSelectedList())){
            reportErrorGrowl(null, "distribute.meeting.msg.update.meeting.date.empty");
            return false;
        }
        if(DataUtil.isNullObject(meetingDateUpdate)){
            reportErrorGrowl(null, "distribute.meeting.msg.meeting.date.req");
            return false;
        }
        return true;
    }

    public boolean validateBeforeUpdate() throws Exception {
        return true;
    }

    public void updateMeetingDate(){
        try {
            List<DsaResultMeetingDTO> dsaResultMeetingDTOs = new ArrayList<>();
            List<DsaResultMeetingLogDTO> dsaResultMeetingLogDTOs = new ArrayList<>();
            if (!validateSaveUpdateMeetingDate()){
                return;
            }
            if(FacesContext.getCurrentInstance().isValidationFailed())
                return;
            DsaResultMeetingLogDTO dsaResultMeetingLogDTO;
            for (DsaResultMeetingDTO dsaResultMeetingDTO:lazyLoadDsaResultMeeting.getSelectedList()){
                dsaResultMeetingLogDTO = new DsaResultMeetingLogDTO();

                dsaResultMeetingDTO.setMeetingDate(meetingDateUpdate);
                dsaResultMeetingDTO.setUpdatedUser(getUsername());
                dsaResultMeetingDTO.setUpdatedDate(new Date());

                dsaResultMeetingLogDTO.setDsaResultMeetingId(dsaResultMeetingDTO.getId());
                dsaResultMeetingLogDTO.setTlDsaCode(dsaResultMeetingDTO.getTlDsaCode());
                dsaResultMeetingLogDTO.setDsaCode(dsaResultMeetingDTO.getDsaCode());
                dsaResultMeetingLogDTO.setProvinceMeetingId(dsaResultMeetingDTO.getProvinceMeetingCode());
                dsaResultMeetingLogDTO.setDistrictMeetingId(dsaResultMeetingDTO.getDistrictMeetingCode());
                dsaResultMeetingLogDTO.setMeetingDate(meetingDateUpdate);
                dsaResultMeetingLogDTO.setMeetingResult(dsaResultMeetingDTO.getMeetingResult());
                dsaResultMeetingLogDTO.setMeetingReason(dsaResultMeetingDTO.getMeetingReason());
                dsaResultMeetingLogDTO.setDsaNote(dsaResultMeetingDTO.getDsaNote());
                dsaResultMeetingLogDTO.setCreatedDate(new Date());
                dsaResultMeetingLogDTO.setCreatedUser(getUsername());
                dsaResultMeetingLogDTO.setAcceptStatus(dsaResultMeetingDTO.getAcceptStatus());
                dsaResultMeetingLogDTO.setKeepStatus(dsaResultMeetingDTO.getKeepStatus());
                dsaResultMeetingLogDTO.setReturnCca(dsaResultMeetingDTO.getReturnCca());
                dsaResultMeetingLogDTO.setProductType(dsaResultMeetingDTO.getProductType());

                dsaResultMeetingDTOs.add(dsaResultMeetingDTO);
                dsaResultMeetingLogDTOs.add(dsaResultMeetingLogDTO);
            }
            distributeMeetingService.createOrUpdateWithBatchs(dsaResultMeetingDTOs);
            dsaResultMeetingLogService.createOrUpdateWithBatchs(dsaResultMeetingLogDTOs);
            reportSuccessGrowl(null, "distribute.meeting.msg.update.meeting.date.success" ,lazyLoadDsaResultMeeting.getSelectedList().size() + "");
            lazyLoadDsaResultMeeting.getSelectedList().clear();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }

    }

    public void updateMeeting(){
        try {
            if (!validateBeforeUpdate()){
                return;
            }
            if(FacesContext.getCurrentInstance().isValidationFailed())
                return;
            DsaResultMeetingLogDTO dsaResultMeetingLogDTO = new DsaResultMeetingLogDTO();

            if (!DataUtil.isNullOrEmpty(currentDsaResultMeetingDTO.getProvinceMeetingCode())){
                currentDsaResultMeetingDTO.setProvinceMeetingName(getProvinceNameByCode(currentDsaResultMeetingDTO.getProvinceMeetingCode()));
            }
            if (!DataUtil.isNullOrEmpty(currentDsaResultMeetingDTO.getDistrictMeetingCode())){
                currentDsaResultMeetingDTO.setDistrictMeetingName(getDistrictNameByCode(currentDsaResultMeetingDTO.getDistrictMeetingCode()));
            }

            currentDsaResultMeetingDTO.setUpdatedUser(getUsername());
            currentDsaResultMeetingDTO.setUpdatedDate(new Date());

            dsaResultMeetingLogDTO.setDsaResultMeetingId(currentDsaResultMeetingDTO.getId());
            dsaResultMeetingLogDTO.setTlDsaCode(currentDsaResultMeetingDTO.getTlDsaCode());
            dsaResultMeetingLogDTO.setDsaCode(currentDsaResultMeetingDTO.getDsaCode());
            dsaResultMeetingLogDTO.setProvinceMeetingId(currentDsaResultMeetingDTO.getProvinceMeetingCode());
            dsaResultMeetingLogDTO.setDistrictMeetingId(currentDsaResultMeetingDTO.getDistrictMeetingCode());
            dsaResultMeetingLogDTO.setMeetingDate(currentDsaResultMeetingDTO.getMeetingDate());
            dsaResultMeetingLogDTO.setMeetingResult(currentDsaResultMeetingDTO.getMeetingResult());
            dsaResultMeetingLogDTO.setMeetingReason(currentDsaResultMeetingDTO.getMeetingReason());
            dsaResultMeetingLogDTO.setDsaNote(currentDsaResultMeetingDTO.getDsaNote());
            dsaResultMeetingLogDTO.setCreatedDate(new Date());
            dsaResultMeetingLogDTO.setCreatedUser(getUsername());
            dsaResultMeetingLogDTO.setAcceptStatus(currentDsaResultMeetingDTO.getAcceptStatus());
            dsaResultMeetingLogDTO.setKeepStatus(currentDsaResultMeetingDTO.getKeepStatus());
            dsaResultMeetingLogDTO.setReturnCca(currentDsaResultMeetingDTO.getReturnCca());
            dsaResultMeetingLogDTO.setProductType(currentDsaResultMeetingDTO.getProductType());
            distributeMeetingService.createOrUpdate(currentDsaResultMeetingDTO);
            dsaResultMeetingLogService.createOrUpdate(dsaResultMeetingLogDTO);
            reportSuccessGrowl(null, "distribute.meeting.msg.update.meeting.success");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }

    }

    public void assigne(){
        try {
            List<DsaResultMeetingDTO> dsaResultMeetingDTOs = new ArrayList<>();
            List<DsaResultMeetingLogDTO> dsaResultMeetingLogDTOs = new ArrayList<>();
            if (!validateBeforeAssigne()){
                return;
            }
            if(FacesContext.getCurrentInstance().isValidationFailed())
                return;
            UserDTO tlUserDTO = userService.findByUserName(getUsername());
            UserDTO dsaUserDTO = userService.findByUserName(assigneUser);
            if (DataUtil.isNullObject(tlUserDTO)){
                reportErrorGrowl(null, "distribute.meeting.msg.assigned.user.empty");
                return;
            }
            if (DataUtil.isNullObject(dsaUserDTO)){
                reportErrorGrowl(null, "distribute.meeting.msg.assigne.user.empty");
                return;
            }
            DsaResultMeetingLogDTO dsaResultMeetingLogDTO;
            for (DsaResultMeetingDTO dsaResultMeetingDTO:lazyLoadDsaResultMeeting.getSelectedList()){
                dsaResultMeetingLogDTO = new DsaResultMeetingLogDTO();

                dsaResultMeetingDTO.setAssignedUser(getUsername());
                dsaResultMeetingDTO.setAssigned(1l);
                dsaResultMeetingDTO.setAssignedDate(new Date());
                dsaResultMeetingDTO.setTlDsaId(tlUserDTO.getUserId());
                dsaResultMeetingDTO.setTlDsaCode(getUsername());
                dsaResultMeetingDTO.setDsaId(dsaUserDTO.getUserId());
                dsaResultMeetingDTO.setDsaCode(DataUtil.safeToUpper(assigneUser));
//                dsaResultMeetingDTO.setReturnCca(0l);
                if (!DataUtil.isNullObject(dsaResultMeetingDTO.getMeetingResult())){
                    if (DataUtil.safeEqual(dsaResultMeetingDTO.getMeetingResult(),9) || DataUtil.safeEqual(dsaResultMeetingDTO.getMeetingResult(),11)){
                        dsaResultMeetingDTO.setMeetingResult(2l);
                    }
                    if (DataUtil.safeEqual(dsaResultMeetingDTO.getMeetingResult(),10)){
                        dsaResultMeetingDTO.setMeetingResult(5l);
                        dsaResultMeetingDTO.setMeetingReason(11l);
                        dsaResultMeetingDTO.setMeetingReasonName(convertOptionSetByCodeAndValue(Const.AP_DOMAIN.SALE_DSA_REASON,DataUtil.safeToString(dsaResultMeetingDTO.getMeetingReason())));
                    }
                    dsaResultMeetingDTO.setMeetingResultName(convertOptionSetByCodeAndValue(Const.AP_DOMAIN.SALE_DSA_MEETING_RESULT,DataUtil.safeToString(dsaResultMeetingDTO.getMeetingResult())));
                }

                dsaResultMeetingDTO.setUpdatedUser(getUsername());
                dsaResultMeetingDTO.setUpdatedDate(new Date());

                dsaResultMeetingLogDTO.setDsaResultMeetingId(dsaResultMeetingDTO.getId());
                dsaResultMeetingLogDTO.setTlDsaCode(getUsername());
                dsaResultMeetingLogDTO.setDsaCode(DataUtil.safeToUpper(assigneUser));
                if (!DataUtil.isNullObject(dsaResultMeetingDTO.getMeetingResult())){
                    if (DataUtil.safeEqual(dsaResultMeetingDTO.getMeetingResult(),9) || DataUtil.safeEqual(dsaResultMeetingDTO.getMeetingResult(),11)){
                        dsaResultMeetingLogDTO.setMeetingResult(2l);
                    }
                    if (DataUtil.safeEqual(dsaResultMeetingDTO.getMeetingResult(),10)){
                        dsaResultMeetingLogDTO.setMeetingResult(5l);
                        dsaResultMeetingLogDTO.setMeetingReason(11l);
                    }
                }
                dsaResultMeetingLogDTO.setProvinceMeetingId(dsaResultMeetingDTO.getProvinceMeetingCode());
                dsaResultMeetingLogDTO.setDistrictMeetingId(dsaResultMeetingDTO.getDistrictMeetingCode());
                dsaResultMeetingLogDTO.setMeetingDate(dsaResultMeetingDTO.getMeetingDate());
                dsaResultMeetingLogDTO.setDsaNote(dsaResultMeetingDTO.getDsaNote());
                dsaResultMeetingLogDTO.setAcceptStatus(dsaResultMeetingDTO.getAcceptStatus());
                dsaResultMeetingLogDTO.setKeepStatus(dsaResultMeetingDTO.getKeepStatus());
                dsaResultMeetingLogDTO.setReturnCca(dsaResultMeetingDTO.getReturnCca());
                dsaResultMeetingLogDTO.setProductType(dsaResultMeetingDTO.getProductType());
                dsaResultMeetingLogDTO.setCreatedUser(getUsername());
                dsaResultMeetingLogDTO.setCreatedDate(new Date());

                dsaResultMeetingDTOs.add(dsaResultMeetingDTO);
                dsaResultMeetingLogDTOs.add(dsaResultMeetingLogDTO);
            }
            distributeMeetingService.createOrUpdateWithBatchs(dsaResultMeetingDTOs);
            dsaResultMeetingLogService.createOrUpdateWithBatchs(dsaResultMeetingLogDTOs);
            reportSuccessGrowl(null, "distribute.meeting.msg.assigne.success", DataUtil.safeToUpper(assigneUser) ,lazyLoadDsaResultMeeting.getSelectedList().size() + "");
            lazyLoadDsaResultMeeting.getSelectedList().clear();
        } catch (Exception e) {
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

            DsaResultMeetingSearchDTO inputSearchUI = (DsaResultMeetingSearchDTO)lazyLoadDsaResultMeeting.getRequestSearchDTO();

            DsaResultMeetingSearchDTO inputSearchExport = new DsaResultMeetingSearchDTO();
            org.springframework.beans.BeanUtils.copyProperties(inputSearchUI, inputSearchExport);

            inputSearchExport.setFirst(0);
            inputSearchExport.setPageSize(Integer.MAX_VALUE);
            List<DsaResultMeetingDTO> lstData = distributeMeetingService.load(inputSearchExport).getData();

            logger.info(location + " size = " + lstData.size());

            bean.setTemplateName(TEMPLATE_FILE_NAME);
            bean.putValue("lstData", lstData);
            bean.putValue("province", getProvinceNameByCode(inputSearchExport.getProvinceMeetingCode()));
            String districtNames = "";
            if (!DataUtil.isNullOrEmpty(inputSearchExport.getDistrictMeetingCodes())){
                for (String districtId: inputSearchExport.getDistrictMeetingCodes()) {
                    districtNames = districtNames + getDistrictNameByCode(districtId)+ ";" ;
                }
            }
            bean.putValue("district", districtNames);
            if (!DataUtil.isNullObject(inputSearchExport.getAssigned()) && DataUtil.safeEqual(inputSearchExport.getAssigned(),1l)) {
                bean.putValue("meetingAssigne", getText("distribute.meeting.assigned.status.label"));
            }else if (!DataUtil.isNullObject(inputSearchExport.getAssigned()) && DataUtil.safeEqual(inputSearchExport.getAssigned(),0l)){
                bean.putValue("meetingAssigne", getText("distribute.meeting.not.assigne.label.status"));
            }
            if (!DataUtil.isNullObject(inputSearchExport.getAssigned()) && DataUtil.safeEqual(inputSearchExport.getTypeMeeting(),1l)) {
                bean.putValue("meetingType", getText("distribute.meeting.type.old.status.label"));
            }else if (!DataUtil.isNullObject(inputSearchExport.getAssigned()) && DataUtil.safeEqual(inputSearchExport.getTypeMeeting(),0l)){
                bean.putValue("meetingType", getText("distribute.meeting.type.new.status.label"));
            }
            bean.putValue("fromDateCall", inputSearchExport.getFromCallDate());
            bean.putValue("toDateCall", inputSearchExport.getToCallDate());
            bean.putValue("meetingDate", inputSearchExport.getMeetDate());
            bean.putValue("assigneDate", inputSearchExport.getAssignDate());

            String dsas = "";
            if (!DataUtil.isNullOrEmpty(inputSearchExport.getDsaCodes())){
                for (String dsa: inputSearchExport.getDsaCodes()) {
                    dsas = dsas  + dsa+ ";";
                }
            }
            bean.putValue("dsa", dsas);
            String meetingResults = "";
            if (!DataUtil.isNullOrEmpty(inputSearchExport.getMeetingResults())){
                for (String meetingResult: inputSearchExport.getMeetingResults()) {
                    meetingResults = meetingResults  + convertOptionSetByCodeAndValue(Const.AP_DOMAIN.SALE_DSA_MEETING_RESULT,DataUtil.safeToString(meetingResult))+ ";";
                }
            }
            bean.putValue("meetingStatus", meetingResults);

            Workbook workbook = FileUtil.exportWorkBook(bean);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String exportFileName = BASE_FILE_NAME_EXPORT + "_" +DateUtil.parseDateToString(new Date(), "yyyyMMddHHmmSS") + ".xlsx";
            logger.info(location + " exportFileName = " + exportFileName);
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=" + exportFileName);
            externalContext.setResponseContentType("application/vnd.ms-excel");
            workbook.write(externalContext.getResponseOutputStream());
            facesContext.getResponseComplete();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void getListDistrictByProvinceId(String provinceId){
        try {
            if (!DataUtil.isNullOrEmpty(provinceId)) {
                lstDistricts = areaService.findDistrictByProvinceCode(provinceId);
            }else {
                lstDistricts = new ArrayList<>();
            }
            listCheckBoxLabel.put(getText("distribute.meeting.district.label"), getText("common.label.choose"));
        }  catch (Exception e){
            logger.error(e.getMessage(),e);
            reportErrorGrowl(null, "common.error.happened");
        }
    }

    public void validateBeforeDelete(){
        if(DataUtil.isNullOrEmpty(lazyLoadDsaResultMeeting.getSelectedList())){
            reportErrorGrowl(null, "apConfig.msg.delete.empty");
        }
    }

    public void prepareShowHistoryMeeting(DsaResultMeetingDTO dsaResultMeetingDTO){
        isCreateState = false;
        try {
            if (!DataUtil.isNullOrEmpty(dsaResultMeetingLogDTOs)){
                dsaResultMeetingLogDTOs.clear();
            }
            dsaResultMeetingLogDTOs = dsaResultMeetingLogService.findByDsaResultMeeting(dsaResultMeetingDTO.getId());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            reportErrorGrowl(null, "common.error.happened");
        }
    }

    public void prepareUpdate(DsaResultMeetingDTO dsaResultMeetingDTO){
        isCreateState = false;
        try {
            currentDsaResultMeetingDTO = DataUtil.cloneBean(dsaResultMeetingDTO);
            if (!DataUtil.isNullOrEmpty(currentDsaResultMeetingDTO.getProvinceMeetingCode())){
                getListDistrictByProvinceId(currentDsaResultMeetingDTO.getProvinceMeetingCode());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            reportErrorGrowl(null, "common.error.happened");
        }
    }

    public void prepareSearchResultBO(DsaResultMeetingDTO dsaResultMeetingDTO){
        try {
            boCheckupResultDTO = boCheckupResultService.findByDsaResultMeeting(dsaResultMeetingDTO.getId());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            reportErrorGrowl(null, "common.error.happened");
        }
    }

    public void downloadFileAtt(String prefixFolder,String fileName) {
        try {
            Path path = Paths.get(PREFIX_FOLDER_DSA+prefixFolder+fileName);
            byte[] fileBytes = Files.readAllBytes(path);
            InputStream stream = new ByteArrayInputStream(fileBytes);
            downloadFile = new DefaultStreamedContent(stream, findTailFile(fileName), fileName);
        } catch (Exception ex) {
            reportErrorGrowl(null, "common.error.happened");
        }
    }

    public void doPreImport() {
        try {
            if (!DataUtil.isNullOrEmpty(listError)){
                listError.clear();
            }
            fileNameImport = "";
            RequestContext.getCurrentInstance().execute("PF('dlgImport').show();");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void validateBeforImport() {
    }

    public boolean validate() {
        return true;
    }

    public void importResultMeeting() {
        try {
            BaseMessage message = validateFileUploadWhiteList(uploadedFileImport, ALLOW_EXTENSION_TEMPLATE_XLS, MAX_SIZE_5M);
            if (!message.isSuccess()) {
                contentByteImport = null;
                fileNameImport = null;
                LogicException ex = new LogicException(message.getErrorCode(), message.getKeyMsg());
                throw ex;
            }
            if (validate()) {
                List<DsaResultMeetingDTO> dsaResultMeetingDTOS = readExcelToListMeeting(contentByteImport);
                if (DataUtil.isNullOrEmpty(dsaResultMeetingDTOS)) {
                    reportError("", "", "import.dsa.input.meeting.not.success");
                } else {
                    BaseMessage msg = new BaseMessage();
                    if (msg.isSuccess()) {
                        reportSuccess("", "import.dsa.input.meeting.success", (dsaResultMeetingDTOS.size()) + "/" + (dsaResultMeetingDTOS.size() + listError.size()));
                    } else {
                        reportError("", msg.getKeyMsg(),getText("common.error.happened"));
                    }
                }
            }
        } catch (LogicException ex) {
            logger.error(ex.getMessage(), ex);
            reportErrorGrowl(null, ex.getMessage());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            reportErrorGrowl(null, ex.getMessage());
        }
    }

    private List<DsaResultMeetingDTO> readExcelToListMeeting(byte[] content) throws LogicException, Exception {
        List<DsaResultMeetingDTO> dsaResultMeetingDTOS = new ArrayList<>();
        List<String> resultMeetingIds = new ArrayList<>();
        if (!DataUtil.isNullOrEmpty(meetingResults)) {
            resultMeetingIds = meetingResults.stream().map(OptionSetValueDTO::getValue).collect(Collectors.toList());
        }
        return dsaResultMeetingDTOS;
    }


    @Secured("@")
    public void fileUploadImportAction(FileUploadEvent event) {
        try {
            uploadedFileImport = event.getFile();
            BaseMessage message = validateFileUploadWhiteList(uploadedFileImport, ALLOW_EXTENSION_TEMPLATE_XLS, MAX_SIZE_5M);
            if (!message.isSuccess()) {
                fileNameImport = null;
                contentByteImport = null;
                LogicException ex = new LogicException(message.getErrorCode(), message.getKeyMsg());
                focusElementByRawCSSSlector(".outputAttachFile");
                throw ex;
            }
            listError = Lists.newArrayList();
            fileNameImport = uploadedFileImport.getFileName();
            contentByteImport = uploadedFileImport.getContents();

            ExcellUtil ex = new ExcellUtil(uploadedFileImport, contentByteImport);
            Sheet sheet = ex.getSheetAt(0);
            int totalRows = sheet.getPhysicalNumberOfRows();
            if (totalRows > MAX_ROW_IMPORT) {
                listError = Lists.newArrayList();
                throw new LogicException("", "distribute.meeting.input.meeting.delete.maxline", DataUtil.safeToString(MAX_ROW_IMPORT));
            }
        }  catch (LogicException ex) {
            logger.error(ex.getMessage(), ex);
            reportErrorGrowl(null, ex.getMessage());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            reportErrorGrowl(null, "common.error.happened");
        }
    }

    @Secured("@")
    public void downloadFileTemplate() {
        try {
            String fileDownload = Faces.getRealPath("/resources/templates/"+FOLDER_PREFIX+FILE_IMPORT_TEMPLATE_PATH);
            Path path = Paths.get(fileDownload);
            byte[] fileBytes = Files.readAllBytes(path);
            InputStream stream = new ByteArrayInputStream(fileBytes);
            downloadFileTemplate = new DefaultStreamedContent(stream, findTailFile(FILE_IMPORT_TEMPLATE_PATH), FILE_IMPORT_TEMPLATE_PATH);
            return;
        } catch (Exception ex) {
            reportErrorGrowl(null, "common.error.happened");
        }
    }

    @Secured("@")
    public void downloadFileError() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + "IMPORT_DSA_TEMPLATE_MEETING_TEMPLATE_ERROR_"+ DateUtil.date2ddMMyyyyHHMMssNoSlash(new Date())+".xls" + "\"");
            externalContext.setResponseContentType("application/vnd.ms-excel");
            bookError.write(externalContext.getResponseOutputStream());
            facesContext.responseComplete();
            return;
        } catch (Exception ex) {
            reportErrorGrowl(null, "common.error.happened");
        }
    }

    public void delete(){
        validateBeforeDelete();
        if(FacesContext.getCurrentInstance().isValidationFailed())
            return;
        try {
            reportSuccessGrowl(null, "creditscore.msg.delete.success", lazyLoadDsaResultMeeting.getSelectedList().size() + "");
            lazyLoadDsaResultMeeting.getSelectedList().clear();
        } catch (Exception e) {
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

    public String getHeaderDialog() {
        return isCreateState ? getText("distribute.meeting.title.dlg.assigne") : getText("distribute.meeting.title.dlg.history");
    }

    public boolean isCreateState() {
        return isCreateState;
    }

    public void setCreateState(boolean createState) {
        isCreateState = createState;
    }

    public SnDataModel<DsaResultMeetingDTO> getLazyLoadDsaResultMeeting() {
        return lazyLoadDsaResultMeeting;
    }

    public void setLazyLoadDsaResultMeeting(SnDataModel<DsaResultMeetingDTO> lazyLoadDsaResultMeeting) {
        this.lazyLoadDsaResultMeeting = lazyLoadDsaResultMeeting;
    }

    public List<AreaDTO> getLstProvinces() {
        return lstProvinces;
    }

    public void setLstProvinces(List<AreaDTO> lstProvinces) {
        this.lstProvinces = lstProvinces;
    }

    public List<AreaDTO> getLstDistricts() {
        return lstDistricts;
    }

    public void setLstDistricts(List<AreaDTO> lstDistricts) {
        this.lstDistricts = lstDistricts;
    }

    public List<UserDTO> getLstDSAs() {
        return lstDSAs;
    }

    public void setLstDSAs(List<UserDTO> lstDSAs) {
        this.lstDSAs = lstDSAs;
    }

    public String getAssigneUser() {
        return assigneUser;
    }

    public void setAssigneUser(String assigneUser) {
        this.assigneUser = assigneUser;
    }

    public List<OptionSetValueDTO> getMeetingResults() {
        return meetingResults;
    }

    public void setMeetingResults(List<OptionSetValueDTO> meetingResults) {
        this.meetingResults = meetingResults;
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

    public Map<String, String> getListCheckBoxLabel() {
        return listCheckBoxLabel;
    }

    public void setListCheckBoxLabel(Map<String, String> listCheckBoxLabel) {
        this.listCheckBoxLabel = listCheckBoxLabel;
    }

    public String getFileNameImport() {
        return fileNameImport;
    }

    public void setFileNameImport(String fileNameImport) {
        this.fileNameImport = fileNameImport;
    }

    public UploadedFile getUploadedFileImport() {
        return uploadedFileImport;
    }

    public void setUploadedFileImport(UploadedFile uploadedFileImport) {
        this.uploadedFileImport = uploadedFileImport;
    }

    public List<String[]> getListError() {
        return listError;
    }

    public void setListError(List<String[]> listError) {
        this.listError = listError;
    }

    public byte[] getContentByteImport() {
        return contentByteImport;
    }

    public void setContentByteImport(byte[] contentByteImport) {
        this.contentByteImport = contentByteImport;
    }

    public Workbook getBookError() {
        return bookError;
    }

    public void setBookError(Workbook bookError) {
        this.bookError = bookError;
    }

    public StreamedContent getDownloadFileTemplate() {
        return downloadFileTemplate;
    }

    public void setDownloadFileTemplate(StreamedContent downloadFileTemplate) {
        this.downloadFileTemplate = downloadFileTemplate;
    }

    public Date getMeetingDateUpdate() {
        return meetingDateUpdate;
    }

    public void setMeetingDateUpdate(Date meetingDateUpdate) {
        this.meetingDateUpdate = meetingDateUpdate;
    }

    public DsaResultMeetingDTO getCurrentDsaResultMeetingDTO() {
        return currentDsaResultMeetingDTO;
    }

    public void setCurrentDsaResultMeetingDTO(DsaResultMeetingDTO currentDsaResultMeetingDTO) {
        this.currentDsaResultMeetingDTO = currentDsaResultMeetingDTO;
    }

    public boolean isRenderBtnUpdate() {
        return renderBtnUpdate;
    }

    public void setRenderBtnUpdate(boolean renderBtnUpdate) {
        this.renderBtnUpdate = renderBtnUpdate;
    }

    public boolean isRenderBtnUpdateMeetingDate() {
        return renderBtnUpdateMeetingDate;
    }

    public void setRenderBtnUpdateMeetingDate(boolean renderBtnUpdateMeetingDate) {
        this.renderBtnUpdateMeetingDate = renderBtnUpdateMeetingDate;
    }

    public boolean isRenderBtnAssigne() {
        return renderBtnAssigne;
    }

    public void setRenderBtnAssigne(boolean renderBtnAssigne) {
        this.renderBtnAssigne = renderBtnAssigne;
    }

    public List<OptionSetValueDTO> getCcaAcceptStatus() {
        return ccaAcceptStatus;
    }

    public void setCcaAcceptStatus(List<OptionSetValueDTO> ccaAcceptStatus) {
        this.ccaAcceptStatus = ccaAcceptStatus;
    }

    public BoCheckupResultDTO getBoCheckupResultDTO() {
        return boCheckupResultDTO;
    }

    public void setBoCheckupResultDTO(BoCheckupResultDTO boCheckupResultDTO) {
        this.boCheckupResultDTO = boCheckupResultDTO;
    }
}
