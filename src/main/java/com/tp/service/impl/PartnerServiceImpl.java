package com.tp.service.impl;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.tp.dto.PartnerDTO;
import com.tp.model.Partner;
import com.tp.model.QPartner;
import com.tp.repo.PartnerRepo;
import com.tp.service.PartnerService;
import com.tp.util.BaseMapper;
import com.tp.util.Const;
import com.tp.util.DataUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartnerServiceImpl extends BaseServiceImpl implements PartnerService {

    private final BaseMapper<Partner, PartnerDTO> mapper = new BaseMapper<>(Partner.class,PartnerDTO.class);

    @Autowired
    private PartnerRepo repo;
    public static final Logger logger = Logger.getLogger(PartnerService.class);

    public PartnerDTO findOne(Long id) throws Exception {
        return mapper.toDtoBean(repo.findOne(id));
    }

    @Override
    public List<PartnerDTO> findAllPartner() throws Exception {
        BooleanExpression predicate = QPartner.partner.status.eq(DataUtil.safeToLong(Const.STATUS.ACTIVCE));
        List<Partner> partners = Lists.newArrayList(repo.findAll(predicate));
        if(DataUtil.isNullOrEmpty(partners))
            return null;
        return mapper.toDtoBean(partners);
    }

}
