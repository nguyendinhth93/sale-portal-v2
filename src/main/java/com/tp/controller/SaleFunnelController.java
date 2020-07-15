package com.tp.controller;

import com.tp.common.custom.SnDataModel;
import com.tp.crmhn.service.SaleFunnelCcaService;
import com.tp.dto.AreaDTO;
import com.tp.dto.BoundCodeDTO;
import com.tp.dto.PartnerDTO;
import com.tp.dto.SaleFunnelCcaDTO;
import com.tp.dto.admin.UserDTO;
import com.tp.dto.requestSearch.SaleFunnelCCASearchDTO;
import com.tp.service.AreaService;
import com.tp.service.PartnerService;
import com.tp.service.SaleFunnelService;
import com.tp.service.UserService;
import com.tp.util.DataUtil;
import com.tp.util.DateUtil;
import com.tp.util.FileExportBean;
import com.tp.util.FileUtil;
import org.apache.poi.ss.usermodel.Workbook;
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
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by DINH NGUYEN on 20/07/2017.
 */
@ManagedBean
@Component
@Scope("view")
public class SaleFunnelController extends BaseController{

    final static Logger logger = LoggerFactory.getLogger(SaleFunnelController.class);

    private static final String TEMPLATE_FILE_NAME          = "SALE_FUNNEL.xlsx";
    private static final String BASE_FILE_NAME_EXPORT       = "saleFunnel";

    @Autowired
    private SaleFunnelService saleFunnelService;
    @Autowired
    private PartnerService partnerService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private UserService userService;

    private SnDataModel<SaleFunnelCcaDTO> lazyLoadSaleFunnelCca;
    private List<PartnerDTO> partnerDTOs;
    private List<AreaDTO> lstProvinces;
    private List<BoundCodeDTO> lstBoundCodes;
    private List<UserDTO> lstUserDTOs;
    private Map<String, String> listCheckBoxLabel = new ConcurrentHashMap<String, String>();

    private Long totalMeetings;
    private Long totalAccepts;
    private Long totalNotContacteds;
    private Long totalFullDocs;
    private Double totalPercentFullDocs;
    private Long totalFullDocAccepts;
    private Double totalPercentFullDocsAccept;
    private Long totalFullDocNotContacted;
    private Double totalPercentFullDocNotContacted;
    private Long totalCheckup;
    private Double totalPercentCheckups;
    private Long totalQDEs;
    private Double totalPercentQDEs;
    private Long totalApproves;
    private Double totalPercentApproves;
    private String reportedDate;


