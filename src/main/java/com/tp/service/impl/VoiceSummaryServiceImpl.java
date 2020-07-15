package com.tp.service.impl;

import com.tp.dto.DsaResultMeetingSearchDTO;
import com.tp.dto.VoiceDetailReportSearchDTO;
import com.tp.dto.VoiceSummaryDTO;
import com.tp.dto.requestSearch.RequestSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.model.VoiceSummary;
import com.tp.repo.VoiceSummaryRepo;
import com.tp.service.VoiceSummaryService;
import com.tp.util.BaseMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoiceSummaryServiceImpl extends BaseServiceImpl implements VoiceSummaryService {

    private final BaseMapper<VoiceSummary, VoiceSummaryDTO> mapper = new BaseMapper<>(VoiceSummary.class,VoiceSummaryDTO.class);

    @Autowired
    private VoiceSummaryRepo repo;
    public static final Logger logger = Logger.getLogger(VoiceSummaryService.class);

    public VoiceSummaryDTO findOne(Long id) throws Exception {
        return mapper.toDtoBean(repo.findOne(id));
    }

    @Override
    public ResponseSearchDTO<VoiceSummaryDTO> load(RequestSearchDTO requestSearchDTO) throws Exception {
        VoiceDetailReportSearchDTO voiceDetailReportSearchDTO = (VoiceDetailReportSearchDTO) requestSearchDTO;
        return repo.searchVoiceSummary(voiceDetailReportSearchDTO);
    }
}
