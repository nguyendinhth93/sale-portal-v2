/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.dto.fw;

import com.tp.common.util.GetTextFromBundleHelper;
import com.tp.util.DataUtil;

import java.util.Arrays;

/**
 * @author DINH NGUYEN
 */
public class BaseMessage implements java.io.Serializable {

    /**
     * Ma loi
     */
    private String errorCode;
    /**
     * Truong description khong dung den nua, tat ca thong bao tu service tra ve phai dung keyMsg va paramMsg
     * Viec lay description cu the se lay tu 2 truong nay de khuyen khich viec service chi tra ve keyMsg
     */
    @Deprecated
    private String description;
    /**
     * Thanh cong hay khong
     */
    private boolean success;
    /**
     * Ma loi
     */
    private String keyMsg;
    /**
     * Cac tham so bo sung cho ma loi de ra thong bao cu the
     */
    private String[] paramsMsg;

    public BaseMessage() {
    }

    public BaseMessage(boolean success) {
        this.success = success;
    }

    public BaseMessage(String errorCode, boolean success) {
        this.errorCode = errorCode;
        this.success = success;
    }

    public BaseMessage(String errorCode, boolean success, String description) {
        this.errorCode = errorCode;
        this.description = description;
        this.success = success;
    }

    public BaseMessage(BaseMessage msg) {
        this.errorCode = msg.errorCode;
        this.description = msg.description;
        this.success = msg.success;
        this.keyMsg = msg.keyMsg;
        this.paramsMsg = msg.paramsMsg;
    }

    public String getKeyMsg() {
        return keyMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public BaseMessage getBaseMsg() {
        return new BaseMessage(this);
    }

    public String[] getParamsMsg() {
        return paramsMsg;
    }

    public void setParamsMsg(String[] paramsMsg) {
        this.paramsMsg = paramsMsg;
    }

    @Override
    public String toString() {
        return "BaseMessage{" +
                "errorCode='" + errorCode + '\'' +
                ", description='" + description + '\'' +
                ", success=" + success +
                ", keyMsg='" + keyMsg + '\'' +
                ", paramsMsg=" + Arrays.toString(paramsMsg) +
                '}';
    }
}
