package com.tp.service;

import com.tp.dto.CcaRoleDTO;

public interface CcaRoleService {

    public CcaRoleDTO findOne(Long id) throws Exception;

    public CcaRoleDTO findByUserId(Long userId) throws Exception;

}