package com.tp.model;

import java.io.Serializable;

/**
 * Created by hopnv on 12/06/2017.
 */
public abstract class BaseModel implements Serializable {

    protected String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
