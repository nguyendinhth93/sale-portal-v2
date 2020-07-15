package com.tp.controller;

import com.tp.common.custom.SnDataModel;
import com.tp.common.exception.LogicException;
import com.tp.dto.CampaignDTO;
import com.tp.dto.CreditScoringDTO;
import com.tp.dto.requestSearch.CreditScoringSearchDTO;
import com.tp.service.CampaignService;
import com.tp.service.CreditScoringService;
import com.tp.util.DataUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by DINH NGUYEN on 20/07/2017.
 */
@ManagedBean
@Component
@Scope("view")
public class CreditScoringController extends BaseController{

    final static Logger logger = LoggerFactory.getLogger(CreditScoringController.class);

    @Autowired
    private CreditScoringService creditScoringService;
    @Autowired
    private CampaignService campaignService;

    private CreditScoringDTO currentCreditScoring;
    private boolean isCreateState;
    private SnDataModel<CreditScoringDTO> lazyLoadCreditScoring;
    private List<CampaignDTO> campaignDTOs;

    @PostConstruct
    @Override
    public void init(){
        try {
            campaignDTOs = campaignService.findAllCampaignActive();
            lazyLoadCreditScoring = new SnDataModel<>(creditScoringService, new CreditScoringSearchDTO());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            reportErrorGrowl(null, "common.error.happened");
        }
    }

    public void prepareToShowAdd(){
        currentCreditScoring = new CreditScoringDTO();
        isCreateState = true;
    }

    public void prepareToShowUpdate(CreditScoringDTO creditScoringDTO){
        currentCreditScoring = new CreditScoringDTO();
        try {
            BeanUtils.copyProperties(currentCreditScoring, creditScoringDTO);
            currentCreditScoring.setIsdn(currentCreditScoring.getIsdn());
            isCreateState = false;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }
    }

    public boolean validateBeforeSave() throws Exception {
        if(DataUtil.safeEqual(currentCreditScoring.getIsdn(), currentCreditScoring.getIsdn()))
            return true;

        CreditScoringDTO creditScoringDTO = creditScoringService.findByIsdnAndProgramCode(currentCreditScoring.getIsdn(),currentCreditScoring.getProgramCode());
        if(creditScoringDTO != null){
            reportErrorGrowl(null, "creditscore.msg.add.fail.detail", currentCreditScoring.getIsdn(), currentCreditScoring.getProgramCode());
            return false;
        }
        return true;
    }

    public void saveOrUpdate(){
        try {
            if(!validateBeforeSave()){
                return;
            }
            CreditScoringDTO creditScoringDTO = creditScoringService.saveOrUpdate(currentCreditScoring, getUsername());
            if(isCreateState)
                reportSuccessGrowl(null, "creditscore.msg.add.success.detail",creditScoringDTO.getIsdn(),creditScoringDTO.getProgramCode());
            else
                reportSuccessGrowl(null, "creditscore.msg.add.update.detail");
            RequestContext.getCurrentInstance().execute("PF('dlgCreditScoring').hide()");
        } catch (LogicException ex){
            reportError(null, ex.getKeyMsg(), ex.getParams());
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }

    }

    public void validateBeforeDelete(){
        if(DataUtil.isNullOrEmpty(lazyLoadCreditScoring.getSelectedList())){
            reportErrorGrowl(null, "apConfig.msg.delete.empty");
        }
    }

    public void prepareDeleteOne(CreditScoringDTO creditScoringDTO){
        lazyLoadCreditScoring.getSelectedList().clear();
        lazyLoadCreditScoring.getSelectedList().add(creditScoringDTO);
    }

    public void delete(){
        validateBeforeDelete();
        if(FacesContext.getCurrentInstance().isValidationFailed())
            return;
        try {
            creditScoringService.delete(lazyLoadCreditScoring.getSelectedList().stream().map(s -> s.getId()).collect(Collectors.toList()), getUsername());
            reportSuccessGrowl(null, "creditscore.msg.delete.success", lazyLoadCreditScoring.getSelectedList().size() + "");
            lazyLoadCreditScoring.getSelectedList().clear();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }
    }

    public String getHeaderDialog() {
        return isCreateState ? getText("creditscore.title.dlg.add") : getText("creditscore.title.dlg.update");
    }

    public boolean isCreateState() {
        return isCreateState;
    }

    public void setCreateState(boolean createState) {
        isCreateState = createState;
    }

    public CreditScoringDTO getCurrentCreditScoring() {
        return currentCreditScoring;
    }

    public void setCurrentCreditScoring(CreditScoringDTO currentCreditScoring) {
        this.currentCreditScoring = currentCreditScoring;
    }

    public SnDataModel<CreditScoringDTO> getLazyLoadCreditScoring() {
        return lazyLoadCreditScoring;
    }

    public void setLazyLoadCreditScoring(SnDataModel<CreditScoringDTO> lazyLoadCreditScoring) {
        this.lazyLoadCreditScoring = lazyLoadCreditScoring;
    }

    public List<CampaignDTO> getCampaignDTOs() {
        return campaignDTOs;
    }

    public void setCampaignDTOs(List<CampaignDTO> campaignDTOs) {
        this.campaignDTOs = campaignDTOs;
    }
}
