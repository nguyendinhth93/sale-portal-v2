package com.tp.dto.requestSearch;

/**
 * Created by user on 11/09/2017.
 */
public class MenuRequestSearchDTO extends RequestSearchDTO {
    private Long parentId;
    private String name;
    private String code;
    private String desc;
    private Long status;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
