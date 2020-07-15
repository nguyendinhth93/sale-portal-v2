package com.tp.service.impl;

import com.tp.dto.BoCheckupResultLogDTO;
import com.tp.model.BoCheckupResultLog;
import com.tp.repo.BoCheckupResultLogRepo;
import com.tp.service.BoCheckupResultLogService;
import com.tp.util.BaseMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoCheckupResultLogServiceImpl extends BaseServiceImpl implements BoCheckupResultLogService {

    private final BaseMapper<BoCheckupResultLog, BoCheckupResultLogDTO> mapper = new BaseMapper<>(BoCheckupResultLog.class,BoCheckupResultLogDTO.class);

    @Autowired
    private BoCheckupResultLogRepo repo;
    public static final Logger logger = Logger.getLogger(BoCheckupResultLogService.class);

    public BoCheckupResultLogDTO findOne(Long id) throws Exception {
        return mapper.toDtoBean(repo.findOne(id));
    }

    @Override
    public BoCheckupResultLogDTO createOrUpdate(BoCheckupResultLogDTO boCheckupResultLogDTO) throws Exception {
        return mapper.toDtoBean(repo.save(mapper.toPersistenceBean(boCheckupResultLogDTO)));
    }

}
