package com.tp.dto.requestSearch;

import java.util.Date;

/**
 * Created by Dinh Nguyen on 06/09/2018.
 */
public class CampaignSearchDTO extends RequestSearchDTO {

    private String productName;
    private String programCode;
    private String province;
    private String scores;
    private String latestResponseCode;   //
    private String latestResponseDes;    //
    private Date latestDateCall;
    private Date fromDate;
    private Date toDate;


    public CampaignSearchDTO(Date latestDateCall, String latestResponseCode, String latestResponseDes, String productName, String programCode, String province, String scores, Date fromDate, Date toDate) {
        this.latestDateCall = latestDateCall;
        this.latestResponseCode = latestResponseCode;
        this.latestResponseDes = latestResponseDes;
        this.productName = productName;
        this.programCode = programCode;
        this.province = province;
        this.scores = scores;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public CampaignSearchDTO() {
    }

    public Date getLatestDateCall() {
        return latestDateCall;
    }

    public void setLatestDateCall(Date latestDateCall) {
        this.latestDateCall = latestDateCall;
    }

    public String getLatestResponseCode() {
        return latestResponseCode;
    }

    public void setLatestResponseCode(String latestResponseCode) {
        this.latestResponseCode = latestResponseCode;
    }

    public String getLatestResponseDes() {
        return latestResponseDes;
    }

    public void setLatestResponseDes(String latestResponseDes) {
        this.latestResponseDes = latestResponseDes;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getScores() {
        return scores;
    }

    public void setScores(String scores) {
        this.scores = scores;
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
