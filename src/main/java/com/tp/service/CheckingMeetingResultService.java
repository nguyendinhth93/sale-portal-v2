package com.tp.service;

import com.tp.dto.BoCheckupResultDTO;

public interface CheckingMeetingResultService extends BaseSearchService<BoCheckupResultDTO>{

    public BoCheckupResultDTO findOne(Long id) throws Exception;
}