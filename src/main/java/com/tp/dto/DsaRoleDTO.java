package com.tp.dto;

import java.io.Serializable;
import java.util.Date;
public class DsaRoleDTO extends BaseDTO implements Serializable {
public static enum COLUMNS {CREATEDBY, CREATEDDATE, FULLNAME, ID, LEADUSERID, ROLE, STATUS, UPDATEDBY, UPDATEDDATE, USERNAME, EXCLUSE_ID_LIST};    private String createdBy;
    private Date createdDate;
    private String fullName;
    private Long id;
    private Long leadUserId;
    private String role;
    private Long status;
    private String updatedBy;
    private Date updatedDate;
    private String userName;
    public String getCreatedBy() {
        return this.createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public Date getCreatedDate() {
        return this.createdDate;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    public String getFullName() {
        return this.fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getLeadUserId() {
        return this.leadUserId;
    }
    public void setLeadUserId(Long leadUserId) {
        this.leadUserId = leadUserId;
    }
    public String getRole() {
        return this.role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public Long getStatus() {
        return this.status;
    }
    public void setStatus(Long status) {
        this.status = status;
    }
    public String getUpdatedBy() {
        return this.updatedBy;
    }
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    public Date getUpdatedDate() {
        return this.updatedDate;
    }
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
