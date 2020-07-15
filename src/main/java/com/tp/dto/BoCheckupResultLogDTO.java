package com.tp.dto;

import java.io.Serializable;
import java.util.Date;
public class BoCheckupResultLogDTO extends BaseDTO implements Serializable {
public static enum COLUMNS {BOCHECKUPCODE, BOCHECKUPRESULTID, CHECKUPREASON, CHECKUPREASONDETAIL, CHECKUPSTATUS, CREATEDDATE, CREATEDUSER, CUSTOMERNAME, CUSTOMERPHONE, ID, LIMITTSRISK, NATIONALID, QDESTATUS, RECEIVEDDSADATE, RETURNDSADATE, SENDQDEDATE, TLBOCHECKUPCODE, EXCLUSE_ID_LIST};
    private String boCheckupCode;
    private Long boCheckupResultId;
    private Long checkupReason;
    private String checkupReasonDetail;
    private Long checkupStatus;
    private Date createdDate;
    private String createdUser;
    private String customerName;
    private String customerPhone;
    private Long id;
    private Long limitTsRisk;
    private String nationalId;
    private Long qdeStatus;
    private Date receivedDsaDate;
    private Date returnDsaDate;
    private Date sendQdeDate;
    private String tlBoCheckupCode;
    private Long sendToFollow;
    private Date confirmMeetingDate;

    // Add for Bo Follow
    private String tlBoFollow;
    private String boFollow;
    private Date toLosDate;
    private String losCode;
    private Long statusDocVpbank;
    private Long groupDocStatus;
    private Long detailStatusDocVpbank;
    private String noteStatusDocVpbank;
    private Long docReturnStatus;
    private Long confirmErrorBo;
    private Date toFiDate;
    private Date returnResultQdeDate;
    private Date activeDate;
    private Long limitApproval;
    private String boundCodeDsa;
    private Long uplLimitDsa;
    private Long ccLimitDsa;
    private Date assigneeBoFollowDate;

    public Date getAssigneeBoFollowDate() {
        return assigneeBoFollowDate;
    }

    public void setAssigneeBoFollowDate(Date assigneeBoFollowDate) {
        this.assigneeBoFollowDate = assigneeBoFollowDate;
    }

    public Long getUplLimitDsa() {
        return uplLimitDsa;
    }

    public void setUplLimitDsa(Long uplLimitDsa) {
        this.uplLimitDsa = uplLimitDsa;
    }

    public Long getCcLimitDsa() {
        return ccLimitDsa;
    }

    public void setCcLimitDsa(Long ccLimitDsa) {
        this.ccLimitDsa = ccLimitDsa;
    }

    public String getBoundCodeDsa() {
        return boundCodeDsa;
    }

    public void setBoundCodeDsa(String boundCodeDsa) {
        this.boundCodeDsa = boundCodeDsa;
    }

    public String getTlBoFollow() {
        return tlBoFollow;
    }

    public void setTlBoFollow(String tlBoFollow) {
        this.tlBoFollow = tlBoFollow;
    }

    public String getBoFollow() {
        return boFollow;
    }

    public void setBoFollow(String boFollow) {
        this.boFollow = boFollow;
    }

    public Date getToLosDate() {
        return toLosDate;
    }

    public void setToLosDate(Date toLosDate) {
        this.toLosDate = toLosDate;
    }

    public String getLosCode() {
        return losCode;
    }

    public void setLosCode(String losCode) {
        this.losCode = losCode;
    }

    public Long getStatusDocVpbank() {
        return statusDocVpbank;
    }

    public void setStatusDocVpbank(Long statusDocVpbank) {
        this.statusDocVpbank = statusDocVpbank;
    }

    public Long getGroupDocStatus() {
        return groupDocStatus;
    }

    public void setGroupDocStatus(Long groupDocStatus) {
        this.groupDocStatus = groupDocStatus;
    }

    public Long getDetailStatusDocVpbank() {
        return detailStatusDocVpbank;
    }

    public void setDetailStatusDocVpbank(Long detailStatusDocVpbank) {
        this.detailStatusDocVpbank = detailStatusDocVpbank;
    }

