package com.tp.service;

import com.tp.dto.BoCheckupResultDTO;
import com.tp.dto.DsaResultMeetingDTO;

import java.util.List;

public interface BoFollowResultService extends BaseSearchService<BoCheckupResultDTO>{

    public BoCheckupResultDTO findOne(Long id) throws Exception;

    public BoCheckupResultDTO createOrUpdate(BoCheckupResultDTO boCheckupResultDTO) throws Exception;

    public BoCheckupResultDTO createOrUpdateFromMeeting(DsaResultMeetingDTO dsaResultMeetingDTO) throws Exception;

    public List<BoCheckupResultDTO> findByNameAndPhoneNationalId(String customerName, String customerPhone, String nationalId) throws Exception;
}