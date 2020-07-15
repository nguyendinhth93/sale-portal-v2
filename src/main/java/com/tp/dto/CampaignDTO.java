package com.tp.dto;

import java.io.Serializable;
import java.util.Date;
public class CampaignDTO extends BaseDTO implements Serializable {
public static enum COLUMNS {CONTENT, CREATEDBY, CREATEDDATE, FROMDATE, ID, LATESTDATECALL, LATESTRESPONSECODE, LATESTRESPONSEDES, PRODUCTNAME, PROGRAMCODE, PROVINCE, SCORES, TODATE, TOTAL, TOTALTIMECALLAPI, TRANSID, UPDATEDBY, UPDATEDDATE, EXCLUSE_ID_LIST};    private String content;
    private String createdBy;
    private Date createdDate;
    private Date fromDate;
    private Long id;
    private Date latestDateCall;
    private String latestResponseCode;
    private String latestResponseDes;
    private String productName;
    private String programCode;
    private String province;
    private String scores;
    private Date toDate;
    private Long total;
    private Long totalTimeCallAPI;
    private String transId;
    private String updatedBy;
    private Date updatedDate;
    private Long status;


    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
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
    public Date getFromDate() {
        return this.fromDate;
    }
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public String getProductName() {
        return this.productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProgramCode() {
        return this.programCode;
    }
    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }
    public String getProvince() {
        return this.province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getScores() {
        return this.scores;
    }
    public void setScores(String scores) {
        this.scores = scores;
    }
    public Date getToDate() {
        return this.toDate;
    }
    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
    public Long getTotal() {
        return this.total;
    }
    public void setTotal(Long total) {
        this.total = total;
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
