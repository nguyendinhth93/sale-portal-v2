package com.tp.service;


import com.tp.dto.DsaResultMeetingDTO;
import com.tp.dto.admin.UserDTO;

import java.util.List;

public interface UpdateMeetingService extends BaseSearchService<DsaResultMeetingDTO>{

    DsaResultMeetingDTO createOrUpdate(DsaResultMeetingDTO dsaResultMeetingDTO) throws Exception;

    List<DsaResultMeetingDTO> createOrUpdateWithBatchs(List<DsaResultMeetingDTO> dsaResultMeetingDTOs) throws Exception;

    List<UserDTO> getAllDsaAndMeetingAvailiableByTlDsa(String tlDsaCode) throws Exception;

}