package com.tp.service.impl;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.tp.dto.admin.UserDTO;
import com.tp.dto.admin.UserGroupDTO;
import com.tp.dto.requestSearch.CheckingMeetingResultSearchDTO;
import com.tp.dto.requestSearch.RequestSearchDTO;
import com.tp.dto.requestSearch.UserGroupSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.model.admin.*;
import com.tp.repo.UserGroupRepo;
import com.tp.service.UserGroupService;
import com.tp.util.BaseMapper;
import com.tp.util.Const;
import com.tp.util.DataUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class UserGroupServiceImpl extends BaseServiceImpl implements UserGroupService {

    private final BaseMapper<UserGroup, UserGroupDTO> mapper = new BaseMapper<>(UserGroup.class,UserGroupDTO.class);

    @Autowired
    private UserGroupRepo repo;
    public static final Logger logger = Logger.getLogger(UserGroupService.class);

    public UserGroupDTO findOne(Long id) throws Exception {
        return mapper.toDtoBean(repo.findOne(id));
    }

    @Override
    public ResponseSearchDTO<UserGroupDTO> load(RequestSearchDTO requestSearchDTO) throws Exception {
        UserGroupSearchDTO userGroupSearchDTO = (UserGroupSearchDTO) requestSearchDTO;
        return repo.searchUserGroup(userGroupSearchDTO);
    }

    @Override
    public UserGroupDTO findByGroupIdUserId(Long groupId, Long userId) {
        BooleanExpression predicate = QUserGroup.userGroup.status.eq(Const.STATUS.ACTIVCE);
        predicate = predicate.and(QUserGroup.userGroup.groupId.eq(groupId));
        predicate = predicate.and(QUserGroup.userGroup.userId.eq(userId));
        List<UserGroup> list = Lists.newArrayList(repo.findAll(predicate));
        if(DataUtil.isNullOrEmpty(list))
            return null;
        return mapper.toDtoBean(list.get(0));
    }

    @Override
    public UserGroupDTO createOrUpdate(UserGroupDTO userGroupDTO, UserDTO userDTO) throws Exception {
        if (DataUtil.isNullObject(userGroupDTO.getId())){
            userGroupDTO.setCreatedDate(new Date());
            userGroupDTO.setCreatedBy(userDTO.getUserName());
        }
        userGroupDTO.setUpdatedDate(new Date());
        userDTO.setUpdatedBy(userDTO.getUserName());
        return mapper.toDtoBean(repo.save(mapper.toPersistenceBean(userGroupDTO)));
    }
}
