package com.tp.controller;

import com.tp.common.custom.SnDataModel;
import com.tp.common.exception.LogicException;
import com.tp.dto.*;
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
public class AcceptedMeetingController extends BaseController{

    final static Logger logger = LoggerFactory.getLogger(AcceptedMeetingController.class);

    @Autowired
    private AcceptedMeetingService acceptedMeetingService;

    private SnDataModel<DsaResultMeetingDTO> lazyLoadDsaResultMeeting;
    private List<AreaDTO> lstDistrictDsas;


    @PostConstruct
    @Override
    public void init(){
        try {
            DsaResultMeetingSearchDTO dsaResultMeetingSearchDTO = new DsaResultMeetingSearchDTO();
            dsaResultMeetingSearchDTO.setDsaCode(getUsername());
            lazyLoadDsaResultMeeting = new SnDataModel<>(acceptedMeetingService, dsaResultMeetingSearchDTO);
            lstDistrictDsas = acceptedMeetingService.findAllDistrictByDsaAndDay(getUsername(),7l);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            reportErrorGrowl(null, "common.error.happened");
        }
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

    public SnDataModel<DsaResultMeetingDTO> getLazyLoadDsaResultMeeting() {
        return lazyLoadDsaResultMeeting;
    }

    public void setLazyLoadDsaResultMeeting(SnDataModel<DsaResultMeetingDTO> lazyLoadDsaResultMeeting) {
        this.lazyLoadDsaResultMeeting = lazyLoadDsaResultMeeting;
    }

    public List<AreaDTO> getLstDistrictDsas() {
        return lstDistrictDsas;
    }

    public void setLstDistrictDsas(List<AreaDTO> lstDistrictDsas) {
        this.lstDistrictDsas = lstDistrictDsas;
    }
}
