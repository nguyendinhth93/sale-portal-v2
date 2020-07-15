package com.tp.service;


import com.tp.dto.OptionSetDTO;

/**
 * Created by hopnv on 28/07/2017.
 */
public interface OptionSetService {

    OptionSetDTO findOptionSetByCode(String code) throws Exception;

}
