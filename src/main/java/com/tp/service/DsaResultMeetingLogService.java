package com.tp.service;

import com.tp.dto.DsaResultMeetingLogDTO;

import java.util.List;

public interface DsaResultMeetingLogService {

    public DsaResultMeetingLogDTO findOne(Long id) throws Exception;

    public DsaResultMeetingLogDTO createOrUpdate(DsaResultMeetingLogDTO dsaResultMeetingLogDTO) throws Exception;

    public List<DsaResultMeetingLogDTO> createOrUpdateWithBatchs(List<DsaResultMeetingLogDTO> dsaResultMeetingLogDTOs) throws Exception;

    public List<DsaResultMeetingLogDTO> findByDsaResultMeeting(Long dsaResultMeetingId) throws Exception;

}