package com.tp.service.impl;

import com.tp.dto.BoCheckupResultDTO;
import com.tp.dto.requestSearch.CheckingMeetingResultSearchDTO;
import com.tp.dto.requestSearch.RequestSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.model.BoCheckupResult;
import com.tp.repo.BoCheckupResultRepo;
import com.tp.service.BoCheckupResultService;
import com.tp.service.CheckingMeetingResultService;
import com.tp.util.BaseMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckingMeetingResultServiceImpl extends BaseServiceImpl implements CheckingMeetingResultService {

    private final BaseMapper<BoCheckupResult, BoCheckupResultDTO> mapper = new BaseMapper<>(BoCheckupResult.class,BoCheckupResultDTO.class);

    @Autowired
    private BoCheckupResultRepo repo;

    public static final Logger logger = Logger.getLogger(BoCheckupResultService.class);

    @Override
    public ResponseSearchDTO<BoCheckupResultDTO> load(RequestSearchDTO requestSearchDTO) throws Exception {
        CheckingMeetingResultSearchDTO checkingMeetingResultSearchDTO = (CheckingMeetingResultSearchDTO) requestSearchDTO;
        return repo.searchCheckingMeetingResult(checkingMeetingResultSearchDTO);
    }

    public BoCheckupResultDTO findOne(Long id) throws Exception {
        return mapper.toDtoBean(repo.findOne(id));
    }
}
