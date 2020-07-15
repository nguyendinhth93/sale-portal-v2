package com.tp.service;


import com.tp.dto.AreaDTO;
import com.tp.dto.DsaResultMeetingDTO;

import java.util.List;

public interface AcceptedMeetingService extends BaseSearchService<DsaResultMeetingDTO>{

    public List<AreaDTO> findAllDistrictByDsaAndDay(String dsaCode,Long dayQuery) throws Exception;

}