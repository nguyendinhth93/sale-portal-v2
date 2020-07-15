package com.tp.repo;

import com.tp.dto.AreaDTO;

import java.util.List;

public interface AreaRepoCustom {

    public List<AreaDTO> findAllProvinceCRM() throws Exception;

    public List<AreaDTO> findAllDistrictCRMById(String provinceId) throws Exception;

    public AreaDTO findProvinceCRMById(String provinceId) throws Exception;

    public AreaDTO findDistrictCRMById(String districtId) throws Exception;

}