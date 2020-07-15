package com.tp.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Created by hopnv on 28/07/2017.
 */
public class OptionSetValueDTO extends BaseDTO implements Serializable {
    private String createBy;
    private Date createDate;
    private String description;
    private Long displayOrder;
    private Long id;
    private Date lastUpdateDate;
    private String lastUpdateBy;
    private String name;
    private String value;
    private String code;
    private String oldCode;
    private String oldName;
    private String status;
    private Long optionSetId;

    public OptionSetValueDTO() {
    }

    public String getOldCode() {
        return oldCode;
    }

    public void setOldCode(String oldCode) {
        this.oldCode = oldCode;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDisplayOrder() {
        return this.displayOrder;
    }

    public void setDisplayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
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

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getOptionSetId() {
        return this.optionSetId;
    }

    public void setOptionSetId(Long optionSetId) {
        this.optionSetId = optionSetId;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean equals(Object o) {
        if(this == o) {
            return true;
        } else if(o != null && this.getClass() == o.getClass()) {
            OptionSetValueDTO that = (OptionSetValueDTO)o;
            return Objects.equals(this.id, that.id);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.id});
    }

    @Override
    public String toString() {
        return "OptionSetValueDTO{" +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}

