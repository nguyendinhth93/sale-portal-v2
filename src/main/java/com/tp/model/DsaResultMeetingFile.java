/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author DINH_NGUYEN
 */
@Entity
@Table(name = "dsa_result_meeting_file")
public class DsaResultMeetingFile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "dsa_result_meeting_id")
    private Long dsaResultMeetingId;
    @Column(name = "dsa_result_meeting_log_id")
    private Long dsaResultMeetingLogId;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "prefix_folder")
    private String prefixFolder;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "created_user")
    private String createdUser;

    public DsaResultMeetingFile() {
    }

    public DsaResultMeetingFile(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDsaResultMeetingId() {
        return dsaResultMeetingId;
    }

    public void setDsaResultMeetingId(Long dsaResultMeetingId) {
        this.dsaResultMeetingId = dsaResultMeetingId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPrefixFolder() {
        return prefixFolder;
    }

    public void setPrefixFolder(String prefixFolder) {
        this.prefixFolder = prefixFolder;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Long getDsaResultMeetingLogId() {
        return dsaResultMeetingLogId;
    }

    public void setDsaResultMeetingLogId(Long dsaResultMeetingLogId) {
        this.dsaResultMeetingLogId = dsaResultMeetingLogId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DsaResultMeetingFile)) {
            return false;
        }
        DsaResultMeetingFile other = (DsaResultMeetingFile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.DsaResultMeetingFile[ id=" + id + " ]";
    }
    
}
