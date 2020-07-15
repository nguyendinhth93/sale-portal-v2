package com.tp.service.impl;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.tp.common.exception.LogicException;
import com.tp.dto.CcaRoleDTO;
import com.tp.dto.OptionSetValueDTO;
import com.tp.dto.TeamDTO;
import com.tp.dto.admin.UserDTO;
import com.tp.dto.requestSearch.RequestSearchDTO;
import com.tp.dto.requestSearch.UserInputSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchUserDTO;
import com.tp.model.admin.QUser;
import com.tp.model.admin.User;
import com.tp.repo.UserRepo;
import com.tp.service.CcaRoleService;
import com.tp.service.OptionSetValueService;
import com.tp.service.TeamService;
import com.tp.service.UserService;
import com.tp.util.BaseMapper;
import com.tp.util.Const;
import com.tp.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 21/08/2017.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    CcaRoleService ccaRoleService;
    @Autowired
    TeamService teamService;
    @Autowired
    OptionSetValueService optionSetValueService;

    @Autowired
    PasswordEncoder passwordEncoder;

    private BaseMapper<User, UserDTO> mapper = new BaseMapper<>(User.class, UserDTO.class);

    @Override
    public ResponseSearchDTO<UserDTO> load(RequestSearchDTO requestSearchDTO) throws Exception {
        UserInputSearchDTO userInputSearchDTO = (UserInputSearchDTO) requestSearchDTO;
        ResponseSearchDTO<UserDTO> userDTOResponseSearch = userRepo.searchUser(userInputSearchDTO);
        List<UserDTO> userDTOs = userDTOResponseSearch.getData();
        if (!DataUtil.isNullOrEmpty(userDTOs)){
            for (UserDTO userDTO:userDTOs){
                OptionSetValueDTO optionSetValueDTO = optionSetValueService.findByCodeAndValue("SALE_COMMON_STATUS",DataUtil.safeToString(userDTO.getStatus()));
                userDTO.setStatusName(optionSetValueDTO.getName());
                userDTO.setStatusName(optionSetValueDTO.getName());
            }
        }
        userDTOResponseSearch.setData(userDTOs);
        return userDTOResponseSearch;
    }

    @Override
    public UserDTO saveOrUpdate(UserDTO userDTO, String username) throws Exception {
        boolean isCreate = userDTO.getUserId() == null ? true : false;
        Date currentDate = new Date();
        if(isCreate){
            if (DataUtil.isNullOrEmpty(userDTO.getPassWord())){
                userDTO.setPassWord("123456");
            }
            userDTO.setPassWord(passwordEncoder.encode(userDTO.getPassWord()));
            userDTO.setCreatedDate(currentDate);
            userDTO.setCreatedBy(username);
            userDTO.setUpdatedBy(username);
            userDTO.setUpdatedDate(currentDate);
        } else {
            userDTO.setUpdatedBy(username);
            userDTO.setUpdatedDate(currentDate);
        }
        userRepo.saveAndFlush(mapper.toPersistenceBean(userDTO));
        return userDTO;
    }

    @Override
    public UserDTO findByUserName(String userName) throws Exception {
        BooleanExpression predicate = QUser.user.userName.equalsIgnoreCase(userName);
        predicate = predicate.and(QUser.user.status.eq(Const.STATUS.ACTIVCE));
        List<User> list = Lists.newArrayList(userRepo.findAll(predicate));
        if(DataUtil.isNullOrEmpty(list))
            return null;
        return mapper.toDtoBean(list.get(0));
    }

    @Override
    public void delete(List<Long> userId, String username) throws LogicException, Exception {
        Date currentDate = new Date();
        List<User> list = new ArrayList<>();
        for(Long id : userId) {
            User user = userRepo.findOne(id);
            if (user == null || DataUtil.safeEqual(user.getStatus(), Const.STATUS.INACTIVCE)) {
                throw new LogicException("location.msg.delete.fail");
            }
            user.setStatus(Const.STATUS.INACTIVCE);
            user.setCreatedBy(username);
            user.setCreatedDate(currentDate);
            list.add(user);
        }
        userRepo.save(list);
    }

    @Override
    public UserDTO findById(Long userId) throws Exception {
        BooleanExpression predicate = QUser.user.userId.eq(userId);
        predicate = predicate.and(QUser.user.status.eq(Const.STATUS.ACTIVCE));
        List<User> list = Lists.newArrayList(userRepo.findAll(predicate));
        if(DataUtil.isNullOrEmpty(list))
            return null;
        return mapper.toDtoBean(list.get(0));
    }

    @Override
    public UserDTO login(String userName, String password) throws Exception {
        UserDTO userDTO = findByUserName(userName);
        if (userDTO == null) {
            throw new LogicException("login.invalid.username");
        }
        boolean validPassword = passwordEncoder.matches(password, userDTO.getPassWord());

        if (!validPassword) {
            throw new LogicException("login.invalid.password");
        }
        return userDTO;
    }

    @Override
    public List<UserDTO> findDSAByUser(String userName) throws Exception{
        return mapper.toDtoBean(userRepo.findDSAByUser(userName));
    }

    @Override
    public List<UserDTO> findTlDSAByUser(String userName) throws Exception {
        return mapper.toDtoBean(userRepo.findTlDSAByUser(userName));
    }

    @Override
    public List<UserDTO> findTlDSA() throws Exception {
        return mapper.toDtoBean(userRepo.findTlDSA());
    }

    @Override
    public List<UserDTO> findAllCCa() throws Exception {
        return mapper.toDtoBean(userRepo.findAllCCa());
    }

    @Override
    public List<UserDTO> findAllStaffCodeByTeam(String teamCode) throws Exception {
        return mapper.toDtoBean(userRepo.findAllStaffCodeByTeam(teamCode));
    }

    @Override
    public List<UserDTO> findAllStaffCodeByListTeam(List<String> teamCodes)  throws Exception {
        return mapper.toDtoBean(userRepo.findAllStaffCodeByListTeam(teamCodes));
    }

    @Override
    public UserDTO findByVoiceCode(String voiceCode) throws Exception {
        BooleanExpression predicate = QUser.user.voiceCode.eq(voiceCode);
        predicate = predicate.and(QUser.user.status.eq(Const.STATUS.ACTIVCE));
        List<User> list = Lists.newArrayList(userRepo.findAll(predicate));
        if(DataUtil.isNullOrEmpty(list))
            return null;
        return mapper.toDtoBean(list.get(0));
    }

    @Override
    public UserDTO findTlByUserName(String userName) throws Exception {
        return mapper.toDtoBean(userRepo.findTlByUserName(userName));
    }

    @Override
    public List<UserDTO> findAllByUserTl(String userNameTl) throws Exception {
        return mapper.toDtoBean(userRepo.findAllByUserTl(userNameTl));
    }

    @Override
    public List<UserDTO> findAllUserOnTeamByUser(String userName) throws Exception {
        UserDTO userDTO = findByUserName(userName);
        if (!DataUtil.isNullObject(userDTO)) {
            CcaRoleDTO ccaRoleDTO = ccaRoleService.findByUserId(userDTO.getUserId());
            if (!DataUtil.isNullObject(ccaRoleDTO)){
                TeamDTO teamDTO = teamService.findOne(ccaRoleDTO.getTeamId());
                if (!DataUtil.isNullObject(teamDTO)){
                    return mapper.toDtoBean(userRepo.findAllByTeamId(teamDTO.getId()));
                }
            }
        }
        return null;
    }

    @Override
    public List<UserDTO> findAllUserByRole(List<String> roles) throws Exception {
        BooleanExpression predicate = QUser.user.role.in(roles);
        predicate = predicate.and(QUser.user.status.eq(Const.STATUS.ACTIVCE));
        List<User> list = Lists.newArrayList(userRepo.findAll(predicate));
        return mapper.toDtoBean(list);
    }

    @Override
    public List<UserDTO> findAllActive() throws Exception {
        BooleanExpression predicate = QUser.user.status.eq(Const.STATUS.ACTIVCE);
        List<User> list = Lists.newArrayList(userRepo.findAll(predicate));
        return mapper.toDtoBean(list);
    }
}
