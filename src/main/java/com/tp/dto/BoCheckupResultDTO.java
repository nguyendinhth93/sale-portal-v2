package com.tp.dto;

import java.io.Serializable;
import java.util.Date;
public class BoCheckupResultDTO extends BaseDTO implements Serializable {
public static enum COLUMNS {BOCHECKUPCODE, BOUNDCODE, CCLIMIT, CCACODE, CHECKUPREASON, CHECKUPREASONDETAIL, CHECKUPSTATUS, CREATEDDATE, CREATEDUSER, CUSTOMERNAME, CUSTOMERPHONE, DSACODE, DSARESULTMEETINGID, DSASALECODE, ID, LIMITTSRISK, MEETINGDATE, NATIONALID, PARTNERCODE, PROVINCEPHONE, QDESTATUS, RECEIVEDDSADATE, RETURNDSADATE, SENDQDEDATE, STATUS, TLBOCHECKUPCODE, TLCCACODE, TLDSACODE, UPDATEDDATE, UPDATEDUSER, UPLLIMIT, VALIDDATE, EXCLUSE_ID_LIST};
private String boCheckupCode;
    private String boundCode;
    private Long ccLimit;
    private String ccaCode;
    private Long checkupReason;
    private String checkupReasonDetail;
    private Long checkupStatus;
    private Date createdDate;
    private String createdUser;
    private String customerName;
    private String customerPhone;
    private String dsaCode;
    private Long dsaResultMeetingId;
    private String dsaSaleCode;
    private Long id;
    private Long limitTsRisk;
    private Date meetingDate;
    private String nationalId;
    private String partnerCode;
    private String provincePhone;
    private Long qdeStatus;
    private Date receivedDsaDate;
    private Date returnDsaDate;
    private Date sendQdeDate;
    private Long status;
    private String tlBoCheckupCode;
    private String tlCcaCode;
    private String tlDsaCode;
    private Date updatedDate;
    private String updatedUser;
    private Long uplLimit;
    private Date validDate;
    private Long sendToFollow;
    private Date confirmMeetingDate;
    private String limitTsRiskStr;
    private String qdeStatusStr;
    private String checkupStatusStr;
    private String checkupReasonStr;

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

    private String statusDocVpbankStr;
    private String groupDocStatusStr;
    private String detailStatusDocVpbankStr;
    private String docReturnStatusStr;
    private String boundCodeDsa;
    private String provinceCode;
    private String provinceName;
    private String districtCode;
    private String districtName;

    private Long uplLimitDsa;
    private Long ccLimitDsa;
    private Date assigneeBoFollowDate;
    private String confirmErrorBoStr;

    public String getConfirmErrorBoStr() {
        return confirmErrorBoStr;
    }

    public void setConfirmErrorBoStr(String confirmErrorBoStr) {
        this.confirmErrorBoStr = confirmErrorBoStr;
    }

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

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getBoundCodeDsa() {
        return boundCodeDsa;
    }

    public void setBoundCodeDsa(String boundCodeDsa) {
        this.boundCodeDsa = boundCodeDsa;
    }

    public String getStatusDocVpbankStr() {
        return statusDocVpbankStr;
    }

    public void setStatusDocVpbankStr(String statusDocVpbankStr) {
        this.statusDocVpbankStr = statusDocVpbankStr;
    }

    public String getGroupDocStatusStr() {
        return groupDocStatusStr;
    }

    public void setGroupDocStatusStr(String groupDocStatusStr) {
        this.groupDocStatusStr = groupDocStatusStr;
    }

    public String getDetailStatusDocVpbankStr() {
        return detailStatusDocVpbankStr;
    }

    public void setDetailStatusDocVpbankStr(String detailStatusDocVpbankStr) {
        this.detailStatusDocVpbankStr = detailStatusDocVpbankStr;
    }

    public String getDocReturnStatusStr() {
        return docReturnStatusStr;
    }

