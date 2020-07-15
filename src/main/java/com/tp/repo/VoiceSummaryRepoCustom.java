package com.tp.repo;

import com.tp.dto.VoiceDetailReportSearchDTO;
import com.tp.dto.VoiceSummaryDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;

public interface VoiceSummaryRepoCustom {

    public ResponseSearchDTO<VoiceSummaryDTO> searchVoiceSummary(VoiceDetailReportSearchDTO voiceDetailReportSearchDTO) throws Exception;

}