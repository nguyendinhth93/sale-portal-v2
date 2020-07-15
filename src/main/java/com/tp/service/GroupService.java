package com.tp.service;

import com.tp.dto.admin.GroupDTO;

import java.util.List;

public interface GroupService {

    public GroupDTO findOne(Long id) throws Exception;

    public List<GroupDTO> findAll() throws Exception;

}