package com.tp.dto;

import com.tp.util.DataUtil;

import java.io.Serializable;
import java.util.Date;

public class DsaResultMeetingDTO extends BaseDTO implements Serializable {
public static enum COLUMNS {ASSIGNED, ASSIGNEDDATE, ASSIGNEDUSER, BOUNDCODE, BOUNDCODEDSA, CALLDATE, CCACODE, CCAID, CCANAME, CCANOTE, CCAREASON, CREATEDDATE, CREATEDUSER, CUSTOMERNAME, CUSTOMERPHONE, DISTRICTMEETINGID, DISTRICTMEETINGNAME, DSACODE, DSAID, DSANOTE, ID, LIMIT, LIMITCARD, MEETINGADDRESS, MEETINGDATE, MEETINGREASON, MEETINGREASONNAME, MEETINGRESULT, MEETINGRESULTNAME, MEETINGTIME, NATIONALID, PARTNERID, PARTNERNAME, PROVINCELEAD, PROVINCEMEETINGID, PROVINCEMEETINGNAME, SCORERANGE, STATUS, TLDSACODE, TLDSAID, UPDATEDDATE, UPDATEDUSER, VALIDDATE, VOUCHERHEADERID, EXCLUSE_ID_LIST};
    private Long assigned;
    private Date assignedDate;
    private String assignedUser;
    private String boundCode;
    private String boundCodeDsa;
    private Date callDate;
    private String jtlCcaId;
    private String jtlCcaName;
    private String jtlCcaCode;
    private String ccaCode;
    private String ccaId;
    private String ccaName;
    private String ccaNote;
    private String ccaReason;
    private Date createdDate;
    private String createdUser;
    private String customerName;
    private String customerPhone;
    private String districtMeetingId;
    private String districtMeetingName;
    private String dsaCode;
    private Long dsaId;
    private String dsaNote;
    private Long id;
    private Long limit;
    private Long limitCard;
    private String meetingAddress;
    private Date meetingDate;
    private Long meetingReason;
    private String meetingReasonName;
    private Long meetingResult;
    private String meetingResultName;
    private String meetingTime;
    private String nationalId;
    private String partnerId;
    private String partnerName;
    private String provinceLead;
    private String provinceMeetingId;
    private String provinceMeetingName;
    private String scoreRange;
    private Long status;
    private String tlDsaCode;
    private Long tlDsaId;
    private Date updatedDate;
    private String updatedUser;
    private Date validDate;
    private String voucherHeaderId;
    private Date ccaMeetingDate;
    private String dbSource;
    private Date postDate;
    private Long acceptStatus;
    private String acceptStatusName;
//    private boolean acceptstatusvalue;
    private String provinceMeetingCode;
    private String districtMeetingCode;
    private Long keepStatus;
    private boolean keepStatusValue;
    private Long returnCca;
    private String returnCcaName;
    private boolean returnCcaValue;
    private Date returnCcaDate;
    private String partnerCode;
    private Long productType;


    public DsaResultMeetingDTO(Long assigned, Date assignedDate, String assignedUser, String boundCode, String boundCodeDsa, Date callDate, String jtlCcaId, String jtlCcaName, String ccaCode, String ccaId, String ccaName, String ccaNote, String ccaReason, Date createdDate, String createdUser, String customerName, String customerPhone, String districtMeetingId, String districtMeetingName, String dsaCode, Long dsaId, String dsaNote, Long id, Long limit, Long limitCard, String meetingAddress, Date meetingDate, Long meetingReason, String meetingReasonName, Long meetingResult, String meetingResultName, String meetingTime, String nationalId, String partnerId, String partnerName, String provinceLead, String provinceMeetingId, String provinceMeetingName, String scoreRange, Long status, String tlDsaCode, Long tlDsaId, Date updatedDate, String updatedUser, Date validDate, String voucherHeaderId, Date ccaMeetingDate, String dbSource, Long acceptStatus, boolean acceptStatusValue) {
        this.assigned = assigned;
        this.assignedDate = assignedDate;
        this.assignedUser = assignedUser;
        this.boundCode = boundCode;
        this.boundCodeDsa = boundCodeDsa;
        this.callDate = callDate;
        this.jtlCcaId = jtlCcaId;
        this.jtlCcaName = jtlCcaName;
        this.ccaCode = ccaCode;
        this.ccaId = ccaId;
        this.ccaName = ccaName;
        this.ccaNote = ccaNote;
        this.ccaReason = ccaReason;
        this.createdDate = createdDate;
        this.createdUser = createdUser;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.districtMeetingId = districtMeetingId;
        this.districtMeetingName = districtMeetingName;
        this.dsaCode = dsaCode;
        this.dsaId = dsaId;
        this.dsaNote = dsaNote;
        this.id = id;
        this.limit = limit;
        this.limitCard = limitCard;
        this.meetingAddress = meetingAddress;
        this.meetingDate = meetingDate;
        this.meetingReason = meetingReason;
        this.meetingReasonName = meetingReasonName;
        this.meetingResult = meetingResult;
        this.meetingResultName = meetingResultName;
        this.meetingTime = meetingTime;
        this.nationalId = nationalId;
        this.partnerId = partnerId;
        this.partnerName = partnerName;
        this.provinceLead = provinceLead;
        this.provinceMeetingId = provinceMeetingId;
        this.provinceMeetingName = provinceMeetingName;
        this.scoreRange = scoreRange;
        this.status = status;
        this.tlDsaCode = tlDsaCode;
        this.tlDsaId = tlDsaId;
        this.updatedDate = updatedDate;
        this.updatedUser = updatedUser;
        this.validDate = validDate;
        this.voucherHeaderId = voucherHeaderId;
        this.ccaMeetingDate = ccaMeetingDate;
        this.dbSource = dbSource;
        this.acceptStatus = acceptStatus;
    }

