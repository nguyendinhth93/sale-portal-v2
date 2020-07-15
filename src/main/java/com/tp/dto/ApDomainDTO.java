package com.tp.dto;


import java.io.Serializable;
import java.util.Date;

public class ApDomainDTO extends BaseDTO implements Serializable {

    private Long apDomainId;
    private String code;
    private String oldCode;
    private String name;
    private Integer status;
    private String type;
    private String value;
    private String description;
    private String createBy;
    private Date createDate;
    private String lastUpdateBy;
    private Date lastUpdateDate;

    public ApDomainDTO(Long apDomainId, String code, String name, Integer status, String type, String value) {
        this.apDomainId = apDomainId;
        this.code = code;
        this.name = name;
        this.status = status;
        this.type = type;
        this.value = value;
    }

    public ApDomainDTO() {
    }

    public Long getApDomainId() {
        return this.apDomainId;
    }

    public void setApDomainId(Long apDomainId) {
        this.apDomainId = apDomainId;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getOldCode() {
        return oldCode;
    }

    public void setOldCode(String oldCode) {
        this.oldCode = oldCode;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
