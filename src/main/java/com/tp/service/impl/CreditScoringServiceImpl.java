package com.tp.service.impl;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.tp.common.exception.LogicException;
import com.tp.dto.CreditScoringDTO;
import com.tp.dto.requestSearch.CampaignSearchDTO;
import com.tp.dto.requestSearch.CreditScoringSearchDTO;
import com.tp.dto.requestSearch.RequestSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.model.CreditScoring;
import com.tp.model.QCreditScoring;
import com.tp.repo.CreditScoringRepo;
import com.tp.service.CreditScoringService;
import com.tp.util.BaseMapper;
import com.tp.util.Const;
import com.tp.util.DataUtil;
import com.tp.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CreditScoringServiceImpl  extends BaseServiceImpl implements CreditScoringService {

    private final BaseMapper<CreditScoring,CreditScoringDTO> mapper = new BaseMapper<>(CreditScoring.class,CreditScoringDTO.class);

    @Autowired
    private CreditScoringRepo repo;
    public static final Logger logger = Logger.getLogger(CreditScoringService.class);


    @Override
    public CreditScoringDTO saveOrUpdate(CreditScoringDTO creditScoringDTO, String username) throws Exception {
        boolean isCreate = creditScoringDTO.getId() == null ? true : false;
        Date currentDate = new Date();
        if(isCreate){
            creditScoringDTO.setStatusDes(0);
            creditScoringDTO.setCreatedDate(currentDate);
            creditScoringDTO.setCreatedBy(username);
            creditScoringDTO.setTransId("CREDIT_SCORING_"+creditScoringDTO.getProgramCode().toUpperCase()+"_"+creditScoringDTO.getIsdn().toUpperCase()+"_"+ DateUtil.date2ddMMyyyyHHMMssNoSlash(new Date()));
        } else {
            creditScoringDTO.setStatusDes(0);
            creditScoringDTO.setUpdatedDate(currentDate);
            creditScoringDTO.setUpdatedBy(username);
        }
        return mapper.toDtoBean(repo.saveAndFlush(mapper.toPersistenceBean(creditScoringDTO)));
    }

    public CreditScoringDTO findOne(Long id) throws Exception {
        return mapper.toDtoBean(repo.findOne(id));
    }

    @Override
    public CreditScoringDTO findByIsdnAndProgramCode(String isdn,String programCode) throws Exception {
        BooleanExpression predicate = QCreditScoring.creditScoring1.isdn.equalsIgnoreCase(isdn);
        predicate = predicate.and(QCreditScoring.creditScoring1.programCode.equalsIgnoreCase(programCode));
        predicate = predicate.and(QCreditScoring.creditScoring1.status.eq(Const.STATUS.ACTIVCE));

        List<CreditScoring> creditScorings = Lists.newArrayList(repo.findAll(predicate));
        if(DataUtil.isNullOrEmpty(creditScorings))
            return null;
        return mapper.toDtoBean(creditScorings.get(0));
    }

    @Override
    public void delete(List<Long> creditScoringIds, String username) throws LogicException, Exception {
        Date currentDate = new Date();
        List<CreditScoring> creditScoringDTOs = new ArrayList<>();
        for(Long creditScoringId : creditScoringIds) {
            CreditScoring creditScoring = repo.findOne(creditScoringId);
            if (creditScoring == null || DataUtil.safeEqual(creditScoring.getStatus(), Const.STATUS.INACTIVCE)) {
                throw new LogicException("apConfig.msg.delete.fail");
            }
            creditScoring.setStatus(Const.STATUS.INACTIVCE);
            creditScoring.setUpdatedBy(username);
            creditScoring.setUpdatedDate(currentDate);
            creditScoringDTOs.add(creditScoring);
        }
        repo.save(creditScoringDTOs);
    }

    @Override
    public ResponseSearchDTO<CreditScoringDTO> load(RequestSearchDTO requestSearchDTO) throws Exception {
        CreditScoringSearchDTO creditScoringSearchDTO = (CreditScoringSearchDTO) requestSearchDTO;
        return repo.searchCreditScoring(creditScoringSearchDTO);
    }
}
