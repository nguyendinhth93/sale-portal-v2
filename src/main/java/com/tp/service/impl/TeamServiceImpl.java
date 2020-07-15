package com.tp.service.impl;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.tp.dto.TeamDTO;
import com.tp.model.QTeam;
import com.tp.model.Team;
import com.tp.repo.TeamRepo;
import com.tp.service.TeamService;
import com.tp.util.BaseMapper;
import com.tp.util.Const;
import com.tp.util.DataUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl extends BaseServiceImpl implements TeamService {

    private final BaseMapper<Team, TeamDTO> mapper = new BaseMapper<>(Team.class,TeamDTO.class);

    @Autowired
    private TeamRepo repo;
    public static final Logger logger = Logger.getLogger(TeamService.class);

    public TeamDTO findOne(Long id) throws Exception {
        return mapper.toDtoBean(repo.findOne(id));
    }

    @Override
    public List<TeamDTO> findTeamByTeamLeadId(Long teamLeadId) throws Exception {
        BooleanExpression predicate = QTeam.team.status.eq(DataUtil.safeToLong(Const.STATUS.ACTIVCE));
        predicate = predicate.and(QTeam.team.teamLeadId.eq(teamLeadId));
        List<Team> teams = Lists.newArrayList(repo.findAll(predicate));
        if(DataUtil.isNullOrEmpty(teams))
            return null;
        return mapper.toDtoBean(teams);
    }

    @Override
    public List<TeamDTO> findTeamByType(Long type) throws Exception {
        BooleanExpression predicate = QTeam.team.status.eq(DataUtil.safeToLong(Const.STATUS.ACTIVCE));
        predicate = predicate.and(QTeam.team.type.eq(type));
        List<Team> teams = Lists.newArrayList(repo.findAll(predicate));
        if(DataUtil.isNullOrEmpty(teams))
            return null;
        return mapper.toDtoBean(teams);
    }

}
