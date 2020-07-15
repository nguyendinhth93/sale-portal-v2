package com.tp.controller;

import com.tp.common.custom.SnDataModel;
import com.tp.common.exception.LogicException;
import com.tp.dto.*;
import com.tp.dto.admin.UserDTO;
import com.tp.service.*;
import com.tp.util.Const;
import com.tp.util.DataUtil;
import com.tp.util.DateUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by DINH NGUYEN on 20/07/2017.
 */
@ManagedBean
@Component
@Scope("view")
public class InputMeetingController extends BaseController{

    final static Logger logger = LoggerFactory.getLogger(InputMeetingController.class);

    @Autowired
    private InputMeetingService inputMeetingService;
    @Autowired
    private DistributeMeetingService distributeMeetingService;
    @Autowired
    private DsaResultMeetingLogService dsaResultMeetingLogService;
    @Autowired
    private DsaResultMeetingFileService dsaResultMeetingFileService;
    @Autowired
    private DsaResultMeetingService dsaResultMeetingService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private OptionSetValueService optionSetValueService;
    @Autowired
    private BoCheckupResultService boCheckupResultService;

    @Value("${DSA_PREFIX_FOLDER_SYSTEM_FILE_UPLOAD}")
    private String PREFIX_FOLDER_DSA;

    @Value("${DSA_SUFFIX_FOLDER_SYSTEM_FILE_UPLOAD}")
    private String SUFFIX_FOLDER_DSA;

    private DsaResultMeetingDTO currentDsaResultMeeting;
    private boolean isCreateState;
    private SnDataModel<DsaResultMeetingDTO> lazyLoadDsaResultMeeting;
    private List<AreaDTO> areaDTOs;
    private List<OptionSetValueDTO> meetingResults;
    private List<OptionSetValueDTO> reasonLists;
    private List<OptionSetValueDTO> ccaAcceptStatus;
    private UploadedFile uploadedFile;
    private boolean renderBtnSubmit;
    private boolean checkDisableMeetingDate;
    private byte[] contentByte;
    private String fileName;
    private List<AreaDTO> lstProvinces;
    private List<AreaDTO> lstDistricts;
    private List<AreaDTO> lstDistrictDsas;
    private boolean renderCcaNoteFlag;


    @PostConstruct
    @Override
    public void init(){
        try {
            areaDTOs = areaService.findAllProvince();
            DsaResultMeetingSearchDTO dsaResultMeetingSearchDTO = new DsaResultMeetingSearchDTO();
            dsaResultMeetingSearchDTO.setDsaCode(getUsername());
            lazyLoadDsaResultMeeting = new SnDataModel<>(inputMeetingService, dsaResultMeetingSearchDTO);
            meetingResults = optionSetValueService.findByActionCode(Const.AP_DOMAIN.SALE_DSA_MEETING_RESULT);
            ccaAcceptStatus = optionSetValueService.findByActionCode(Const.AP_DOMAIN.SALE_CCA_ACCEPT_STATUS);
            lstProvinces = areaService.findAllProvince();
            lstDistrictDsas = inputMeetingService.findAllDistrictByDsa(getUsername());
            renderCcaNoteFlag = true;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            reportErrorGrowl(null, "common.error.happened");
        }
    }

