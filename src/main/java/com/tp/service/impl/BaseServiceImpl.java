package com.tp.service.impl;

import com.tp.util.DbUtil;

import javax.persistence.EntityManager;
import java.util.Date;

/**
 * @author DINH NGUYEN, add 27/05/2015
 */
public abstract class BaseServiceImpl {

    public Date getSysDate(EntityManager em) throws Exception {
        return DbUtil.getSysDateMysql(em);
    }

    public Long getSequence(EntityManager em, String sequence) throws Exception {
        return DbUtil.getSequenceLongValue(em, sequence);
    }

}
