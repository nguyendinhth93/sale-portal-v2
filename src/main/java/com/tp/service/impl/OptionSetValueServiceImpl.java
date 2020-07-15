package com.tp.service.impl;

import com.tp.common.exception.LogicException;
import com.tp.dto.OptionSetDTO;
import com.tp.dto.OptionSetInputSearchDTO;
import com.tp.dto.OptionSetValueDTO;
import com.tp.dto.requestSearch.RequestSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.model.OptionSetValue;
import com.tp.repo.OptionSetValueRepo;
import com.tp.service.OptionSetService;
import com.tp.service.OptionSetValueService;
import com.tp.util.BaseMapper;
import com.tp.util.Const;
import com.tp.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hopnv on 28/07/2017.
 */
@Service
public class OptionSetValueServiceImpl implements OptionSetValueService {

    @Autowired
    private OptionSetValueRepo optionSetValueRepo;
    @Autowired
    private OptionSetService optionSetService;

    private BaseMapper<OptionSetValue, OptionSetValueDTO> mapper = new BaseMapper<>(OptionSetValue.class, OptionSetValueDTO.class);

    @Override
    public ResponseSearchDTO<OptionSetValueDTO> load(RequestSearchDTO requestSearchDTO) throws Exception {

        OptionSetInputSearchDTO optionSetInputSearchDTO = (OptionSetInputSearchDTO) requestSearchDTO;
        return optionSetValueRepo.searchOptionSetValue(optionSetInputSearchDTO);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OptionSetValueDTO saveOrUpdate(String actionCode, OptionSetValueDTO optionSetValueDTO, String username) throws Exception {
        boolean isCreate = optionSetValueDTO.getId() == null ? true : false;
        Date currentDate = new Date();
        if(isCreate){
            OptionSetDTO optionSetByCode = optionSetService.findOptionSetByCode(actionCode);
            optionSetValueDTO.setOptionSetId(optionSetByCode.getId());
            optionSetValueDTO.setCreateDate(currentDate);
            optionSetValueDTO.setCreateBy(username);
        } else {
            optionSetValueDTO.setLastUpdateDate(currentDate);
            optionSetValueDTO.setLastUpdateBy(username);
        }

        return mapper.toDtoBean(optionSetValueRepo.saveAndFlush(mapper.toPersistenceBean(optionSetValueDTO)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> optionSetConfigValueIds, String username) throws LogicException, Exception {
        Date currentDate = new Date();
        List<OptionSetValue> optionSetValues = new ArrayList<>();
        for(Long optionSetConfigValueId : optionSetConfigValueIds) {
            OptionSetValue optionSetValue = optionSetValueRepo.findOne(optionSetConfigValueId);
            if (optionSetValue == null || DataUtil.safeEqual(optionSetValue.getStatus(), Const.STATUS.INACTIVCE)) {
                throw new LogicException("apConfig.msg.delete.fail");
            }
            optionSetValue.setStatus(Const.STATUS.INACTIVCE);
            optionSetValue.setLastUpdateBy(username);
            optionSetValue.setLastUpdateDate(currentDate);
            optionSetValues.add(optionSetValue);
        }

        optionSetValueRepo.save(optionSetValues);
    }

    @Override
    public OptionSetValueDTO findByCodeAndActionCode(String actionCode, String code) throws Exception {
        return mapper.toDtoBean(optionSetValueRepo.findByCodeAndActionCode(actionCode, code));
    }

    @Override
    public List<OptionSetValueDTO> findListByCodeAndActionCode(String actionCode, String code) throws Exception {
        return mapper.toDtoBean(optionSetValueRepo.findListByCodeAndActionCode(actionCode, code));
    }

    @Override
    public OptionSetValueDTO findByCodeAndNameAndActionCode(String actionCode, String code,String name) throws Exception {
        return mapper.toDtoBean(optionSetValueRepo.findByCodeAndNameAndActionCode(actionCode, code,name));
    }

    @Override
//    @Cacheable(value="optionSetValueServiceImpl.findByCodeAndValue", key = "#code and #value")
    public OptionSetValueDTO findByCodeAndValue(String code, String value) throws Exception {
        return mapper.toDtoBean(optionSetValueRepo.findByCodeAndValue(code, value));
    }

    @Override
    public List<OptionSetValueDTO> findLikeByCodeOrNameAndActionCode(String actionCode, String code, String name) throws Exception {
        return mapper.toDtoBean(optionSetValueRepo.findLikeByCodeOrNameAndActionCode(actionCode, code, name));
    }

    @Override
    public OptionSetValueDTO findByName(String actionCode,String name) throws Exception {
        return mapper.toDtoBean(optionSetValueRepo.findByName(actionCode,name));
    }

    //@Cacheable(value="optionSetValueServiceImpl.findByActionCode", key = "#actionCode")
    @Override
    public List<OptionSetValueDTO> findByActionCode(String actionCode) throws Exception {
        return DataUtil.defaultIfNull(mapper.toDtoBean(optionSetValueRepo.findByActionCode(actionCode)), new ArrayList<>());
    }

    @Override
    public OptionSetValueDTO findById(Long id) throws Exception {
        return mapper.toDtoBean(optionSetValueRepo.findById(id));
    }
    @Override
    public Long getIdOptionSetValue(String actionCode, String name){
        return optionSetValueRepo.getIdOptionSetValue(actionCode,name);
    }

    @Override
    public List<OptionSetValueDTO> findSateAndQuantity(String actionCode ,Long sparePartId,Long branchFromId) throws Exception {
        return mapper.toDtoBean(optionSetValueRepo.findSateAndQuantity(actionCode,sparePartId,branchFromId));
    }

    @Override
    public List<OptionSetValueDTO> findByType(String actionCode, String type) throws Exception {
        return mapper.toDtoBean(optionSetValueRepo.findByType(actionCode,type));
    }
}
