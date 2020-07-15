package com.tp.repo;

import com.tp.dto.BoCheckupResultDTO;
import com.tp.dto.requestSearch.BoCheckupResultSearchDTO;
import com.tp.dto.requestSearch.BoFollowResultSearchDTO;
import com.tp.dto.requestSearch.CheckingMeetingResultSearchDTO;
import com.tp.dto.responseSearch.ResponseSearchDTO;

public interface BoCheckupResultRepoCustom {

    ResponseSearchDTO<BoCheckupResultDTO> searchBoCheckupResult(BoCheckupResultSearchDTO boCheckupResultInputSearchDTO) throws Exception;

    ResponseSearchDTO<BoCheckupResultDTO> searchBoFollowResult(BoFollowResultSearchDTO boFollowResultSearchDTO) throws Exception;

    ResponseSearchDTO<BoCheckupResultDTO> searchCheckingMeetingResult(CheckingMeetingResultSearchDTO checkingMeetingResultSearchDTO) throws Exception;
}