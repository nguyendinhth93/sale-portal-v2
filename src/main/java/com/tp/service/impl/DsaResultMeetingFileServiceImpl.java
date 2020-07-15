package com.tp.service.impl;

import com.tp.dto.DsaResultMeetingFileDTO;
import com.tp.model.DsaResultMeetingFile;
import com.tp.repo.DsaResultMeetingFileRepo;
import com.tp.service.DsaResultMeetingFileService;
import com.tp.util.BaseMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DsaResultMeetingFileServiceImpl extends BaseServiceImpl implements DsaResultMeetingFileService {

    private final BaseMapper<DsaResultMeetingFile,DsaResultMeetingFileDTO> mapper = new BaseMapper<>(DsaResultMeetingFile.class,DsaResultMeetingFileDTO.class);

    @Autowired
    private DsaResultMeetingFileRepo repo;
    public static final Logger logger = Logger.getLogger(DsaResultMeetingFileService.class);

    public DsaResultMeetingFileDTO findOne(Long id) throws Exception {
        return mapper.toDtoBean(repo.findOne(id));
    }

    @Override
    public DsaResultMeetingFileDTO createOrUpdate(DsaResultMeetingFileDTO dsaResultMeetingFileDTO) throws Exception {
        return mapper.toDtoBean(repo.save(mapper.toPersistenceBean(dsaResultMeetingFileDTO)));
    }

    @Override
    public DsaResultMeetingFileDTO findByDsaResultMeetingLog(Long dsaResultMeetingId,Long dsaResultMeetingLogId) throws Exception {
        return mapper.toDtoBean(repo.findByDsaResultMeetingLog(dsaResultMeetingId, dsaResultMeetingLogId));
    }

    @Override
    public DsaResultMeetingFileDTO findLastByDsaResultMeeting(Long dsaResultMeetingId) throws Exception {
        return mapper.toDtoBean(repo.findLastByDsaResultMeeting(dsaResultMeetingId));
    }

}
