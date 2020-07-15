package com.tp.service.impl;

import com.tp.dto.DsaResultMeetingDTO;
import com.tp.dto.DsaResultMeetingSearchDTO;
import com.tp.dto.admin.UserDTO;
import com.tp.dto.requestSearch.RequestSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.model.DsaResultMeeting;
import com.tp.repo.DsaResultMeetingRepo;
import com.tp.service.DistributeMeetingService;
import com.tp.service.UpdateMeetingService;
import com.tp.util.BaseMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpdateMeetingServiceImpl extends BaseServiceImpl implements UpdateMeetingService {

    private final BaseMapper<DsaResultMeeting,DsaResultMeetingDTO> mapper = new BaseMapper<>(DsaResultMeeting.class,DsaResultMeetingDTO.class);

    @Autowired
    private DsaResultMeetingRepo repo;
    public static final Logger logger = Logger.getLogger(DistributeMeetingService.class);

    @Override
    public ResponseSearchDTO<DsaResultMeetingDTO> load(RequestSearchDTO requestSearchDTO) throws Exception {
        DsaResultMeetingSearchDTO dsaResultMeetingSearchDTO = (DsaResultMeetingSearchDTO) requestSearchDTO;
        return repo.searchDsaResultMeetingForCcaSort(dsaResultMeetingSearchDTO);
    }

    @Override
    public DsaResultMeetingDTO createOrUpdate(DsaResultMeetingDTO dsaResultMeetingDTO) throws Exception {
        return mapper.toDtoBean(repo.save(mapper.toPersistenceBean(dsaResultMeetingDTO)));
    }

    @Override
    public List<DsaResultMeetingDTO> createOrUpdateWithBatchs(List<DsaResultMeetingDTO> dsaResultMeetingDTOs) throws Exception {
        return mapper.toDtoBean(repo.save(mapper.toPersistenceBean(dsaResultMeetingDTOs)));
    }

    @Override
    public List<UserDTO> getAllDsaAndMeetingAvailiableByTlDsa(String tlDsaCode) throws Exception {
        return repo.getAllDsaAndMeetingAvailiableByTlDsa(tlDsaCode);
    }
}
