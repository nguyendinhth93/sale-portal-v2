package com.tp.controller;

import com.tp.common.custom.SnDataModel;
import com.tp.dto.TeamDTO;
import com.tp.dto.VoiceDetailReportSearchDTO;
import com.tp.dto.VoiceSummaryDTO;
import com.tp.dto.admin.UserDTO;
import com.tp.dwh.dto.CXBitCdrDTO;
import com.tp.service.OptionSetValueService;
import com.tp.service.TeamService;
import com.tp.service.UserService;
import com.tp.service.VoiceSummaryService;
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
public class VoiceSummaryReportController extends BaseController{

    final static Logger logger = LoggerFactory.getLogger(VoiceSummaryReportController.class);

    private static final String TEMPLATE_FILE_NAME          = "VOICE_SUMMARY_REPORT.xlsx";
    private static final String BASE_FILE_NAME_EXPORT       = "voiceSummaryReport";

    @Autowired
    private VoiceSummaryService voiceSummaryService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private UserService userService;

    private SnDataModel<VoiceSummaryDTO> lazyLoadVoiceSummary;
    private boolean isCreateState;
    private List<TeamDTO> teamDTOs;
    private List<UserDTO> staffCodes;

    @PostConstruct
    @Override
    public void init(){
        try {
            VoiceDetailReportSearchDTO voiceDetailReportSearchDTO = new VoiceDetailReportSearchDTO();
            Calendar calFrom =  Calendar.getInstance();
            calFrom.add(Calendar.DAY_OF_MONTH,-7);
            voiceDetailReportSearchDTO.setFromDate(calFrom.getTime());
            voiceDetailReportSearchDTO.setToDate(new Date());
            UserDTO userDTO = getUser();
            if (hasPermission("ROLE_TL_CCA")) {
                teamDTOs = teamService.findTeamByTeamLeadId(userDTO.getUserId());
            }else if (hasPermission("ROLE_STL_CCA")){
                teamDTOs = teamService.findTeamByType(Const.ROLE_TYPE_TEAM.TEAM_247);
            }
            if (!DataUtil.isNullOrEmpty(teamDTOs)) {
                List<String> teamCodes = new ArrayList<>();
                for (TeamDTO teamDTO:teamDTOs) {
                    teamCodes.add(teamDTO.getTeamCode());
                }
                voiceDetailReportSearchDTO.setTeamCodes(teamCodes);
            }
            lazyLoadVoiceSummary = new SnDataModel<>(voiceSummaryService, voiceDetailReportSearchDTO);
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

            VoiceDetailReportSearchDTO inputSearchUI = (VoiceDetailReportSearchDTO)lazyLoadVoiceSummary.getRequestSearchDTO();

            VoiceDetailReportSearchDTO inputSearchExport = new VoiceDetailReportSearchDTO();
            org.springframework.beans.BeanUtils.copyProperties(inputSearchUI, inputSearchExport);

            inputSearchExport.setFirst(0);
            inputSearchExport.setPageSize(Integer.MAX_VALUE);
            List<VoiceSummaryDTO> lstData = voiceSummaryService.load(inputSearchExport).getData();

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

    public String getHeaderDialog() {
        return isCreateState ? getText("distribute.meeting.title.dlg.assigne") : getText("distribute.meeting.title.dlg.history");
    }

    public boolean isCreateState() {
        return isCreateState;
    }

    public void setCreateState(boolean createState) {
        isCreateState = createState;
    }

    public SnDataModel<VoiceSummaryDTO> getLazyLoadVoiceSummary() {
        return lazyLoadVoiceSummary;
    }

    public void setLazyLoadVoiceSummary(SnDataModel<VoiceSummaryDTO> lazyLoadVoiceSummary) {
        this.lazyLoadVoiceSummary = lazyLoadVoiceSummary;
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
