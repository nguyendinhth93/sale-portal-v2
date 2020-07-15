package com.tp.controller;

import com.tp.common.custom.SnDataModel;
import com.tp.dto.*;
import com.tp.dto.admin.UserDTO;
import com.tp.service.*;
import com.tp.util.*;
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
public class UpdateMeetingController extends BaseController{

    final static Logger logger = LoggerFactory.getLogger(UpdateMeetingController.class);

    private static final String TEMPLATE_FILE_NAME          = "DISTRIBUTE_MEETING_TEMPLATE.xlsx";
    private static final String BASE_FILE_NAME_EXPORT       = "resultMeeting";

    @Autowired
    private UpdateMeetingService updateMeetingService;
    @Autowired
    private DsaResultMeetingLogService dsaResultMeetingLogService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private OptionSetValueService optionSetValueService;
    @Autowired
    private PartnerService partnerService;
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
    private List<OptionSetValueDTO> productTypes;
    private Map<String, String> listCheckBoxLabel = new ConcurrentHashMap<String, String>();
    private DsaResultMeetingDTO currentDsaResultMeetingDTO;
    private BoCheckupResultDTO boCheckupResultDTO;
    private StreamedContent downloadFile;
    private List<PartnerDTO> partnerDTOs;
    private List<UserDTO> userDTOs;
    private String assigneUser;
    private boolean renderBtnAssigne;

    @PostConstruct
    @Override
    public void init(){
        try {
            DsaResultMeetingSearchDTO dsaResultMeetingSearchDTO = new DsaResultMeetingSearchDTO();
            if (hasPermission("ROLE_TL_CCA")){
                dsaResultMeetingSearchDTO.setJtlCcaCode(getUsername());
            }else if(hasPermission("ROLE_CCA")){
                dsaResultMeetingSearchDTO.setCcaCode(getUsername());
            }
            userDTOs = userService.findAllCCa();
            dsaResultMeetingSearchDTO.setFromMeetDate(new Date());
            Calendar calToDate = Calendar.getInstance();
            calToDate.add(Calendar.DAY_OF_MONTH,1);
            dsaResultMeetingSearchDTO.setToMeetDate(calToDate.getTime());
            lazyLoadDsaResultMeeting = new SnDataModel<>(updateMeetingService, dsaResultMeetingSearchDTO);
            lstProvinces = areaService.findAllProvince();
            meetingResults = optionSetValueService.findByActionCode(Const.AP_DOMAIN.SALE_DSA_MEETING_RESULT);
            ccaAcceptStatus = optionSetValueService.findByActionCode(Const.AP_DOMAIN.SALE_CCA_ACCEPT_STATUS);
            productTypes = optionSetValueService.findByActionCode(Const.AP_DOMAIN.SALE_PRODUCT_TYPE);
            partnerDTOs = partnerService.findAllPartner();
            if (hasPermission("ROLE_TL_CCA")){
                renderBtnAssigne = true;
            }
            listCheckBoxLabel.put(getText("distribute.meeting.district.label"), getText("common.label.choose"));
            listCheckBoxLabel.put(getText("update.meeting.status.label"), getText("common.label.choose"));
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            reportErrorGrowl(null, "common.error.happened");
        }
    }

