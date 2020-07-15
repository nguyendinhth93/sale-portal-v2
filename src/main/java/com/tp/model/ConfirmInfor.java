package com.tp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="confirm_info")
public class ConfirmInfor implements Serializable{
	
	private static final long serialVersionUID = -465946975570922328L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "program_code")
	private String programCode;
	
	@Column(name = "trans_id")
	private String transId;
	
	@Column(name = "isdn")
	private String isdn;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "created_date", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date createdDate;
	
	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name = "latest_date_call")
	private Date latestDateCall;
	
	@Column(name = "latest_response_code")
	private String latestResponseCode;
	
	@Column(name = "latest_response_des")
	private String latestResponseDes;
	
	@Column(name = "total_time_call_api")
	private Long totalTimeCallAPI;
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "status_des")
	private Integer statusDes;
	
	@Column(name = "receiveDate")
	private Date receiveDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProgramCode() {
		return programCode;
	}

	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getIsdn() {
		return isdn;
	}

	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Date getLatestDateCall() {
		return latestDateCall;
	}

	public void setLatestDateCall(Date latestDateCall) {
		this.latestDateCall = latestDateCall;
	}

	public String getLatestResponseCode() {
		return latestResponseCode;
	}

	public void setLatestResponseCode(String latestResponseCode) {
		this.latestResponseCode = latestResponseCode;
	}

	public String getLatestResponseDes() {
		return latestResponseDes;
	}

	public void setLatestResponseDes(String latestResponseDes) {
		this.latestResponseDes = latestResponseDes;
	}

	public Long getTotalTimeCallAPI() {
		return totalTimeCallAPI;
	}

	public void setTotalTimeCallAPI(Long totalTimeCallAPI) {
		this.totalTimeCallAPI = totalTimeCallAPI;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatusDes() {
		return statusDes;
	}

	public void setStatusDes(Integer statusDes) {
		this.statusDes = statusDes;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}
}