    public void prepareToShowUpdate(DsaResultMeetingDTO dsaResultMeetingDTO){
        currentDsaResultMeeting = new DsaResultMeetingDTO();
        try {
            BeanUtils.copyProperties(currentDsaResultMeeting, dsaResultMeetingDTO);
            if (DataUtil.isNullObject(currentDsaResultMeeting.getMeetingDate())){
                currentDsaResultMeeting.setMeetingDate(currentDsaResultMeeting.getCcaMeetingDate());
            }
            isCreateState = false;
            fileName = "";
            contentByte = null;
            if (DataUtil.safeEqual(currentDsaResultMeeting.getMeetingResult(),1) || checkTimeSubmit() == false){
                renderBtnSubmit = true;
            }else{
                renderBtnSubmit = false;
            }

            if (!DataUtil.isNullOrEmpty(currentDsaResultMeeting.getProvinceMeetingCode())){
                getListDistrictByProvinceId(currentDsaResultMeeting.getProvinceMeetingCode());
            }
            if (!DataUtil.isNullObject(currentDsaResultMeeting.getMeetingResult())){
                updateReasonByMeetingResult(DataUtil.safeToString(currentDsaResultMeeting.getMeetingResult()));
            }
            if (DataUtil.safeEqual(currentDsaResultMeeting.getMeetingResult(),2)){
                checkDisableMeetingDate = false;
            }else {
                checkDisableMeetingDate = true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }
    }

    public void prepareToShowUpdateNoteDsa(DsaResultMeetingDTO dsaResultMeetingDTO){
        currentDsaResultMeeting = new DsaResultMeetingDTO();
        try {
            BeanUtils.copyProperties(currentDsaResultMeeting, dsaResultMeetingDTO);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }
    }

    public boolean checkTimeSubmit(){
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        if (hour > 19 || hour < 7){
            return false;
        }
        return true;
    }

    public boolean validateBeforeSave() throws Exception {

        if (DataUtil.safeEqual(currentDsaResultMeeting.getProvinceMeetingCode(),-1)){
            reportErrorGrowl(null, "distribute.meeting.msg.province.req");
            return false;
        }

        if (DataUtil.safeEqual(currentDsaResultMeeting.getDistrictMeetingCode(),-1)){
            reportErrorGrowl(null, "distribute.meeting.msg.district.req");
            return false;
        }

        if (DataUtil.safeEqual(currentDsaResultMeeting.getMeetingResult(),-1)){
            reportErrorGrowl(null, "input.meeting.meeting.result.req.mess");
            return false;
        }

        if (DataUtil.safeEqual(currentDsaResultMeeting.getMeetingResult(),-1)){
            reportErrorGrowl(null, "input.meeting.meeting.result.req.mess");
            return false;
        }

        if (DataUtil.safeEqual(currentDsaResultMeeting.getMeetingResult(),1)){
            if (DataUtil.isNullObject(contentByte)){
                reportErrorGrowl(null, "distribute.meeting.msg.file.req");
                return false;
            }
        }

        if (DataUtil.safeEqual(currentDsaResultMeeting.getMeetingResult(),2)){
            if (DataUtil.isNullObject(currentDsaResultMeeting.getMeetingDate())){
                reportErrorGrowl(null, "distribute.meeting.msg.meeting.date.req");
                return false;
            }
        }
        if (DataUtil.safeEqual(currentDsaResultMeeting.getMeetingResult(),3)
                ||DataUtil.safeEqual(currentDsaResultMeeting.getMeetingResult(),4)
                ||DataUtil.safeEqual(currentDsaResultMeeting.getMeetingResult(),5)
                ||DataUtil.safeEqual(currentDsaResultMeeting.getMeetingResult(),6)){
            if (DataUtil.isNullObject(currentDsaResultMeeting.getMeetingReason())){
                reportErrorGrowl(null, "distribute.meeting.msg.reason.req");
                return false;
            }
        }

        if (DataUtil.safeEqual(currentDsaResultMeeting.getMeetingResult(),3) && DataUtil.safeEqual(currentDsaResultMeeting.getMeetingReason(),6)){
            if (DataUtil.isNullObject(currentDsaResultMeeting.getDsaNote())){
                reportErrorGrowl(null, "distribute.meeting.msg.note.req");
                return false;
            }
        }

        if (DataUtil.safeEqual(currentDsaResultMeeting.getMeetingResult(),4) && DataUtil.safeEqual(currentDsaResultMeeting.getMeetingReason(),10)){
            if (DataUtil.isNullObject(currentDsaResultMeeting.getDsaNote())){
                reportErrorGrowl(null, "distribute.meeting.msg.note.req");
                return false;
            }
        }

        if (DataUtil.safeEqual(currentDsaResultMeeting.getMeetingResult(),8) && DataUtil.safeEqual(currentDsaResultMeeting.getMeetingReason(),20)){
            if (DataUtil.isNullObject(currentDsaResultMeeting.getDsaNote())){
                reportErrorGrowl(null, "distribute.meeting.msg.note.req");
                return false;
            }
        }

        if (DataUtil.safeEqual(currentDsaResultMeeting.getMeetingResult(),7)){
            if (DataUtil.isNullOrEmpty(currentDsaResultMeeting.getDsaNote())){
                reportErrorGrowl(null, "distribute.meeting.msg.note.req");
                return false;
            }
        }
        return true;
    }

    public void saveOrUpdate(){
        try {
            if(!validateBeforeSave()){
                return;
            }
            byte[] fileSave;
            String fileNameSave = "";

//            if (!DataUtil.isNullObject(currentDsaResultMeeting.getMeetingResult()) && DataUtil.safeEqual(currentDsaResultMeeting.getMeetingResult(),5)){
//                Calendar cal = Calendar.getInstance();
//                cal.add(Calendar.DAY_OF_MONTH,1);
//                currentDsaResultMeeting.setMeetingDate(cal.getTime());
//            }
//
//            if (!DataUtil.isNullObject(currentDsaResultMeeting.getMeetingResult()) && DataUtil.safeEqual(currentDsaResultMeeting.getMeetingResult(),2)){
//                DsaResultMeetingDTO oldDsaResultMeetingDTO = dsaResultMeetingService.findOne(currentDsaResultMeeting.getId());
//                if (!DataUtil.isNullObject(oldDsaResultMeetingDTO) && DateUtil.compareDateOnly(currentDsaResultMeeting.getMeetingDate(),oldDsaResultMeetingDTO.getMeetingDate())) {
//                    Calendar cal = Calendar.getInstance();
//                    cal.add(Calendar.DAY_OF_MONTH,1);
//                    currentDsaResultMeeting.setMeetingDate(cal.getTime());
//                }
//            }

            currentDsaResultMeeting.setMeetingResultName(convertOptionSetByCodeAndValue(Const.AP_DOMAIN.SALE_DSA_MEETING_RESULT,DataUtil.safeToString(currentDsaResultMeeting.getMeetingResult())));
            if (!DataUtil.isNullObject(currentDsaResultMeeting.getMeetingReason())){
                currentDsaResultMeeting.setMeetingReasonName(convertOptionSetByCodeAndValue(Const.AP_DOMAIN.SALE_DSA_REASON,DataUtil.safeToString(currentDsaResultMeeting.getMeetingReason())));
            }

            if (!DataUtil.isNullOrEmpty(currentDsaResultMeeting.getProvinceMeetingCode())){
                AreaDTO areaDTO = areaService.findProvinceByCode(currentDsaResultMeeting.getProvinceMeetingCode());
                if (!DataUtil.isNullObject(areaDTO)){
                    currentDsaResultMeeting.setProvinceMeetingName(areaDTO.getName());
                }
            }

            if (!DataUtil.isNullOrEmpty(currentDsaResultMeeting.getDistrictMeetingCode())){
                AreaDTO areaDTO = areaService.findDistrictByCode(currentDsaResultMeeting.getDistrictMeetingCode());
                if (!DataUtil.isNullObject(areaDTO)){
                    currentDsaResultMeeting.setDistrictMeetingName(areaDTO.getName());
                }
            }

            currentDsaResultMeeting.setUpdatedUser(getUsername());
            currentDsaResultMeeting.setUpdatedDate(new Date());
            distributeMeetingService.createOrUpdate(currentDsaResultMeeting);
            if (DataUtil.safeEqual(currentDsaResultMeeting.getMeetingResult(),1)){
                boCheckupResultService.createOrUpdateFromMeeting(currentDsaResultMeeting);
            }

            DsaResultMeetingLogDTO dsaResultMeetingLogDTO = new DsaResultMeetingLogDTO();
            dsaResultMeetingLogDTO.setDsaResultMeetingId(currentDsaResultMeeting.getId());
            dsaResultMeetingLogDTO.setTlDsaCode(currentDsaResultMeeting.getTlDsaCode());
            dsaResultMeetingLogDTO.setDsaCode(currentDsaResultMeeting.getDsaCode());
            dsaResultMeetingLogDTO.setProvinceMeetingId(currentDsaResultMeeting.getProvinceMeetingCode());
            dsaResultMeetingLogDTO.setDistrictMeetingId(currentDsaResultMeeting.getDistrictMeetingCode());
            dsaResultMeetingLogDTO.setMeetingDate(currentDsaResultMeeting.getMeetingDate());
            dsaResultMeetingLogDTO.setMeetingResult(currentDsaResultMeeting.getMeetingResult());
            dsaResultMeetingLogDTO.setMeetingReason(currentDsaResultMeeting.getMeetingReason());
            dsaResultMeetingLogDTO.setDsaNote(currentDsaResultMeeting.getDsaNote());
            dsaResultMeetingLogDTO.setCreatedDate(new Date());
            dsaResultMeetingLogDTO.setCreatedUser(getUsername());
            dsaResultMeetingLogDTO.setAcceptStatus(currentDsaResultMeeting.getAcceptStatus());
            dsaResultMeetingLogDTO.setKeepStatus(currentDsaResultMeeting.getKeepStatus());
            dsaResultMeetingLogDTO.setReturnCca(currentDsaResultMeeting.getReturnCca());
            dsaResultMeetingLogDTO.setProductType(currentDsaResultMeeting.getProductType());
            dsaResultMeetingLogDTO = dsaResultMeetingLogService.createOrUpdate(dsaResultMeetingLogDTO);

            if (!DataUtil.isNullObject(contentByte)){
                fileSave = contentByte;
                fileNameSave = fileName;
                if (!DataUtil.isNullOrEmpty(fileName)){
                    fileNameSave = getUsername() + "_" + DateUtil.date2yyyyMMddNoSlash(new Date()) + "_" + currentDsaResultMeeting.getCustomerPhone()+"_"+fileNameSave;
                }
                Path pathDirectory = Paths.get(PREFIX_FOLDER_DSA+SUFFIX_FOLDER_DSA+"/"+DateUtil.date2yyyyMMddNoSlash(new Date()));
                Path path = Paths.get(PREFIX_FOLDER_DSA+SUFFIX_FOLDER_DSA+"/"+DateUtil.date2yyyyMMddNoSlash(new Date())+"/"+fileNameSave);
                if (Files.notExists(pathDirectory)){
                    Files.createDirectory(pathDirectory);
                }
                Files.write(path, fileSave);
                DsaResultMeetingFileDTO dsaResultMeetingFileDTO = new DsaResultMeetingFileDTO();
                dsaResultMeetingFileDTO.setFileName(fileNameSave);
                dsaResultMeetingFileDTO.setPrefixFolder(SUFFIX_FOLDER_DSA+"/"+DateUtil.date2yyyyMMddNoSlash(new Date())+"/");
                dsaResultMeetingFileDTO.setCreatedDate(new Date());
                dsaResultMeetingFileDTO.setCreatedUser(getUsername());
                dsaResultMeetingFileDTO.setDsaResultMeetingId(currentDsaResultMeeting.getId());
                dsaResultMeetingFileDTO.setDsaResultMeetingLogId(dsaResultMeetingLogDTO.getId());
                dsaResultMeetingFileService.createOrUpdate(dsaResultMeetingFileDTO);
            }
            reportSuccessGrowl(null, "distribute.meeting.msg.add.update.detail");
            RequestContext.getCurrentInstance().execute("PF('dlgInputMeeting').hide()");
        } catch (LogicException ex){
            reportError(null, ex.getKeyMsg(), ex.getParams());
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }
    }

    public boolean validateBeforeSaveNote() throws Exception {
        if (DataUtil.isNullOrEmpty(currentDsaResultMeeting.getDsaNote())){
            reportErrorGrowl(null, "input.meeting.dsa.note.req.mess");
            return false;
        }
        return true;
    }

    public void saveOrUpdateNote(){
        try {
            if(!validateBeforeSaveNote()){
                return;
            }
            currentDsaResultMeeting.setUpdatedUser(getUsername());
            currentDsaResultMeeting.setUpdatedDate(new Date());
            distributeMeetingService.createOrUpdate(currentDsaResultMeeting);

            DsaResultMeetingLogDTO dsaResultMeetingLogDTO = new DsaResultMeetingLogDTO();
            dsaResultMeetingLogDTO.setDsaResultMeetingId(currentDsaResultMeeting.getId());
            dsaResultMeetingLogDTO.setTlDsaCode(currentDsaResultMeeting.getTlDsaCode());
            dsaResultMeetingLogDTO.setDsaCode(currentDsaResultMeeting.getDsaCode());
            dsaResultMeetingLogDTO.setProvinceMeetingId(currentDsaResultMeeting.getProvinceMeetingCode());
            dsaResultMeetingLogDTO.setDistrictMeetingId(currentDsaResultMeeting.getDistrictMeetingCode());
            dsaResultMeetingLogDTO.setMeetingDate(currentDsaResultMeeting.getMeetingDate());
            dsaResultMeetingLogDTO.setMeetingResult(currentDsaResultMeeting.getMeetingResult());
            dsaResultMeetingLogDTO.setMeetingReason(currentDsaResultMeeting.getMeetingReason());
            dsaResultMeetingLogDTO.setDsaNote(currentDsaResultMeeting.getDsaNote());
            dsaResultMeetingLogDTO.setCreatedDate(new Date());
            dsaResultMeetingLogDTO.setCreatedUser(getUsername());
            dsaResultMeetingLogDTO.setAcceptStatus(currentDsaResultMeeting.getAcceptStatus());
            dsaResultMeetingLogDTO.setKeepStatus(currentDsaResultMeeting.getKeepStatus());
            dsaResultMeetingLogDTO.setReturnCca(currentDsaResultMeeting.getReturnCca());
            dsaResultMeetingLogDTO.setProductType(currentDsaResultMeeting.getProductType());
            dsaResultMeetingLogService.createOrUpdate(dsaResultMeetingLogDTO);

            reportSuccessGrowl(null, "input.meeting.msg.note.dsa.update.detail");
            RequestContext.getCurrentInstance().execute("PF('dlgInputNoteDsa').hide()");
        } catch (LogicException ex){
            reportError(null, ex.getKeyMsg(), ex.getParams());
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }
    }

    public void checkOrUnCheckCcaNoteFlag(){
        try {
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            reportErrorGrowl(null, "common.error.happened");
        }
    }

    public void checkOrUnCheckKeepStatus(DsaResultMeetingDTO dsaResultMeetingDTO){
        try {
            if (DataUtil.isNullObject(dsaResultMeetingDTO.getKeepStatus()) || DataUtil.safeEqual(dsaResultMeetingDTO.getKeepStatus(),0)){
                dsaResultMeetingDTO.setKeepStatus(1l);
            }else{
                dsaResultMeetingDTO.setKeepStatus(0l);
            }
            dsaResultMeetingDTO.setUpdatedUser(getUsername());
            dsaResultMeetingDTO.setUpdatedDate(new Date());
            distributeMeetingService.createOrUpdate(dsaResultMeetingDTO);

            DsaResultMeetingLogDTO dsaResultMeetingLogDTO = new DsaResultMeetingLogDTO();
            dsaResultMeetingLogDTO.setDsaResultMeetingId(dsaResultMeetingDTO.getId());
            dsaResultMeetingLogDTO.setTlDsaCode(dsaResultMeetingDTO.getTlDsaCode());
            dsaResultMeetingLogDTO.setDsaCode(dsaResultMeetingDTO.getDsaCode());
            dsaResultMeetingLogDTO.setProvinceMeetingId(dsaResultMeetingDTO.getProvinceMeetingCode());
            dsaResultMeetingLogDTO.setDistrictMeetingId(dsaResultMeetingDTO.getDistrictMeetingCode());
            dsaResultMeetingLogDTO.setMeetingDate(dsaResultMeetingDTO.getMeetingDate());
            dsaResultMeetingLogDTO.setMeetingResult(dsaResultMeetingDTO.getMeetingResult());
            dsaResultMeetingLogDTO.setMeetingReason(dsaResultMeetingDTO.getMeetingReason());
            dsaResultMeetingLogDTO.setDsaNote(dsaResultMeetingDTO.getDsaNote());
            dsaResultMeetingLogDTO.setCreatedDate(new Date());
            dsaResultMeetingLogDTO.setCreatedUser(getUsername());
            dsaResultMeetingLogDTO.setAcceptStatus(dsaResultMeetingDTO.getAcceptStatus());
            dsaResultMeetingLogDTO.setKeepStatus(dsaResultMeetingDTO.getKeepStatus());
            dsaResultMeetingLogService.createOrUpdate(dsaResultMeetingLogDTO);

            reportSuccessGrowl(null, "distribute.meeting.keep.status.msg.detail");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            reportErrorGrowl(null, "common.error.happened");
        }
    }

    public void checkOrUnCheckReturnCca(DsaResultMeetingDTO dsaResultMeetingDTO){
        try {
            if (DataUtil.isNullObject(dsaResultMeetingDTO.getReturnCca()) || DataUtil.safeEqual(dsaResultMeetingDTO.getReturnCca(),0)){
                dsaResultMeetingDTO.setReturnCca(1l);
            }else{
                dsaResultMeetingDTO.setReturnCca(0l);
            }
            dsaResultMeetingDTO.setReturnCcaDate(new Date());
            dsaResultMeetingDTO.setUpdatedUser(getUsername());
            dsaResultMeetingDTO.setUpdatedDate(new Date());
            distributeMeetingService.createOrUpdate(dsaResultMeetingDTO);

            DsaResultMeetingLogDTO dsaResultMeetingLogDTO = new DsaResultMeetingLogDTO();
            dsaResultMeetingLogDTO.setDsaResultMeetingId(dsaResultMeetingDTO.getId());
            dsaResultMeetingLogDTO.setTlDsaCode(dsaResultMeetingDTO.getTlDsaCode());
            dsaResultMeetingLogDTO.setDsaCode(dsaResultMeetingDTO.getDsaCode());
            dsaResultMeetingLogDTO.setProvinceMeetingId(dsaResultMeetingDTO.getProvinceMeetingCode());
            dsaResultMeetingLogDTO.setDistrictMeetingId(dsaResultMeetingDTO.getDistrictMeetingCode());
            dsaResultMeetingLogDTO.setMeetingDate(dsaResultMeetingDTO.getMeetingDate());
            dsaResultMeetingLogDTO.setMeetingResult(dsaResultMeetingDTO.getMeetingResult());
            dsaResultMeetingLogDTO.setMeetingReason(dsaResultMeetingDTO.getMeetingReason());
            dsaResultMeetingLogDTO.setDsaNote(dsaResultMeetingDTO.getDsaNote());
            dsaResultMeetingLogDTO.setCreatedDate(new Date());
            dsaResultMeetingLogDTO.setCreatedUser(getUsername());
            dsaResultMeetingLogDTO.setAcceptStatus(dsaResultMeetingDTO.getAcceptStatus());
            dsaResultMeetingLogDTO.setKeepStatus(dsaResultMeetingDTO.getKeepStatus());
            dsaResultMeetingLogDTO.setReturnCca(dsaResultMeetingDTO.getReturnCca());
            dsaResultMeetingLogDTO.setReturnCcaDate(new Date());
            dsaResultMeetingLogDTO.setProductType(dsaResultMeetingDTO.getProductType());
            dsaResultMeetingLogService.createOrUpdate(dsaResultMeetingLogDTO);

            reportSuccessGrowl(null, "distribute.meeting.return.cca.msg.detail");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            reportErrorGrowl(null, "common.error.happened");
        }
    }

    public void saveOrUpdateReturnCca(){
        try {
            currentDsaResultMeeting.setReturnCca(1l);
            currentDsaResultMeeting.setReturnCcaDate(new Date());
            currentDsaResultMeeting.setUpdatedUser(getUsername());
            currentDsaResultMeeting.setUpdatedDate(new Date());
            distributeMeetingService.createOrUpdate(currentDsaResultMeeting);

            DsaResultMeetingLogDTO dsaResultMeetingLogDTO = new DsaResultMeetingLogDTO();
            dsaResultMeetingLogDTO.setDsaResultMeetingId(currentDsaResultMeeting.getId());
            dsaResultMeetingLogDTO.setTlDsaCode(currentDsaResultMeeting.getTlDsaCode());
            dsaResultMeetingLogDTO.setDsaCode(currentDsaResultMeeting.getDsaCode());
            dsaResultMeetingLogDTO.setProvinceMeetingId(currentDsaResultMeeting.getProvinceMeetingCode());
            dsaResultMeetingLogDTO.setDistrictMeetingId(currentDsaResultMeeting.getDistrictMeetingCode());
            dsaResultMeetingLogDTO.setMeetingDate(currentDsaResultMeeting.getMeetingDate());
            dsaResultMeetingLogDTO.setMeetingResult(currentDsaResultMeeting.getMeetingResult());
            dsaResultMeetingLogDTO.setMeetingReason(currentDsaResultMeeting.getMeetingReason());
            dsaResultMeetingLogDTO.setDsaNote(currentDsaResultMeeting.getDsaNote());
            dsaResultMeetingLogDTO.setCreatedDate(new Date());
            dsaResultMeetingLogDTO.setCreatedUser(getUsername());
            dsaResultMeetingLogDTO.setAcceptStatus(currentDsaResultMeeting.getAcceptStatus());
            dsaResultMeetingLogDTO.setKeepStatus(currentDsaResultMeeting.getKeepStatus());
            dsaResultMeetingLogDTO.setReturnCca(currentDsaResultMeeting.getReturnCca());
            dsaResultMeetingLogDTO.setReturnCcaDate(new Date());
            dsaResultMeetingLogDTO.setProductType(currentDsaResultMeeting.getProductType());
            dsaResultMeetingLogService.createOrUpdate(dsaResultMeetingLogDTO);
            RequestContext.getCurrentInstance().execute("PF('dlgReturnCca').hide()");
            reportSuccessGrowl(null, "distribute.meeting.return.cca.msg.detail");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            reportErrorGrowl(null, "common.error.happened");
        }
    }

    public void checkOrUnCheckReturnCcaDoNothing(DsaResultMeetingDTO dsaResultMeetingDTO){
        try {
            dsaResultMeetingDTO.setReturnCcaValue(false);
            RequestContext.getCurrentInstance().execute("PF('returnCcaConfirmDialog').hide()");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            reportErrorGrowl(null, "common.error.happened");
        }
    }

    @Secured("@")
    public void fileUploadAction(FileUploadEvent event) {
        try {
            uploadedFile = event.getFile();
            contentByte = uploadedFile.getContents();
            fileName = uploadedFile.getFileName();
        }  catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            reportErrorGrowl(null, "common.error.happened");
        }
    }

