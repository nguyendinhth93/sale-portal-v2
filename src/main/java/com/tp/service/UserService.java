package com.tp.service;

import com.tp.common.exception.LogicException;
import com.tp.dto.admin.UserDTO;
import com.tp.model.admin.User;

import java.util.List;

/**
 * Created by user on 21/08/2017.
 */
public interface UserService extends BaseSearchService<UserDTO> {
    UserDTO saveOrUpdate(UserDTO userDTO, String username) throws Exception;
    UserDTO findByUserName(String userName) throws Exception;
    void delete(List<Long> userId, String username) throws LogicException,Exception;
    UserDTO findById(Long userId) throws Exception;
    UserDTO login(String userName, String password) throws Exception;
    List<UserDTO> findDSAByUser(String userName) throws Exception;
    List<UserDTO> findTlDSAByUser(String userName) throws Exception;
    public List<UserDTO> findTlDSA() throws Exception;
    List<UserDTO> findAllCCa() throws Exception;

    List<UserDTO> findAllStaffCodeByTeam(String teamCode) throws Exception;
    public List<UserDTO> findAllStaffCodeByListTeam(List<String> teamCodes) throws Exception;
    UserDTO findByVoiceCode(String voiceCode) throws Exception;
    UserDTO findTlByUserName(String userName) throws Exception;
    public List<UserDTO> findAllByUserTl(String userNameTl) throws Exception;
    public List<UserDTO> findAllUserOnTeamByUser(String userName) throws Exception;
    List<UserDTO> findAllUserByRole(List<String> roles) throws Exception;
    List<UserDTO> findAllActive() throws Exception;
}
