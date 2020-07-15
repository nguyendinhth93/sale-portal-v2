package com.tp.repo.impl;

import com.tp.dto.AreaDTO;
import com.tp.dto.VoiceDetailReportSearchDTO;
import com.tp.dto.VoiceSummaryDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;
import com.tp.model.DsaResultMeeting;
import com.tp.repo.VoiceSummaryRepoCustom;
import com.tp.util.Const;
import com.tp.util.DataUtil;
import com.tp.util.DateUtil;
import com.tp.util.DbUtil;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class VoiceSummaryRepoImpl implements VoiceSummaryRepoCustom {

    public static final Logger logger = Logger.getLogger(VoiceSummaryRepoCustom.class);

    @PersistenceContext(unitName = Const.PERSISTENCE.SALE_PORTAL)
    private EntityManager em;

    @Override
    public ResponseSearchDTO<VoiceSummaryDTO> searchVoiceSummary(VoiceDetailReportSearchDTO voiceDetailReportSearchDTO) throws Exception {
        ResponseSearchDTO<VoiceSummaryDTO> responseSearch = new ResponseSearchDTO<>();
        List<VoiceSummaryDTO> voiceSummaryDTOs = new ArrayList<>();
        StringBuilder sql = new StringBuilder(" SELECT {0} FROM ( ");
        sql.append("  select vs.call_date, t.team_code,  ");
        sql.append("  (select full_name from user where id = t.team_lead_id) jtl_cca_name,   ");
        sql.append("  vs.voice_code, u.full_name, vs.total_customer, ");
        sql.append("  vs.total_call, vs.on_call,vs.answered,vs.busy,vs.failed,vs.no_answer,vs.talk_time ");
        sql.append("  from cca_role cr  ");
        sql.append("  inner join user u on cr.user_id = u.id ");
        sql.append("  inner join team t on cr.team_id = t.id ");
        sql.append("  inner join voice_summary vs on u.voice_code = vs.voice_code  ");
        sql.append(" where 1=1 ");
        if (!DataUtil.isNullObject(voiceDetailReportSearchDTO.getFromDate())){
            sql.append(" AND DATE(vs.call_date) >= :fromDate ");
        }
        if (!DataUtil.isNullObject(voiceDetailReportSearchDTO.getToDate())){
            sql.append(" AND DATE(vs.call_date) <= :toDate ");
        }
        if (!DataUtil.isNullObject(voiceDetailReportSearchDTO.getTeamCode())){
            sql.append(" AND UPPER(t.team_code) = :teamCode ");
        }
        if (!DataUtil.isNullObject(voiceDetailReportSearchDTO.getTeamCodes())){
            sql.append(" AND UPPER(t.team_code) IN :teamCodes ");
        }
        if (!DataUtil.isNullObject(voiceDetailReportSearchDTO.getVoiceCode())){
            sql.append(" AND UPPER(u.voice_code) = :voiceCode ");
        }
        if (!DataUtil.isNullObject(voiceDetailReportSearchDTO.getStaffCode())){
            sql.append(" AND UPPER(u.user_name) = :userName ");
        }
        sql.append("  group by vs.call_date, t.team_code, jtl_cca_name, vs.voice_code) VOICE_SUMMARY_REPORT ");
        String sqlCount = MessageFormat.format(sql.toString(), "count(*)");
        Query queryCount = em.createNativeQuery(sqlCount);
        String sqlSelect = MessageFormat.format(sql.toString(), " * ");
        Query querySelect = em.createNativeQuery(sqlSelect);
        querySelect.setFirstResult(voiceDetailReportSearchDTO.getFirst());
        querySelect.setMaxResults(voiceDetailReportSearchDTO.getPageSize());

        if (!DataUtil.isNullObject(voiceDetailReportSearchDTO.getFromDate())){
            queryCount.setParameter("fromDate", DateUtil.date2String(voiceDetailReportSearchDTO.getFromDate()));
            querySelect.setParameter("fromDate", DateUtil.date2String(voiceDetailReportSearchDTO.getFromDate()));
        }
        if (!DataUtil.isNullObject(voiceDetailReportSearchDTO.getToDate())){
            queryCount.setParameter("toDate", DateUtil.date2String(voiceDetailReportSearchDTO.getToDate()));
            querySelect.setParameter("toDate", DateUtil.date2String(voiceDetailReportSearchDTO.getToDate()));
        }
        if (!DataUtil.isNullObject(voiceDetailReportSearchDTO.getTeamCode())){
            queryCount.setParameter("teamCode",DataUtil.safeToUpper(voiceDetailReportSearchDTO.getTeamCode()));
            querySelect.setParameter("teamCode", DataUtil.safeToUpper(voiceDetailReportSearchDTO.getTeamCode()));
        }
        if (!DataUtil.isNullObject(voiceDetailReportSearchDTO.getVoiceCode())){
            queryCount.setParameter("voiceCode",DataUtil.safeToUpper(voiceDetailReportSearchDTO.getVoiceCode()));
            querySelect.setParameter("voiceCode", DataUtil.safeToUpper(voiceDetailReportSearchDTO.getVoiceCode()));
        }
        if (!DataUtil.isNullObject(voiceDetailReportSearchDTO.getStaffCode())){
            queryCount.setParameter("userName",DataUtil.safeToUpper(voiceDetailReportSearchDTO.getStaffCode()));
            querySelect.setParameter("userName", DataUtil.safeToUpper(voiceDetailReportSearchDTO.getStaffCode()));
        }
        if (!DataUtil.isNullObject(voiceDetailReportSearchDTO.getTeamCodes())){
            queryCount.setParameter("teamCodes",voiceDetailReportSearchDTO.getTeamCodes());
            querySelect.setParameter("teamCodes", voiceDetailReportSearchDTO.getTeamCodes());
        }

        responseSearch.setRowCount(DataUtil.safeToLong(queryCount.getSingleResult()));
        List resultList = querySelect.getResultList();

        if (!DataUtil.isNullOrEmpty(resultList)){
            for (int i = 0; i < resultList.size(); i++) {
                Object[] obj = (Object[]) resultList.get(i);
                VoiceSummaryDTO voiceSummaryDTO = new VoiceSummaryDTO();
                if (obj[0] != null) {
                    voiceSummaryDTO.setCallDate(DateUtil.stringToDateWithOutTime(obj[0].toString(),DateUtil.FORMAT_DATE_YYYYMMDD));
                }
                if (obj[1] != null) {
                    voiceSummaryDTO.setTeam(obj[1].toString());
                }
                if (obj[2] != null) {
                    voiceSummaryDTO.setJtlCcaCode(obj[2].toString());
                }
                if (obj[3] != null) {
                    voiceSummaryDTO.setVoiceCode(obj[3].toString());
                }
                if (obj[4] != null) {
                    voiceSummaryDTO.setCcaCode(obj[4].toString());
                }
                if (obj[5] != null) {
                    voiceSummaryDTO.setTotalCustomer(DataUtil.safeToLong(obj[5].toString()));
                }
                if (obj[6] != null) {
                    voiceSummaryDTO.setTotalCall(DataUtil.safeToLong(obj[6].toString()));
                }
                if (obj[7] != null) {
                    voiceSummaryDTO.setOnCall(DataUtil.safeToLong(obj[7].toString()));
                }
                if (obj[8] != null) {
                    voiceSummaryDTO.setAnswered(DataUtil.safeToLong(obj[8].toString()));
                }
                if (obj[9] != null) {
                    voiceSummaryDTO.setBusy(DataUtil.safeToLong(obj[9].toString()));
                }
                if (obj[10] != null) {
                    voiceSummaryDTO.setFailed(DataUtil.safeToLong(obj[10].toString()));
                }
                if (obj[11] != null) {
                    voiceSummaryDTO.setNoAnswer(DataUtil.safeToLong(obj[11].toString()));
                }
                if (obj[12] != null) {
                    voiceSummaryDTO.setTalkTime(DataUtil.safeToDouble(obj[12].toString()));
                }
                voiceSummaryDTOs.add(voiceSummaryDTO);
            }
        }

        responseSearch.setData(voiceSummaryDTOs);

        return responseSearch;
    }
}