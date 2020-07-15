package com.tp.service;


import com.tp.dto.AreaDTO;
import com.tp.dto.DsaResultMeetingDTO;

import java.util.List;

public interface InputMeetingService extends BaseSearchService<DsaResultMeetingDTO>{

    public List<AreaDTO> findAllDistrictByDsa(String dsaCode) throws Exception;

}