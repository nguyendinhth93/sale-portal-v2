package com.tp.dto;

import java.io.Serializable;
import java.util.Date;
public class DsaResultMeetingFileDTO extends BaseDTO implements Serializable {
public static enum COLUMNS {CREATEDDATE, CREATEDUSER, DSARESULTMEETINGID, FILENAME, ID, PREFIXFOLDER, EXCLUSE_ID_LIST};    private Date createdDate;
    private String createdUser;
    private Long dsaResultMeetingId;
    private String fileName;
    private Long id;
    private String prefixFolder;
    private Long dsaResultMeetingLogId;

    public Long getDsaResultMeetingLogId() {
        return dsaResultMeetingLogId;
    }

    public void setDsaResultMeetingLogId(Long dsaResultMeetingLogId) {
        this.dsaResultMeetingLogId = dsaResultMeetingLogId;
    }

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
    public Long getDsaResultMeetingId() {
        return this.dsaResultMeetingId;
    }
    public void setDsaResultMeetingId(Long dsaResultMeetingId) {
        this.dsaResultMeetingId = dsaResultMeetingId;
    }
    public String getFileName() {
        return this.fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPrefixFolder() {
        return this.prefixFolder;
    }
    public void setPrefixFolder(String prefixFolder) {
        this.prefixFolder = prefixFolder;
    }
}
