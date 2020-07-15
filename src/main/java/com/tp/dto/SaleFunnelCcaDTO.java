package com.tp.dto;

import java.io.Serializable;
import java.util.List;

public class SaleFunnelCcaDTO extends BaseDTO implements Serializable {

    private String ccaCode;
    private String ccaId;
    private String ccaName;
    private String jtlCcaCode;
    private String jtlCcaId;
    private String jtlCcaName;
    private Long totalLeads;
    private Long totalMeetings;
    private Long totalReceivedLeads;
    private Long totalAcceptedMeetings;
    private Long totalNotContactedMeetings;
    private Long totalDocs;
    private Long totalFullDocMeetings;
    private Long totalFullDocNotContacted;
    private Long totalCheckups;
    private Long totalQDEs;
    private Long totalApproves;
    private Long totalActives;
    private List<String> listIds;
    private Double percentFullDocs;
    private Double percentFullDocsAccept;
    private Double percentFullDocsNotContacted;
    private Double percentCheckups;
    private Double percentQDEs;
    private Double percentApproves;
    private List<String> listPhones;


    public SaleFunnelCcaDTO() {
    }

    public SaleFunnelCcaDTO(String ccaCode, String ccaId, String ccaName, String jtlCcaCode, String jtlCcaId, String jtlCcaName, Long totalLeads, Long totalMeetings, Long totalReceivedLeads, Long totalAcceptedMeetings, Long totalFullDocMeetings, Long totalCheckups, Long totalQDEs, Long totalApproves, Long totalActives) {
        this.ccaCode = ccaCode;
        this.ccaId = ccaId;
        this.ccaName = ccaName;
        this.jtlCcaCode = jtlCcaCode;
        this.jtlCcaId = jtlCcaId;
        this.jtlCcaName = jtlCcaName;
        this.totalLeads = totalLeads;
        this.totalMeetings = totalMeetings;
        this.totalReceivedLeads = totalReceivedLeads;
        this.totalAcceptedMeetings = totalAcceptedMeetings;
        this.totalFullDocMeetings = totalFullDocMeetings;
        this.totalCheckups = totalCheckups;
        this.totalQDEs = totalQDEs;
        this.totalApproves = totalApproves;
        this.totalActives = totalActives;
    }

    public List<String> getListPhones() {
        return listPhones;
    }

    public void setListPhones(List<String> listPhones) {
        this.listPhones = listPhones;
    }

    public Double getPercentFullDocs() {
        return percentFullDocs;
    }

    public void setPercentFullDocs(Double percentFullDocs) {
        this.percentFullDocs = percentFullDocs;
    }

    public Double getPercentFullDocsAccept() {
        return percentFullDocsAccept;
    }

    public void setPercentFullDocsAccept(Double percentFullDocsAccept) {
        this.percentFullDocsAccept = percentFullDocsAccept;
    }

    public Double getPercentFullDocsNotContacted() {
        return percentFullDocsNotContacted;
    }

    public void setPercentFullDocsNotContacted(Double percentFullDocsNotContacted) {
        this.percentFullDocsNotContacted = percentFullDocsNotContacted;
    }

    public Double getPercentCheckups() {
        return percentCheckups;
    }

    public void setPercentCheckups(Double percentCheckups) {
        this.percentCheckups = percentCheckups;
    }

    public Double getPercentQDEs() {
        return percentQDEs;
    }

    public void setPercentQDEs(Double percentQDEs) {
        this.percentQDEs = percentQDEs;
    }

    public Double getPercentApproves() {
        return percentApproves;
    }

    public void setPercentApproves(Double percentApproves) {
        this.percentApproves = percentApproves;
    }

    public Long getTotalDocs() {
        return totalDocs;
    }

    public void setTotalDocs(Long totalDocs) {
        this.totalDocs = totalDocs;
    }

    public List<String> getListIds() {
        return listIds;
    }

    public void setListIds(List<String> listIds) {
        this.listIds = listIds;
    }

    public Long getTotalFullDocNotContacted() {
        return totalFullDocNotContacted;
    }

    public void setTotalFullDocNotContacted(Long totalFullDocNotContacted) {
        this.totalFullDocNotContacted = totalFullDocNotContacted;
    }

    public Long getTotalNotContactedMeetings() {
        return totalNotContactedMeetings;
    }

    public void setTotalNotContactedMeetings(Long totalNotContactedMeetings) {
        this.totalNotContactedMeetings = totalNotContactedMeetings;
    }

    public String getCcaName() {
        return ccaName;
    }

    public void setCcaName(String ccaName) {
        this.ccaName = ccaName;
    }

    public String getJtlCcaName() {
        return jtlCcaName;
    }

    public void setJtlCcaName(String jtlCcaName) {
        this.jtlCcaName = jtlCcaName;
    }

    public String getCcaCode() {
        return ccaCode;
    }

    public void setCcaCode(String ccaCode) {
        this.ccaCode = ccaCode;
    }

    public String getCcaId() {
        return ccaId;
    }

    public void setCcaId(String ccaId) {
        this.ccaId = ccaId;
    }

    public String getJtlCcaCode() {
        return jtlCcaCode;
    }

    public void setJtlCcaCode(String jtlCcaCode) {
        this.jtlCcaCode = jtlCcaCode;
    }

    public String getJtlCcaId() {
        return jtlCcaId;
    }

    public void setJtlCcaId(String jtlCcaId) {
        this.jtlCcaId = jtlCcaId;
    }

    public Long getTotalLeads() {
        return totalLeads;
    }

    public void setTotalLeads(Long totalLeads) {
        this.totalLeads = totalLeads;
    }

    public Long getTotalMeetings() {
        return totalMeetings;
    }

    public void setTotalMeetings(Long totalMeetings) {
        this.totalMeetings = totalMeetings;
    }

    public Long getTotalReceivedLeads() {
        return totalReceivedLeads;
    }

    public void setTotalReceivedLeads(Long totalReceivedLeads) {
        this.totalReceivedLeads = totalReceivedLeads;
    }

    public Long getTotalAcceptedMeetings() {
        return totalAcceptedMeetings;
    }

    public void setTotalAcceptedMeetings(Long totalAcceptedMeetings) {
        this.totalAcceptedMeetings = totalAcceptedMeetings;
    }

    public Long getTotalFullDocMeetings() {
        return totalFullDocMeetings;
    }

    public void setTotalFullDocMeetings(Long totalFullDocMeetings) {
        this.totalFullDocMeetings = totalFullDocMeetings;
    }

    public Long getTotalCheckups() {
        return totalCheckups;
    }

    public void setTotalCheckups(Long totalCheckups) {
        this.totalCheckups = totalCheckups;
    }

    public Long getTotalQDEs() {
        return totalQDEs;
    }

    public void setTotalQDEs(Long totalQDEs) {
        this.totalQDEs = totalQDEs;
    }

    public Long getTotalApproves() {
        return totalApproves;
    }

    public void setTotalApproves(Long totalApproves) {
        this.totalApproves = totalApproves;
    }

    public Long getTotalActives() {
        return totalActives;
    }

    public void setTotalActives(Long totalActives) {
        this.totalActives = totalActives;
    }
}
