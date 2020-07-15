package com.tp.service;

import com.tp.dto.VoiceSummaryDTO;

public interface VoiceSummaryService extends BaseSearchService<VoiceSummaryDTO>{

    public VoiceSummaryDTO findOne(Long id) throws Exception;


}