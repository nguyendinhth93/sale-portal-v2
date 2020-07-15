package com.tp.service;


import com.tp.common.exception.LogicException;
import com.tp.dto.OptionSetValueDTO;

import java.util.List;

/**
 * Created by hopnv on 28/07/2017.
 */
public interface OptionSetValueService extends BaseSearchService<OptionSetValueDTO> {

    OptionSetValueDTO saveOrUpdate(String actionCode, OptionSetValueDTO optionSetValueDTO, String username) throws Exception;
    void delete(List<Long> optionSetConfigValueIds, String username) throws LogicException, Exception;
    OptionSetValueDTO findByCodeAndActionCode(String actionCode, String code) throws Exception;
    List<OptionSetValueDTO> findListByCodeAndActionCode(String actionCode, String code) throws Exception;
    List<OptionSetValueDTO> findByActionCode(String actionCode) throws Exception;
    OptionSetValueDTO findById(Long id) throws Exception;
    OptionSetValueDTO findByCodeAndNameAndActionCode(String actionCode, String code, String name) throws Exception;
    OptionSetValueDTO findByCodeAndValue(String code, String value) throws Exception;
    List<OptionSetValueDTO> findLikeByCodeOrNameAndActionCode(String actionCode, String code, String name) throws Exception;
    Long getIdOptionSetValue(String actionCode, String name);
    OptionSetValueDTO findByName(String actionCode, String name) throws Exception;
    List<OptionSetValueDTO> findSateAndQuantity(String actionCode, Long sparePartId, Long branchFromId) throws Exception;
    List<OptionSetValueDTO> findByType(String actionCode, String type) throws Exception;
}