    public void setDocReturnStatusStr(String docReturnStatusStr) {
        this.docReturnStatusStr = docReturnStatusStr;
    }

    public String getNoteStatusDocVpbank() {
        return noteStatusDocVpbank;
    }

    public void setNoteStatusDocVpbank(String noteStatusDocVpbank) {
        this.noteStatusDocVpbank = noteStatusDocVpbank;
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

    public String getLimitTsRiskStr() {
        return limitTsRiskStr;
    }

    public void setLimitTsRiskStr(String limitTsRiskStr) {
        this.limitTsRiskStr = limitTsRiskStr;
    }

    public String getQdeStatusStr() {
        return qdeStatusStr;
    }

    public void setQdeStatusStr(String qdeStatusStr) {
        this.qdeStatusStr = qdeStatusStr;
    }

    public String getCheckupStatusStr() {
        return checkupStatusStr;
    }

    public void setCheckupStatusStr(String checkupStatusStr) {
        this.checkupStatusStr = checkupStatusStr;
    }

    public String getCheckupReasonStr() {
        return checkupReasonStr;
    }

    public void setCheckupReasonStr(String checkupReasonStr) {
        this.checkupReasonStr = checkupReasonStr;
    }

    public String getBoCheckupCode() {
        return this.boCheckupCode;
    }
    public void setBoCheckupCode(String boCheckupCode) {
        this.boCheckupCode = boCheckupCode;
    }
    public String getBoundCode() {
        return this.boundCode;
    }
    public void setBoundCode(String boundCode) {
        this.boundCode = boundCode;
    }
    public Long getCcLimit() {
        return this.ccLimit;
    }
    public void setCcLimit(Long ccLimit) {
        this.ccLimit = ccLimit;
    }
    public String getCcaCode() {
        return this.ccaCode;
    }
    public void setCcaCode(String ccaCode) {
        this.ccaCode = ccaCode;
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
    public String getDsaCode() {
        return this.dsaCode;
    }
    public void setDsaCode(String dsaCode) {
        this.dsaCode = dsaCode;
    }
    public Long getDsaResultMeetingId() {
        return this.dsaResultMeetingId;
    }
    public void setDsaResultMeetingId(Long dsaResultMeetingId) {
        this.dsaResultMeetingId = dsaResultMeetingId;
    }
    public String getDsaSaleCode() {
        return this.dsaSaleCode;
    }
    public void setDsaSaleCode(String dsaSaleCode) {
        this.dsaSaleCode = dsaSaleCode;
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
    public Date getMeetingDate() {
        return this.meetingDate;
    }
    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }
    public String getNationalId() {
        return this.nationalId;
    }
    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }
    public String getPartnerCode() {
        return this.partnerCode;
    }
    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }
    public String getProvincePhone() {
        return this.provincePhone;
    }
    public void setProvincePhone(String provincePhone) {
        this.provincePhone = provincePhone;
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
    public Long getStatus() {
        return this.status;
    }
    public void setStatus(Long status) {
        this.status = status;
    }
    public String getTlBoCheckupCode() {
        return this.tlBoCheckupCode;
    }
    public void setTlBoCheckupCode(String tlBoCheckupCode) {
        this.tlBoCheckupCode = tlBoCheckupCode;
    }
    public String getTlCcaCode() {
        return this.tlCcaCode;
    }
    public void setTlCcaCode(String tlCcaCode) {
        this.tlCcaCode = tlCcaCode;
    }
    public String getTlDsaCode() {
        return this.tlDsaCode;
    }
    public void setTlDsaCode(String tlDsaCode) {
        this.tlDsaCode = tlDsaCode;
    }
    public Date getUpdatedDate() {
        return this.updatedDate;
    }
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
    public String getUpdatedUser() {
        return this.updatedUser;
    }
    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }
    public Long getUplLimit() {
        return this.uplLimit;
    }
    public void setUplLimit(Long uplLimit) {
        this.uplLimit = uplLimit;
    }
    public Date getValidDate() {
        return this.validDate;
    }
    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }
}
