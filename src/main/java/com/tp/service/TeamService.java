package com.tp.service;

import com.tp.dto.TeamDTO;

import java.util.List;

public interface TeamService {

    public TeamDTO findOne(Long id) throws Exception;

    public List<TeamDTO> findTeamByTeamLeadId(Long teamLeadId) throws Exception;

    public List<TeamDTO> findTeamByType(Long type) throws Exception;

}