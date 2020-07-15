package com.tp.dto;

import com.tp.dto.requestSearch.RequestSearchDTO;

/**
 * Created by hopnv on 28/07/2017.
 */
public class OptionSetInputSearchDTO extends RequestSearchDTO {

    private String actionCode;
    private String name;
    private String value;
    private String description;
    private String code;
    private Long [] brandId;

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long[] getBrandId() {
        return brandId;
    }

    public void setBrandId(Long[] brandId) {
        this.brandId = brandId;
    }
}
