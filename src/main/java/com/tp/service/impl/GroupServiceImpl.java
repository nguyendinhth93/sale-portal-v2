package com.tp.service.impl;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.tp.dto.admin.GroupDTO;
import com.tp.model.admin.Group;
import com.tp.model.admin.QGroup;
import com.tp.model.admin.QUser;
import com.tp.repo.GroupRepo;
import com.tp.service.GroupService;
import com.tp.util.BaseMapper;
import com.tp.util.Const;
import com.tp.util.DataUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl extends BaseServiceImpl implements GroupService {

    private final BaseMapper<Group, GroupDTO> mapper = new BaseMapper<>(Group.class,GroupDTO.class);

    @Autowired
    private GroupRepo repo;
    public static final Logger logger = Logger.getLogger(GroupService.class);

    public GroupDTO findOne(Long id) throws Exception {
        return mapper.toDtoBean(repo.findOne(id));
    }

    @Override
    public List<GroupDTO> findAll() throws Exception {
        BooleanExpression predicate = QGroup.group.status.eq(Const.STATUS.ACTIVCE);
        List<Group> list = Lists.newArrayList(repo.findAll(predicate));
        if(DataUtil.isNullOrEmpty(list))
            return null;
        return mapper.toDtoBean(list);
    }

}
