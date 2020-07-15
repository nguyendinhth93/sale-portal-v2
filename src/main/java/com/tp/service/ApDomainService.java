package com.tp.service;

import java.util.List;

import com.tp.common.exception.LogicException;
import com.tp.dto.ApDomainDTO;

/**
 * Created by hopnv on 21/07/2017.
 */
public interface ApDomainService extends BaseSearchService<ApDomainDTO>{

    ApDomainDTO saveOrUpdate(ApDomainDTO apDomainDTO, String username) throws Exception;

    List<ApDomainDTO> findByType(String type) throws Exception;

    ApDomainDTO findByCodeAndType(String type, String code) throws Exception;

    public ApDomainDTO findByValueAndType(String type, String value) throws Exception;

    void delete(List<Long> apDomainId, String username) throws LogicException, Exception;
}
