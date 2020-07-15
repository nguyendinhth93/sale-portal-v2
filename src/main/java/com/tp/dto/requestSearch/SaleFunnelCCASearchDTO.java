package com.tp.dto.requestSearch;

import java.util.Date;
import java.util.List;

/**
 * Created by Dinh Nguyen on 06/09/2018.
 */
public class SaleFunnelCCASearchDTO extends RequestSearchDTO {

    private String projectCode;
    private List<String> boundCodes;
    private String province;
    private String tlCcaCode;
    private String ccaCode;
    private Date fromDate;
    private Date toDate;
    private String dataSource;


    public SaleFunnelCCASearchDTO() {
    }

    public SaleFunnelCCASearchDTO(String projectCode, List<String> boundCodes, String province, String tlCcaCode, String ccaCode, Date fromDate, Date toDate) {
        this.projectCode = projectCode;
        this.boundCodes = boundCodes;
        this.province = province;
        this.tlCcaCode = tlCcaCode;
        this.ccaCode = ccaCode;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public List<String> getBoundCodes() {
        return boundCodes;
    }

    public void setBoundCodes(List<String> boundCodes) {
        this.boundCodes = boundCodes;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getTlCcaCode() {
        return tlCcaCode;
    }

    public void setTlCcaCode(String tlCcaCode) {
        this.tlCcaCode = tlCcaCode;
    }

    public String getCcaCode() {
        return ccaCode;
    }

    public void setCcaCode(String ccaCode) {
        this.ccaCode = ccaCode;
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
