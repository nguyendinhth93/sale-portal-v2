package com.tp.dto;

import java.io.Serializable;
import java.util.Date;
public class AreaDTO extends BaseDTO implements Serializable {
public static enum COLUMNS {CODE, CREATEDATE, CREATEUSER, ID, NAME, PARENTCODE, STATUS, TYPE, UPDATEDATE, UPDATEUSER, EXCLUSE_ID_LIST};    private String code;
    private Date createDate;
    private String createUser;
    private Long id;
    private String name;
    private String parentCode;
    private Integer status;
    private String type;
    private Date updateDate;
    private String updateUser;
    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public Date getCreateDate() {
        return this.createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public String getCreateUser() {
        return this.createUser;
    }
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getParentCode() {
        return this.parentCode;
    }
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
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
    public Date getUpdateDate() {
        return this.updateDate;
    }
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    public String getUpdateUser() {
        return this.updateUser;
    }
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}
