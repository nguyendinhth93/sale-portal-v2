package com.tp.dwh.repo;

import com.tp.dto.VoiceDetailReportSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.dwh.dto.CXBitCdrDTO;

public interface CXBitCdrRepoCustom {

    ResponseSearchDTO<CXBitCdrDTO> searchCXBitCdr(VoiceDetailReportSearchDTO voiceDetailReportSearchDTO) throws Exception;

}