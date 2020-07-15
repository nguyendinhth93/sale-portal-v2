/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.util;

import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DbUtil {
    private static Logger logger = Logger.getLogger(DbUtil.class);
    public static void setParamInQuery(Query query, String prefix, List objs) {
        int idx = 1;
        for (Object obj : objs) {
            query.setParameter(prefix + String.valueOf(idx++), obj);
        }
    }

    public static List createListParamInQuery(String prefix, List objs) {
        List lst = new ArrayList<>();
        int idx = 1;
        for (Object obj : objs) {
            lst.add(new Object[]{prefix + String.valueOf(idx++), obj});
        }

        return lst;
    }

    public static Map<String, Object> createMapParamInQuery(String prefix, List objs) {
        Map<String, Object> map = new HashedMap();
        int idx = 1;
        for (Object obj : objs) {
            map.put(prefix + String.valueOf(idx++), obj);
        }
        return map;
    }


    public static String createInQuery(String prefix, List objs) {
        String inQuery = " IN (";
        for (int idx = 1; idx < objs.size() + 1; idx++) {
            inQuery += ",:" + prefix + idx;
        }
        inQuery += ") ";
        inQuery = inQuery.replaceFirst(",", "");

        return inQuery;
    }


    public static String createInQuery(String column, String prefix, List objs) {
        StringBuilder sqlIn = new StringBuilder();
        int size = objs.size();
        int paging = size/1000;
        if (size%1000 == 0) {
            paging = paging - 1;
        }
        if (paging > 0) {
            for (int i = 0; i <= paging; i++) {
                if (i == 0) {
                    List<Object> conditionIn = objs.subList(0, 1000);
                    sqlIn.append("( " + column + createInQuery( prefix + i, conditionIn) + " OR ");
                } else if (i == paging) {
                    sqlIn.append(column + createInQuery(prefix + i, objs.subList(i*1000, size))  + " ) ");
                } else {
                    sqlIn.append(column + createInQuery(prefix + i, objs.subList(i*1000, (i+1)*1000)) + " OR " );
                }
            }
        } else {
            sqlIn.append(column + "" + createInQuery(prefix, objs));
        }
        return sqlIn.toString();
    }

    public static void setParamInQuery(Query query, String column, String prefix, List objs) {
        int size = objs.size();
        int paging = size/1000;
        if (size%1000 == 0) {
            paging = paging - 1;
        }
        if (paging > 0) {
            for (int i = 0; i <= paging; i++) {
                if (i == 0) {
                    List<Object> conditionIn = objs.subList(0, 1000);
                    DbUtil.setParamInQuery(query, prefix + i, conditionIn);
                } else if (i == paging) {
                    DbUtil.setParamInQuery(query, prefix + i, objs.subList(i*1000, size));
                } else {
                    DbUtil.setParamInQuery(query, prefix + i, objs.subList(i*1000, (i+1)*1000));
                }
            }
        } else {
            DbUtil.setParamInQuery(query, prefix, objs);
        }
    }

    public static Date getSysDate(EntityManager em) throws Exception {
        String queryString = "SELECT sysdate from dual";
        Query queryObject = em.createNativeQuery(queryString);
        return (Date) queryObject.getSingleResult();
    }

    public static Date getSysDateMysql(EntityManager em) throws Exception {
        String queryString = "SELECT SYSDATE() from dual";
        Query queryObject = em.createNativeQuery(queryString);
        return (Date) queryObject.getSingleResult();
    }
    public static Date getSysDate(EntityManager em, String pattern) throws Exception {
        return getSysDate(em);
    }

    public static Long getSequenceLongValue(EntityManager em, String sequenceName) {
        String sql = "select " + sequenceName + ".nextval from dual";
        Query query = em.createNativeQuery(sql);
        return ((BigDecimal) query.getSingleResult()).longValue();
    }

    public static BigInteger getSequence(EntityManager em, String sequenceName) {
        String sql = "select " + sequenceName + ".nextval from dual";
        Query query = em.createNativeQuery(sql);
        return ((BigDecimal) query.getSingleResult()).toBigInteger();
    }

    public static void setInsertCareNull(StringBuilder sql, Object value, List parameterList) {
        setInsertCareNull(sql, value, parameterList, false);
    }

    public static void setInsertCareNull(StringBuilder sql, Object value, List parameterList, boolean isEnd) {
        if (value != null) {
            sql.append(isEnd ? "?)" : "?,");
            parameterList.add(value);
        } else {
            sql.append(isEnd ? "null)" : "?)");
        }
    }

    public static void close(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
        } finally {
            resultSet = null;
        }
    }

    public static void close(PreparedStatement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
        } finally {
            stmt = null;
        }
    }

}
