/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.dwh.model;

import com.tp.model.BaseModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author DINH_NGUYEN
 */
@Entity
@Table(name = "cx_bit_cdr")
public class CXBitCdr extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "calldate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date calldate;

    @Column(name = "clid")
    private String clid;

    @Column(name = "src")
    private String src;

    @Column(name = "dst")
    private String dst;

    @Column(name = "dcontext")
    private String dcontext;

    @Column(name = "channel")
    private String channel;

    @Column(name = "dstchannel")
    private String dstchannel;

    @Column(name = "lastapp")
    private String lastapp;

    @Column(name = "lastdata")
    private String lastdata;

    @Column(name = "duration")
    private int duration;

    @Column(name = "billsec")
    private int billsec;

    @Column(name = "disposition")
    private String disposition;

    @Column(name = "amaflags")
    private int amaflags;

    @Column(name = "accountcode")
    private String accountcode;

    @Column(name = "userfield")
    private String userfield;

    @Column(name = "uniqueid")
    private String uniqueid;

    @Column(name = "linkedid")
    private String linkedid;

    @Column(name = "\"sequence\"")
    private String sequence;

    @Column(name = "peeraccount")
    private String peeraccount;

    @Column(name = "transfer_source")
    private String transferSource;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "transfer_date")
    private Date transferDate;

    public CXBitCdr() {
    }

    public CXBitCdr(Long id) {
        this.id = id;
    }

    public CXBitCdr(Long id, Date calldate, String clid, String src, String dst, String dcontext, String channel, String dstchannel, String lastapp, String lastdata, int duration, int billsec, String disposition, int amaflags, String accountcode, String userfield, String uniqueid, String linkedid, String sequence, String peeraccount) {
        this.id = id;
        this.calldate = calldate;
        this.clid = clid;
        this.src = src;
        this.dst = dst;
        this.dcontext = dcontext;
        this.channel = channel;
        this.dstchannel = dstchannel;
        this.lastapp = lastapp;
        this.lastdata = lastdata;
        this.duration = duration;
        this.billsec = billsec;
        this.disposition = disposition;
        this.amaflags = amaflags;
        this.accountcode = accountcode;
        this.userfield = userfield;
        this.uniqueid = uniqueid;
        this.linkedid = linkedid;
        this.sequence = sequence;
        this.peeraccount = peeraccount;
    }

    public String getTransferSource() {
        return transferSource;
    }

    public void setTransferSource(String transferSource) {
        this.transferSource = transferSource;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCalldate() {
        return calldate;
    }

    public void setCalldate(Date calldate) {
        this.calldate = calldate;
    }

    public String getClid() {
        return clid;
    }

    public void setClid(String clid) {
        this.clid = clid;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

    public String getDcontext() {
        return dcontext;
    }

    public void setDcontext(String dcontext) {
        this.dcontext = dcontext;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDstchannel() {
        return dstchannel;
    }

    public void setDstchannel(String dstchannel) {
        this.dstchannel = dstchannel;
    }

    public String getLastapp() {
        return lastapp;
    }

    public void setLastapp(String lastapp) {
        this.lastapp = lastapp;
    }

    public String getLastdata() {
        return lastdata;
    }

    public void setLastdata(String lastdata) {
        this.lastdata = lastdata;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getBillsec() {
        return billsec;
    }

    public void setBillsec(int billsec) {
        this.billsec = billsec;
    }

    public String getDisposition() {
        return disposition;
    }

    public void setDisposition(String disposition) {
        this.disposition = disposition;
    }

    public int getAmaflags() {
        return amaflags;
    }

    public void setAmaflags(int amaflags) {
        this.amaflags = amaflags;
    }

    public String getAccountcode() {
        return accountcode;
    }

    public void setAccountcode(String accountcode) {
        this.accountcode = accountcode;
    }

    public String getUserfield() {
        return userfield;
    }

    public void setUserfield(String userfield) {
        this.userfield = userfield;
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }

    public String getLinkedid() {
        return linkedid;
    }

    public void setLinkedid(String linkedid) {
        this.linkedid = linkedid;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getPeeraccount() {
        return peeraccount;
    }

    public void setPeeraccount(String peeraccount) {
        this.peeraccount = peeraccount;
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
        if (!(object instanceof CXBitCdr)) {
            return false;
        }
        CXBitCdr other = (CXBitCdr) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.CXBitCdrHN[ id=" + id + " ]";
    }
    
}
