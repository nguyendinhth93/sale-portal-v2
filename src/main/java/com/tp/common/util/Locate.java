package com.tp.common.util;

import java.io.Serializable;

public class Locate implements Serializable {
    private String language;
    private String country;

    public Locate() {
    }

    public Locate(String language, String country) {
        this.language = language;
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += ((language != null && country !=null) ? language.hashCode() + country.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if  (obj ==null || !(obj instanceof Locate)) return false;
        Locate locate = (Locate) obj;
        return (language.equals(locate.getLanguage())&& country.equals(locate.getCountry()));
    }
}
