package com.tp.dto;

import com.tp.dto.requestSearch.RequestSearchDTO;

import java.util.Date;
import java.util.List;

/**
 * Created by Dinh Nguyen on 06/09/2018.
 */
public class DsaResultMeetingSearchDTO extends RequestSearchDTO {

    private String dsaCode;
    private Date assignDate;
    private String provinceMeetingCode;
    private String districtMeetingCode;
    private Date meetDate;
    private Long assigned;
    private Date fromMeetDate;
    private Date toMeetDate;
    // change mutil selected
    private List<String> districtMeetingCodes;
    private List<String> dsaCodes;
    private List<String> meetingResults;
    private Date fromCallDate;
    private Date toCallDate;
    private String customerPhone;
    private Long acceptStatus;

    //0 La meeting moi, 1 la meeting cu
    private Long typeMeeting;
    //bo sung tim kiem cho cca
    private String jtlCcaCode;
    private String ccaCode;
    private String partnerCode;

    public DsaResultMeetingSearchDTO() {
    }

    public DsaResultMeetingSearchDTO(String dsaCode, Date assignDate, String provinceMeetingCode, String districtMeetingCode, Date meetDate, Long assigned, List<String> districtMeetingCodes, List<String> dsaCodes, List<String> meetingResults, Date fromCallDate, Date toCallDate, String customerPhone, Long acceptStatus, Long typeMeeting) {
        this.dsaCode = dsaCode;
        this.assignDate = assignDate;
        this.provinceMeetingCode = provinceMeetingCode;
        this.districtMeetingCode = districtMeetingCode;
        this.meetDate = meetDate;
        this.assigned = assigned;
        this.districtMeetingCodes = districtMeetingCodes;
        this.dsaCodes = dsaCodes;
        this.meetingResults = meetingResults;
        this.fromCallDate = fromCallDate;
        this.toCallDate = toCallDate;
        this.customerPhone = customerPhone;
        this.acceptStatus = acceptStatus;
        this.typeMeeting = typeMeeting;
    }

    public Long getTypeMeeting() {
        return typeMeeting;
    }

    public void setTypeMeeting(Long typeMeeting) {
        this.typeMeeting = typeMeeting;
    }

    public Long getAcceptStatus() {
        return acceptStatus;
    }

    public void setAcceptStatus(Long acceptStatus) {
        this.acceptStatus = acceptStatus;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getDsaCode() {
        return dsaCode;
    }

    public void setDsaCode(String dsaCode) {
        this.dsaCode = dsaCode;
    }

    public List<String> getMeetingResults() {
        return meetingResults;
    }

    public void setMeetingResults(List<String> meetingResults) {
        this.meetingResults = meetingResults;
    }

    public List<String> getDistrictMeetingCodes() {
        return districtMeetingCodes;
    }

    public void setDistrictMeetingCodes(List<String> districtMeetingCodes) {
        this.districtMeetingCodes = districtMeetingCodes;
    }

    public List<String> getDsaCodes() {
        return dsaCodes;
    }

    public void setDsaCodes(List<String> dsaCodes) {
        this.dsaCodes = dsaCodes;
    }

    public Date getFromCallDate() {
        return fromCallDate;
    }

    public void setFromCallDate(Date fromCallDate) {
        this.fromCallDate = fromCallDate;
    }

    public Date getToCallDate() {
        return toCallDate;
    }

    public void setToCallDate(Date toCallDate) {
        this.toCallDate = toCallDate;
    }

    public Long getAssigned() {
        return assigned;
    }

    public void setAssigned(Long assigned) {
        this.assigned = assigned;
    }

    public Date getMeetDate() {
        return meetDate;
    }

    public void setMeetDate(Date meetDate) {
        this.meetDate = meetDate;
    }

    public Date getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(Date assignDate) {
        this.assignDate = assignDate;
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

    public String getJtlCcaCode() {
        return jtlCcaCode;
    }

    public void setJtlCcaCode(String jtlCcaCode) {
        this.jtlCcaCode = jtlCcaCode;
    }

    public String getCcaCode() {
        return ccaCode;
    }

    public void setCcaCode(String ccaCode) {
        this.ccaCode = ccaCode;
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

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }
}
