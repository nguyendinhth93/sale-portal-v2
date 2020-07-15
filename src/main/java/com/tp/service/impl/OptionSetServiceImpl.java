package com.tp.service.impl;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.tp.dto.OptionSetDTO;
import com.tp.model.OptionSet;
import com.tp.model.QOptionSet;
import com.tp.repo.OptionSetRepo;
import com.tp.service.OptionSetService;
import com.tp.util.BaseMapper;
import com.tp.util.Const;
import com.tp.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hopnv on 28/07/2017.
 */
@Service
public class OptionSetServiceImpl implements OptionSetService {

    private BaseMapper<OptionSet, OptionSetDTO> mapper = new BaseMapper<>(OptionSet.class, OptionSetDTO.class);

    @Autowired
    private OptionSetRepo optionSetRepo;

    @Override
    public OptionSetDTO findOptionSetByCode(String code) throws Exception {

        BooleanExpression predicate = QOptionSet.optionSet.status.eq(Const.STATUS.ACTIVCE);
        predicate = predicate.and(QOptionSet.optionSet.code.equalsIgnoreCase(code));

        List<OptionSet> optionSets = Lists.newArrayList(optionSetRepo.findAll(predicate));
        if(DataUtil.isNullOrEmpty(optionSets))
            return null;
        return mapper.toDtoBean(optionSets.get(0));
    }
}
