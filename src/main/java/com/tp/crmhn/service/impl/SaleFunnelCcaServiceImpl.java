package com.tp.crmhn.service.impl;

import com.tp.crmhn.service.SaleFunnelCcaService;
import com.tp.dto.ApDomainDTO;
import com.tp.dto.BoundCodeDTO;
import com.tp.dto.SaleFunnelCcaDTO;
import com.tp.dto.requestSearch.ApDomainInputSearchDTO;
import com.tp.dto.requestSearch.RequestSearchDTO;
import com.tp.dto.requestSearch.SaleFunnelCCASearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.dwh.repo.CXBitCdrRepo;
import com.tp.dwh.service.CXBitCdrService;
import com.tp.repo.DsaResultMeetingRepo;
import com.tp.service.UserService;
import com.tp.service.impl.BaseServiceImpl;
import com.tp.util.DataUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class SaleFunnelCcaServiceImpl extends BaseServiceImpl implements SaleFunnelCcaService {

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
        saleFunnelCcaDTOs = dsaResultMeetingRepo.findAllSaleFunnelData(saleFunnelCCASearchDTO);
        if (!DataUtil.isNullOrEmpty(saleFunnelCcaDTOs)){
            for (SaleFunnelCcaDTO saleFunnelCcaDTO: saleFunnelCcaDTOs){
                saleFunnelCcaDTO = dsaResultMeetingRepo.mergeDataSaleFunnel(saleFunnelCcaDTO,saleFunnelCCASearchDTO);
            }
            responseSearch.setData(saleFunnelCcaDTOs);
            responseSearch.setRowCount(dsaResultMeetingRepo.countAllSaleFunnelData(saleFunnelCCASearchDTO));
        }
        return responseSearch;
    }

    @Override
    public List<BoundCodeDTO> findAllBoundCode() throws Exception {
        return dsaResultMeetingRepo.findAllBoundCode();
    }
}
