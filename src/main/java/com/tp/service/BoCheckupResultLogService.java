package com.tp.service;

import com.tp.dto.BoCheckupResultLogDTO;
public interface BoCheckupResultLogService {

    public BoCheckupResultLogDTO findOne(Long id) throws Exception;

    public BoCheckupResultLogDTO createOrUpdate(BoCheckupResultLogDTO boCheckupResultLogDTO) throws Exception;
}