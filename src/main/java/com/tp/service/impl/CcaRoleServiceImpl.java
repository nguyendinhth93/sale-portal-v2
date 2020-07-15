package com.tp.service.impl;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.tp.dto.CcaRoleDTO;
import com.tp.model.ApDomain;
import com.tp.model.CcaRole;
import com.tp.model.QApDomain;
import com.tp.model.QCcaRole;
import com.tp.repo.CcaRoleRepo;
import com.tp.service.CcaRoleService;
import com.tp.util.BaseMapper;
import com.tp.util.Const;
import com.tp.util.DataUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CcaRoleServiceImpl extends BaseServiceImpl implements CcaRoleService {

    private final BaseMapper<CcaRole, CcaRoleDTO> mapper = new BaseMapper<>(CcaRole.class,CcaRoleDTO.class);

    @Autowired
    private CcaRoleRepo repo;
    public static final Logger logger = Logger.getLogger(CcaRoleService.class);

    public CcaRoleDTO findOne(Long id) throws Exception {
        return mapper.toDtoBean(repo.findOne(id));
    }

    @Override
    public CcaRoleDTO findByUserId(Long userId) throws Exception {
        BooleanExpression predicate = QCcaRole.ccaRole.userId.eq(userId);
        List<CcaRole> ccaRoles = Lists.newArrayList(repo.findAll(predicate));
        if(DataUtil.isNullOrEmpty(ccaRoles))
            return null;
        return mapper.toDtoBean(ccaRoles.get(0));
    }

}
