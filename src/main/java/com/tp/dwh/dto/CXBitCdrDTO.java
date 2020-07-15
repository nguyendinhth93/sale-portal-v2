package com.tp.dwh.dto;

import com.tp.dto.BaseDTO;

import java.io.Serializable;
import java.util.Date;

public class CXBitCdrDTO extends BaseDTO implements Serializable {
public static enum COLUMNS {ACCOUNTCODE, AMAFLAGS, BILLSEC, CALLDATE, CHANNEL, CLID, DCONTEXT, DISPOSITION, DST, DSTCHANNEL, DURATION, ID, LASTAPP, LASTDATA, LINKEDID, PEERACCOUNT, SEQUENCE, SRC, UNIQUEID, USERFIELD, EXCLUSE_ID_LIST};    private String accountcode;
    private int amaflags;
    private int billsec;
    private Date calldate;
    private String channel;
    private String clid;
    private String dcontext;
    private String disposition;
    private String dst;
    private String dstchannel;
    private int duration;
    private Long id;
    private String lastapp;
    private String lastdata;
    private String linkedid;
    private String peeraccount;
    private String sequence;
    private String src;
    private String uniqueid;
    private String userfield;

    //Add properies
    private String transferSource;
    private String transferDate;

    private String staffName;

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getTransferSource() {
        return transferSource;
    }

    public void setTransferSource(String transferSource) {
        this.transferSource = transferSource;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }
    public String getAccountcode() {
        return this.accountcode;
    }
    public void setAccountcode(String accountcode) {
        this.accountcode = accountcode;
    }
    public int getAmaflags() {
        return this.amaflags;
    }
    public void setAmaflags(int amaflags) {
        this.amaflags = amaflags;
    }
    public int getBillsec() {
        return this.billsec;
    }
    public void setBillsec(int billsec) {
        this.billsec = billsec;
    }
    public Date getCalldate() {
        return this.calldate;
    }
    public void setCalldate(Date calldate) {
        this.calldate = calldate;
    }
    public String getChannel() {
        return this.channel;
    }
    public void setChannel(String channel) {
        this.channel = channel;
    }
    public String getClid() {
        return this.clid;
    }
    public void setClid(String clid) {
        this.clid = clid;
    }
    public String getDcontext() {
        return this.dcontext;
    }
    public void setDcontext(String dcontext) {
        this.dcontext = dcontext;
    }
    public String getDisposition() {
        return this.disposition;
    }
    public void setDisposition(String disposition) {
        this.disposition = disposition;
    }
    public String getDst() {
        return this.dst;
    }
    public void setDst(String dst) {
        this.dst = dst;
    }
    public String getDstchannel() {
        return this.dstchannel;
    }
    public void setDstchannel(String dstchannel) {
        this.dstchannel = dstchannel;
    }
    public int getDuration() {
        return this.duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getLastapp() {
        return this.lastapp;
    }
    public void setLastapp(String lastapp) {
        this.lastapp = lastapp;
    }
    public String getLastdata() {
        return this.lastdata;
    }
    public void setLastdata(String lastdata) {
        this.lastdata = lastdata;
    }
    public String getLinkedid() {
        return this.linkedid;
    }
    public void setLinkedid(String linkedid) {
        this.linkedid = linkedid;
    }
    public String getPeeraccount() {
        return this.peeraccount;
    }
    public void setPeeraccount(String peeraccount) {
        this.peeraccount = peeraccount;
    }
    public String getSequence() {
        return this.sequence;
    }
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
    public String getSrc() {
        return this.src;
    }
    public void setSrc(String src) {
        this.src = src;
    }
    public String getUniqueid() {
        return this.uniqueid;
    }
    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }
    public String getUserfield() {
        return this.userfield;
    }
    public void setUserfield(String userfield) {
        this.userfield = userfield;
    }
}
