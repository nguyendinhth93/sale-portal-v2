package com.tp.repo;

import com.tp.dto.ApDomainDTO;
import com.tp.dto.requestSearch.ApDomainInputSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;

/**
 * Created by hopnv on 21/07/2017.
 */
public interface ApDomainRepoCustom {

    ResponseSearchDTO<ApDomainDTO> searchApDomain(ApDomainInputSearchDTO apDomainInputSearchDTO) throws Exception;

}
