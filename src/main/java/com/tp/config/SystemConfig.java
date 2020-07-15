package com.tp.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by hopnv on 23/07/2017.
 */
@ManagedBean
@Component
@ApplicationScoped
@PropertySource(value = {"classpath:system-config.properties"})
public class SystemConfig {

    @Value("${LOGIN_URL}")
    private String loginUrl;
    @Value("${INDEX_URL}")
    private String indexUrl;
    @Value("${APP_CODE}")
    private String appCode;
    @Value("${VSA_LOGIN_URL}")
    private String vsaLoginUrl;
    @Value("${FORBIDDEN_URL}")
    private String forbiddenUrl;
    private List<String> bypassUrls;
    private List<String> ignoreRenderBreadcrumbs;
    @Autowired
    private Environment environment;

    @PostConstruct
    public void init(){
        String bypass_url = environment.getRequiredProperty("BYPASS_URL");
        bypassUrls = Arrays.asList(bypass_url.split(",")).stream().map(s -> s.trim()).collect(Collectors.toList());
        String ignore_render_bread_crumb_url = environment.getRequiredProperty("IGNORE_RENDER_BREAD_CRUMB_URL");
        ignoreRenderBreadcrumbs = Arrays.asList(ignore_render_bread_crumb_url.split(",")).stream().map(s -> s.trim()).collect(Collectors.toList());
    }

    public List<String> getBypassUrls() {
        return bypassUrls;
    }

    public void setBypassUrls(List<String> bypassUrls) {
        this.bypassUrls = bypassUrls;
    }

    public List<String> getIgnoreRenderBreadcrumbs() {
        return ignoreRenderBreadcrumbs;
    }

    public void setIgnoreRenderBreadcrumbs(List<String> ignoreRenderBreadcrumbs) {
        this.ignoreRenderBreadcrumbs = ignoreRenderBreadcrumbs;
    }

    public String getIndexUrl() {
        return indexUrl;
    }

    public void setIndexUrl(String indexUrl) {
        this.indexUrl = indexUrl;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getVsaLoginUrl() {
        return vsaLoginUrl;
    }

    public void setVsaLoginUrl(String vsaLoginUrl) {
        this.vsaLoginUrl = vsaLoginUrl;
    }

    public String getForbiddenUrl() {
        return forbiddenUrl;
    }

    public void setForbiddenUrl(String forbiddenUrl) {
        this.forbiddenUrl = forbiddenUrl;
    }
}
