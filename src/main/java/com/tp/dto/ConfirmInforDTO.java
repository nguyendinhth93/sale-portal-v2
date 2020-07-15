package com.tp.dto;

import java.io.Serializable;
import java.util.Date;
public class ConfirmInforDTO extends BaseDTO implements Serializable {
public static enum COLUMNS {CREATEDBY, CREATEDDATE, ID, ISDN, LATESTDATECALL, LATESTRESPONSECODE, LATESTRESPONSEDES, PROGRAMCODE, RECEIVEDATE, STATUS, STATUSDES, TOTALTIMECALLAPI, TRANSID, UPDATEDBY, UPDATEDDATE, EXCLUSE_ID_LIST};    private String createdBy;
    private Date createdDate;
    private Long id;
    private String isdn;
    private Date latestDateCall;
    private String latestResponseCode;
    private String latestResponseDes;
    private String programCode;
    private Date receiveDate;
    private Integer status;
    private Integer statusDes;
    private Long totalTimeCallAPI;
    private String transId;
    private String updatedBy;
    private Date updatedDate;
    public String getCreatedBy() {
        return this.createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public Date getCreatedDate() {
        return this.createdDate;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getIsdn() {
        return this.isdn;
    }
    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }
    public Date getLatestDateCall() {
        return this.latestDateCall;
    }
    public void setLatestDateCall(Date latestDateCall) {
        this.latestDateCall = latestDateCall;
    }
    public String getLatestResponseCode() {
        return this.latestResponseCode;
    }
    public void setLatestResponseCode(String latestResponseCode) {
        this.latestResponseCode = latestResponseCode;
    }
    public String getLatestResponseDes() {
        return this.latestResponseDes;
    }
    public void setLatestResponseDes(String latestResponseDes) {
        this.latestResponseDes = latestResponseDes;
    }
    public String getProgramCode() {
        return this.programCode;
    }
    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }
    public Date getReceiveDate() {
        return this.receiveDate;
    }
    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }
    public Integer getStatus() {
        return this.status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getStatusDes() {
        return this.statusDes;
    }
    public void setStatusDes(Integer statusDes) {
        this.statusDes = statusDes;
    }
    public Long getTotalTimeCallAPI() {
        return this.totalTimeCallAPI;
    }
    public void setTotalTimeCallAPI(Long totalTimeCallAPI) {
        this.totalTimeCallAPI = totalTimeCallAPI;
    }
    public String getTransId() {
        return this.transId;
    }
    public void setTransId(String transId) {
        this.transId = transId;
    }
    public String getUpdatedBy() {
        return this.updatedBy;
    }
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    public Date getUpdatedDate() {
        return this.updatedDate;
    }
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