    public DsaResultMeetingDTO() {
    }

    public Long getProductType() {
        return productType;
    }

    public void setProductType(Long productType) {
        this.productType = productType;
    }

    public String getReturnCcaName() {
        return returnCcaName;
    }

    public void setReturnCcaName(String returnCcaName) {
        this.returnCcaName = returnCcaName;
    }

    public String getAcceptStatusName() {
        return acceptStatusName;
    }

    public void setAcceptStatusName(String acceptStatusName) {
        this.acceptStatusName = acceptStatusName;
    }

    public boolean isReturnCcaValue() {
        return returnCcaValue;
    }

    public Date getReturnCcaDate() {
        return returnCcaDate;
    }

    public void setReturnCcaDate(Date returnCcaDate) {
        this.returnCcaDate = returnCcaDate;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public void setReturnCcaValue(boolean returnCcaValue) {
        this.returnCcaValue = returnCcaValue;
    }

    public boolean isKeepStatusValue() {
        return keepStatusValue;
    }

    public void setKeepStatusValue(boolean keepStatusValue) {
        this.keepStatusValue = keepStatusValue;
    }

    public Long getKeepStatus() {
        return keepStatus;
    }

    public void setKeepStatus(Long keepStatus) {
        this.keepStatus = keepStatus;
    }

    public String getProvinceMeetingCode() {
        return provinceMeetingCode;
    }

    public void setProvinceMeetingCode(String provinceMeetingCode) {
        this.provinceMeetingCode = provinceMeetingCode;
    }

    public String getDistrictMeetingCode() {
        return districtMeetingCode;
    }

    public void setDistrictMeetingCode(String districtMeetingCode) {
        this.districtMeetingCode = districtMeetingCode;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public Long getAcceptStatus() {
        return acceptStatus;
    }

    public void setAcceptStatus(Long acceptStatus) {
        this.acceptStatus = acceptStatus;
    }

    public String getDbSource() {
        return dbSource;
    }

    public void setDbSource(String dbSource) {
        this.dbSource = dbSource;
    }

    public Date getCcaMeetingDate() {
        return ccaMeetingDate;
    }

    public void setCcaMeetingDate(Date ccaMeetingDate) {
        this.ccaMeetingDate = ccaMeetingDate;
    }

    public Long getAssigned() {
        return this.assigned;
    }
    public void setAssigned(Long assigned) {
        this.assigned = assigned;
    }
    public Date getAssignedDate() {
        return this.assignedDate;
    }
    public void setAssignedDate(Date assignedDate) {
        this.assignedDate = assignedDate;
    }
    public String getAssignedUser() {
        return this.assignedUser;
    }
    public void setAssignedUser(String assignedUser) {
        this.assignedUser = assignedUser;
    }
    public String getBoundCode() {
        return this.boundCode;
    }
    public void setBoundCode(String boundCode) {
        this.boundCode = boundCode;
    }
    public String getBoundCodeDsa() {
        return this.boundCodeDsa;
    }
    public void setBoundCodeDsa(String boundCodeDsa) {
        this.boundCodeDsa = boundCodeDsa;
    }
    public Date getCallDate() {
        return this.callDate;
    }
    public void setCallDate(Date callDate) {
        this.callDate = callDate;
    }
    public String getCcaCode() {
        return this.ccaCode;
    }
    public void setCcaCode(String ccaCode) {
        this.ccaCode = ccaCode;
    }
    public String getCcaId() {
        return this.ccaId;
    }
    public void setCcaId(String ccaId) {
        this.ccaId = ccaId;
    }
    public String getCcaName() {
        return this.ccaName;
    }
    public void setCcaName(String ccaName) {
        this.ccaName = ccaName;
    }
    public String getCcaNote() {
        return this.ccaNote;
    }
    public void setCcaNote(String ccaNote) {
        this.ccaNote = ccaNote;
    }
    public String getCcaReason() {
        return this.ccaReason;
    }
    public void setCcaReason(String ccaReason) {
        this.ccaReason = ccaReason;
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
    public String getDistrictMeetingId() {
        return this.districtMeetingId;
    }
    public void setDistrictMeetingId(String districtMeetingId) {
        this.districtMeetingId = districtMeetingId;
    }
    public String getDistrictMeetingName() {
        return this.districtMeetingName;
    }
    public void setDistrictMeetingName(String districtMeetingName) {
        this.districtMeetingName = districtMeetingName;
    }
    public String getDsaCode() {
        return this.dsaCode;
    }
    public void setDsaCode(String dsaCode) {
        this.dsaCode = dsaCode;
    }
    public Long getDsaId() {
        return this.dsaId;
    }
    public void setDsaId(Long dsaId) {
        this.dsaId = dsaId;
    }
    public String getDsaNote() {
        return this.dsaNote;
    }
    public void setDsaNote(String dsaNote) {
        this.dsaNote = dsaNote;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getLimit() {
        return this.limit;
    }
    public void setLimit(Long limit) {
        this.limit = limit;
    }
    public Long getLimitCard() {
        return this.limitCard;
    }
    public void setLimitCard(Long limitCard) {
        this.limitCard = limitCard;
    }
    public String getMeetingAddress() {
        return this.meetingAddress;
    }
    public void setMeetingAddress(String meetingAddress) {
        this.meetingAddress = meetingAddress;
    }
    public Date getMeetingDate() {
        return this.meetingDate;
    }
    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }
    public Long getMeetingReason() {
        return this.meetingReason;
    }
    public void setMeetingReason(Long meetingReason) {
        this.meetingReason = meetingReason;
    }
    public String getMeetingReasonName() {
        return this.meetingReasonName;
    }
    public void setMeetingReasonName(String meetingReasonName) {
        this.meetingReasonName = meetingReasonName;
    }
    public Long getMeetingResult() {
        return this.meetingResult;
    }
    public void setMeetingResult(Long meetingResult) {
        this.meetingResult = meetingResult;
    }
    public String getMeetingResultName() {
        return this.meetingResultName;
    }
    public void setMeetingResultName(String meetingResultName) {
        this.meetingResultName = meetingResultName;
    }
    public String getMeetingTime() {
        return this.meetingTime;
    }
    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }
    public String getNationalId() {
        return this.nationalId;
    }
    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }
    public String getPartnerId() {
        return this.partnerId;
    }
    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }
    public String getPartnerName() {
        return this.partnerName;
    }
    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }
    public String getProvinceLead() {
        return this.provinceLead;
    }
    public void setProvinceLead(String provinceLead) {
        this.provinceLead = provinceLead;
    }
    public String getProvinceMeetingId() {
        return this.provinceMeetingId;
    }
    public void setProvinceMeetingId(String provinceMeetingId) {
        this.provinceMeetingId = provinceMeetingId;
    }
    public String getProvinceMeetingName() {
        return this.provinceMeetingName;
    }
    public void setProvinceMeetingName(String provinceMeetingName) {
        this.provinceMeetingName = provinceMeetingName;
    }
    public String getScoreRange() {
        return this.scoreRange;
    }
    public void setScoreRange(String scoreRange) {
        this.scoreRange = scoreRange;
    }
    public Long getStatus() {
        return this.status;
    }
    public void setStatus(Long status) {
        this.status = status;
    }
    public String getTlDsaCode() {
        return this.tlDsaCode;
    }
    public void setTlDsaCode(String tlDsaCode) {
        this.tlDsaCode = tlDsaCode;
    }
    public Long getTlDsaId() {
        return this.tlDsaId;
    }
    public void setTlDsaId(Long tlDsaId) {
        this.tlDsaId = tlDsaId;
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
    public Date getValidDate() {
        return this.validDate;
    }
    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }
    public String getVoucherHeaderId() {
        return this.voucherHeaderId;
    }
    public void setVoucherHeaderId(String voucherHeaderId) {
        this.voucherHeaderId = voucherHeaderId;
    }

    public String getJtlCcaId() {
        return jtlCcaId;
    }

    public void setJtlCcaId(String jtlCcaId) {
        this.jtlCcaId = jtlCcaId;
    }

    public String getJtlCcaName() {
        return jtlCcaName;
    }

    public void setJtlCcaName(String jtlCcaName) {
        this.jtlCcaName = jtlCcaName;
    }

    public String getJtlCcaCode() {
        return jtlCcaCode;
    }

    public void setJtlCcaCode(String jtlCcaCode) {
        this.jtlCcaCode = jtlCcaCode;
    }

    public Long getReturnCca() {
        return returnCca;
    }

    public void setReturnCca(Long returnCca) {
        this.returnCca = returnCca;
    }
}
