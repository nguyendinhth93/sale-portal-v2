package com.tp.service.impl;

import com.tp.dto.RatioCollectMeetingDTO;
import com.tp.dto.RatioCollectMeetingSearchDTO;
import com.tp.dto.VoiceDetailReportSearchDTO;
import com.tp.dto.admin.UserDTO;
import com.tp.dto.requestSearch.RequestSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.dwh.dto.CXBitCdrDTO;
import com.tp.dwh.model.CXBitCdr;
import com.tp.dwh.repo.CXBitCdrRepo;
import com.tp.dwh.service.CXBitCdrService;
import com.tp.repo.DsaResultMeetingRepo;
import com.tp.service.RatioCollectMeetingService;
import com.tp.service.UserService;
import com.tp.util.BaseMapper;
import com.tp.util.DataUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RatioCollectMeetingServiceImpl extends BaseServiceImpl implements RatioCollectMeetingService {

    @Autowired
    private DsaResultMeetingRepo dsaResultMeetingRepo;

    public static final Logger logger = Logger.getLogger(RatioCollectMeetingService.class);

    @Override
    public ResponseSearchDTO<RatioCollectMeetingDTO> load(RequestSearchDTO requestSearchDTO) throws Exception {
        RatioCollectMeetingSearchDTO ratioCollectMeetingSearchDTO = (RatioCollectMeetingSearchDTO) requestSearchDTO;
        ResponseSearchDTO<RatioCollectMeetingDTO> ratioCollectMeetingDTOResponseSearchDTO = dsaResultMeetingRepo.searchRatioCollectMeeting(ratioCollectMeetingSearchDTO);
        return ratioCollectMeetingDTOResponseSearchDTO;
    }
}
