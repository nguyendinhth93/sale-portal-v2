package com.tp.repo.impl;

import com.tp.model.DsaResultMeetingFile;
import com.tp.repo.DsaResultMeetingFileRepoCustom;
import com.tp.util.Const;
import com.tp.util.DataUtil;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class DsaResultMeetingFileRepoImpl implements DsaResultMeetingFileRepoCustom {

    public static final Logger logger = Logger.getLogger(DsaResultMeetingFileRepoCustom.class);

    @PersistenceContext(unitName = Const.PERSISTENCE.SALE_PORTAL)
    private EntityManager em;

    @Override
    public DsaResultMeetingFile findByDsaResultMeetingLog(Long dsaResultMeetingId,Long dsaResultMeetingLogId) throws Exception {
        StringBuilder sql = new StringBuilder(" SELECT * FROM DSA_RESULT_MEETING_FILE WHERE 1=1 AND DSA_RESULT_MEETING_LOG_ID = :dsaResultMeetingLogId AND DSA_RESULT_MEETING_ID = :dsaResultMeetingId");
        Query querySelect = em.createNativeQuery(sql.toString(),DsaResultMeetingFile.class);
        querySelect.setParameter("dsaResultMeetingLogId",dsaResultMeetingLogId);
        querySelect.setParameter("dsaResultMeetingId",dsaResultMeetingId);
        List<DsaResultMeetingFile> dsaResultMeetingFiles = querySelect.getResultList();
        if (!DataUtil.isNullOrEmpty(dsaResultMeetingFiles)){
            return dsaResultMeetingFiles.get(0);
        }
        return null;
    }

    @Override
    public DsaResultMeetingFile findLastByDsaResultMeeting(Long dsaResultMeetingId) throws Exception {
        StringBuilder sql = new StringBuilder(" SELECT * FROM DSA_RESULT_MEETING_FILE WHERE 1=1 AND DSA_RESULT_MEETING_ID = :dsaResultMeetingId ORDER BY CREATED_DATE DESC");
        Query querySelect = em.createNativeQuery(sql.toString(),DsaResultMeetingFile.class);
        querySelect.setParameter("dsaResultMeetingId",dsaResultMeetingId);
        List<DsaResultMeetingFile> dsaResultMeetingFiles = querySelect.getResultList();
        if (!DataUtil.isNullOrEmpty(dsaResultMeetingFiles)){
            return dsaResultMeetingFiles.get(0);
        }
        return null;
    }
}