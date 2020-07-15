package com.tp.controller;

import com.tp.common.custom.SnDataModel;
import com.tp.dto.DsaResultMeetingDTO;
import com.tp.dto.DsaResultMeetingSearchDTO;
import com.tp.dto.TeamDTO;
import com.tp.dto.VoiceDetailReportSearchDTO;
import com.tp.dto.admin.UserDTO;
import com.tp.dwh.dto.CXBitCdrDTO;
import com.tp.dwh.service.CXBitCdrService;
import com.tp.service.OptionSetValueService;
import com.tp.service.TeamService;
import com.tp.service.UpdateMeetingService;
import com.tp.service.UserService;
import com.tp.util.*;
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
public class VoiceDetailReportController extends BaseController{

    final static Logger logger = LoggerFactory.getLogger(VoiceDetailReportController.class);

    private static final String TEMPLATE_FILE_NAME          = "VOICE_DETAIL_REPORT.xlsx";
    private static final String BASE_FILE_NAME_EXPORT       = "voiceReport";

    @Autowired
    private CXBitCdrService cxBitCdrService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private UserService userService;

    @Value("${DSA_PREFIX_FOLDER_SYSTEM_FILE_UPLOAD}")
    private String PREFIX_FOLDER_DSA;

    private SnDataModel<CXBitCdrDTO> lazyLoadCXBitCdr;
    private boolean isCreateState;
    private Map<String, String> listCheckBoxLabel = new ConcurrentHashMap<String, String>();
    private List<TeamDTO> teamDTOs;
    private List<UserDTO> staffCodes;

    @PostConstruct
    @Override
    public void init(){
        try {
            UserDTO userDTO = getUser();
            if (hasPermission("ROLE_TL_CCA")) {
                teamDTOs = teamService.findTeamByTeamLeadId(userDTO.getUserId());
            }else if (hasPermission("ROLE_STL_CCA")){
                teamDTOs = teamService.findTeamByType(Const.ROLE_TYPE_TEAM.TEAM_247);
            }
            VoiceDetailReportSearchDTO voiceDetailReportSearchDTO = new VoiceDetailReportSearchDTO();
            Calendar calFrom =  Calendar.getInstance();
            calFrom.add(Calendar.DAY_OF_MONTH,-7);
            voiceDetailReportSearchDTO.setFromDate(calFrom.getTime());
            voiceDetailReportSearchDTO.setToDate(new Date());
            if (!DataUtil.isNullOrEmpty(teamDTOs)) {
                List<String> teamCodes = new ArrayList<>();
                for (TeamDTO teamDTO:teamDTOs) {
                    teamCodes.add(teamDTO.getTeamCode());
                }
                voiceDetailReportSearchDTO.setTeamCodes(teamCodes);
            }
            lazyLoadCXBitCdr = new SnDataModel<>(cxBitCdrService, voiceDetailReportSearchDTO);
            listCheckBoxLabel.put(getText("distribute.meeting.district.label"), getText("common.label.choose"));
            listCheckBoxLabel.put(getText("update.meeting.status.label"), getText("common.label.choose"));
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

            VoiceDetailReportSearchDTO inputSearchUI = (VoiceDetailReportSearchDTO)lazyLoadCXBitCdr.getRequestSearchDTO();

            VoiceDetailReportSearchDTO inputSearchExport = new VoiceDetailReportSearchDTO();
            org.springframework.beans.BeanUtils.copyProperties(inputSearchUI, inputSearchExport);

            inputSearchExport.setFirst(0);
            inputSearchExport.setPageSize(Integer.MAX_VALUE);
            List<CXBitCdrDTO> lstData = cxBitCdrService.load(inputSearchExport).getData();

            logger.info(location + " size = " + lstData.size());

            bean.setTemplateName(TEMPLATE_FILE_NAME);
            bean.putValue("lstData", lstData);
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

    public boolean validate() {
        return true;
    }

    public void findAllStaffCodeByTeam (String teamCode) throws Exception {
        if (!DataUtil.isNullOrEmpty(staffCodes)){
            staffCodes.clear();
        }
        staffCodes = userService.findAllStaffCodeByTeam(teamCode);
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

    public SnDataModel<CXBitCdrDTO> getLazyLoadCXBitCdr() {
        return lazyLoadCXBitCdr;
    }

    public void setLazyLoadCXBitCdr(SnDataModel<CXBitCdrDTO> lazyLoadCXBitCdr) {
        this.lazyLoadCXBitCdr = lazyLoadCXBitCdr;
    }

    public Map<String, String> getListCheckBoxLabel() {
        return listCheckBoxLabel;
    }

    public void setListCheckBoxLabel(Map<String, String> listCheckBoxLabel) {
        this.listCheckBoxLabel = listCheckBoxLabel;
    }

    public List<TeamDTO> getTeamDTOs() {
        return teamDTOs;
    }

    public void setTeamDTOs(List<TeamDTO> teamDTOs) {
        this.teamDTOs = teamDTOs;
    }

    public List<UserDTO> getStaffCodes() {
        return staffCodes;
    }

    public void setStaffCodes(List<UserDTO> staffCodes) {
        this.staffCodes = staffCodes;
    }
}
