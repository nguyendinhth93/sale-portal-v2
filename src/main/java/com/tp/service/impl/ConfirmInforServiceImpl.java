package com.tp.service.impl;

import com.tp.dto.ConfirmInforDTO;
import com.tp.model.ConfirmInfor;
import com.tp.repo.ConfirmInforRepo;
import com.tp.service.ConfirmInforService;
import com.tp.util.BaseMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmInforServiceImpl extends BaseServiceImpl implements ConfirmInforService {

    private final BaseMapper<ConfirmInfor,ConfirmInforDTO> mapper = new BaseMapper<>(ConfirmInfor.class,ConfirmInforDTO.class);

    @Autowired
    private ConfirmInforRepo repo;
    public static final Logger logger = Logger.getLogger(ConfirmInforService.class);

    public ConfirmInforDTO findOne(Long id) throws Exception {
        return mapper.toDtoBean(repo.findOne(id));
    }

}
