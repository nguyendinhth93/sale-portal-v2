package com.tp.common.exception;

/**
 * Created by hopnv on 24/07/2017.
 */
public class LogicException extends Exception{

    private String keyMsg;
    private String [] params;
    private String description;

    public LogicException() {
    }

    public LogicException(String keyMsg) {
        this.keyMsg = keyMsg;
    }

    public LogicException(String keyMsg, String... params) {
        this.keyMsg = keyMsg;
        this.params = params;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeyMsg() {
        return keyMsg;
    }

    public void setKeyMsg(String keyMsg) {
        this.keyMsg = keyMsg;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }
}
