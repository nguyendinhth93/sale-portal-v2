package com.tp.service;

import com.tp.dto.DsaResultMeetingFileDTO;

public interface DsaResultMeetingFileService {

    public DsaResultMeetingFileDTO findOne(Long id) throws Exception;

    public DsaResultMeetingFileDTO createOrUpdate(DsaResultMeetingFileDTO dsaResultMeetingFileDTO) throws Exception;

    public DsaResultMeetingFileDTO findByDsaResultMeetingLog(Long dsaResultMeetingId,Long dsaResultMeetingLogId) throws Exception;

    public DsaResultMeetingFileDTO findLastByDsaResultMeeting(Long dsaResultMeetingId) throws Exception;
}