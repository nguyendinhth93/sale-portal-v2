package com.tp.dwh.service.impl;

import com.tp.dto.VoiceDetailReportSearchDTO;
import com.tp.dto.admin.UserDTO;
import com.tp.dto.requestSearch.RequestSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.dwh.dto.CXBitCdrDTO;
import com.tp.dwh.model.CXBitCdr;
import com.tp.dwh.repo.CXBitCdrRepo;
import com.tp.dwh.service.CXBitCdrService;
import com.tp.service.UserService;
import com.tp.service.impl.BaseServiceImpl;
import com.tp.util.BaseMapper;
import com.tp.util.DataUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CXBitCdrServiceImpl extends BaseServiceImpl implements CXBitCdrService {

    private final BaseMapper<CXBitCdr, CXBitCdrDTO> mapper = new BaseMapper<>(CXBitCdr.class,CXBitCdrDTO.class);

    @Autowired
    private CXBitCdrRepo cxBitCdrRepo;
    @Autowired
    private UserService userService;

    public static final Logger logger = Logger.getLogger(CXBitCdrService.class);

    public CXBitCdrDTO findOne(Long id) throws Exception {
        return mapper.toDtoBean(cxBitCdrRepo.findOne(id));
    }

    @Override
    public ResponseSearchDTO<CXBitCdrDTO> load(RequestSearchDTO requestSearchDTO) throws Exception {
        VoiceDetailReportSearchDTO voiceDetailReportSearchDTO = (VoiceDetailReportSearchDTO) requestSearchDTO;
        if (!DataUtil.isNullOrEmpty(voiceDetailReportSearchDTO.getTeamCodes())){
            List<UserDTO> userDTOs = userService.findAllStaffCodeByListTeam(voiceDetailReportSearchDTO.getTeamCodes());
            List<String> voiceCodes = new ArrayList<>();
            if (!DataUtil.isNullOrEmpty(userDTOs)){
                for (UserDTO userDTO: userDTOs){
                    voiceCodes.add(userDTO.getVoiceCode());
                }
                voiceDetailReportSearchDTO.setVoiceCodes(voiceCodes);
            }
        }

        if (!DataUtil.isNullOrEmpty(voiceDetailReportSearchDTO.getTeamCode())){
            List<UserDTO> userDTOs = userService.findAllStaffCodeByTeam(voiceDetailReportSearchDTO.getTeamCode());
            List<String> voiceCodes = new ArrayList<>();
            if (!DataUtil.isNullOrEmpty(userDTOs)){
                for (UserDTO userDTO: userDTOs){
                    voiceCodes.add(userDTO.getVoiceCode());
                }
                voiceDetailReportSearchDTO.setVoiceCodes(voiceCodes);
            }
        }

        if (!DataUtil.isNullOrEmpty(voiceDetailReportSearchDTO.getStaffCode())){
            voiceDetailReportSearchDTO.setVoiceCode(voiceDetailReportSearchDTO.getStaffCode());
        }

        ResponseSearchDTO<CXBitCdrDTO> cxBitCdrDTOResponseSearchDTO = cxBitCdrRepo.searchCXBitCdr(voiceDetailReportSearchDTO);
        return cxBitCdrDTOResponseSearchDTO;
    }
}
