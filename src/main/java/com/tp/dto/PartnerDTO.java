package com.tp.dto;
import java.lang.Long;
import java.util.Date;
import java.io.Serializable;
public class PartnerDTO extends BaseDTO implements Serializable {
public static enum COLUMNS {CREATEDDATE, CREATEDUSER, ID, PARTNERCODE, PARTNERNAME, STATUS, UPDATEDDATE, UPDATEDUSER, EXCLUSE_ID_LIST};    private Date createdDate;
    private String createdUser;
    private Long id;
    private String partnerCode;
    private String partnerName;
    private Long status;
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
    public String getPartnerCode() {
        return this.partnerCode;
    }
    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }
    public String getPartnerName() {
        return this.partnerName;
    }
    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }
    public Long getStatus() {
        return this.status;
    }
    public void setStatus(Long status) {
        this.status = status;
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
