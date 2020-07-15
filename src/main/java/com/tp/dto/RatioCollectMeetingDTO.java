package com.tp.dto;

import java.io.Serializable;

public class RatioCollectMeetingDTO extends BaseDTO implements Serializable {

    private String provinceName;
    private String provinceCode;
    private String districtName;
    private String districtCode;
    private String jtlDsaCode;
    private String dsaCode;
    private Long totalAccept;
    private Long totalMeeting;
    private Long totalNotConnected;
    private Long totalFullDocument;
    private Long totalReturnCca;
    private Double ratioFullDocAndAccept;
    private Double ratioFullDocAndNotConnected;
    private Double ratioFullDocAndTotalMeeting;

    public RatioCollectMeetingDTO(String provinceName, String provinceCode, String districtName, String districtCode, String jtlDsaCode, String dsaCode, Long totalAccept, Long totalMeeting, Long totalNotConnected, Long totalFullDocument, Long totalReturnCca, Double ratioFullDocAndAccept, Double ratioFullDocAndNotConnected, Double ratioFullDocAndTotalMeeting) {
        this.provinceName = provinceName;
        this.provinceCode = provinceCode;
        this.districtName = districtName;
        this.districtCode = districtCode;
        this.jtlDsaCode = jtlDsaCode;
        this.dsaCode = dsaCode;
        this.totalAccept = totalAccept;
        this.totalMeeting = totalMeeting;
        this.totalNotConnected = totalNotConnected;
        this.totalFullDocument = totalFullDocument;
        this.totalReturnCca = totalReturnCca;
        this.ratioFullDocAndAccept = ratioFullDocAndAccept;
        this.ratioFullDocAndNotConnected = ratioFullDocAndNotConnected;
        this.ratioFullDocAndTotalMeeting = ratioFullDocAndTotalMeeting;
    }

    public RatioCollectMeetingDTO() {
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getJtlDsaCode() {
        return jtlDsaCode;
    }

    public void setJtlDsaCode(String jtlDsaCode) {
        this.jtlDsaCode = jtlDsaCode;
    }

    public String getDsaCode() {
        return dsaCode;
    }

    public void setDsaCode(String dsaCode) {
        this.dsaCode = dsaCode;
    }

    public Long getTotalAccept() {
        return totalAccept;
    }

    public void setTotalAccept(Long totalAccept) {
        this.totalAccept = totalAccept;
    }

    public Long getTotalMeeting() {
        return totalMeeting;
    }

    public void setTotalMeeting(Long totalMeeting) {
        this.totalMeeting = totalMeeting;
    }

    public Long getTotalNotConnected() {
        return totalNotConnected;
    }

    public void setTotalNotConnected(Long totalNotConnected) {
        this.totalNotConnected = totalNotConnected;
    }

    public Long getTotalFullDocument() {
        return totalFullDocument;
    }

    public void setTotalFullDocument(Long totalFullDocument) {
        this.totalFullDocument = totalFullDocument;
    }

    public Long getTotalReturnCca() {
        return totalReturnCca;
    }

    public void setTotalReturnCca(Long totalReturnCca) {
        this.totalReturnCca = totalReturnCca;
    }

    public Double getRatioFullDocAndAccept() {
        return ratioFullDocAndAccept;
    }

    public void setRatioFullDocAndAccept(Double ratioFullDocAndAccept) {
        this.ratioFullDocAndAccept = ratioFullDocAndAccept;
    }

    public Double getRatioFullDocAndNotConnected() {
        return ratioFullDocAndNotConnected;
    }

    public void setRatioFullDocAndNotConnected(Double ratioFullDocAndNotConnected) {
        this.ratioFullDocAndNotConnected = ratioFullDocAndNotConnected;
    }

    public Double getRatioFullDocAndTotalMeeting() {
        return ratioFullDocAndTotalMeeting;
    }

    public void setRatioFullDocAndTotalMeeting(Double ratioFullDocAndTotalMeeting) {
        this.ratioFullDocAndTotalMeeting = ratioFullDocAndTotalMeeting;
    }
}
