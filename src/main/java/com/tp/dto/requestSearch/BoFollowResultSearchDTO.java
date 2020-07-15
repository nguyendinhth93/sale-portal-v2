package com.tp.dto.requestSearch;

import java.util.Date;
import java.util.List;

/**
 * Created by hopnv on 24/07/2017.
 */
public class BoFollowResultSearchDTO extends RequestSearchDTO {

    private String customerName;
    private String customerPhone;
    private String nationalId;
    private String losCode;
    private Date toLosDateFrom;
    private Date toLosDateTo;
    private String statusDocVpBank;
    private String groupDocStatus;
    private String detailDocStatus;
    private Date toFiDateFrom;
    private Date toFiDateTo;
    private Date returnQdeDateFrom;
    private Date returnQdeDateTo;
    private Date assigneDateFrom;
    private Date assigneDateTo;

    private String tlBoFollow;
    private String boFollow;
    private String assigneStatus;
    private List<String> provinceList;
    private List<String> boFollows;


    public BoFollowResultSearchDTO(String customerName, String customerPhone, String nationalId, String losCode, Date toLosDateFrom, Date toLosDateTo, String statusDocVpBank, String groupDocStatus, String detailDocStatus, Date toFiDateFrom, Date toFiDateTo, Date returnQdeDateFrom, Date returnQdeDateTo) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.nationalId = nationalId;
        this.losCode = losCode;
        this.toLosDateFrom = toLosDateFrom;
        this.toLosDateTo = toLosDateTo;
        this.statusDocVpBank = statusDocVpBank;
        this.groupDocStatus = groupDocStatus;
        this.detailDocStatus = detailDocStatus;
        this.toFiDateFrom = toFiDateFrom;
        this.toFiDateTo = toFiDateTo;
        this.returnQdeDateFrom = returnQdeDateFrom;
        this.returnQdeDateTo = returnQdeDateTo;
    }

    public BoFollowResultSearchDTO() {
    }

    public List<String> getProvinceList() {
        return provinceList;
    }

    public void setProvinceList(List<String> provinceList) {
        this.provinceList = provinceList;
    }

    public List<String> getBoFollows() {
        return boFollows;
    }

    public void setBoFollows(List<String> boFollows) {
        this.boFollows = boFollows;
    }

    public Date getAssigneDateFrom() {
        return assigneDateFrom;
    }

    public void setAssigneDateFrom(Date assigneDateFrom) {
        this.assigneDateFrom = assigneDateFrom;
    }

    public Date getAssigneDateTo() {
        return assigneDateTo;
    }

    public void setAssigneDateTo(Date assigneDateTo) {
        this.assigneDateTo = assigneDateTo;
    }

    public String getAssigneStatus() {
        return assigneStatus;
    }

    public void setAssigneStatus(String assigneStatus) {
        this.assigneStatus = assigneStatus;
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

    public String getLosCode() {
        return losCode;
    }

    public void setLosCode(String losCode) {
        this.losCode = losCode;
    }

    public Date getToLosDateFrom() {
        return toLosDateFrom;
    }

    public void setToLosDateFrom(Date toLosDateFrom) {
        this.toLosDateFrom = toLosDateFrom;
    }

    public Date getToLosDateTo() {
        return toLosDateTo;
    }

    public void setToLosDateTo(Date toLosDateTo) {
        this.toLosDateTo = toLosDateTo;
    }

    public String getStatusDocVpBank() {
        return statusDocVpBank;
    }

    public void setStatusDocVpBank(String statusDocVpBank) {
        this.statusDocVpBank = statusDocVpBank;
    }

    public String getGroupDocStatus() {
        return groupDocStatus;
    }

    public void setGroupDocStatus(String groupDocStatus) {
        this.groupDocStatus = groupDocStatus;
    }

    public String getDetailDocStatus() {
        return detailDocStatus;
    }

    public void setDetailDocStatus(String detailDocStatus) {
        this.detailDocStatus = detailDocStatus;
    }

    public Date getToFiDateFrom() {
        return toFiDateFrom;
    }

    public void setToFiDateFrom(Date toFiDateFrom) {
        this.toFiDateFrom = toFiDateFrom;
    }

    public Date getToFiDateTo() {
        return toFiDateTo;
    }

    public void setToFiDateTo(Date toFiDateTo) {
        this.toFiDateTo = toFiDateTo;
    }

    public Date getReturnQdeDateFrom() {
        return returnQdeDateFrom;
    }

    public void setReturnQdeDateFrom(Date returnQdeDateFrom) {
        this.returnQdeDateFrom = returnQdeDateFrom;
    }

    public Date getReturnQdeDateTo() {
        return returnQdeDateTo;
    }

    public void setReturnQdeDateTo(Date returnQdeDateTo) {
        this.returnQdeDateTo = returnQdeDateTo;
    }
}
