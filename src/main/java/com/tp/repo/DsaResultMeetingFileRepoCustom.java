package com.tp.repo;

import com.tp.model.DsaResultMeetingFile;

public interface DsaResultMeetingFileRepoCustom {

    public DsaResultMeetingFile findByDsaResultMeetingLog(Long dsaResultMeetingId,Long dsaResultMeetingLogId) throws Exception;

    public DsaResultMeetingFile findLastByDsaResultMeeting(Long dsaResultMeetingId) throws Exception;
}