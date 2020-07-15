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
@Table(name="campaign")
public class Campaign extends BaseModel implements Serializable{
	private static final long serialVersionUID = 4309593180187762848L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "program_code")
	private String programCode;
	
	@Column(name = "trans_id")
	private String transId;
	
	@Column(name = "product_name")
	private String productName;
	
	@Column(name = "scores")
	private String scores;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "total")
	private Long total;
	
	@Column(name = "from_date")
	private Date fromDate;
	
	@Column(name = "to_date")
	private Date toDate;

	@Column(name = "province")
	private String province;
	
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getScores() {
		return scores;
	}

	public void setScores(String scores) {
		this.scores = scores;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
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
	
	
}
