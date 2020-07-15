package com.tp.repo;


import com.tp.model.OptionSetValue;

/**
 * Created by hopnv on 28/07/2017.
 */
public interface OptionSetValueRepo  extends  BaseRepository<OptionSetValue, Long>, OptionSetValueRepoCustom {
    OptionSetValue findById(Long id);
}
