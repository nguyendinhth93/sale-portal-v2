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
 * @author DINH-NGUYEN
 */
@Entity
@Table(name = "bo_checkup_result_log")
public class BoCheckupResultLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "bo_checkup_result_id")
    private Long boCheckupResultId;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "customer_phone")
    private String customerPhone;
    @Column(name = "national_id")
    private String nationalId;
    @Column(name = "limit_ts_risk")
    private Long limitTsRisk;
    @Column(name = "received_dsa_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date receivedDsaDate;
    @Column(name = "return_dsa_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnDsaDate;
    @Column(name = "send_qde_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendQdeDate;
    @Column(name = "tl_bo_checkup_code")
    private String tlBoCheckupCode;
    @Column(name = "bo_checkup_code")
    private String boCheckupCode;
    @Column(name = "qde_status")
    private Long qdeStatus;
    @Column(name = "checkup_status")
    private Long checkupStatus;
    @Column(name = "checkup_reason")
    private Long checkupReason;
    @Column(name = "checkup_reason_detail")
    private String checkupReasonDetail;
    @Column(name = "created_user")
    private String createdUser;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "send_to_follow")
    private Long sendToFollow;
    @Column(name = "confirm_meeting_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date confirmMeetingDate;
    @Column(name = "tl_bo_follow")
    private String tlBoFollow;
    @Column(name = "bo_follow")
    private String boFollow;
    @Column(name = "to_los_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date toLosDate;
    @Column(name = "los_code")
    private String losCode;
    @Column(name = "status_doc_vpbank")
    private Long statusDocVpbank;
    @Column(name = "group_doc_status")
    private Long groupDocStatus;
    @Column(name = "detail_status_doc_vpbank")
    private Long detailStatusDocVpbank;
    @Column(name = "note_status_doc_vpbank")
    private String noteStatusDocVpbank;
    @Column(name = "doc_return_status")
    private Long docReturnStatus;
    @Column(name = "confirm_error_bo")
    private Long confirmErrorBo;
    @Column(name = "to_fi_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date toFiDate;
    @Column(name = "return_result_qde_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnResultQdeDate;
    @Column(name = "active_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date activeDate;
    @Column(name = "limit_approval")
    private Long limitApproval;
    @Column(name = "bound_code_dsa")
    private String boundCodeDsa;
    @Column(name = "upl_limit_dsa")
    private Long uplLimitDsa;
    @Column(name = "cc_limit_dsa")
    private Long ccLimitDsa;
    @Column(name = "assignee_bo_follow_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date assigneeBoFollowDate;

    public Date getAssigneeBoFollowDate() {
        return assigneeBoFollowDate;
    }

    public void setAssigneeBoFollowDate(Date assigneeBoFollowDate) {
        this.assigneeBoFollowDate = assigneeBoFollowDate;
    }

    public Long getUplLimitDsa() {
        return uplLimitDsa;
    }

    public void setUplLimitDsa(Long uplLimitDsa) {
        this.uplLimitDsa = uplLimitDsa;
    }

    public Long getCcLimitDsa() {
        return ccLimitDsa;
    }

    public void setCcLimitDsa(Long ccLimitDsa) {
        this.ccLimitDsa = ccLimitDsa;
    }

    public String getBoundCodeDsa() {
        return boundCodeDsa;
    }

    public void setBoundCodeDsa(String boundCodeDsa) {
        this.boundCodeDsa = boundCodeDsa;
    }

    public Date getConfirmMeetingDate() {
        return confirmMeetingDate;
    }

    public void setConfirmMeetingDate(Date confirmMeetingDate) {
        this.confirmMeetingDate = confirmMeetingDate;
    }

    public Long getSendToFollow() {
        return sendToFollow;
    }

    public void setSendToFollow(Long sendToFollow) {
        this.sendToFollow = sendToFollow;
    }

    public BoCheckupResultLog() {
    }

    public BoCheckupResultLog(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBoCheckupResultId() {
        return boCheckupResultId;
    }

    public void setBoCheckupResultId(Long boCheckupResultId) {
        this.boCheckupResultId = boCheckupResultId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public Long getLimitTsRisk() {
        return limitTsRisk;
    }

    public void setLimitTsRisk(Long limitTsRisk) {
        this.limitTsRisk = limitTsRisk;
    }

    public Date getReceivedDsaDate() {
        return receivedDsaDate;
    }

    public void setReceivedDsaDate(Date receivedDsaDate) {
        this.receivedDsaDate = receivedDsaDate;
    }

    public Date getReturnDsaDate() {
        return returnDsaDate;
    }

    public void setReturnDsaDate(Date returnDsaDate) {
        this.returnDsaDate = returnDsaDate;
    }

    public Date getSendQdeDate() {
        return sendQdeDate;
    }

    public void setSendQdeDate(Date sendQdeDate) {
        this.sendQdeDate = sendQdeDate;
    }

    public String getTlBoCheckupCode() {
        return tlBoCheckupCode;
    }

    public void setTlBoCheckupCode(String tlBoCheckupCode) {
        this.tlBoCheckupCode = tlBoCheckupCode;
    }

    public String getBoCheckupCode() {
        return boCheckupCode;
    }

    public void setBoCheckupCode(String boCheckupCode) {
        this.boCheckupCode = boCheckupCode;
    }

    public Long getQdeStatus() {
        return qdeStatus;
    }

    public void setQdeStatus(Long qdeStatus) {
        this.qdeStatus = qdeStatus;
    }

    public Long getCheckupStatus() {
        return checkupStatus;
    }

    public void setCheckupStatus(Long checkupStatus) {
        this.checkupStatus = checkupStatus;
    }

    public Long getCheckupReason() {
        return checkupReason;
    }

    public void setCheckupReason(Long checkupReason) {
        this.checkupReason = checkupReason;
    }

    public String getCheckupReasonDetail() {
        return checkupReasonDetail;
    }

    public void setCheckupReasonDetail(String checkupReasonDetail) {
        this.checkupReasonDetail = checkupReasonDetail;
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

    public String getTlBoFollow() {
        return tlBoFollow;
    }

    public void setTlBoFollow(String tlBoFollow) {
        this.tlBoFollow = tlBoFollow;
    }

    public String getBoFollow() {
        return boFollow;
    }

    public void setBoFollow(String boFollow) {
        this.boFollow = boFollow;
    }

    public Date getToLosDate() {
        return toLosDate;
    }

    public void setToLosDate(Date toLosDate) {
        this.toLosDate = toLosDate;
    }

    public String getLosCode() {
        return losCode;
    }

    public void setLosCode(String losCode) {
        this.losCode = losCode;
    }

    public Long getStatusDocVpbank() {
        return statusDocVpbank;
    }

    public void setStatusDocVpbank(Long statusDocVpbank) {
        this.statusDocVpbank = statusDocVpbank;
    }

    public Long getGroupDocStatus() {
        return groupDocStatus;
    }

    public void setGroupDocStatus(Long groupDocStatus) {
        this.groupDocStatus = groupDocStatus;
    }

    public Long getDetailStatusDocVpbank() {
        return detailStatusDocVpbank;
    }

    public void setDetailStatusDocVpbank(Long detailStatusDocVpbank) {
        this.detailStatusDocVpbank = detailStatusDocVpbank;
    }

    public String getNoteStatusDocVpbank() {
        return noteStatusDocVpbank;
    }

    public void setNoteStatusDocVpbank(String noteStatusDocVpbank) {
        this.noteStatusDocVpbank = noteStatusDocVpbank;
    }

    public Long getDocReturnStatus() {
        return docReturnStatus;
    }

    public void setDocReturnStatus(Long docReturnStatus) {
        this.docReturnStatus = docReturnStatus;
    }

    public Long getConfirmErrorBo() {
        return confirmErrorBo;
    }

    public void setConfirmErrorBo(Long confirmErrorBo) {
        this.confirmErrorBo = confirmErrorBo;
    }

    public Date getToFiDate() {
        return toFiDate;
    }

    public void setToFiDate(Date toFiDate) {
        this.toFiDate = toFiDate;
    }

    public Date getReturnResultQdeDate() {
        return returnResultQdeDate;
    }

    public void setReturnResultQdeDate(Date returnResultQdeDate) {
        this.returnResultQdeDate = returnResultQdeDate;
    }

    public Date getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

    public Long getLimitApproval() {
        return limitApproval;
    }

    public void setLimitApproval(Long limitApproval) {
        this.limitApproval = limitApproval;
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
        if (!(object instanceof BoCheckupResultLog)) {
            return false;
        }
        BoCheckupResultLog other = (BoCheckupResultLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.BoCheckupResultLog[ id=" + id + " ]";
    }
    
}
