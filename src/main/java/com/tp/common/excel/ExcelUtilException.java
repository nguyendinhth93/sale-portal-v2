package com.tp.common.excel;

/**
 * Created by pham on 11/21/2016.
 */
public class ExcelUtilException extends Exception {
    private String message;
    private int errorCode;

    public ExcelUtilException(String message) {
        this.message = message;
    }

}
