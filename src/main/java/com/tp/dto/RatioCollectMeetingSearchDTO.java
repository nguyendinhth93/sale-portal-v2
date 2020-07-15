package com.tp.dto;

import com.tp.dto.requestSearch.RequestSearchDTO;

import java.util.Date;
import java.util.List;

/**
 * Created by Dinh Nguyen on 06/09/2018.
 */
public class RatioCollectMeetingSearchDTO extends RequestSearchDTO {

    private Date fromDate;
    private Date toDate;
    private String provinceCode;
    private String districtCode;
    private String jtlDsaCode;

    public RatioCollectMeetingSearchDTO() {
    }

    public RatioCollectMeetingSearchDTO(Date fromDate, Date toDate, String provinceCode, String districtCode, String jtlDsaCode) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.provinceCode = provinceCode;
        this.districtCode = districtCode;
        this.jtlDsaCode = jtlDsaCode;
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

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
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
}