    public String getNoteStatusDocVpbank() {
        return noteStatusDocVpbank;
    }

    public void setNoteStatusDocVpbank(String noteStatusDocVpbank) {
        this.noteStatusDocVpbank = noteStatusDocVpbank;
    }

    public Long getDocReturnStatus() {
        return docReturnStatus;
    }

    public void setDocReturnStatus(Long docReturnStatus) {
        this.docReturnStatus = docReturnStatus;
    }

    public Long getConfirmErrorBo() {
        return confirmErrorBo;
    }

    public void setConfirmErrorBo(Long confirmErrorBo) {
        this.confirmErrorBo = confirmErrorBo;
    }

    public Date getToFiDate() {
        return toFiDate;
    }

    public void setToFiDate(Date toFiDate) {
        this.toFiDate = toFiDate;
    }

    public Date getReturnResultQdeDate() {
        return returnResultQdeDate;
    }

    public void setReturnResultQdeDate(Date returnResultQdeDate) {
        this.returnResultQdeDate = returnResultQdeDate;
    }

    public Date getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

    public Long getLimitApproval() {
        return limitApproval;
    }

    public void setLimitApproval(Long limitApproval) {
        this.limitApproval = limitApproval;
    }

    public Date getConfirmMeetingDate() {
        return confirmMeetingDate;
    }

    public void setConfirmMeetingDate(Date confirmMeetingDate) {
        this.confirmMeetingDate = confirmMeetingDate;
    }

    public Long getSendToFollow() {
        return sendToFollow;
    }

    public void setSendToFollow(Long sendToFollow) {
        this.sendToFollow = sendToFollow;
    }

    public String getBoCheckupCode() {
        return this.boCheckupCode;
    }
    public void setBoCheckupCode(String boCheckupCode) {
        this.boCheckupCode = boCheckupCode;
    }
    public Long getBoCheckupResultId() {
        return this.boCheckupResultId;
    }
    public void setBoCheckupResultId(Long boCheckupResultId) {
        this.boCheckupResultId = boCheckupResultId;
    }
    public Long getCheckupReason() {
        return this.checkupReason;
    }
    public void setCheckupReason(Long checkupReason) {
        this.checkupReason = checkupReason;
    }
    public String getCheckupReasonDetail() {
        return this.checkupReasonDetail;
    }
    public void setCheckupReasonDetail(String checkupReasonDetail) {
        this.checkupReasonDetail = checkupReasonDetail;
    }
    public Long getCheckupStatus() {
        return this.checkupStatus;
    }
    public void setCheckupStatus(Long checkupStatus) {
        this.checkupStatus = checkupStatus;
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
    public String getCustomerName() {
        return this.customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCustomerPhone() {
        return this.customerPhone;
    }
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getLimitTsRisk() {
        return this.limitTsRisk;
    }
    public void setLimitTsRisk(Long limitTsRisk) {
        this.limitTsRisk = limitTsRisk;
    }
    public String getNationalId() {
        return this.nationalId;
    }
    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }
    public Long getQdeStatus() {
        return this.qdeStatus;
    }
    public void setQdeStatus(Long qdeStatus) {
        this.qdeStatus = qdeStatus;
    }
    public Date getReceivedDsaDate() {
        return this.receivedDsaDate;
    }
    public void setReceivedDsaDate(Date receivedDsaDate) {
        this.receivedDsaDate = receivedDsaDate;
    }
    public Date getReturnDsaDate() {
        return this.returnDsaDate;
    }
    public void setReturnDsaDate(Date returnDsaDate) {
        this.returnDsaDate = returnDsaDate;
    }
    public Date getSendQdeDate() {
        return this.sendQdeDate;
    }
    public void setSendQdeDate(Date sendQdeDate) {
        this.sendQdeDate = sendQdeDate;
    }
    public String getTlBoCheckupCode() {
        return this.tlBoCheckupCode;
    }
    public void setTlBoCheckupCode(String tlBoCheckupCode) {
        this.tlBoCheckupCode = tlBoCheckupCode;
    }
}
