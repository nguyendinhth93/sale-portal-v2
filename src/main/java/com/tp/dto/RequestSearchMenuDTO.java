package com.tp.dto;

import com.tp.dto.requestSearch.RequestSearchDTO;

/**
 * Created by user on 23/08/2017.
 */
public class RequestSearchMenuDTO extends RequestSearchDTO {
    private String name;
    private Long parentId;
    private Integer status;
    private Long appId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }
}
