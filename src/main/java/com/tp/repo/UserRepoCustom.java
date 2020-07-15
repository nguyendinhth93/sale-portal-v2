package com.tp.repo;

import com.tp.dto.admin.UserDTO;
import com.tp.dto.requestSearch.UserInputSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.model.admin.User;

import java.util.List;

/**
 * Created by user on 21/08/2017.
 */
public interface UserRepoCustom {
    ResponseSearchDTO<UserDTO> searchUser (UserInputSearchDTO userInputSearchDTO) throws Exception;
    public List<User> findDSAByUser(String userName) throws Exception;
    List<User> findTlDSAByUser(String userName) throws Exception;

    public List<User> findTlDSA() throws Exception;
    public List<User> findAllCCa() throws Exception;

    public List<User> findAllStaffCodeByTeam(String teamCode) throws Exception;
    public List<User> findAllStaffCodeByListTeam(List<String> teamCodes) throws Exception;
    public List<User> findAllByUserTl(String userNameTl) throws Exception;
    public List<User> findAllByTeamId(Long teamId) throws Exception;
    User findTlByUserName(String userName) throws Exception;
}
