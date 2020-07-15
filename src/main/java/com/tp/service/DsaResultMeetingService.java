package com.tp.service;

import com.tp.dto.DsaResultMeetingDTO;

import java.util.List;

public interface DsaResultMeetingService extends BaseSearchService<DsaResultMeetingDTO>{

    public DsaResultMeetingDTO findOne(Long id) throws Exception;

    public List<DsaResultMeetingDTO> findAll() throws Exception;

}