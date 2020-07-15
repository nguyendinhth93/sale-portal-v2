/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author DINH_NGUYEN
 */
@Entity
@Table(name = "dsa_result_meeting")
public class DsaResultMeeting extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "voucher_header_id")
    private String voucherHeaderId;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "national_id")
    private String nationalId;
    @Column(name = "customer_phone")
    private String customerPhone;
    @Column(name = "\"limit\"")
    private Long limit;
    @Column(name = "\"limit_card\"")
    private Long limitCard;
    @Column(name = "score_range")
    private String scoreRange;
    @Column(name = "bound_code")
    private String boundCode;
    @Column(name = "bound_code_dsa")
    private String boundCodeDsa;
    @Column(name = "partner_id")
    private String partnerId;
    @Column(name = "partner_name")
    private String partnerName;
    @Column(name = "jtl_cca_id")
    private String jtlCcaId;
    @Column(name = "jtl_cca_name")
    private String jtlCcaName;
    @Column(name = "jtl_cca_code")
    private String jtlCcaCode;
    @Column(name = "cca_id")
    private String ccaId;
    @Column(name = "cca_name")
    private String ccaName;
    @Column(name = "cca_code")
    private String ccaCode;
    @Column(name = "cca_note")
    private String ccaNote;
    @Column(name = "call_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date callDate;
    @Column(name = "cca_reason")
    private String ccaReason;
    @Column(name = "meeting_time")
    private String meetingTime;
    @Column(name = "province_lead")
    private String provinceLead;
    @Column(name = "valid_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validDate;
    @Column(name = "province_meeting_id")
    private String provinceMeetingId;
    @Column(name = "province_meeting_code")
    private String provinceMeetingCode;
    @Column(name = "province_meeting_name")
    private String provinceMeetingName;
    @Column(name = "district_meeting_id")
    private String districtMeetingId;
    @Column(name = "district_meeting_code")
    private String districtMeetingCode;
    @Column(name = "district_meeting_name")
    private String districtMeetingName;
    @Column(name = "\"meeting_address\"")
    private String meetingAddress;
    @Column(name = "\"meeting_date\"")
    @Temporal(TemporalType.TIMESTAMP)
    private Date meetingDate;
    @Column(name = "meeting_result")
    private Long meetingResult;
    @Column(name = "meeting_result_name")
    private String meetingResultName;
    @Column(name = "\"meeting_reason\"")
    private Long meetingReason;
    @Column(name = "\"meeting_reason_name\"")
    private String meetingReasonName;
    @Column(name = "dsa_note")
    private String dsaNote;
    @Column(name = "tl_dsa_id")
    private Long tlDsaId;
    @Column(name = "tl_dsa_code")
    private String tlDsaCode;
    @Column(name = "dsa_id")
    private Long dsaId;
    @Column(name = "dsa_code")
    private String dsaCode;
    @Column(name = "assigned")
    private Long assigned;
    @Column(name = "assigned_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date assignedDate;
    @Column(name = "assigned_user")
    private String assignedUser;
    @Column(name = "status")
    private Long status;
    @Column(name = "created_user")
    private String createdUser;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "updated_user")
    private String updatedUser;
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @Column(name = "cca_meeting_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ccaMeetingDate;
    @Column(name = "db_source")
    private String dbSource;
    @Column(name = "accept_status")
    private Long acceptStatus;
    @Column(name = "\"post_date\"")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postDate;
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


    public DsaResultMeeting() {
    }

    public Long getProductType() {
        return productType;
    }

    public void setProductType(Long productType) {
        this.productType = productType;
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

    public Long getReturnCca() {
        return returnCca;
    }

    public void setReturnCca(Long returnCca) {
        this.returnCca = returnCca;
    }

    public Long getKeepStatus() {
        return keepStatus;
    }

    public void setKeepStatus(Long keepStatus) {
        this.keepStatus = keepStatus;
    }

    public String getProvinceMeetingCode() {
        return provinceMeetingCode;
    }

    public void setProvinceMeetingCode(String provinceMeetingCode) {
        this.provinceMeetingCode = provinceMeetingCode;
    }

    public String getDistrictMeetingCode() {
        return districtMeetingCode;
    }

    public void setDistrictMeetingCode(String districtMeetingCode) {
        this.districtMeetingCode = districtMeetingCode;
    }

    public DsaResultMeeting(Long id) {
        this.id = id;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
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

    public String getVoucherHeaderId() {
        return voucherHeaderId;
    }

    public void setVoucherHeaderId(String voucherHeaderId) {
        this.voucherHeaderId = voucherHeaderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public Long getLimitCard() {
        return limitCard;
    }

    public void setLimitCard(Long limitCard) {
        this.limitCard = limitCard;
    }

    public String getScoreRange() {
        return scoreRange;
    }

    public void setScoreRange(String scoreRange) {
        this.scoreRange = scoreRange;
    }

    public String getBoundCode() {
        return boundCode;
    }

    public void setBoundCode(String boundCode) {
        this.boundCode = boundCode;
    }

    public String getBoundCodeDsa() {
        return boundCodeDsa;
    }

    public void setBoundCodeDsa(String boundCodeDsa) {
        this.boundCodeDsa = boundCodeDsa;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getCcaId() {
        return ccaId;
    }

    public void setCcaId(String ccaId) {
        this.ccaId = ccaId;
    }

    public String getCcaName() {
        return ccaName;
    }

    public void setCcaName(String ccaName) {
        this.ccaName = ccaName;
    }

    public String getCcaCode() {
        return ccaCode;
    }

    public void setCcaCode(String ccaCode) {
        this.ccaCode = ccaCode;
    }

    public String getCcaNote() {
        return ccaNote;
    }

    public void setCcaNote(String ccaNote) {
        this.ccaNote = ccaNote;
    }

    public Date getCallDate() {
        return callDate;
    }

    public void setCallDate(Date callDate) {
        this.callDate = callDate;
    }

    public String getCcaReason() {
        return ccaReason;
    }

    public void setCcaReason(String ccaReason) {
        this.ccaReason = ccaReason;
    }

    public String getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }

    public String getProvinceLead() {
        return provinceLead;
    }

    public void setProvinceLead(String provinceLead) {
        this.provinceLead = provinceLead;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public String getProvinceMeetingId() {
        return provinceMeetingId;
    }

    public void setProvinceMeetingId(String provinceMeetingId) {
        this.provinceMeetingId = provinceMeetingId;
    }

    public String getProvinceMeetingName() {
        return provinceMeetingName;
    }

    public void setProvinceMeetingName(String provinceMeetingName) {
        this.provinceMeetingName = provinceMeetingName;
    }

    public String getDistrictMeetingId() {
        return districtMeetingId;
    }

    public void setDistrictMeetingId(String districtMeetingId) {
        this.districtMeetingId = districtMeetingId;
    }

    public String getDistrictMeetingName() {
        return districtMeetingName;
    }

    public void setDistrictMeetingName(String districtMeetingName) {
        this.districtMeetingName = districtMeetingName;
    }

    public String getMeetingAddress() {
        return meetingAddress;
    }

    public void setMeetingAddress(String meetingAddress) {
        this.meetingAddress = meetingAddress;
    }

    public Date getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }

    public Long getMeetingResult() {
        return meetingResult;
    }

    public void setMeetingResult(Long meetingResult) {
        this.meetingResult = meetingResult;
    }

    public String getMeetingResultName() {
        return meetingResultName;
    }

    public void setMeetingResultName(String meetingResultName) {
        this.meetingResultName = meetingResultName;
    }

    public Long getMeetingReason() {
        return meetingReason;
    }

    public void setMeetingReason(Long meetingReason) {
        this.meetingReason = meetingReason;
    }

    public String getMeetingReasonName() {
        return meetingReasonName;
    }

    public void setMeetingReasonName(String meetingReasonName) {
        this.meetingReasonName = meetingReasonName;
    }

    public String getDsaNote() {
        return dsaNote;
    }

    public void setDsaNote(String dsaNote) {
        this.dsaNote = dsaNote;
    }

    public Long getTlDsaId() {
        return tlDsaId;
    }

    public void setTlDsaId(Long tlDsaId) {
        this.tlDsaId = tlDsaId;
    }

    public String getTlDsaCode() {
        return tlDsaCode;
    }

    public void setTlDsaCode(String tlDsaCode) {
        this.tlDsaCode = tlDsaCode;
    }

    public Long getDsaId() {
        return dsaId;
    }

    public void setDsaId(Long dsaId) {
        this.dsaId = dsaId;
    }

    public String getDsaCode() {
        return dsaCode;
    }

    public void setDsaCode(String dsaCode) {
        this.dsaCode = dsaCode;
    }

    public Long getAssigned() {
        return assigned;
    }

    public void setAssigned(Long assigned) {
        this.assigned = assigned;
    }

    public Date getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(Date assignedDate) {
        this.assignedDate = assignedDate;
    }

    public String getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(String assignedUser) {
        this.assignedUser = assignedUser;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
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

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getJtlCcaId() {
        return jtlCcaId;
    }

    public void setJtlCcaId(String jtlCcaId) {
        this.jtlCcaId = jtlCcaId;
    }

    public String getJtlCcaName() {
        return jtlCcaName;
    }

    public void setJtlCcaName(String jtlCcaName) {
        this.jtlCcaName = jtlCcaName;
    }

    public Date getCcaMeetingDate() {
        return ccaMeetingDate;
    }

    public void setCcaMeetingDate(Date ccaMeetingDate) {
        this.ccaMeetingDate = ccaMeetingDate;
    }

    public String getDbSource() {
        return dbSource;
    }

    public void setDbSource(String dbSource) {
        this.dbSource = dbSource;
    }

    public String getJtlCcaCode() {
        return jtlCcaCode;
    }

    public void setJtlCcaCode(String jtlCcaCode) {
        this.jtlCcaCode = jtlCcaCode;
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
        if (!(object instanceof DsaResultMeeting)) {
            return false;
        }
        DsaResultMeeting other = (DsaResultMeeting) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.DsaResultMeeting[ id=" + id + " ]";
    }
    
}
