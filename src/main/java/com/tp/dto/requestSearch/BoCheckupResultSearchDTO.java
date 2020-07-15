package com.tp.dto.requestSearch;

import java.util.Date;
import java.util.List;

/**
 * Created by hopnv on 24/07/2017.
 */
public class BoCheckupResultSearchDTO extends RequestSearchDTO {

    private String customerName;
    private String customerPhone;
    private String nationalId;
    private Date fromMeetDate;
    private Date toMeetDate;
    private Date receivedDsaDateFrom;
    private Date receivedDsaDateTo;
    private Date returnDsaDateFrom;
    private Date returnDsaDateTo;
    private Date sendQdeDateFrom;
    private Date sendQdeDateTo;
    private Long status;
    private Long qdeStatus;
    private Long checkupStatus;
    private String staffBoCheckup;
    private String sendToFollow;
    private String assigneStatus;
    private List<String> provinceList;
    private List<String> boCheckups;



    public BoCheckupResultSearchDTO(String customerName, String customerPhone, String nationalId, Date fromMeetDate, Date toMeetDate) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.nationalId = nationalId;
        this.fromMeetDate = fromMeetDate;
        this.toMeetDate = toMeetDate;
    }

    public List<String> getBoCheckups() {
        return boCheckups;
    }

    public void setBoCheckups(List<String> boCheckups) {
        this.boCheckups = boCheckups;
    }

    public List<String> getProvinceList() {
        return provinceList;
    }

    public void setProvinceList(List<String> provinceList) {
        this.provinceList = provinceList;
    }

    public BoCheckupResultSearchDTO() {
    }

    public String getAssigneStatus() {
        return assigneStatus;
    }

    public void setAssigneStatus(String assigneStatus) {
        this.assigneStatus = assigneStatus;
    }

    public String getSendToFollow() {
        return sendToFollow;
    }

    public void setSendToFollow(String sendToFollow) {
        this.sendToFollow = sendToFollow;
    }

    public String getStaffBoCheckup() {
        return staffBoCheckup;
    }

    public void setStaffBoCheckup(String staffBoCheckup) {
        this.staffBoCheckup = staffBoCheckup;
    }

    public Date getReceivedDsaDateFrom() {
        return receivedDsaDateFrom;
    }

    public void setReceivedDsaDateFrom(Date receivedDsaDateFrom) {
        this.receivedDsaDateFrom = receivedDsaDateFrom;
    }

    public Date getReceivedDsaDateTo() {
        return receivedDsaDateTo;
    }

    public void setReceivedDsaDateTo(Date receivedDsaDateTo) {
        this.receivedDsaDateTo = receivedDsaDateTo;
    }

    public Date getReturnDsaDateFrom() {
        return returnDsaDateFrom;
    }

    public void setReturnDsaDateFrom(Date returnDsaDateFrom) {
        this.returnDsaDateFrom = returnDsaDateFrom;
    }

    public Date getReturnDsaDateTo() {
        return returnDsaDateTo;
    }

    public void setReturnDsaDateTo(Date returnDsaDateTo) {
        this.returnDsaDateTo = returnDsaDateTo;
    }

    public Date getSendQdeDateFrom() {
        return sendQdeDateFrom;
    }

    public void setSendQdeDateFrom(Date sendQdeDateFrom) {
        this.sendQdeDateFrom = sendQdeDateFrom;
    }

    public Date getSendQdeDateTo() {
        return sendQdeDateTo;
    }

    public void setSendQdeDateTo(Date sendQdeDateTo) {
        this.sendQdeDateTo = sendQdeDateTo;
    }

    public Long getCheckupStatus() {
        return checkupStatus;
    }

    public void setCheckupStatus(Long checkupStatus) {
        this.checkupStatus = checkupStatus;
    }

    public Long getQdeStatus() {
        return qdeStatus;
    }

    public void setQdeStatus(Long qdeStatus) {
        this.qdeStatus = qdeStatus;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public Date getFromMeetDate() {
        return fromMeetDate;
    }

    public void setFromMeetDate(Date fromMeetDate) {
        this.fromMeetDate = fromMeetDate;
    }

    public Date getToMeetDate() {
        return toMeetDate;
    }

    public void setToMeetDate(Date toMeetDate) {
        this.toMeetDate = toMeetDate;
    }
}