    @PostConstruct
    @Override
    public void init(){
        try {
            SaleFunnelCCASearchDTO saleFunnelCCASearchDTO = new SaleFunnelCCASearchDTO();
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH,1);
            saleFunnelCCASearchDTO.setFromDate(cal.getTime());
            saleFunnelCCASearchDTO.setToDate(new Date());
            lazyLoadSaleFunnelCca = new SnDataModel<>(saleFunnelService, saleFunnelCCASearchDTO);
            partnerDTOs = partnerService.findAllPartner();
            lstProvinces = areaService.findAllProvince();
            lstBoundCodes = saleFunnelService.findAllBoundCode();
            List<String> roles = new ArrayList<>();
            roles.add("TL_CCA");
            roles.add("JTL_CCA");
            lstUserDTOs = userService.findAllUserByRole(roles);
            listCheckBoxLabel.put(getText("sale.funnel.label.boundCode"), getText("common.label.choose"));
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

            SaleFunnelCCASearchDTO inputSearchUI = (SaleFunnelCCASearchDTO)lazyLoadSaleFunnelCca.getRequestSearchDTO();

            SaleFunnelCCASearchDTO inputSearchExport = new SaleFunnelCCASearchDTO();
            org.springframework.beans.BeanUtils.copyProperties(inputSearchUI, inputSearchExport);

            inputSearchExport.setFirst(0);
            inputSearchExport.setPageSize(Integer.MAX_VALUE);
            List<SaleFunnelCcaDTO> lstData = saleFunnelService.load(inputSearchExport).getData();

            logger.info(location + " size = " + lstData.size());

            totalMeetings = 0l;
            totalAccepts = 0l;
            totalNotContacteds = 0l;
            totalFullDocs = 0l;
            totalFullDocAccepts = 0l;
            totalFullDocNotContacted = 0l;
            totalCheckup = 0l;
            totalQDEs = 0l;
            totalApproves = 0l;
            for (SaleFunnelCcaDTO saleFunnelCcaDTO:lstData){
                totalMeetings = totalMeetings + saleFunnelCcaDTO.getTotalMeetings();
                totalAccepts = totalAccepts + saleFunnelCcaDTO.getTotalAcceptedMeetings();
                totalNotContacteds = totalNotContacteds + saleFunnelCcaDTO.getTotalNotContactedMeetings();
                totalFullDocs = totalFullDocs + saleFunnelCcaDTO.getTotalDocs();
                totalFullDocAccepts = totalFullDocAccepts + saleFunnelCcaDTO.getTotalFullDocMeetings();
                totalFullDocNotContacted = totalFullDocNotContacted + saleFunnelCcaDTO.getTotalFullDocNotContacted();
                totalCheckup = totalCheckup + saleFunnelCcaDTO.getTotalCheckups();
                totalQDEs = totalQDEs + saleFunnelCcaDTO.getTotalQDEs();
                totalApproves = totalApproves + saleFunnelCcaDTO.getTotalApproves();
            }

            if (!DataUtil.safeEqual(totalMeetings,0)){
                double percentDocs = ((double) totalFullDocs/totalMeetings) * 100;
                totalPercentFullDocs = DataUtil.round(percentDocs, 2);
            }else{
                totalPercentFullDocs = 0d;
            }
            if (!DataUtil.safeEqual(totalAccepts,0)){
                double percentFullDocs = ((double) totalFullDocAccepts/totalAccepts) * 100;
                totalPercentFullDocsAccept = DataUtil.round(percentFullDocs, 2);
            }else{
                totalPercentFullDocsAccept =0d;
            }
            if (!DataUtil.safeEqual(totalNotContacteds,0)){
                double percentFullDocsNotContacted = ((double) totalFullDocNotContacted/totalNotContacteds) * 100;
                totalPercentFullDocNotContacted = DataUtil.round(percentFullDocsNotContacted, 2);
            }else{
                totalPercentFullDocNotContacted = 0d;
            }
            if (!DataUtil.safeEqual(totalFullDocs,0)){
                double percentCheckups = ((double) totalCheckup/totalFullDocs) * 100;
                totalPercentCheckups = DataUtil.round(percentCheckups, 2);
            }else{
                totalPercentCheckups = 0d;
            }
            if (!DataUtil.safeEqual(totalCheckup,0)){
                double percentQDEs = ((double) totalQDEs / totalCheckup) * 100;
                totalPercentQDEs = DataUtil.round(percentQDEs, 2);
            }else{
                totalPercentQDEs = 0d;
            }
            if (!DataUtil.safeEqual(totalQDEs,0)){
                double percentApproves = ((double) totalApproves/totalQDEs) * 100;
                totalPercentApproves = DataUtil.round(percentApproves, 2);
            }else{
                totalPercentApproves = 0d;
            }

            reportedDate = DateUtil.date2String(new Date());
            bean.putValue("reportedDate", reportedDate);
            bean.putValue("totalMeetings", totalMeetings);
            bean.putValue("totalAccepts", totalAccepts);
            bean.putValue("totalNotContacteds", totalNotContacteds);
            bean.putValue("totalFullDocs", totalFullDocs);
            bean.putValue("totalPercentFullDocs", totalPercentFullDocs);
            bean.putValue("totalFullDocAccepts", totalFullDocAccepts);
            bean.putValue("totalPercentFullDocsAccept", totalPercentFullDocsAccept);
            bean.putValue("totalFullDocNotContacted", totalFullDocNotContacted);
            bean.putValue("totalPercentFullDocNotContacted", totalPercentFullDocNotContacted);
            bean.putValue("totalCheckup", totalCheckup);
            bean.putValue("totalPercentCheckups", totalPercentCheckups);
            bean.putValue("totalQDEs", totalQDEs);
            bean.putValue("totalPercentQDEs", totalPercentQDEs);
            bean.putValue("totalApproves", totalApproves);
            bean.putValue("totalPercentApproves", totalPercentApproves);
            bean.putValue("partnerCodes", inputSearchExport.getProjectCode());
            bean.putValue("provinces", inputSearchExport.getProvince());
            bean.putValue("boundCodes", getBoundCodeToWriteExcel(inputSearchExport));
            bean.putValue("tlCca", inputSearchExport.getTlCcaCode());
            bean.putValue("fromDate", DateUtil.date2String(inputSearchExport.getFromDate()));
            bean.putValue("toDate", DateUtil.date2String(inputSearchExport.getToDate()));

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

    public String getBoundCodeToWriteExcel(SaleFunnelCCASearchDTO saleFunnelCCASearchDTO) {
        return saleFunnelCCASearchDTO.getBoundCodes().toString().replace("[","").replace("]","");
    }

    public void updateLabelSelectCheckbox(String key, List<String> values) {
        try {
            listCheckBoxLabel.put(key, (!DataUtil.isNullOrEmpty(values) ? values.size() + " " + getText("common.cbb.isSelected") : getText("common.label.choose")));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public SnDataModel<SaleFunnelCcaDTO> getLazyLoadSaleFunnelCca() {
        return lazyLoadSaleFunnelCca;
    }

    public void setLazyLoadSaleFunnelCca(SnDataModel<SaleFunnelCcaDTO> lazyLoadSaleFunnelCca) {
        this.lazyLoadSaleFunnelCca = lazyLoadSaleFunnelCca;
    }

    public List<PartnerDTO> getPartnerDTOs() {
        return partnerDTOs;
    }

    public void setPartnerDTOs(List<PartnerDTO> partnerDTOs) {
        this.partnerDTOs = partnerDTOs;
    }

    public List<AreaDTO> getLstProvinces() {
        return lstProvinces;
    }

    public void setLstProvinces(List<AreaDTO> lstProvinces) {
        this.lstProvinces = lstProvinces;
    }

    public List<BoundCodeDTO> getLstBoundCodes() {
        return lstBoundCodes;
    }

    public void setLstBoundCodes(List<BoundCodeDTO> lstBoundCodes) {
        this.lstBoundCodes = lstBoundCodes;
    }

    public Map<String, String> getListCheckBoxLabel() {
        return listCheckBoxLabel;
    }

    public void setListCheckBoxLabel(Map<String, String> listCheckBoxLabel) {
        this.listCheckBoxLabel = listCheckBoxLabel;
    }

    public List<UserDTO> getLstUserDTOs() {
        return lstUserDTOs;
    }

    public void setLstUserDTOs(List<UserDTO> lstUserDTOs) {
        this.lstUserDTOs = lstUserDTOs;
    }

    public Long getTotalMeetings() {
        return totalMeetings;
    }

    public void setTotalMeetings(Long totalMeetings) {
        this.totalMeetings = totalMeetings;
    }

    public Long getTotalAccepts() {
        return totalAccepts;
    }

    public void setTotalAccepts(Long totalAccepts) {
        this.totalAccepts = totalAccepts;
    }

    public Long getTotalNotContacteds() {
        return totalNotContacteds;
    }

    public void setTotalNotContacteds(Long totalNotContacteds) {
        this.totalNotContacteds = totalNotContacteds;
    }

    public Long getTotalFullDocs() {
        return totalFullDocs;
    }

    public void setTotalFullDocs(Long totalFullDocs) {
        this.totalFullDocs = totalFullDocs;
    }

    public Double getTotalPercentFullDocs() {
        return totalPercentFullDocs;
    }

    public void setTotalPercentFullDocs(Double totalPercentFullDocs) {
        this.totalPercentFullDocs = totalPercentFullDocs;
    }

    public Long getTotalFullDocAccepts() {
        return totalFullDocAccepts;
    }

    public void setTotalFullDocAccepts(Long totalFullDocAccepts) {
        this.totalFullDocAccepts = totalFullDocAccepts;
    }

    public Double getTotalPercentFullDocsAccept() {
        return totalPercentFullDocsAccept;
    }

    public void setTotalPercentFullDocsAccept(Double totalPercentFullDocsAccept) {
        this.totalPercentFullDocsAccept = totalPercentFullDocsAccept;
    }

    public Long getTotalFullDocNotContacted() {
        return totalFullDocNotContacted;
    }

    public void setTotalFullDocNotContacted(Long totalFullDocNotContacted) {
        this.totalFullDocNotContacted = totalFullDocNotContacted;
    }

    public Double getTotalPercentFullDocNotContacted() {
        return totalPercentFullDocNotContacted;
    }

    public void setTotalPercentFullDocNotContacted(Double totalPercentFullDocNotContacted) {
        this.totalPercentFullDocNotContacted = totalPercentFullDocNotContacted;
    }

    public Long getTotalCheckup() {
        return totalCheckup;
    }

    public void setTotalCheckup(Long totalCheckup) {
        this.totalCheckup = totalCheckup;
    }

    public Double getTotalPercentCheckups() {
        return totalPercentCheckups;
    }

    public void setTotalPercentCheckups(Double totalPercentCheckups) {
        this.totalPercentCheckups = totalPercentCheckups;
    }

    public Long getTotalQDEs() {
        return totalQDEs;
    }

    public void setTotalQDEs(Long totalQDEs) {
        this.totalQDEs = totalQDEs;
    }

    public Double getTotalPercentQDEs() {
        return totalPercentQDEs;
    }

    public void setTotalPercentQDEs(Double totalPercentQDEs) {
        this.totalPercentQDEs = totalPercentQDEs;
    }

    public Long getTotalApproves() {
        return totalApproves;
    }

    public void setTotalApproves(Long totalApproves) {
        this.totalApproves = totalApproves;
    }

    public Double getTotalPercentApproves() {
        return totalPercentApproves;
    }

    public void setTotalPercentApproves(Double totalPercentApproves) {
        this.totalPercentApproves = totalPercentApproves;
    }

    public String getReportedDate() {
        return reportedDate;
    }

    public void setReportedDate(String reportedDate) {
        this.reportedDate = reportedDate;
    }
}
