package com.tp.util;

import java.util.List;

/**
 * Created by hopnv on 24/07/2017.
 */
public class QueryUtil {

    public static String createSafeLikeValue(String value){
        if(DataUtil.isNullOrEmpty(value))
            return "%%";
        value = DataUtil.trim(value);
        value = value.replaceAll("%","\\\\%");
        value = value.replaceAll("_","\\\\_");
        return "%" + value.toUpperCase() + "%";
    }

    public static String createSafeInQuery(List<String> lstIds){
        return lstIds.toString().replace("[", "(").replace("]", ")");
    }

}
