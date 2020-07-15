package com.tp.dto;

import java.io.Serializable;
import java.util.Date;

public class VoiceSummaryDTO extends BaseDTO implements Serializable {
    public static enum COLUMNS {ANSWERED, BUSY, CALLDATE, CREATEDDATE, CREATEDUSER, FAILED, ID, NOANSWER, ONCALL, TOTALCALL, TOTALCUSTOMER, VOICECODE, EXCLUSE_ID_LIST};

    private Long answered;
    private Long busy;
    private Date callDate;
    private Date createdDate;
    private String createdUser;
    private Long failed;
    private Long id;
    private Long noAnswer;
    private Long onCall;
    private Long totalCall;
    private Long totalCustomer;
    private String voiceCode;
    private String team;
    private String jtlCcaCode;
    private String ccaCode;
    private Double talkTime;

    public VoiceSummaryDTO(Long answered, Long busy, Date callDate, Date createdDate, String createdUser, Long failed, Long id, Long noAnswer, Long onCall, Long totalCall, Long totalCustomer, String voiceCode, String team, String jtlCcaCode, String ccaCode) {
        this.answered = answered;
        this.busy = busy;
        this.callDate = callDate;
        this.createdDate = createdDate;
        this.createdUser = createdUser;
        this.failed = failed;
        this.id = id;
        this.noAnswer = noAnswer;
        this.onCall = onCall;
        this.totalCall = totalCall;
        this.totalCustomer = totalCustomer;
        this.voiceCode = voiceCode;
        this.team = team;
        this.jtlCcaCode = jtlCcaCode;
        this.ccaCode = ccaCode;
    }

    public VoiceSummaryDTO() {
    }

    public Double getTalkTime() {
        return talkTime;
    }

    public void setTalkTime(Double talkTime) {
        this.talkTime = talkTime;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getJtlCcaCode() {
        return jtlCcaCode;
    }

    public void setJtlCcaCode(String jtlCcaCode) {
        this.jtlCcaCode = jtlCcaCode;
    }

    public String getCcaCode() {
        return ccaCode;
    }

    public void setCcaCode(String ccaCode) {
        this.ccaCode = ccaCode;
    }

    public Long getAnswered() {
        return this.answered;
    }

    public void setAnswered(Long answered) {
        this.answered = answered;
    }

    public Long getBusy() {
        return this.busy;
    }

    public void setBusy(Long busy) {
        this.busy = busy;
    }

    public Date getCallDate() {
        return this.callDate;
    }

    public void setCallDate(Date callDate) {
        this.callDate = callDate;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedUser() {
        return this.createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Long getFailed() {
        return this.failed;
    }

    public void setFailed(Long failed) {
        this.failed = failed;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNoAnswer() {
        return this.noAnswer;
    }

    public void setNoAnswer(Long noAnswer) {
        this.noAnswer = noAnswer;
    }

    public Long getOnCall() {
        return this.onCall;
    }

    public void setOnCall(Long onCall) {
        this.onCall = onCall;
    }

    public Long getTotalCall() {
        return this.totalCall;
    }

    public void setTotalCall(Long totalCall) {
        this.totalCall = totalCall;
    }

    public Long getTotalCustomer() {
        return this.totalCustomer;
    }

    public void setTotalCustomer(Long totalCustomer) {
        this.totalCustomer = totalCustomer;
    }

    public String getVoiceCode() {
        return this.voiceCode;
    }

    public void setVoiceCode(String voiceCode) {
        this.voiceCode = voiceCode;
    }
}
