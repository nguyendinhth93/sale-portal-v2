package com.tp.controller;

import com.tp.common.custom.SnDataModel;
import com.tp.common.exception.LogicException;
import com.tp.dto.AreaDTO;
import com.tp.dto.CampaignDTO;
import com.tp.dto.requestSearch.CampaignSearchDTO;
import com.tp.service.AreaService;
import com.tp.service.CampaignService;
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
public class CampaignController extends BaseController{

    final static Logger logger = LoggerFactory.getLogger(CampaignController.class);

    @Autowired
    private CampaignService campaignService;
    @Autowired
    private AreaService areaService;

    private CampaignDTO currentCampaign;
    private boolean isCreateState;
    private SnDataModel<CampaignDTO> lazyLoadCampaign;
    private List<AreaDTO> areaDTOs;


    @PostConstruct
    @Override
    public void init(){
        try {
            areaDTOs = areaService.findAllProvince();
            lazyLoadCampaign = new SnDataModel<>(campaignService, new CampaignSearchDTO());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            reportErrorGrowl(null, "common.error.happened");
        }
    }

    public void prepareToShowAdd(){
        currentCampaign = new CampaignDTO();
        isCreateState = true;
    }

    public void prepareToShowUpdate(CampaignDTO campaignDTO){
        currentCampaign = new CampaignDTO();
        try {
            BeanUtils.copyProperties(currentCampaign, campaignDTO);
            currentCampaign.setProgramCode(currentCampaign.getProgramCode());
            isCreateState = false;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }
    }

    public boolean validateBeforeSave() throws Exception {
        if(DataUtil.safeEqual(currentCampaign.getProgramCode(), currentCampaign.getProgramCode()))
            return true;

        CampaignDTO campaignDTO = campaignService.findByCode(currentCampaign.getProgramCode());
        if(campaignDTO != null){
            reportErrorGrowl(null, "campaign.msg.add.fail.detail", currentCampaign.getProgramCode());
            return false;
        }
        return true;
    }

    public void saveOrUpdate(){
        try {
            if(!validateBeforeSave()){
                return;
            }
            CampaignDTO campaignDTO = campaignService.saveOrUpdate(currentCampaign, getUsername());
            if(isCreateState)
                reportSuccessGrowl(null, "campaign.msg.add.success.detail",campaignDTO.getProgramCode());
            else
                reportSuccessGrowl(null, "campaign.msg.add.update.detail");
            RequestContext.getCurrentInstance().execute("PF('dlgCampaign').hide()");
        } catch (LogicException ex){
            reportError(null, ex.getKeyMsg(), ex.getParams());
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }

    }

    public void validateBeforeDelete(){
        if(DataUtil.isNullOrEmpty(lazyLoadCampaign.getSelectedList())){
            reportErrorGrowl(null, "apConfig.msg.delete.empty");
        }
    }

    public void prepareDeleteOne(CampaignDTO campaignDTO){
        lazyLoadCampaign.getSelectedList().clear();
        lazyLoadCampaign.getSelectedList().add(campaignDTO);
    }

    public void delete(){
        validateBeforeDelete();
        if(FacesContext.getCurrentInstance().isValidationFailed())
            return;
        try {
            campaignService.delete(lazyLoadCampaign.getSelectedList().stream().map(s -> s.getId()).collect(Collectors.toList()), getUsername());
            reportSuccessGrowl(null, "campaign.msg.delete.success", lazyLoadCampaign.getSelectedList().size() + "");
            lazyLoadCampaign.getSelectedList().clear();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }
    }

    public String getHeaderDialog() {
        return isCreateState ? getText("campaign.title.dlg.add") : getText("campaign.title.dlg.update");
    }

    public boolean isCreateState() {
        return isCreateState;
    }

    public void setCreateState(boolean createState) {
        isCreateState = createState;
    }

    public CampaignService getCampaignService() {
        return campaignService;
    }

    public void setCampaignService(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    public CampaignDTO getCurrentCampaign() {
        return currentCampaign;
    }

    public void setCurrentCampaign(CampaignDTO currentCampaign) {
        this.currentCampaign = currentCampaign;
    }

    public SnDataModel<CampaignDTO> getLazyLoadCampaign() {
        return lazyLoadCampaign;
    }

    public void setLazyLoadCampaign(SnDataModel<CampaignDTO> lazyLoadCampaign) {
        this.lazyLoadCampaign = lazyLoadCampaign;
    }

    public List<AreaDTO> getAreaDTOs() {
        return areaDTOs;
    }

    public void setAreaDTOs(List<AreaDTO> areaDTOs) {
        this.areaDTOs = areaDTOs;
    }
}
