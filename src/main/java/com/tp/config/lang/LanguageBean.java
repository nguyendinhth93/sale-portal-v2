package com.tp.config.lang;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.springframework.stereotype.Component;

/**
 * Created by hopnv on 20/07/2017.
 */
@Component
@ManagedBean(name="language")
@SessionScoped
public class LanguageBean implements Serializable{

    private static final long serialVersionUID = 1L;
    private Locale locale;

    private static Map<String,Locale> countries;
    static{
        countries = new LinkedHashMap<>();
        countries.put("English", Locale.ENGLISH);
        countries.put("Vietnamese", new Locale("vi"));
    }

    public Map<String, Locale> getCountriesInMap() {
        return countries;
    }

    @PostConstruct
    public void init(){
        locale = FacesContext.getCurrentInstance().getApplication().getDefaultLocale();
        FacesContext.getCurrentInstance()
                .getViewRoot().setLocale(locale);
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void changeLocale(String country){

        Locale localeTemp = countries.get(country);
        if(locale != null){
            locale = localeTemp;
            FacesContext.getCurrentInstance()
                    .getViewRoot().setLocale(locale);
        }
    }

}
