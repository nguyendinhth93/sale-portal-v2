package com.tp.service;

import com.tp.common.exception.LogicException;
import com.tp.dto.CreditScoringDTO;

import java.util.List;

public interface CreditScoringService extends BaseSearchService<CreditScoringDTO>{

    CreditScoringDTO saveOrUpdate(CreditScoringDTO creditScoringDTO, String username) throws Exception;

    public CreditScoringDTO findOne(Long id) throws Exception;

    public CreditScoringDTO findByIsdnAndProgramCode(String isdn,String programCode) throws Exception;

    public void delete(List<Long> creditScoringIds, String username) throws LogicException, Exception;

}