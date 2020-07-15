package com.tp.service.impl;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.tp.dto.AreaDTO;
import com.tp.model.Area;
import com.tp.model.QArea;
import com.tp.repo.AreaRepo;
import com.tp.service.AreaService;
import com.tp.util.BaseMapper;
import com.tp.util.Const;
import com.tp.util.DataUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl extends BaseServiceImpl implements AreaService {

    private final BaseMapper<Area,AreaDTO> mapper = new BaseMapper<>(Area.class,AreaDTO.class);

    @Autowired
    private AreaRepo repo;
    public static final Logger logger = Logger.getLogger(AreaService.class);

    public AreaDTO findOne(Long id) throws Exception {
        return mapper.toDtoBean(repo.findOne(id));
    }

    @Override
    public List<AreaDTO> findAllProvince() throws Exception {
        BooleanExpression predicate = QArea.area.type.equalsIgnoreCase(Const.AREA.PROVINCE);
        predicate = predicate.and(QArea.area.status.eq(Const.STATUS.ACTIVCE));

        List<Area> areas = Lists.newArrayList(repo.findAll(predicate));
        return mapper.toDtoBean(areas);
    }

    @Override
    public List<AreaDTO> findDistrictByProvinceCode(String provinceCode) throws Exception {
        BooleanExpression predicate = QArea.area.type.equalsIgnoreCase(Const.AREA.DISTRICT);
        predicate = predicate.and(QArea.area.status.eq(Const.STATUS.ACTIVCE));
        predicate = predicate.and(QArea.area.parentCode.eq(provinceCode));
        List<Area> areas = Lists.newArrayList(repo.findAll(predicate));
        return mapper.toDtoBean(areas);
    }

    @Override
    public AreaDTO findProvinceByCode(String provinceCode) throws Exception {
        BooleanExpression predicate = QArea.area.type.equalsIgnoreCase(Const.AREA.PROVINCE);
        predicate = predicate.and(QArea.area.status.eq(Const.STATUS.ACTIVCE));
        predicate = predicate.and(QArea.area.code.eq(provinceCode));
        List<Area> areas = Lists.newArrayList(repo.findAll(predicate));
        if (!DataUtil.isNullOrEmpty(areas)) {
            return mapper.toDtoBean(areas.get(0));
        }
        return null;
    }

    @Override
    public AreaDTO findDistrictByCode(String districtCode) throws Exception {
        BooleanExpression predicate = QArea.area.type.equalsIgnoreCase(Const.AREA.DISTRICT);
        predicate = predicate.and(QArea.area.status.eq(Const.STATUS.ACTIVCE));
        predicate = predicate.and(QArea.area.code.eq(districtCode));
        List<Area> areas = Lists.newArrayList(repo.findAll(predicate));
        if (!DataUtil.isNullOrEmpty(areas)) {
            return mapper.toDtoBean(areas.get(0));
        }
        return null;
    }

    @Override
    public List<AreaDTO> findAllProvinceCRM() throws Exception {
        return repo.findAllProvinceCRM();
    }

    @Override
    public List<AreaDTO> findAllDistrictCRMById(String provinceId) throws Exception {
        return repo.findAllDistrictCRMById(provinceId);
    }

    @Override
    public AreaDTO findProvinceCRMById(String provinceId) throws Exception {
        return repo.findProvinceCRMById(provinceId);
    }

    @Override
    public AreaDTO findDistrictCRMById(String districtId) throws Exception {
        return repo.findDistrictCRMById(districtId);
    }

}
