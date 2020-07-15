package com.tp.dto;

import com.tp.dto.requestSearch.RequestSearchDTO;

import java.util.Date;
import java.util.List;

/**
 * Created by Dinh Nguyen on 06/09/2018.
 */
public class VoiceDetailReportSearchDTO extends RequestSearchDTO {

    private String voiceCode;
    private String staffCode;
    private String staffName;
    private String teamCode;
    private Date fromDate;
    private Date toDate;
    private List<String> voiceCodes;
    private String dst;
    private List<String> teamCodes;

    public VoiceDetailReportSearchDTO() {
    }

    public VoiceDetailReportSearchDTO(String voiceCode, String staffCode, String staffName, String teamCode, Date fromDate, Date toDate) {
        this.voiceCode = voiceCode;
        this.staffCode = staffCode;
        this.staffName = staffName;
        this.teamCode = teamCode;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public List<String> getTeamCodes() {
        return teamCodes;
    }

    public void setTeamCodes(List<String> teamCodes) {
        this.teamCodes = teamCodes;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

    public List<String> getVoiceCodes() {
        return voiceCodes;
    }

    public void setVoiceCodes(List<String> voiceCodes) {
        this.voiceCodes = voiceCodes;
    }

    public String getVoiceCode() {
        return voiceCode;
    }

    public void setVoiceCode(String voiceCode) {
        this.voiceCode = voiceCode;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
}
