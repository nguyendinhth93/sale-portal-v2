package com.tp.service.impl;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.tp.dto.BoCheckupResultDTO;
import com.tp.dto.BoCheckupResultLogDTO;
import com.tp.dto.DsaResultMeetingDTO;
import com.tp.dto.admin.UserDTO;
import com.tp.dto.requestSearch.BoCheckupResultSearchDTO;
import com.tp.dto.requestSearch.RequestSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.model.*;
import com.tp.repo.BoCheckupResultLogRepo;
import com.tp.repo.BoCheckupResultRepo;
import com.tp.service.BoCheckupResultService;
import com.tp.service.UserService;
import com.tp.util.BaseMapper;
import com.tp.util.Const;
import com.tp.util.DataUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BoCheckupResultServiceImpl extends BaseServiceImpl implements BoCheckupResultService {

    private final BaseMapper<BoCheckupResult, BoCheckupResultDTO> mapper = new BaseMapper<>(BoCheckupResult.class,BoCheckupResultDTO.class);
    private final BaseMapper<BoCheckupResultLog, BoCheckupResultLogDTO> mapperLog = new BaseMapper<>(BoCheckupResultLog.class,BoCheckupResultLogDTO.class);

    @Autowired
    private BoCheckupResultRepo repo;
    @Autowired
    private BoCheckupResultLogRepo boCheckupResultLogRepo;
    @Autowired
    private UserService userService;

    public static final Logger logger = Logger.getLogger(BoCheckupResultService.class);

    @Override
    public ResponseSearchDTO<BoCheckupResultDTO> load(RequestSearchDTO requestSearchDTO) throws Exception {
        BoCheckupResultSearchDTO boCheckupResultInputSearchDTO = (BoCheckupResultSearchDTO) requestSearchDTO;
        return repo.searchBoCheckupResult(boCheckupResultInputSearchDTO);
    }

    public BoCheckupResultDTO findOne(Long id) throws Exception {
        return mapper.toDtoBean(repo.findOne(id));
    }

    @Override
    public BoCheckupResultDTO createOrUpdate(BoCheckupResultDTO boCheckupResultDTO) throws Exception {
        BoCheckupResult boCheckupResult = repo.save(mapper.toPersistenceBean(boCheckupResultDTO));

        BoCheckupResultLogDTO checkupResultLogDTO = new BoCheckupResultLogDTO();
        checkupResultLogDTO.setBoCheckupResultId(boCheckupResult.getId());
        checkupResultLogDTO.setCustomerName(boCheckupResult.getCustomerName());
        checkupResultLogDTO.setCustomerPhone(boCheckupResult.getCustomerPhone());
        checkupResultLogDTO.setNationalId(boCheckupResult.getNationalId());
        checkupResultLogDTO.setLimitTsRisk(boCheckupResult.getLimitTsRisk());
        checkupResultLogDTO.setReceivedDsaDate(boCheckupResult.getReceivedDsaDate());
        checkupResultLogDTO.setReturnDsaDate(boCheckupResult.getReturnDsaDate());
        checkupResultLogDTO.setSendQdeDate(boCheckupResult.getSendQdeDate());
        checkupResultLogDTO.setTlBoCheckupCode(boCheckupResult.getTlBoCheckupCode());
        checkupResultLogDTO.setBoCheckupCode(boCheckupResult.getBoCheckupCode());
        checkupResultLogDTO.setQdeStatus(boCheckupResult.getQdeStatus());
        checkupResultLogDTO.setCheckupStatus(boCheckupResult.getCheckupStatus());
        checkupResultLogDTO.setCheckupReason(boCheckupResult.getCheckupReason());
        checkupResultLogDTO.setCheckupReasonDetail(boCheckupResult.getCheckupReasonDetail());
        checkupResultLogDTO.setConfirmMeetingDate(boCheckupResult.getConfirmMeetingDate());
        checkupResultLogDTO.setTlBoFollow(boCheckupResult.getTlBoFollow());
        checkupResultLogDTO.setBoFollow(boCheckupResult.getBoFollow());
        checkupResultLogDTO.setToLosDate(boCheckupResult.getToLosDate());
        checkupResultLogDTO.setLosCode(boCheckupResult.getLosCode());
        checkupResultLogDTO.setStatusDocVpbank(boCheckupResult.getStatusDocVpbank());
        checkupResultLogDTO.setGroupDocStatus(boCheckupResult.getGroupDocStatus());
        checkupResultLogDTO.setDetailStatusDocVpbank(boCheckupResult.getDetailStatusDocVpbank());
        checkupResultLogDTO.setNoteStatusDocVpbank(boCheckupResult.getNoteStatusDocVpbank());
        checkupResultLogDTO.setDocReturnStatus(boCheckupResult.getDocReturnStatus());
        checkupResultLogDTO.setConfirmErrorBo(boCheckupResult.getConfirmErrorBo());
        checkupResultLogDTO.setToFiDate(boCheckupResult.getToFiDate());
        checkupResultLogDTO.setReturnResultQdeDate(boCheckupResult.getReturnResultQdeDate());
        checkupResultLogDTO.setActiveDate(boCheckupResult.getActiveDate());
        checkupResultLogDTO.setLimitApproval(boCheckupResult.getLimitApproval());
        checkupResultLogDTO.setBoundCodeDsa(boCheckupResult.getBoundCodeDsa());
        checkupResultLogDTO.setUplLimitDsa(boCheckupResult.getUplLimitDsa());
        checkupResultLogDTO.setCcLimitDsa(boCheckupResult.getCcLimitDsa());
        checkupResultLogDTO.setAssigneeBoFollowDate(boCheckupResult.getAssigneeBoFollowDate());
        checkupResultLogDTO.setCreatedUser(boCheckupResult.getUpdatedUser());
        checkupResultLogDTO.setSendToFollow(boCheckupResult.getSendToFollow());
        checkupResultLogDTO.setCreatedDate(new Date());

        boCheckupResultLogRepo.save(mapperLog.toPersistenceBean(checkupResultLogDTO));
        return mapper.toDtoBean(boCheckupResult);
    }

    @Override
    public BoCheckupResultDTO createOrUpdateFromMeeting(DsaResultMeetingDTO dsaResultMeetingDTO) throws Exception {
        BoCheckupResultDTO boCheckupResultDTO = new BoCheckupResultDTO();
        boCheckupResultDTO.setDsaResultMeetingId(dsaResultMeetingDTO.getId());
        boCheckupResultDTO.setCustomerName(dsaResultMeetingDTO.getCustomerName());
        boCheckupResultDTO.setCustomerPhone(dsaResultMeetingDTO.getCustomerPhone());
        boCheckupResultDTO.setNationalId(dsaResultMeetingDTO.getNationalId());
        boCheckupResultDTO.setPartnerCode(dsaResultMeetingDTO.getPartnerCode());
        boCheckupResultDTO.setBoundCode(dsaResultMeetingDTO.getBoundCode());
        boCheckupResultDTO.setProvincePhone(dsaResultMeetingDTO.getProvinceLead());
        boCheckupResultDTO.setProvinceCode(dsaResultMeetingDTO.getProvinceMeetingCode());
        boCheckupResultDTO.setProvinceName(dsaResultMeetingDTO.getProvinceMeetingName());
        boCheckupResultDTO.setDistrictCode(dsaResultMeetingDTO.getDistrictMeetingCode());
        boCheckupResultDTO.setDistrictName(dsaResultMeetingDTO.getDistrictMeetingName());
        boCheckupResultDTO.setUplLimit(dsaResultMeetingDTO.getLimit());
        boCheckupResultDTO.setCcLimit(dsaResultMeetingDTO.getLimitCard());
        boCheckupResultDTO.setMeetingDate(dsaResultMeetingDTO.getMeetingDate());
        boCheckupResultDTO.setValidDate(dsaResultMeetingDTO.getValidDate());
        boCheckupResultDTO.setTlCcaCode(dsaResultMeetingDTO.getJtlCcaCode());
        boCheckupResultDTO.setCcaCode(dsaResultMeetingDTO.getCcaCode());
        boCheckupResultDTO.setTlDsaCode(dsaResultMeetingDTO.getTlDsaCode());
        boCheckupResultDTO.setDsaCode(dsaResultMeetingDTO.getDsaCode());
        boCheckupResultDTO.setStatus(1l);
        boCheckupResultDTO.setSendToFollow(0l);
        boCheckupResultDTO.setConfirmMeetingDate(dsaResultMeetingDTO.getReturnCcaDate());

        UserDTO userDTO = userService.findByUserName(dsaResultMeetingDTO.getUpdatedUser());
        if (!DataUtil.isNullObject(userDTO)){
            boCheckupResultDTO.setDsaSaleCode(userDTO.getSaleCode());
        }
        boCheckupResultDTO.setCreatedUser(dsaResultMeetingDTO.getUpdatedUser());
        boCheckupResultDTO.setCreatedDate(new Date());
        boCheckupResultDTO.setUpdatedUser(dsaResultMeetingDTO.getUpdatedUser());
        boCheckupResultDTO.setUpdatedDate(new Date());

        BoCheckupResult boCheckupResult = repo.save(mapper.toPersistenceBean(boCheckupResultDTO));
        BoCheckupResultLogDTO checkupResultLogDTO = new BoCheckupResultLogDTO();
        checkupResultLogDTO.setBoCheckupResultId(boCheckupResult.getId());
        checkupResultLogDTO.setCustomerName(boCheckupResult.getCustomerName());
        checkupResultLogDTO.setCustomerPhone(boCheckupResult.getCustomerPhone());
        checkupResultLogDTO.setNationalId(boCheckupResult.getNationalId());
        checkupResultLogDTO.setConfirmMeetingDate(boCheckupResult.getConfirmMeetingDate());
        checkupResultLogDTO.setCreatedUser(dsaResultMeetingDTO.getUpdatedUser());
        checkupResultLogDTO.setCreatedDate(new Date());
        boCheckupResultLogRepo.save(mapperLog.toPersistenceBean(checkupResultLogDTO));
        return mapper.toDtoBean(boCheckupResult);
    }

    @Override
    public List<BoCheckupResultDTO> findByNameAndPhoneNationalId(String customerName, String customerPhone, String nationalId) throws Exception {
        BooleanExpression predicate = QBoCheckupResult.boCheckupResult.customerName.equalsIgnoreCase(customerName);
        predicate = predicate.and(QBoCheckupResult.boCheckupResult.customerPhone.eq(customerPhone));
        predicate = predicate.and(QBoCheckupResult.boCheckupResult.nationalId.eq(nationalId));
        predicate = predicate.and(QBoCheckupResult.boCheckupResult.status.eq(DataUtil.safeToLong(Const.STATUS.ACTIVCE)));

        List<BoCheckupResult> boCheckupResults = Lists.newArrayList(repo.findAll(predicate));
        if(DataUtil.isNullOrEmpty(boCheckupResults))
            return null;
        return mapper.toDtoBean(boCheckupResults);
    }

    @Override
    public BoCheckupResultDTO findByDsaResultMeeting(Long dsaResultMeetingId) throws Exception {
        BooleanExpression predicate = QBoCheckupResult.boCheckupResult.dsaResultMeetingId.eq(dsaResultMeetingId);
        List<BoCheckupResult> boCheckupResults = Lists.newArrayList(repo.findAll(predicate));
        if(DataUtil.isNullOrEmpty(boCheckupResults))
            return null;
        return mapper.toDtoBean(boCheckupResults.get(0));
    }

}
