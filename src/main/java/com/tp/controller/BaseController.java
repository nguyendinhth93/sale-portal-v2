package com.tp.controller;

import com.tp.config.lang.LanguageBean;
import com.tp.dto.ApDomainDTO;
import com.tp.dto.AreaDTO;
import com.tp.dto.OptionSetValueDTO;
import com.tp.dto.admin.UserDTO;
import com.tp.dto.fw.BaseMessage;
import com.tp.service.ApDomainService;
import com.tp.service.AreaService;
import com.tp.service.OptionSetValueService;
import com.tp.util.Const;
import com.tp.util.DataUtil;
import com.tp.util.DateUtil;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.tp.config.lang.Utf8ResourceBundle.UTF8_CONTROL;

/**
 * Created by hopnv on 20/07/2017.
 */
public abstract class BaseController implements Serializable {

    final static Logger logger = LoggerFactory.getLogger(BaseController.class);

    public static final String[] ALLOW_EXTENSION_TEMPLATE_XLS = {"xls", "xlsx"};
    public static final int MAX_SIZE_5M = 5 * 1024 * 1024;

    @Autowired
    private LanguageBean languageBean;

    @Autowired
    private ApDomainService apDomainService;
    @Autowired
    private OptionSetValueService optionSetValueService;
    @Autowired
    private AreaService areaService;

    protected ResourceBundle bundle;

    protected abstract void init() throws Exception;

    protected void reportSuccess(String id, String key){
        reportSuccess(id, key, null);
    }

    protected void reportSuccess(String id, String key, String... params){
        FacesContext.getCurrentInstance().addMessage(id, new FacesMessage(FacesMessage.SEVERITY_INFO, null, DataUtil.isNullOrEmpty(params) ? getText(key) : getTextParams(key, params)));
        RequestContext.getCurrentInstance().update(id);
    }

    protected void reportSuccessGrowl(String id, String key){
        reportSuccessGrowl(id, key, new String[]{});
    }

    protected void reportSuccessGrowl(String id, String key, String... params){
        FacesContext.getCurrentInstance().addMessage(id, new FacesMessage(FacesMessage.SEVERITY_INFO, getText("common.msg.summary.success"), DataUtil.isNullOrEmpty(params) ? getText(key) : getTextParams(key, params)));
        if(!DataUtil.isNullOrEmpty(id))
            RequestContext.getCurrentInstance().update(id);
    }

    protected void reportErrorGrowl(String id, String key){
        reportErrorGrowl(id, key, new String[]{});
    }

    protected void reportErrorGrowl(String id, String key, String... params){
        FacesContext.getCurrentInstance().addMessage(id, new FacesMessage(FacesMessage.SEVERITY_ERROR, getText("common.msg.summary.fail"), DataUtil.isNullOrEmpty(params) ? getText(key) : getTextParams(key, params)));
        if(!DataUtil.isNullOrEmpty(id))
            RequestContext.getCurrentInstance().update(id);
        FacesContext.getCurrentInstance().validationFailed();
    }

    protected void reportError(String id, String key){
        reportError(id, key, null);
    }

    protected void reportErrorSystem(String id){
        reportErrorGrowl(id, "common.error.happened.againAction");
    }

    protected void reportError(String id, String key, String... params){
        FacesContext.getCurrentInstance().addMessage(id, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, DataUtil.isNullOrEmpty(params) ? getText(key) : getTextParams(key, params)));
        if(!DataUtil.isNullOrEmpty(id))
            RequestContext.getCurrentInstance().update(id);
        FacesContext.getCurrentInstance().validationFailed();
    }

