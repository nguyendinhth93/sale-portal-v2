package com.tp.common.util;

import com.tp.config.lang.LanguageBean;
import com.tp.util.Const;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.context.FacesContext;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import static com.tp.config.lang.Utf8ResourceBundle.UTF8_CONTROL;

public class GetTextFromBundleHelper {
    public static final Logger logger = Logger.getLogger(GetTextFromBundleHelper.class);

    @Autowired
    private LanguageBean languageBean;

    protected ResourceBundle bundle;

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

    public String getTextParam(String key, String... params) {
        return MessageFormat.format(getText(key), params);
    }
}
