package com.tp.dto;

import java.io.Serializable;
import java.util.Date;
public class TeamDTO extends BaseDTO implements Serializable {
public static enum COLUMNS {CREATEDDATE, CREATEDUSER, ID, STATUS, TEAMCODE, TEAMLEADID, TEAMNAME, TYPE, UPDATEDDATE, UPDATEDUSER, EXCLUSE_ID_LIST};    private Date createdDate;
    private String createdUser;
    private Long id;
    private Long status;
    private String teamCode;
    private Long teamLeadId;
    private String teamName;
    private Long type;
    private Date updatedDate;
    private String updatedUser;
    public Date getCreatedDate() {
        return this.createdDate;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    public String getCreatedUser() {
        return this.createdUser;
    }
    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getStatus() {
        return this.status;
    }
    public void setStatus(Long status) {
        this.status = status;
    }
    public String getTeamCode() {
        return this.teamCode;
    }
    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }
    public Long getTeamLeadId() {
        return this.teamLeadId;
    }
    public void setTeamLeadId(Long teamLeadId) {
        this.teamLeadId = teamLeadId;
    }
    public String getTeamName() {
        return this.teamName;
    }
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    public Long getType() {
        return this.type;
    }
    public void setType(Long type) {
        this.type = type;
    }
    public Date getUpdatedDate() {
        return this.updatedDate;
    }
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
    public String getUpdatedUser() {
        return this.updatedUser;
    }
    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }
}
