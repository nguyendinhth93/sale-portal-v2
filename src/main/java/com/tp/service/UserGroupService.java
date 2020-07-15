package com.tp.service;

import com.tp.dto.admin.UserDTO;
import com.tp.dto.admin.UserGroupDTO;
public interface UserGroupService extends BaseSearchService<UserGroupDTO>{

    public UserGroupDTO findOne(Long id) throws Exception;

    public UserGroupDTO findByGroupIdUserId(Long groupId, Long userId);

    public UserGroupDTO createOrUpdate(UserGroupDTO userGroupDTO,UserDTO userDTO) throws Exception;

}