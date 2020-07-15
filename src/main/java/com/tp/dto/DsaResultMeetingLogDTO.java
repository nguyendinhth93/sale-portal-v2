package com.tp.dto;

import java.io.Serializable;
import java.util.Date;
public class DsaResultMeetingLogDTO extends BaseDTO implements Serializable {
public static enum COLUMNS {CREATEDUSER, DISTRICTMEETINGID, DSACODE, DSANOTE, DSARESULTMEETINGID, ID, MEETINGDATE, MEETINGREASON, MEETINGRESULT, PROVINCEMEETINGID, TLDSACODE, UPDATEDDATE, EXCLUSE_ID_LIST};
    private String createdUser;
    private String districtMeetingId;
    private String dsaCode;
    private String dsaNote;
    private Long dsaResultMeetingId;
    private Long id;
    private Date meetingDate;
    private Long meetingReason;
    private Long meetingResult;
    private String provinceMeetingId;
    private String tlDsaCode;
    private Date createdDate;
    private String prefixFolder;
    private String fileNameUpload;
    private Long acceptStatus;
    private Long keepStatus;
    private Long returnCca;
    private Date returnCcaDate;
    private String partnerCode;
    private Long productType;

    public Long getProductType() {
        return productType;
    }

    public void setProductType(Long productType) {
        this.productType = productType;
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

    public Long getReturnCca() {
        return returnCca;
    }

    public void setReturnCca(Long returnCca) {
        this.returnCca = returnCca;
    }

    public Long getKeepStatus() {
        return keepStatus;
    }

    public void setKeepStatus(Long keepStatus) {
        this.keepStatus = keepStatus;
    }

    public Long getAcceptStatus() {
        return acceptStatus;
    }

    public void setAcceptStatus(Long acceptStatus) {
        this.acceptStatus = acceptStatus;
    }

    public String getPrefixFolder() {
        return prefixFolder;
    }

    public void setPrefixFolder(String prefixFolder) {
        this.prefixFolder = prefixFolder;
    }

    public String getFileNameUpload() {
        return fileNameUpload;
    }

    public void setFileNameUpload(String fileNameUpload) {
        this.fileNameUpload = fileNameUpload;
    }

    public String getCreatedUser() {
        return this.createdUser;
    }
    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }
    public String getDistrictMeetingId() {
        return this.districtMeetingId;
    }
    public void setDistrictMeetingId(String districtMeetingId) {
        this.districtMeetingId = districtMeetingId;
    }
    public String getDsaCode() {
        return this.dsaCode;
    }
    public void setDsaCode(String dsaCode) {
        this.dsaCode = dsaCode;
    }
    public String getDsaNote() {
        return this.dsaNote;
    }
    public void setDsaNote(String dsaNote) {
        this.dsaNote = dsaNote;
    }
    public Long getDsaResultMeetingId() {
        return this.dsaResultMeetingId;
    }
    public void setDsaResultMeetingId(Long dsaResultMeetingId) {
        this.dsaResultMeetingId = dsaResultMeetingId;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public Long getMeetingResult() {
        return this.meetingResult;
    }
    public void setMeetingResult(Long meetingResult) {
        this.meetingResult = meetingResult;
    }
    public String getProvinceMeetingId() {
        return this.provinceMeetingId;
    }
    public void setProvinceMeetingId(String provinceMeetingId) {
        this.provinceMeetingId = provinceMeetingId;
    }
    public String getTlDsaCode() {
        return this.tlDsaCode;
    }
    public void setTlDsaCode(String tlDsaCode) {
        this.tlDsaCode = tlDsaCode;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