    public void getListDistrictByProvinceId(String provinceId){
        try {
            if (!DataUtil.isNullOrEmpty(provinceId)) {
                lstDistricts = areaService.findDistrictByProvinceCode(provinceId);
            }else{
                lstDistricts = new ArrayList<>();
            }
        }  catch (Exception e){
            logger.error(e.getMessage(),e);
            reportErrorGrowl(null, "common.error.happened");
        }

    }

    public void updateByMeetingResult(String meetingResult){
        try {
            updateReasonByMeetingResult(meetingResult);
            if (DataUtil.safeEqual(meetingResult,2)){
                checkDisableMeetingDate = false;
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }
    }

    public void updateReasonByMeetingResult(String meetingResult){
        try {
            if (!DataUtil.isNullOrEmpty(reasonLists)){
                reasonLists.clear();
            }
            reasonLists = optionSetValueService.findListByCodeAndActionCode(Const.AP_DOMAIN.SALE_DSA_REASON,meetingResult);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }
    }

    public void delete(){
    }

    public String getHeaderDialog() {
        return isCreateState ? getText("input.meeting.title.dlg.add") : getText("input.meeting.title.dlg.update");
    }

    public String generateMeetingStatus(String upl,String meetingResult){
        if (!DataUtil.isNullOrEmpty(upl) && !DataUtil.isNullOrEmpty(meetingResult)){
            return upl + " - " + meetingResult;
        }else if(DataUtil.isNullOrEmpty(upl) && !DataUtil.isNullOrEmpty(meetingResult)){
            return meetingResult;
        }else {
            return upl;
        }
    }

    public boolean isCreateState() {
        return isCreateState;
    }

    public void setCreateState(boolean createState) {
        isCreateState = createState;
    }

    public DsaResultMeetingDTO getCurrentDsaResultMeeting() {
        return currentDsaResultMeeting;
    }

    public void setCurrentDsaResultMeeting(DsaResultMeetingDTO currentDsaResultMeeting) {
        this.currentDsaResultMeeting = currentDsaResultMeeting;
    }

    public SnDataModel<DsaResultMeetingDTO> getLazyLoadDsaResultMeeting() {
        return lazyLoadDsaResultMeeting;
    }

    public void setLazyLoadDsaResultMeeting(SnDataModel<DsaResultMeetingDTO> lazyLoadDsaResultMeeting) {
        this.lazyLoadDsaResultMeeting = lazyLoadDsaResultMeeting;
    }

    public List<AreaDTO> getAreaDTOs() {
        return areaDTOs;
    }

    public void setAreaDTOs(List<AreaDTO> areaDTOs) {
        this.areaDTOs = areaDTOs;
    }

    public List<OptionSetValueDTO> getMeetingResults() {
        return meetingResults;
    }

    public void setMeetingResults(List<OptionSetValueDTO> meetingResults) {
        this.meetingResults = meetingResults;
    }

    public List<OptionSetValueDTO> getReasonLists() {
        return reasonLists;
    }

    public void setReasonLists(List<OptionSetValueDTO> reasonLists) {
        this.reasonLists = reasonLists;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public byte[] getContentByte() {
        return contentByte;
    }

    public void setContentByte(byte[] contentByte) {
        this.contentByte = contentByte;
    }

    public boolean isRenderBtnSubmit() {
        return renderBtnSubmit;
    }

    public void setRenderBtnSubmit(boolean renderBtnSubmit) {
        this.renderBtnSubmit = renderBtnSubmit;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public boolean isCheckDisableMeetingDate() {
        return checkDisableMeetingDate;
    }

    public void setCheckDisableMeetingDate(boolean checkDisableMeetingDate) {
        this.checkDisableMeetingDate = checkDisableMeetingDate;
    }

    public List<AreaDTO> getLstDistrictDsas() {
        return lstDistrictDsas;
    }

    public void setLstDistrictDsas(List<AreaDTO> lstDistrictDsas) {
        this.lstDistrictDsas = lstDistrictDsas;
    }

    public boolean isRenderCcaNoteFlag() {
        return renderCcaNoteFlag;
    }

    public void setRenderCcaNoteFlag(boolean renderCcaNoteFlag) {
        this.renderCcaNoteFlag = renderCcaNoteFlag;
    }

    public List<OptionSetValueDTO> getCcaAcceptStatus() {
        return ccaAcceptStatus;
    }

    public void setCcaAcceptStatus(List<OptionSetValueDTO> ccaAcceptStatus) {
        this.ccaAcceptStatus = ccaAcceptStatus;
    }
}
