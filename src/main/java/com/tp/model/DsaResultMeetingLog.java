/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author DINH_NGUYEN
 */
@Entity
@Table(name = "dsa_result_meeting_log")
public class DsaResultMeetingLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "dsa_result_meeting_id")
    private Long dsaResultMeetingId;
    @Column(name = "meeting_result")
    private Long meetingResult;
    @Column(name = "meeting_reason")
    private Long meetingReason;
    @Column(name = "tl_dsa_code")
    private String tlDsaCode;
    @Column(name = "dsa_code")
    private String dsaCode;
    @Column(name = "meeting_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date meetingDate;
    @Column(name = "dsa_note")
    private String dsaNote;
    @Column(name = "province_meeting_id")
    private String provinceMeetingId;
    @Column(name = "district_meeting_id")
    private String districtMeetingId;
    @Column(name = "created_user")
    private String createdUser;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "accept_status")
    private Long acceptStatus;
    @Column(name = "keep_status")
    private Long keepStatus;
    @Column(name = "return_cca")
    private Long returnCca;
    @Column(name = "return_cca_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnCcaDate;
    @Column(name = "partner_code")
    private String partnerCode;
    @Column(name = "product_type")
    private Long productType;


    public DsaResultMeetingLog() {
    }

    public DsaResultMeetingLog(Long id) {
        this.id = id;
    }

    public Long getAcceptStatus() {
        return acceptStatus;
    }

    public void setAcceptStatus(Long acceptStatus) {
        this.acceptStatus = acceptStatus;
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

    public Long getMeetingResult() {
        return meetingResult;
    }

    public void setMeetingResult(Long meetingResult) {
        this.meetingResult = meetingResult;
    }

    public Long getMeetingReason() {
        return meetingReason;
    }

    public void setMeetingReason(Long meetingReason) {
        this.meetingReason = meetingReason;
    }

    public String getTlDsaCode() {
        return tlDsaCode;
    }

    public void setTlDsaCode(String tlDsaCode) {
        this.tlDsaCode = tlDsaCode;
    }

    public String getDsaCode() {
        return dsaCode;
    }

    public void setDsaCode(String dsaCode) {
        this.dsaCode = dsaCode;
    }

    public Date getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getDsaNote() {
        return dsaNote;
    }

    public void setDsaNote(String dsaNote) {
        this.dsaNote = dsaNote;
    }

    public String getProvinceMeetingId() {
        return provinceMeetingId;
    }

    public void setProvinceMeetingId(String provinceMeetingId) {
        this.provinceMeetingId = provinceMeetingId;
    }

    public String getDistrictMeetingId() {
        return districtMeetingId;
    }

    public void setDistrictMeetingId(String districtMeetingId) {
        this.districtMeetingId = districtMeetingId;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getKeepStatus() {
        return keepStatus;
    }

    public void setKeepStatus(Long keepStatus) {
        this.keepStatus = keepStatus;
    }

    public Long getReturnCca() {
        return returnCca;
    }

    public void setReturnCca(Long returnCca) {
        this.returnCca = returnCca;
    }

    public Date getReturnCcaDate() {
        return returnCcaDate;
    }

    public void setReturnCcaDate(Date returnCcaDate) {
        this.returnCcaDate = returnCcaDate;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public Long getProductType() {
        return productType;
    }

    public void setProductType(Long productType) {
        this.productType = productType;
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
        if (!(object instanceof DsaResultMeetingLog)) {
            return false;
        }
        DsaResultMeetingLog other = (DsaResultMeetingLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.DsaResultMeetingLog[ id=" + id + " ]";
    }
    
}
