package com.tp.repo;

import com.tp.dto.*;
import com.tp.dto.admin.UserDTO;
import com.tp.dto.requestSearch.SaleFunnelCCASearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;

import java.util.List;

public interface DsaResultMeetingRepoCustom {

    public ResponseSearchDTO<DsaResultMeetingDTO> searchDsaResultMeeting(DsaResultMeetingSearchDTO dsaResultMeetingSearchDTO) throws Exception;

    public ResponseSearchDTO<DsaResultMeetingDTO> searchDsaResultMeetingForDsa(DsaResultMeetingSearchDTO dsaResultMeetingSearchDTO) throws Exception;

    public ResponseSearchDTO<DsaResultMeetingDTO> searchDsaResultMeetingForDsaSort(DsaResultMeetingSearchDTO dsaResultMeetingSearchDTO) throws Exception;

    public ResponseSearchDTO<DsaResultMeetingDTO> searchDsaResultMeetingForCca(DsaResultMeetingSearchDTO dsaResultMeetingSearchDTO) throws Exception;

    public ResponseSearchDTO<DsaResultMeetingDTO> searchDsaResultMeetingForCcaSort(DsaResultMeetingSearchDTO dsaResultMeetingSearchDTO) throws Exception;

    public ResponseSearchDTO<DsaResultMeetingDTO> searchDsaResultMeetingAccepted(DsaResultMeetingSearchDTO dsaResultMeetingSearchDTO) throws Exception;

    public ResponseSearchDTO<RatioCollectMeetingDTO> searchRatioCollectMeeting(RatioCollectMeetingSearchDTO ratioCollectMeetingSearchDTO) throws Exception;

    public List<AreaDTO> findAllDistrictByDsa(String dsaCode) throws Exception;

    public List<AreaDTO> findAllDistrictByDsaAndDay(String dsaCode,Long dayQuery) throws Exception;

    public List<UserDTO> getAllDsaAndMeetingAvailiableByTlDsa(String tlDsaCode) throws Exception;

    public List<SaleFunnelCcaDTO> findSaleFunnelData(SaleFunnelCCASearchDTO saleFunnelCCASearchDTO) throws Exception;

    public List<SaleFunnelCcaDTO> findAllSaleFunnelData(SaleFunnelCCASearchDTO saleFunnelCCASearchDTO) throws Exception;

    public Long countAllSaleFunnelData(SaleFunnelCCASearchDTO saleFunnelCCASearchDTO) throws Exception;

    public Long countSaleFunnel(SaleFunnelCCASearchDTO saleFunnelCCASearchDTO) throws Exception;

    public SaleFunnelCcaDTO mergeDataSaleFunnel(SaleFunnelCcaDTO saleFunnelCcaDTO, SaleFunnelCCASearchDTO saleFunnelCCASearchDTO);

    public List<BoundCodeDTO> findAllBoundCode() throws Exception;

    public String findEmployeeIdByUser(String username, String dataSource) throws Exception;

}