package com.tp.repo.impl;

import com.tp.model.DsaResultMeetingLog;
import com.tp.repo.DsaResultMeetingLogRepoCustom;
import com.tp.util.Const;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class DsaResultMeetingLogRepoImpl implements DsaResultMeetingLogRepoCustom {

    public static final Logger logger = Logger.getLogger(DsaResultMeetingLogRepoCustom.class);

    @PersistenceContext(unitName = Const.PERSISTENCE.SALE_PORTAL)
    private EntityManager em;

    @Override
    public List<DsaResultMeetingLog> findByDsaResultMeeting(Long dsaResultMeetingId) throws Exception {
        StringBuilder sql = new StringBuilder(" SELECT * FROM DSA_RESULT_MEETING_LOG WHERE 1=1 AND DSA_RESULT_MEETING_ID = :dsaResultMeetingId ORDER BY CREATED_DATE DESC");
        Query querySelect = em.createNativeQuery(sql.toString(),DsaResultMeetingLog.class);
        querySelect.setParameter("dsaResultMeetingId",dsaResultMeetingId);
        List<DsaResultMeetingLog> dsaResultMeetingLogs = querySelect.getResultList();
        return dsaResultMeetingLogs;
    }
}