package com.tp.service.impl;

import com.tp.dto.DsaResultMeetingFileDTO;
import com.tp.dto.DsaResultMeetingLogDTO;
import com.tp.model.DsaResultMeetingLog;
import com.tp.repo.DsaResultMeetingLogRepo;
import com.tp.service.DsaResultMeetingFileService;
import com.tp.service.DsaResultMeetingLogService;
import com.tp.util.BaseMapper;
import com.tp.util.DataUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DsaResultMeetingLogServiceImpl extends BaseServiceImpl implements DsaResultMeetingLogService {

    private final BaseMapper<DsaResultMeetingLog,DsaResultMeetingLogDTO> mapper = new BaseMapper<>(DsaResultMeetingLog.class,DsaResultMeetingLogDTO.class);

    @Autowired
    private DsaResultMeetingLogRepo repo;
    @Autowired
    private DsaResultMeetingFileService dsaResultMeetingFileService;


    public static final Logger logger = Logger.getLogger(DsaResultMeetingLogService.class);

    public DsaResultMeetingLogDTO findOne(Long id) throws Exception {
        return mapper.toDtoBean(repo.findOne(id));
    }

    @Override
    public DsaResultMeetingLogDTO createOrUpdate(DsaResultMeetingLogDTO dsaResultMeetingLogDTO) throws Exception {
        return mapper.toDtoBean(repo.save(mapper.toPersistenceBean(dsaResultMeetingLogDTO)));
    }

    @Override
    public List<DsaResultMeetingLogDTO> createOrUpdateWithBatchs(List<DsaResultMeetingLogDTO> dsaResultMeetingLogDTOs) throws Exception {
        return mapper.toDtoBean(repo.save(mapper.toPersistenceBean(dsaResultMeetingLogDTOs)));
    }

    @Override
    public List<DsaResultMeetingLogDTO> findByDsaResultMeeting(Long dsaResultMeetingId) throws Exception {
        List<DsaResultMeetingLogDTO> dsaResultMeetingLogDTOs = mapper.toDtoBean(repo.findByDsaResultMeeting(dsaResultMeetingId));
        if (!DataUtil.isNullOrEmpty(dsaResultMeetingLogDTOs)){
            for (DsaResultMeetingLogDTO dsaResultMeetingLogDTO: dsaResultMeetingLogDTOs){
                DsaResultMeetingFileDTO dsaResultMeetingFileDTO = dsaResultMeetingFileService.findByDsaResultMeetingLog(dsaResultMeetingId,dsaResultMeetingLogDTO.getId());
                if (!DataUtil.isNullObject(dsaResultMeetingFileDTO)) {
                    dsaResultMeetingLogDTO.setPrefixFolder(dsaResultMeetingFileDTO.getPrefixFolder());
                    dsaResultMeetingLogDTO.setFileNameUpload(dsaResultMeetingFileDTO.getFileName());
                }
            }
        }
        return dsaResultMeetingLogDTOs;
    }
}
