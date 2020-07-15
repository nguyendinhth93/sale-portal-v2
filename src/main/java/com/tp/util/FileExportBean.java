package com.tp.util;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.faces.context.FacesContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dungha7 on 10/24/2016.
 */
public class FileExportBean {
    private String outName;
    private String templateName;
    private String templatePath;
    private String outPath;
    private Map bean;


    public FileExportBean() {
        this.templatePath = FileUtil.getTemplatePath();
        this.outPath = FileUtil.getOutputPath();
    }

    public void putValue(String key, Object value) {
        if (DataUtil.isNullObject(bean)) {
            bean = new HashMap();
        }
        bean.put(key, value);
    }


    /**
     * @return map chua cac tham so can thiet trong file excel: list du lieu, nguoi tao, ...
     */
    public Map getBean() {
        return bean;
    }

    public String getOutName() {
        return outName;
    }

    public void setOutName(String outName) {
        this.outName = outName;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getOutPath() {
        return outPath;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

    public void validate() throws Exception {
        if (DataUtil.isNullOrEmpty(outPath)) {
            throw new Exception("Duong dan file export trong!", new Throwable("outPath trong"));
        }
        if (DataUtil.isNullOrEmpty(outName)) {
            throw new Exception("Ten file export trong!", new Throwable("outName trong"));
        }
        if (DataUtil.isNullOrEmpty(templatePath)) {
            throw new Exception("Duong dan toi file template trong!", new Throwable("templatePath trong"));
        }
        if (DataUtil.isNullOrEmpty(templateName)) {
            throw new Exception("Ten file template trong!", new Throwable("templateName trong"));
        }
    }

    public String getExportFileName() {
        return getOutPath() + "_" + getOutName();
    }

    /**
     * @return file exported
     * @throws Exception
     */
    public File getExportFile() throws Exception {
        return new File(getExportFileName());
    }

    /**
     * @return streamedContent de download
     * @throws Exception
     */
    public StreamedContent getExportFileContent() throws Exception {
        InputStream is = new FileInputStream(getExportFile());
        return new DefaultStreamedContent(is, FacesContext.getCurrentInstance().getExternalContext().getMimeType(outName), outName);
    }
}
