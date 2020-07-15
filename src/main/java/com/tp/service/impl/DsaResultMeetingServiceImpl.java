package com.tp.service.impl;

import com.tp.dto.DsaResultMeetingDTO;
import com.tp.dto.DsaResultMeetingSearchDTO;
import com.tp.dto.requestSearch.RequestSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.model.DsaResultMeeting;
import com.tp.repo.DsaResultMeetingRepo;
import com.tp.service.DsaResultMeetingService;
import com.tp.util.BaseMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DsaResultMeetingServiceImpl extends BaseServiceImpl implements DsaResultMeetingService {

    private final BaseMapper<DsaResultMeeting,DsaResultMeetingDTO> mapper = new BaseMapper<>(DsaResultMeeting.class,DsaResultMeetingDTO.class);

    @Autowired
    private DsaResultMeetingRepo repo;
    public static final Logger logger = Logger.getLogger(DsaResultMeetingService.class);

    public DsaResultMeetingDTO findOne(Long id) throws Exception {
        return mapper.toDtoBean(repo.findOne(id));
    }

    @Override
    public List<DsaResultMeetingDTO> findAll() throws Exception {
        return mapper.toDtoBean(repo.findAll());
    }

    @Override
    public ResponseSearchDTO<DsaResultMeetingDTO> load(RequestSearchDTO requestSearchDTO) throws Exception {
        DsaResultMeetingSearchDTO dsaResultMeetingSearchDTO = (DsaResultMeetingSearchDTO) requestSearchDTO;
        return repo.searchDsaResultMeeting(dsaResultMeetingSearchDTO);
    }
}
