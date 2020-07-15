package com.tp.service.impl;

import com.tp.dto.DsaRoleDTO;
import com.tp.model.DsaRole;
import com.tp.repo.DsaRoleRepo;
import com.tp.service.DsaRoleService;
import com.tp.util.BaseMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DsaRoleServiceImpl extends BaseServiceImpl implements DsaRoleService {

    private final BaseMapper<DsaRole,DsaRoleDTO> mapper = new BaseMapper<>(DsaRole.class,DsaRoleDTO.class);

    @Autowired
    private DsaRoleRepo repo;
    public static final Logger logger = Logger.getLogger(DsaRoleService.class);

    public DsaRoleDTO findOne(Long id) throws Exception {
        return mapper.toDtoBean(repo.findOne(id));
    }

}