    public boolean validateBeforeUpdate() throws Exception {
        if (DataUtil.safeEqual(currentDsaResultMeetingDTO.getAcceptStatus(),1) || DataUtil.safeEqual(currentDsaResultMeetingDTO.getAcceptStatus(),5)){
            if (!DateUtil.compareDateOnly(currentDsaResultMeetingDTO.getMeetingDate(),new Date())){
                reportErrorGrowl(null, "update.meeting.product.err.status.accept.now.label");
                return false;
            }
        }else if (DataUtil.safeEqual(currentDsaResultMeetingDTO.getAcceptStatus(),3)|| DataUtil.safeEqual(currentDsaResultMeetingDTO.getAcceptStatus(),4)){
            if (currentDsaResultMeetingDTO.getMeetingDate().compareTo(new Date())< 0 ){
                reportErrorGrowl(null, "update.meeting.product.err.status.accept.not.full.doc.label");
                return false;
            }
        }
        return true;
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
            currentDsaResultMeetingDTO.setMeetingResult(null);
            currentDsaResultMeetingDTO.setMeetingResultName(null);
            currentDsaResultMeetingDTO.setMeetingReason(null);
            currentDsaResultMeetingDTO.setMeetingResultName(null);
            currentDsaResultMeetingDTO.setReturnCca(0l);
            currentDsaResultMeetingDTO.setReturnCcaDate(new Date());
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
            dsaResultMeetingLogDTO.setReturnCca(0l);
            dsaResultMeetingLogDTO.setReturnCcaDate(new Date());
            dsaResultMeetingLogDTO.setProductType(currentDsaResultMeetingDTO.getProductType());
            updateMeetingService.createOrUpdate(currentDsaResultMeetingDTO);
            dsaResultMeetingLogService.createOrUpdate(dsaResultMeetingLogDTO);
            reportSuccessGrowl(null, getTextParams("update.meeting.msg.update.meeting.success",currentDsaResultMeetingDTO.getCustomerName(),currentDsaResultMeetingDTO.getCustomerPhone()));
            RequestContext.getCurrentInstance().execute("PF('dlgUpdateMeeting').hide()");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }

    }

    public boolean validateBeforeAssigne() throws Exception {
        if(DataUtil.isNullOrEmpty(lazyLoadDsaResultMeeting.getSelectedList())){
            reportErrorGrowl(null, "distribute.meeting.msg.assigne.empty");
            return false;
        }
        return true;
    }

    public void assigne(){
        try {
            List<DsaResultMeetingDTO> dsaResultMeetingDTOs = new ArrayList<>();
            List<DsaResultMeetingLogDTO> dsaResultMeetingLogDTOs = new ArrayList<>();
            if (!validateBeforeAssigne()){
                return;
            }
            DsaResultMeetingLogDTO dsaResultMeetingLogDTO;
            for (DsaResultMeetingDTO dsaResultMeetingDTO:lazyLoadDsaResultMeeting.getSelectedList()){
                dsaResultMeetingLogDTO = new DsaResultMeetingLogDTO();

                dsaResultMeetingDTO.setCcaCode(DataUtil.safeToUpper(assigneUser));
                dsaResultMeetingDTO.setUpdatedUser(getUsername());
                dsaResultMeetingDTO.setUpdatedDate(new Date());

                dsaResultMeetingLogDTO.setDsaResultMeetingId(dsaResultMeetingDTO.getId());
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
            updateMeetingService.createOrUpdateWithBatchs(dsaResultMeetingDTOs);
            dsaResultMeetingLogService.createOrUpdateWithBatchs(dsaResultMeetingLogDTOs);
            reportSuccessGrowl(null, "update.meeting.msg.assigne.success", DataUtil.safeToUpper(assigneUser) ,lazyLoadDsaResultMeeting.getSelectedList().size() + "");
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
            List<DsaResultMeetingDTO> lstData = updateMeetingService.load(inputSearchExport).getData();

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
            if (DataUtil.isNullObject(currentDsaResultMeetingDTO.getMeetingDate())){
                currentDsaResultMeetingDTO.setMeetingDate(currentDsaResultMeetingDTO.getCcaMeetingDate());
            }
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

    public boolean validate() {
        return true;
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

    public List<OptionSetValueDTO> getMeetingResults() {
        return meetingResults;
    }

    public void setMeetingResults(List<OptionSetValueDTO> meetingResults) {
        this.meetingResults = meetingResults;
    }

    public Map<String, String> getListCheckBoxLabel() {
        return listCheckBoxLabel;
    }

    public void setListCheckBoxLabel(Map<String, String> listCheckBoxLabel) {
        this.listCheckBoxLabel = listCheckBoxLabel;
    }

    public DsaResultMeetingDTO getCurrentDsaResultMeetingDTO() {
        return currentDsaResultMeetingDTO;
    }

    public void setCurrentDsaResultMeetingDTO(DsaResultMeetingDTO currentDsaResultMeetingDTO) {
        this.currentDsaResultMeetingDTO = currentDsaResultMeetingDTO;
    }

    public StreamedContent getDownloadFile() {
        return downloadFile;
    }

    public void setDownloadFile(StreamedContent downloadFile) {
        this.downloadFile = downloadFile;
    }

    public List<DsaResultMeetingLogDTO> getDsaResultMeetingLogDTOs() {
        return dsaResultMeetingLogDTOs;
    }

    public void setDsaResultMeetingLogDTOs(List<DsaResultMeetingLogDTO> dsaResultMeetingLogDTOs) {
        this.dsaResultMeetingLogDTOs = dsaResultMeetingLogDTOs;
    }

    public List<OptionSetValueDTO> getCcaAcceptStatus() {
        return ccaAcceptStatus;
    }

    public void setCcaAcceptStatus(List<OptionSetValueDTO> ccaAcceptStatus) {
        this.ccaAcceptStatus = ccaAcceptStatus;
    }

    public List<PartnerDTO> getPartnerDTOs() {
        return partnerDTOs;
    }

    public void setPartnerDTOs(List<PartnerDTO> partnerDTOs) {
        this.partnerDTOs = partnerDTOs;
    }

    public List<OptionSetValueDTO> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(List<OptionSetValueDTO> productTypes) {
        this.productTypes = productTypes;
    }

    public List<UserDTO> getUserDTOs() {
        return userDTOs;
    }

    public void setUserDTOs(List<UserDTO> userDTOs) {
        this.userDTOs = userDTOs;
    }

    public String getAssigneUser() {
        return assigneUser;
    }

    public void setAssigneUser(String assigneUser) {
        this.assigneUser = assigneUser;
    }

    public boolean isRenderBtnAssigne() {
        return renderBtnAssigne;
    }

    public void setRenderBtnAssigne(boolean renderBtnAssigne) {
        this.renderBtnAssigne = renderBtnAssigne;
    }

    public BoCheckupResultDTO getBoCheckupResultDTO() {
        return boCheckupResultDTO;
    }

    public void setBoCheckupResultDTO(BoCheckupResultDTO boCheckupResultDTO) {
        this.boCheckupResultDTO = boCheckupResultDTO;
    }
}
