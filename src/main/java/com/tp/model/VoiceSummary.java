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
@Table(name = "voice_summary")
public class VoiceSummary implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 20)
    @Column(name = "voice_code")
    private String voiceCode;
    @Column(name = "total_customer")
    private Long totalCustomer;
    @Column(name = "total_call")
    private Long totalCall;
    @Column(name = "on_call")
    private Long onCall;
    @Column(name = "answered")
    private Long answered;
    @Column(name = "busy")
    private Long busy;
    @Column(name = "failed")
    private Long failed;
    @Column(name = "no_answer")
    private Long noAnswer;
    @Column(name = "talk_time")
    private Double talkTime;
    @Column(name = "call_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date callDate;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Size(max = 200)
    @Column(name = "created_user")
    private String createdUser;

    public VoiceSummary() {
    }

    public Double getTalkTime() {
        return talkTime;
    }

    public void setTalkTime(Double talkTime) {
        this.talkTime = talkTime;
    }

    public VoiceSummary(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoiceCode() {
        return voiceCode;
    }

    public void setVoiceCode(String voiceCode) {
        this.voiceCode = voiceCode;
    }

    public Long getTotalCustomer() {
        return totalCustomer;
    }

    public void setTotalCustomer(Long totalCustomer) {
        this.totalCustomer = totalCustomer;
    }

    public Long getTotalCall() {
        return totalCall;
    }

    public void setTotalCall(Long totalCall) {
        this.totalCall = totalCall;
    }

    public Long getOnCall() {
        return onCall;
    }

    public void setOnCall(Long onCall) {
        this.onCall = onCall;
    }

    public Long getAnswered() {
        return answered;
    }

    public void setAnswered(Long answered) {
        this.answered = answered;
    }

    public Long getBusy() {
        return busy;
    }

    public void setBusy(Long busy) {
        this.busy = busy;
    }

    public Long getFailed() {
        return failed;
    }

    public void setFailed(Long failed) {
        this.failed = failed;
    }

    public Long getNoAnswer() {
        return noAnswer;
    }

    public void setNoAnswer(Long noAnswer) {
        this.noAnswer = noAnswer;
    }

    public Date getCallDate() {
        return callDate;
    }

    public void setCallDate(Date callDate) {
        this.callDate = callDate;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VoiceSummary)) {
            return false;
        }
        VoiceSummary other = (VoiceSummary) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.VoiceSummary[ id=" + id + " ]";
    }
    
}