//    public StaffDTO getStaffDTO(){
//        return (StaffDTO) getSession().getAttribute(Const.STAFF_INFO);
//    }

    public String getText(String key) {
        try {
            if (bundle == null || (bundle.getLocale().getLanguage().equals(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()))) {
                bundle = ResourceBundle.getBundle(Const.LANGUAGE.BUNDLE_NAME,
                        languageBean == null ? FacesContext.getCurrentInstance().getViewRoot().getLocale() : languageBean.getLocale(), UTF8_CONTROL);
            }
            return bundle.getString(key);
        } catch (Exception e) {
            return key;
        }
    }

    public String getTextParams(String key, String... params) {
        return MessageFormat.format(getText(key), params);
    }

    protected FacesContext getContext() {
        return FacesContext.getCurrentInstance();
    }

    protected Map getRequestMap() {
        return getContext().getExternalContext().getRequestMap();
    }

    protected HttpSession getSession() {
        return getRequest().getSession();
    }

    protected HttpServletRequest getRequest(){
        return (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    protected HttpServletResponse getResponse(){
        return (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
    }

    public boolean hasPermission(String permissionCode){
        List<String> permissions = (List<String>) getSession().getAttribute(Const.ROLE_COMPONENT);
        return permissions.contains(permissionCode);
    }

    protected Object evaluateEL(String elExpression, Class beanClazz) {
        return getContext().getApplication().evaluateExpressionGet(getContext(), elExpression, beanClazz);
    }

    public UserDTO getUser(){
        UserDTO userDTO = (UserDTO) getSession().getAttribute(Const.USER_INFO);
        if(!DataUtil.isNullObject(userDTO)) {
            return userDTO;
        }
        return new UserDTO();
    }

    public String getUsername(){
        UserDTO userDTO = (UserDTO) getSession().getAttribute(Const.USER_INFO);
        if(!DataUtil.isNullObject(userDTO)) {
            return userDTO.getUserName().toUpperCase();
        }
        return "";
    }


    public String getFullName(){
        UserDTO userDTO = (UserDTO) getSession().getAttribute(Const.USER_INFO);
        if(!DataUtil.isNullObject(userDTO)) {
            return userDTO.getFullName();
        }
        return "";
    }

    public String joinStaffCodeAndName(String code, String name){
        if(DataUtil.isNullOrEmpty(code))
            return name;
        return code + " - " + name;
    }

    public BaseMessage validateFileUploadWhiteList(UploadedFile fileAttachment, String[] extension, int maxSize) {
        BaseMessage baseMessage = new BaseMessage(true);
        if (fileAttachment == null) {
            baseMessage.setSuccess(false);
            baseMessage.setDescription(getText("common.import.file.error.file.not.found"));
            return baseMessage;
        }
        String fileName = fileAttachment.getFileName();
//         check file name
        if (DataUtil.isNullOrEmpty(fileName)) {
            baseMessage.setSuccess(false);
            baseMessage.setDescription(getText("common.import.file.error.template"));
            return baseMessage;
        }
        // check filename extension
        String[] fileNameArr = fileName.split("\\.");
        if (fileNameArr == null || fileNameArr.length != 2) {

            BaseMessage msgValid = validateFileName(fileName);
            if (!msgValid.isSuccess()) {
                return msgValid;
            }
            baseMessage.setSuccess(false);
            baseMessage.setDescription(getText("common.import.file.error.template"));
            return baseMessage;
        }

        if (fileAttachment.getContents().length == 0) {
            baseMessage.setSuccess(false);
            baseMessage.setDescription(getText("common.import.file.error.empty"));
            return baseMessage;
        }

        if (maxSize != 0 && fileAttachment.getContents().length > maxSize) {
            baseMessage.setSuccess(false);
            baseMessage.setDescription(getText("common.import.file.error.max.size"));
            return baseMessage;
        }

        //kiem tra ten file mo rong co fai la extension ko
        String extensionFileName = fileNameArr[1];
        if (!Arrays.asList(extension).contains(extensionFileName.toLowerCase())) {
            baseMessage.setSuccess(false);
            baseMessage.setDescription(getText("common.import.file.error.template"));
            return baseMessage;
        }
        return validateFileName(fileName);
    }

    public BaseMessage validateFileName(String fileName) {
        BaseMessage baseMessage = new BaseMessage(true);
        String expression = "[\\s_a-zA-Z0-9\\-\\.\\()]+";
        //Make the comparison case-insensitive.
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(fileName);
        if (!matcher.matches()) {
            baseMessage.setSuccess(false);
            baseMessage.setDescription(getText("common.import.file.error.file.name.invalid"));
            return baseMessage;
        }
        if (fileName.length() >= 100) {
            baseMessage.setSuccess(false);
            baseMessage.setDescription(getTextParams("common.import.file.error.file.name.len", fileName));
            return baseMessage;
        }

        return baseMessage;
    }

    public void focusElementByRawCSSSlector(String selector) {
        RequestContext.getCurrentInstance().execute("focusElementByRawCSSSlector('" + selector + "')");
    }


    public String convertDateToString(Date date){
        return DateUtil.dateToStringddMMyyyy(date);
    }

    public String convertDateTimeToString(Date date){
        return DateUtil.dateToStringddMMyyyyHHmmss(date);
    }

    public String convertApdomainByValueAndType(String value, String type){
        try {
            ApDomainDTO apDomainDTO = apDomainService.findByValueAndType(value,type);
            if (!DataUtil.isNullObject(apDomainDTO)){
                return apDomainDTO.getName();
            }
        } catch (Exception e) {

        }
        return "";
    }

    public String convertOptionSetByCodeAndValue(String code, String value){
        try {
            OptionSetValueDTO optionSetValueDTO = optionSetValueService.findByCodeAndValue(code,value);
            if (!DataUtil.isNullObject(optionSetValueDTO)){
                return optionSetValueDTO.getName();
            }
        } catch (Exception e) {

        }
        return "";
    }
    public String convertNumberUsingCurrentLocate(Number number) {
        return DataUtil.convertNumberUsingCurrentLocale(number);
    }

    public String findTailFile(String fileZ) {
        char[] ch = fileZ.toCharArray();
        int j = 0;
        for (int i = fileZ.length() - 1; i > 0; i--) {
            char c = ch[i];
            if (c == '.') {
                j = i;
                break;
            }
        }
        return fileZ.substring(j, fileZ.length());
    }

    public String toUpperCase(String textValue){
        return DataUtil.safeToUpper(textValue);
    }

    public String getProvinceNameById(String provinceId){
        try {
            AreaDTO areaDTO = areaService.findProvinceCRMById(provinceId);
            if (!DataUtil.isNullObject(areaDTO)){
                return areaDTO.getName();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return "";
    }

    public String getProvinceNameByCode(String provinceCode){
        try {
            AreaDTO areaDTO = areaService.findProvinceByCode(provinceCode);
            if (!DataUtil.isNullObject(areaDTO)){
                return areaDTO.getName();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return "";
    }

    public String getDistrictNameByCode(String districtCode){
        try {
            AreaDTO areaDTO = areaService.findDistrictByCode(districtCode);
            if (!DataUtil.isNullObject(areaDTO)){
                return areaDTO.getName();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return "";
    }

    public String getDistrictNameById(String districtId){
        try {
            AreaDTO areaDTO = areaService.findDistrictCRMById(districtId);
            if (!DataUtil.isNullObject(areaDTO)){
                return areaDTO.getName();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return "";
    }

    public String getCustomerPhoneByTel(String customerPhone){
        return "tel:"+DataUtil.safeToString(customerPhone);
    }
}
