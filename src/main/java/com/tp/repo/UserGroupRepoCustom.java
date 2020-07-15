package com.tp.repo;

import com.tp.dto.admin.UserGroupDTO;
import com.tp.dto.requestSearch.UserGroupSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;

public interface UserGroupRepoCustom {

    public ResponseSearchDTO<UserGroupDTO> searchUserGroup(UserGroupSearchDTO userGroupSearchDTO) throws Exception;

}