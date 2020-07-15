package com.tp.service;

import com.tp.dto.AreaDTO;

import java.util.List;

public interface AreaService {

    public AreaDTO findOne(Long id) throws Exception;

    public List<AreaDTO> findAllProvince() throws Exception;

    public List<AreaDTO> findDistrictByProvinceCode(String provinceCode) throws Exception;

    public AreaDTO findProvinceByCode(String provinceCode) throws Exception;

    public AreaDTO findDistrictByCode(String districtCode) throws Exception;

    public List<AreaDTO> findAllProvinceCRM() throws Exception;

    public List<AreaDTO> findAllDistrictCRMById(String provinceId) throws Exception;

    public AreaDTO findProvinceCRMById(String provinceId) throws Exception;

    public AreaDTO findDistrictCRMById(String districtId) throws Exception;

}