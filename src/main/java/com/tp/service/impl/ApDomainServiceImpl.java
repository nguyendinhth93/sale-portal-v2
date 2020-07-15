package com.tp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.tp.common.exception.LogicException;
import com.tp.dto.ApDomainDTO;
import com.tp.dto.requestSearch.ApDomainInputSearchDTO;
import com.tp.dto.requestSearch.RequestSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.model.ApDomain;
import com.tp.model.QApDomain;
import com.tp.repo.ApDomainRepo;
import com.tp.service.ApDomainService;
import com.tp.util.BaseMapper;
import com.tp.util.Const;
import com.tp.util.DataUtil;

/**
 * Created by hopnv on 21/07/2017.
 */
@Service
public class ApDomainServiceImpl implements ApDomainService {

    @Autowired
    private ApDomainRepo apDomainRepo;
    private BaseMapper<ApDomain, ApDomainDTO> mapper = new BaseMapper<>(ApDomain.class, ApDomainDTO.class);

    @Override
    public ApDomainDTO saveOrUpdate(ApDomainDTO apDomainDTO, String username) throws Exception {

        boolean isCreate = apDomainDTO.getApDomainId() == null ? true : false;
        Date currentDate = new Date();
        if(isCreate){
            apDomainDTO.setCreateDate(currentDate);
            apDomainDTO.setCreateBy(username);
        } else {
            apDomainDTO.setLastUpdateDate(currentDate);
            apDomainDTO.setLastUpdateBy(username);
        }

        return mapper.toDtoBean(apDomainRepo.saveAndFlush(mapper.toPersistenceBean(apDomainDTO)));
    }

    @Override
    public List<ApDomainDTO> findByType(String type) throws Exception {
        BooleanExpression predicate = QApDomain.apDomain.type.equalsIgnoreCase(type);
        predicate = predicate.and(QApDomain.apDomain.status.eq(Const.STATUS.ACTIVCE));

        List<ApDomain> apDomains = Lists.newArrayList(apDomainRepo.findAll(predicate));
        if(DataUtil.isNullOrEmpty(apDomains))
            return null;
        return mapper.toDtoBean(apDomains);
    }

    @Override
//    @Cacheable(value="findByCodeAndType", key="#name")
    public ApDomainDTO findByCodeAndType(String type, String code) throws Exception {
        BooleanExpression predicate = QApDomain.apDomain.type.equalsIgnoreCase(type);
        predicate = predicate.and(QApDomain.apDomain.code.equalsIgnoreCase(code));
        predicate = predicate.and(QApDomain.apDomain.status.eq(Const.STATUS.ACTIVCE));

        List<ApDomain> apDomains = Lists.newArrayList(apDomainRepo.findAll(predicate));
        if(DataUtil.isNullOrEmpty(apDomains))
            return null;
        return mapper.toDtoBean(apDomains.get(0));
    }

    @Override
    @Cacheable(value="findByValueAndType", key="#name")
    public ApDomainDTO findByValueAndType(String type, String value) throws Exception {
        BooleanExpression predicate = QApDomain.apDomain.type.equalsIgnoreCase(type);
        predicate = predicate.and(QApDomain.apDomain.value.equalsIgnoreCase(value));
        predicate = predicate.and(QApDomain.apDomain.status.eq(Const.STATUS.ACTIVCE));

        List<ApDomain> apDomains = Lists.newArrayList(apDomainRepo.findAll(predicate));
        if(DataUtil.isNullOrEmpty(apDomains))
            return null;
        return mapper.toDtoBean(apDomains.get(0));
    }

    @Override
    public void delete(List<Long> apDomainIds, String username) throws LogicException, Exception {
        Date currentDate = new Date();
        List<ApDomain> apDomains = new ArrayList<>();
        for(Long apDomainId : apDomainIds) {
            ApDomain apDomain = apDomainRepo.findOne(apDomainId);
            if (apDomain == null || DataUtil.safeEqual(apDomain.getStatus(), Const.STATUS.INACTIVCE)) {
                throw new LogicException("apConfig.msg.delete.fail");
            }
            apDomain.setStatus(Const.STATUS.INACTIVCE);
            apDomain.setLastUpdateBy(username);
            apDomain.setLastUpdateDate(currentDate);
            apDomains.add(apDomain);
        }

        apDomainRepo.save(apDomains);
    }

    @Override
    public ResponseSearchDTO<ApDomainDTO> load(RequestSearchDTO requestSearchDTO) throws Exception {

        ApDomainInputSearchDTO apDomainInputSearchDTO = (ApDomainInputSearchDTO) requestSearchDTO;
        return apDomainRepo.searchApDomain(apDomainInputSearchDTO);

    }
}
