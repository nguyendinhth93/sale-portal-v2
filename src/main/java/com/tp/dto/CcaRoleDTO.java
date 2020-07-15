package com.tp.dto;

import java.io.Serializable;
import java.util.Date;
public class CcaRoleDTO extends BaseDTO implements Serializable {
public static enum COLUMNS {CREATEDBY, CREATEDDATE, ID, ROLE, STATUS, TEAMID, UPDATEDBY, UPDATEDDATE, USERID, EXCLUSE_ID_LIST};    private String createdBy;
    private Date createdDate;
    private Long id;
    private String role;
    private Long status;
    private Long teamId;
    private String updatedBy;
    private Date updatedDate;
    private Long userId;
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
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public Long getTeamId() {
        return this.teamId;
    }
    public void setTeamId(Long teamId) {
        this.teamId = teamId;
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
    public Long getUserId() {
        return this.userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
