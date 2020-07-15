package com.tp.repo;


import com.tp.dto.OptionSetInputSearchDTO;
import com.tp.dto.OptionSetValueDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.model.OptionSetValue;

import java.util.List;

/**
 * Created by hopnv on 28/07/2017.
 */
public interface OptionSetValueRepoCustom {

    ResponseSearchDTO<OptionSetValueDTO> searchOptionSetValue(OptionSetInputSearchDTO optionSetInputSearchDTO) throws Exception;
    OptionSetValue findByCodeAndActionCode(String actionCode, String code);
    List<OptionSetValue> findListByCodeAndActionCode(String actionCode, String code) throws Exception;
    OptionSetValue findByName(String actionCode, String name);
    List<OptionSetValue> findByActionCode(String actionCode);
    Long getIdOptionSetValue(String actionCode, String name);
    OptionSetValue findByCodeAndNameAndActionCode(String actionCode, String code, String name) throws Exception;
    OptionSetValue findByCodeAndValue(String code, String value) throws Exception;
    List<OptionSetValue> findLikeByCodeOrNameAndActionCode(String actionCode, String code, String name) throws Exception;
    List<OptionSetValue> findSateAndQuantity(String actionCode, Long sparePartId, Long branchFromId) throws Exception;
    List<OptionSetValue> findByType(String actionCode, String type) throws Exception;
}
