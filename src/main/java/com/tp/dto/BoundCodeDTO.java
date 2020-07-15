package com.tp.dto;

import java.io.Serializable;

public class BoundCodeDTO extends BaseDTO implements Serializable {
    private String boundCodeId;
    private String boundCode;


    public BoundCodeDTO() {
    }


    public BoundCodeDTO(String boundCodeId, String boundCode) {
        this.boundCodeId = boundCodeId;
        this.boundCode = boundCode;
    }

    public String getBoundCodeId() {
        return boundCodeId;
    }

    public void setBoundCodeId(String boundCodeId) {
        this.boundCodeId = boundCodeId;
    }

    public String getBoundCode() {
        return boundCode;
    }

    public void setBoundCode(String boundCode) {
        this.boundCode = boundCode;
    }
}
