package com.tp.service.impl;

import com.tp.crmhn.service.SaleFunnelCcaService;
import com.tp.dto.BoundCodeDTO;
import com.tp.dto.SaleFunnelCcaDTO;
import com.tp.dto.requestSearch.RequestSearchDTO;
import com.tp.dto.requestSearch.SaleFunnelCCASearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.repo.DsaResultMeetingRepo;
import com.tp.service.SaleFunnelService;
import com.tp.util.DataUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class SaleFunnelServiceImpl extends BaseServiceImpl implements SaleFunnelService {

    public static final Logger logger = Logger.getLogger(SaleFunnelCcaService.class);

    @Autowired
    DsaResultMeetingRepo dsaResultMeetingRepo;

    @Override
    public ResponseSearchDTO<SaleFunnelCcaDTO> load(RequestSearchDTO requestSearchDTO) throws Exception {
        SaleFunnelCCASearchDTO saleFunnelCCASearchDTO = (SaleFunnelCCASearchDTO) requestSearchDTO;
        Calendar calToDate = Calendar.getInstance();
        calToDate.setTime(saleFunnelCCASearchDTO.getToDate());
        calToDate.set(Calendar.HOUR_OF_DAY,23);
        calToDate.set(Calendar.MINUTE,59);
        calToDate.set(Calendar.MILLISECOND,59);
        saleFunnelCCASearchDTO.setToDate(calToDate.getTime());
        ResponseSearchDTO<SaleFunnelCcaDTO> responseSearch = new ResponseSearchDTO<>();
        List<SaleFunnelCcaDTO> saleFunnelCcaDTOs;
        saleFunnelCcaDTOs = dsaResultMeetingRepo.findSaleFunnelData(saleFunnelCCASearchDTO);
        if (!DataUtil.isNullOrEmpty(saleFunnelCcaDTOs)){
            for (SaleFunnelCcaDTO saleFunnelCcaDTO: saleFunnelCcaDTOs){
                saleFunnelCcaDTO = dsaResultMeetingRepo.mergeDataSaleFunnel(saleFunnelCcaDTO,saleFunnelCCASearchDTO);
            }
            responseSearch.setData(saleFunnelCcaDTOs);
            responseSearch.setRowCount(dsaResultMeetingRepo.countSaleFunnel(saleFunnelCCASearchDTO));
        }
        return responseSearch;
    }

    @Override
    public List<BoundCodeDTO> findAllBoundCode() throws Exception {
        return dsaResultMeetingRepo.findAllBoundCode();
    }

}
