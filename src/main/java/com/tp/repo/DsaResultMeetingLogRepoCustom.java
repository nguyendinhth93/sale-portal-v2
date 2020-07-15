package com.tp.repo;


import com.tp.model.DsaResultMeetingLog;

import java.util.List;

public interface DsaResultMeetingLogRepoCustom {

    public List<DsaResultMeetingLog> findByDsaResultMeeting(Long dsaResultMeetingId) throws Exception;

}