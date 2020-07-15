package com.tp.repo;

import com.tp.dto.CreditScoringDTO;
import com.tp.dto.requestSearch.CreditScoringSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;

public interface CreditScoringRepoCustom {

    public ResponseSearchDTO<CreditScoringDTO> searchCreditScoring(CreditScoringSearchDTO creditScoringSearchDTO) throws Exception;

}