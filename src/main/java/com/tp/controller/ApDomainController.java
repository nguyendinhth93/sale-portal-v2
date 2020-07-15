package com.tp.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tp.common.custom.SnDataModel;
import com.tp.common.exception.LogicException;
import com.tp.dto.ApDomainDTO;
import com.tp.dto.requestSearch.ApDomainInputSearchDTO;
import com.tp.service.ApDomainService;
import com.tp.util.DataUtil;

/**
 * Created by hopnv on 20/07/2017.
 */
@ManagedBean
@Component
@Scope("view")
public class ApDomainController extends BaseController{

    final static Logger logger = LoggerFactory.getLogger(ApDomainController.class);

    @Autowired
    private ApDomainService apDomainService;

    private ApDomainDTO currentApDomain;
    private boolean isCreateState;
    private SnDataModel<ApDomainDTO> lazyLoadApDomain;

    @PostConstruct
    @Override
    public void init(){
        lazyLoadApDomain = new SnDataModel<>(apDomainService, new ApDomainInputSearchDTO());
    }

    public void prepareToShowAdd(){
        currentApDomain = new ApDomainDTO();
    }

    public void prepareToShowUpdate(ApDomainDTO apDomainDTO){
        currentApDomain = new ApDomainDTO();
        try {
            BeanUtils.copyProperties(currentApDomain, apDomainDTO);
            currentApDomain.setOldCode(currentApDomain.getCode());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }
    }

    public boolean validateBeforeSave() throws Exception {
        if(DataUtil.safeEqual(currentApDomain.getCode(), currentApDomain.getOldCode()))
            return true;

        ApDomainDTO apDomainDTO = apDomainService.findByCodeAndType(currentApDomain.getType(), currentApDomain.getCode());
        if(apDomainDTO != null){
            reportErrorGrowl(null, "apConfig.msg.add.fail.detail", currentApDomain.getType(), currentApDomain.getCode());
            return false;
        }
        return true;
    }

    public void saveOrUpdate(){

        try {
            if(!validateBeforeSave()){
                return;
            }

//            ApDomainDTO apDomainDTO = apDomainService.saveOrUpdate(currentApDomain, getStaffDTO().getCode());
            ApDomainDTO apDomainDTO = apDomainService.saveOrUpdate(currentApDomain, "");
            if(isCreateState)
                reportSuccessGrowl(null, "apConfig.msg.add.success.detail", apDomainDTO.getType(), apDomainDTO.getCode());
            else
                reportSuccessGrowl(null, "apConfig.msg.add.update.detail");
            RequestContext.getCurrentInstance().execute("PF('dlgAddApDomain').hide()");
        } catch (LogicException ex){
            reportError(null, ex.getKeyMsg(), ex.getParams());
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }

    }

    public void validateBeforeDelete(){
        if(DataUtil.isNullOrEmpty(lazyLoadApDomain.getSelectedList())){
            reportErrorGrowl(null, "apConfig.msg.delete.empty");
        }
    }

    public void prepareDeleteOne(ApDomainDTO apDomainDTO){
        lazyLoadApDomain.getSelectedList().clear();
        lazyLoadApDomain.getSelectedList().add(apDomainDTO);
    }

    public void delete(){
        validateBeforeDelete();
        if(FacesContext.getCurrentInstance().isValidationFailed())
            return;
        try {
            //apDomainService.delete(lazyLoadApDomain.getSelectedList().stream().map(s -> s.getApDomainId()).collect(Collectors.toList()), getStaffDTO().getCode());
            reportSuccessGrowl(null, "apConfig.msg.delete.success", lazyLoadApDomain.getSelectedList().size() + "");
            lazyLoadApDomain.getSelectedList().clear();
//        } catch (LogicException ex){
//            reportErrorGrowl(null, ex.getKeyMsg(), ex.getParams());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            reportErrorSystem(null);
        }
    }

    public SnDataModel<ApDomainDTO> getLazyLoadApDomain() {
        return lazyLoadApDomain;
    }

    public void setLazyLoadApDomain(SnDataModel<ApDomainDTO> lazyLoadApDomain) {
        this.lazyLoadApDomain = lazyLoadApDomain;
    }

    public String getHeaderDialog() {
        return isCreateState ? getText("apConfig.title.dlg.add") : getText("apConfig.title.dlg.update");
    }

    public boolean isCreateState() {
        return isCreateState;
    }

    public void setCreateState(boolean createState) {
        isCreateState = createState;
    }

    public ApDomainDTO getCurrentApDomain() {
        return currentApDomain;
    }

    public void setCurrentApDomain(ApDomainDTO currentApDomain) {
        this.currentApDomain = currentApDomain;
    }
}
