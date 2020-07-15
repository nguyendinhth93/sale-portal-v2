package com.tp.controller;

import com.tp.common.custom.SnDataModel;
import com.tp.dto.AreaDTO;
import com.tp.dto.RatioCollectMeetingDTO;
import com.tp.dto.RatioCollectMeetingSearchDTO;
import com.tp.dto.admin.UserDTO;
import com.tp.service.AreaService;
import com.tp.service.OptionSetValueService;
import com.tp.service.RatioCollectMeetingService;
import com.tp.service.UserService;
import com.tp.util.DataUtil;
import com.tp.util.DateUtil;
import com.tp.util.FileExportBean;
import com.tp.util.FileUtil;
import org.apache.poi.ss.usermodel.Workbook;
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
import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by DINH NGUYEN on 20/07/2017.
 */
@ManagedBean
@Component
@Scope("view")
public class RatioCollectMeetingReportController extends BaseController{

    final static Logger logger = LoggerFactory.getLogger(RatioCollectMeetingReportController.class);

    private static final String TEMPLATE_FILE_NAME          = "COLLECT_MEETING_REPORT.xlsx";
    private static final String BASE_FILE_NAME_EXPORT       = "collectMeetingReport";

    @Autowired
    private RatioCollectMeetingService ratioCollectMeetingService;
    @Autowired
    private UserService userService;
    @Autowired
    private AreaService areaService;

    private SnDataModel<RatioCollectMeetingDTO> lazyLoadRatioCollectMeeting;
    private boolean isCreateState;
    private List<UserDTO> tlDsas;
    private List<AreaDTO> lstProvinces;
    private List<AreaDTO> lstDistricts;


    @PostConstruct
    @Override
    public void init(){
        try {
            RatioCollectMeetingSearchDTO ratioCollectMeetingSearchDTO = new RatioCollectMeetingSearchDTO();
            ratioCollectMeetingSearchDTO.setToDate(new Date());
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH,-7);
            ratioCollectMeetingSearchDTO.setFromDate(cal.getTime());
            tlDsas = userService.findTlDSA();
            lstProvinces = areaService.findAllProvince();
            lazyLoadRatioCollectMeeting = new SnDataModel<>(ratioCollectMeetingService, ratioCollectMeetingSearchDTO);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            reportErrorGrowl(null, "common.error.happened");
        }
    }

    public void exportData() {
        try {
            String location = "exportData() >";
            logger.info(location + " START");
            FileExportBean bean = new FileExportBean();
            bean.setTemplatePath(FileUtil.getTemplatePath() +BASE_FILE_NAME_EXPORT+"/"+ File.separator);

            RatioCollectMeetingSearchDTO inputSearchUI = (RatioCollectMeetingSearchDTO)lazyLoadRatioCollectMeeting.getRequestSearchDTO();

            RatioCollectMeetingSearchDTO inputSearchExport = new RatioCollectMeetingSearchDTO();
            org.springframework.beans.BeanUtils.copyProperties(inputSearchUI, inputSearchExport);

            inputSearchExport.setFirst(0);
            inputSearchExport.setPageSize(Integer.MAX_VALUE);
            List<RatioCollectMeetingDTO> lstData = ratioCollectMeetingService.load(inputSearchExport).getData();

            logger.info(location + " size = " + lstData.size());

            Long totalAccept = 0l;
            Long totalMeeting = 0l;
            Long totalNotConnected = 0l;
            Long totalFullDocument = 0l;
            Long totalReturnCca = 0l;
            for (RatioCollectMeetingDTO ratioCollectMeetingDTO:lstData){
                totalAccept = totalAccept + ratioCollectMeetingDTO.getTotalAccept();
                totalMeeting = totalMeeting + ratioCollectMeetingDTO.getTotalMeeting();
                totalNotConnected = totalNotConnected + ratioCollectMeetingDTO.getTotalNotConnected();
                totalFullDocument = totalFullDocument + ratioCollectMeetingDTO.getTotalFullDocument();
                totalReturnCca = totalReturnCca + ratioCollectMeetingDTO.getTotalReturnCca();
            }

            bean.setTemplateName(TEMPLATE_FILE_NAME);
            bean.putValue("lstData", lstData);
            bean.putValue("fromDate", inputSearchExport.getFromDate());
            bean.putValue("toDate", inputSearchExport.getToDate());
            bean.putValue("province", getProvinceNameByCode(inputSearchExport.getProvinceCode()));
            bean.putValue("district", getDistrictNameByCode(inputSearchExport.getDistrictCode()));
            bean.putValue("jtlDsa", inputSearchExport.getJtlDsaCode());


            bean.putValue("totalAccept", totalAccept);
            bean.putValue("totalNotConnected", totalNotConnected);
            bean.putValue("totalFullDocument", totalFullDocument);
            bean.putValue("totalReturnCca", totalReturnCca);
            bean.putValue("jtlDsa", inputSearchExport.getJtlDsaCode());
            bean.putValue("totalFullDocument", totalFullDocument);
            bean.putValue("totalReturnCca", totalReturnCca);
            bean.putValue("jtlDsa", inputSearchExport.getJtlDsaCode());
            if (!DataUtil.isNullOrZero(totalAccept)){
                Double ratio = (double) totalFullDocument /  totalAccept;
                bean.putValue("ratioFullDocAndAccept", (double)Math.round(ratio*10)/10 * 100);
            }else{
                bean.putValue("ratioFullDocAndAccept", 0);
            }
            if (!DataUtil.isNullOrZero(totalNotConnected)){
                Double ratio = (double) totalFullDocument /  totalNotConnected;
                bean.putValue("ratioFullDocAndNotConnected", (double)Math.round(ratio*10)/10 * 100);
            }else{
                bean.putValue("ratioFullDocAndNotConnected", 0);
            }
            if (DataUtil.isNullOrZero(totalMeeting)){
                Double ratio = (double) totalFullDocument /  totalMeeting;
                bean.putValue("ratioFullDocAndNotConnected", (double)Math.round(ratio*10)/10 * 100);
            }else{
                bean.putValue("ratioFullDocAndTotalMeeting", 0);
            }

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

    public void getListDistrictByProvinceCode(String provinceCode){
        try {
            if (!DataUtil.isNullOrEmpty(provinceCode)) {
                lstDistricts = areaService.findDistrictByProvinceCode(provinceCode);
            }else {
                lstDistricts = new ArrayList<>();
            }
        }  catch (Exception e){
            logger.error(e.getMessage(),e);
            reportErrorGrowl(null, "common.error.happened");
        }
    }

    public boolean validate() {
        return true;
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

    public SnDataModel<RatioCollectMeetingDTO> getLazyLoadRatioCollectMeeting() {
        return lazyLoadRatioCollectMeeting;
    }

    public void setLazyLoadRatioCollectMeeting(SnDataModel<RatioCollectMeetingDTO> lazyLoadRatioCollectMeeting) {
        this.lazyLoadRatioCollectMeeting = lazyLoadRatioCollectMeeting;
    }

    public List<UserDTO> getTlDsas() {
        return tlDsas;
    }

    public void setTlDsas(List<UserDTO> tlDsas) {
        this.tlDsas = tlDsas;
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
}
