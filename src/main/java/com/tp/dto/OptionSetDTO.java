package com.tp.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hopnv on 28/07/2017.
 */
public class OptionSetDTO extends BaseDTO {
    private String code;
    private String createBy;
    private Date createDate;
    private String description;
    private Long id;
    private Date lastUpdateDate;
    private String lastUpdateBy;
    private String name;
    private String status;
    private List<OptionSetValueDTO> lsOptionSetValueDto = new ArrayList();

    public OptionSetDTO() {
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getLastUpdateDate() {
        return this.lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public List<OptionSetValueDTO> getLsOptionSetValueDto() {
        return this.lsOptionSetValueDto;
    }

    public void setLsOptionSetValueDto(List<OptionSetValueDTO> lsOptionSetValueDto) {
        this.lsOptionSetValueDto = lsOptionSetValueDto;
    }
}

