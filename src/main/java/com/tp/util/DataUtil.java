
package com.tp.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.springframework.beans.BeanUtils;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;


public class DataUtil {
    public static final String SPECIAL_CHARACTERS = "ÃƒÂ Ãƒâ‚¬Ã¡ÂºÂ£Ã¡ÂºÂ¢ÃƒÂ£ÃƒÆ’ÃƒÂ¡Ãƒ?Ã¡ÂºÂ¡Ã¡ÂºÂ Ã„Æ’Ã„â€šÃ¡ÂºÂ±Ã¡ÂºÂ°Ã¡ÂºÂ³Ã¡ÂºÂ²Ã¡ÂºÂµÃ¡ÂºÂ´Ã¡ÂºÂ¯Ã¡ÂºÂ®Ã¡ÂºÂ·Ã¡ÂºÂ¶ÃƒÂ¢Ãƒâ€šÃ¡ÂºÂ§Ã¡ÂºÂ¦Ã¡ÂºÂ©Ã¡ÂºÂ¨Ã¡ÂºÂ«Ã¡ÂºÂªÃ¡ÂºÂ¥Ã¡ÂºÂ¤Ã¡ÂºÂ­Ã¡ÂºÂ¬Ã„â€˜Ã„?ÃƒÂ¨ÃƒË†Ã¡ÂºÂ»Ã¡ÂºÂºÃ¡ÂºÂ½Ã¡ÂºÂ¼ÃƒÂ©Ãƒâ€°Ã¡ÂºÂ¹Ã¡ÂºÂ¸ÃƒÂªÃƒÅ Ã¡Â»?Ã¡Â»â‚¬Ã¡Â»Æ’Ã¡Â»â€šÃ¡Â»â€¦Ã¡Â»â€žÃ¡ÂºÂ¿Ã¡ÂºÂ¾Ã¡Â»â€¡Ã¡Â»â€ ÃƒÂ¬ÃƒÅ’Ã¡Â»â€°Ã¡Â»Ë†Ã„Â©Ã„Â¨ÃƒÂ­Ãƒ?Ã¡Â»â€¹Ã¡Â»Å ÃƒÂ²Ãƒâ€™Ã¡Â»?Ã¡Â»Å½ÃƒÂµÃƒâ€¢ÃƒÂ³Ãƒâ€œÃ¡Â»?Ã¡Â»Å’ÃƒÂ´Ãƒâ€�Ã¡Â»â€œÃ¡Â»â€™Ã¡Â»â€¢Ã¡Â»â€�Ã¡Â»â€”Ã¡Â»â€“Ã¡Â»â€˜Ã¡Â»?Ã¡Â»â„¢Ã¡Â»ËœÃ†Â¡Ã†Â Ã¡Â»?Ã¡Â»Å“Ã¡Â»Å¸Ã¡Â»Å¾Ã¡Â»Â¡Ã¡Â»Â Ã¡Â»â€ºÃ¡Â»Å¡Ã¡Â»Â£Ã¡Â»Â¢ÃƒÂ¹Ãƒâ„¢Ã¡Â»Â§Ã¡Â»Â¦Ã…Â©Ã…Â¨ÃƒÂºÃƒÅ¡Ã¡Â»Â¥Ã¡Â»Â¤Ã†Â°Ã†Â¯Ã¡Â»Â«Ã¡Â»ÂªÃ¡Â»Â­Ã¡Â»Â¬Ã¡Â»Â¯Ã¡Â»Â®Ã¡Â»Â©Ã¡Â»Â¨Ã¡Â»Â±Ã¡Â»Â°ÃƒÂ½Ãƒ?";
    public static final String REPLACEMENTS = "aAaAaAaAaAaAaAaAaAaAaAaAaAaAaAaAaAdDeEeEeEeEeEeEeEeEeEeEeEiIiIiIiIiIoOoOoOoOoOoOoOoOoOoOoOoOoOoOoOoOoOuUuUuUuUuUuUuUuUuUuUuUyY";
    public static final char[] cacKyTuTiengViet = SPECIAL_CHARACTERS.toCharArray();
    public static final char[] cacKyTuTiengVietDaBoDau = REPLACEMENTS.toCharArray();
    public final static String MAX_NUMBER_RANGE = "1000000";
    //    ducnm35_start_common
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String NOT_SPECICAL_PATTERN = "^([ a-z0-9A-ZÃ€Ã�Ã‚ÃƒÃˆÃ‰ÃŠÃŒÃ�Ã’Ã“Ã”Ã•Ã™ÃšÄ‚áº®Ä¨Å¨Æ Ã Ã¡Ã¢Ã£Ã¨Ã©ÃªÃ¬Ã­Ã²Ã³Ã´ÃµÃ¹ÃºÄƒÄ‘Ä©Å©Æ¡Æ¯Ä‚áº áº¢áº¤áº¦áº¨áºªáº¬áº®áº°áº²áº´áº¶áº¸áººáº¼á»€áº¾á»€á»‚Æ°Äƒáº¡áº£áº¥áº§áº©áº«áº­áº¯áº±áº³áºµáº·áº¹áº»áº½Ã©á»�áº¿á»ƒá»„á»†á»ˆá»Šá»Œá»ŽÃ“á»’á»”á»–á»˜á»šá»œá»žá» á»¢á»¤á»¦á»¨á»ªá»…á»‡á»‰á»‹Ã­Ã¬á»‘á»“á»•á»—á»™á»›á»�á»Ÿá»¡á»£á»¥á»§á»©á»«á»¬á»®á»°á»²á»´Ã�á»¶á»¸á»­á»¯á»±á»³á»µá»·á»¹])+$";
    private static final String PHONE_PATTERN = "^[0-9]*$";
	private static final String NOT_SPECICAL_NOTVN = "^[_A-Za-z0-9-]*$";

    public static String addZeroToString(String input, int strLength) {
        String result = input;
        for (int i = 1; i <= strLength - input.length(); i++) {
            result = "0" + result;
        }
        return result;
    }

    public static void main(String [] agrs){
        System.out.println(converterStringToName("   nguyễn   văn  hợp    "));

        List<String> s = new ArrayList<>();
        s.add("a");
        s.add("a");
        s.add("a");
        s.add("b");
        s.stream().filter(d -> d.equals("a"));
        System.out.println(s);

    }

    /**
     * @author hopnv
     * @param str
     * @return du lieu da duoc cat khoang trang va viet hoa chu cai dau
     */
    public static String converterStringToName(String str){
        if(DataUtil.isNullOrEmpty(str))
            return "";
        String [] tokens = str.split("[ ]+");
        String strReturn = "";
        for(String token : tokens){
            strReturn += upperFirstChar(token) + " ";
        }

        return trim(strReturn);
    }

    /**
     * Copy du lieu tu bean sang bean moi
     * Luu y chi copy duoc cac doi tuong o ngoai cung, list se duoc copy theo tham chieu
     * <p>
     * Chi dung duoc cho cac bean java, khong dung duoc voi cac doi tuong dang nhu String, Integer, Long...
     *
     * @param source
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T cloneBean(T source) {
        try {
            if (source == null) {
                return null;
            }
            T dto = (T) source.getClass().getConstructor().newInstance();
            BeanUtils.copyProperties(source, dto);
            return dto;
        } catch (Exception e) {
            return null;
        }
    }

    public static List<Long> convertListStringToLong(String[] inputs) {
        List<Long> result = new ArrayList<>();
        for (String s : inputs) {
            result.add(Long.parseLong(s));
        }
        return result;
    }

    public static List<Long> convertListStringToLong(List<String> inputs) {
        List<Long> result = new ArrayList<>();
        for (String s : inputs) {
            result.add(Long.parseLong(s));
        }
        return result;
    }

    @Deprecated
    @SuppressWarnings("unchecked")
    public static <T> T cloneBean(Object source, Class<T> destClass) {
        try {
            if (source == null) {
                return null;
            }
            T dto = (T) source.getClass().getConstructor().newInstance();
            BeanUtils.copyProperties(source, dto);
            return dto;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            return null;
        }
    }

    @Deprecated
    public static <T> List<T> cloneBean(Iterable<?> sourceList, Class<T> destClass) {
        List<T> destList = Lists.newArrayList();

        if (sourceList == null) {
            return destList;
        }

        for (Object t : sourceList) {
            T destObject = cloneBean(t, destClass);
            if (destObject != null) {
                destList.add(cloneBean(t, destClass));
            }
        }
        return destList;
    }

    public static <T> List<T> cloneBean(Iterable<T> sourceList) {
        final List<T> destList = Lists.newArrayList();

        if (sourceList == null) {
            return destList;
        }

        sourceList.forEach(t -> {
            T destObject = cloneBean(t);
            if (destObject != null) {
                destList.add(cloneBean(t));
            }
        });

        return destList;
    }

    @SuppressWarnings("unchecked")
    public static <T> T deepCloneObject(T source) {
        try {
            if (source == null) {
                return null;
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(source);
            out.flush();
            out.close();

            ObjectInputStream in = new ObjectInputStream(
                    new ByteArrayInputStream(bos.toByteArray()));
            T dto = (T) in.readObject();
            in.close();
            return dto;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }


    public static String forwardPage(String pageName) {
        if (!DataUtil.isNullOrEmpty(pageName)) {
            return "pretty:" + pageName.trim();
        }
        return "";
    }

    public static boolean isNullOrZero(Long value) {
        return (value == null || value.equals(0L));
    }

    /*
    * Kiem tra Long bi null hoac zero
    *
    * @param value
    * @return
    */
    public static boolean isNullOrOneNavigate(Long value) {
        return (value == null || value.equals(-1L));
    }

    /**
     * Kiem tra Bigdecimal bi null hoac zero
     *
     * @param value
     * @return
     */
    public static boolean isNullOrZero(BigDecimal value) {
        return (value == null || value.compareTo(BigDecimal.ZERO) == 0);
    }

    /**
     * Lay ten phuong thuc getter
     *
     * @param columnName
     * @return
     */
    public static String getHibernateName(String columnName) {
        String temp = columnName.toLowerCase();
        String[] arrs = temp.split("_");
        String method = "";
        for (String arr : arrs) {
            method += DataUtil.upperFirstChar(arr);
        }
        return method;
    }

    /**
     * Lay getter
     *
     * @param columnName
     * @return
     */
    public static String getGetterByColumnName(String columnName) {
        return "get" + getHibernateName(columnName);
    }

    /**
     * Lay ten phuong thuc setter
     *
     * @param columnName
     * @return
     */
    public static String getSetterByColumnName(String columnName) {
        return "set" + getHibernateName(columnName);
    }

    /**
     * Upper first character
     *
     * @param input
     * @return
     */
    public static String upperFirstChar(String input) {
        if (DataUtil.isNullOrEmpty(input)) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    /**
     * Lower first characater
     *
     * @param input
     * @return
     */
    public static String lowerFirstChar(String input) {
        if (DataUtil.isNullOrEmpty(input)) {
            return input;
        }
        return input.substring(0, 1).toLowerCase() + input.substring(1);
    }

    public static String safeTrim(String obj) {
        if (obj == null) return null;
        return obj.trim();
    }

    public static Long safeToLong(Object obj1, Long defaultValue) {
        Long result = defaultValue;
        if (obj1 != null) {
            if (obj1 instanceof BigDecimal) {
                return ((BigDecimal) obj1).longValue();
            }
            if (obj1 instanceof BigInteger) {
                return ((BigInteger) obj1).longValue();
            }
            try {
                result = Long.parseLong(obj1.toString());
            } catch (Exception ignored) {
            }
        }

        return result;
    }

    /**
     * @param obj1 Object
     * @return Long
     */
    public static Long safeToLong(Object obj1) {
        return safeToLong(obj1, 0L);
    }

    public static Double safeToDouble(Object obj1, Double defaultValue) {
        Double result = defaultValue;
        if (obj1 != null) {
            try {
                result = Double.parseDouble(obj1.toString());
            } catch (Exception ignored) {
            }
        }

        return result;
    }

    public static Double safeToDouble(Object obj1) {
        return safeToDouble(obj1, 0.0);
    }

    public static String customFormat(String pattern, double value) {
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(value);
    }

    public static Short safeToShort(Object obj1, Short defaultValue) {
        Short result = defaultValue;
        if (obj1 != null) {
            try {
                result = Short.parseShort(obj1.toString());
            } catch (Exception ignored) {
            }
        }

        return result;
    }

    public static Short safeToShort(Object obj1) {
        return safeToShort(obj1, (short) 0);
    }

    public static int safeToInt(Object obj1, int defaultValue) {
        int result = defaultValue;
        if (obj1 != null) {
            try {
                result = Integer.parseInt(obj1.toString());
            } catch (Exception ignored) {
            }
        }

        return result;
    }

    /**
     * @param obj1 Object
     * @return int
     */
    public static int safeToInt(Object obj1) {
        return safeToInt(obj1, 0);
    }

    /**
     * @param obj1 Object
     * @return String
     */
    public static String safeToString(Object obj1, String defaultValue) {
        if (obj1 == null) {
            return defaultValue;
        }

        return obj1.toString();
    }


    public static String safeToLower(String obj1) {
        if (obj1 == null) {
            return null;
        }

        return obj1.toLowerCase();
    }

    public static String safeToUpper(String obj1) {
        if (obj1 == null) {
            return null;
        }

        return obj1.toUpperCase();
    }

    /**
     * @param obj1 Object
     * @return String
     */
    public static String safeToString(Object obj1) {
        return safeToString(obj1, "");
    }

    /**
     * safe equal
     *
     * @param obj1 Long
     * @param obj2 Long
     * @return boolean
     */
    public static boolean safeEqual(Long obj1, Long obj2) {
        if (obj1 == obj2) return true;
        return ((obj1 != null) && (obj2 != null) && (obj1.compareTo(obj2) == 0));
    }

    /**
     * safe equal
     *
     * @param obj1 Long
     * @param obj2 Long
     * @return boolean
     */
    public static boolean safeEqual(BigInteger obj1, BigInteger obj2) {
        if (obj1 == obj2) return true;
        return (obj1 != null) && (obj2 != null) && obj1.equals(obj2);
    }

    public static boolean safeEqual(Short obj1, Short obj2) {
        if (obj1 == obj2) return true;
        return ((obj1 != null) && (obj2 != null) && (obj1.compareTo(obj2) == 0));
    }

    /**
     * safe equal
     *
     * @param obj1 String
     * @param obj2 String
     * @return boolean
     */
    public static boolean safeEqual(String obj1, String obj2) {
        if (obj1 == obj2) return true;
        return ((obj1 != null) && (obj2 != null) && obj1.equals(obj2));
    }
	
	
	/**
     * increase cur no
     *
     * @param obj1 String
     * @param obj2 String
     * @return String
     */
    public static String increaseCurNo(String obj1, int obj2) {
        return String.format("%05d", Integer.parseInt(obj1) + obj2);
    }

    public static boolean isNullOrWhiteSpace(String value) {
        if (value == null) {
            return true;
        }
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isWhitespace(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * check null or empty
     * Su dung ma nguon cua thu vien StringUtils trong apache common lang
     *
     * @param cs String
     * @return boolean
     */
    public static boolean isNullOrEmpty(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNullOrEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNullOrEmpty(final Object[] collection) {
        return collection == null || collection.length == 0;
    }

    public static boolean isNullOrEmpty(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * Tra ve doi tuong default neu object la null, neu khong thi tra object
     *
     * @param object
     * @param defaultValue
     * @param <T>
     * @return
     */
    public static <T> T defaultIfNull(final T object, final T defaultValue) {
        return object != null ? object : defaultValue;
    }

    /**
     * Ham nay mac du nhan tham so truyen vao la object nhung gan nhu chi hoat dong cho doi tuong la string
     * Chuyen sang dung isNullOrEmpty thay the
     *
     * @param obj1
     * @return
     */
    @Deprecated
    public static boolean isStringNullOrEmpty(Object obj1) {
        return obj1 == null || obj1.toString().trim().equals("");
    }

    /**
     * @param obj1 Object
     * @return BigDecimal
     */
    public static BigDecimal safeToBigDecimal(Object obj1) {
        BigDecimal result = BigDecimal.ZERO;
        if (obj1 == null) {
            return result;
        }
        try {
            result = new BigDecimal(obj1.toString());
        } catch (Exception ignored) {
        }

        return result;
    }

    public static BigInteger safeToBigInteger(Object obj1, BigInteger defaultValue) {
        BigInteger result = defaultValue;
        if (obj1 == null) {
            return result;
        }
        try {
            result = new BigInteger(obj1.toString());
        } catch (Exception ignored) {
        }

        return result;
    }

    public static BigInteger safeToBigInteger(Object obj1) {
        BigInteger result = BigInteger.ZERO;
        try {
            if (obj1 instanceof BigInteger) {
                result = (BigInteger) obj1;
            } else {
                result = new BigInteger(obj1.toString());
            }

        } catch (Exception ignored) {
        }

        return result;
    }

    public static BigInteger length(@Nonnull BigInteger from, @Nonnull BigInteger to) {
        return to.subtract(from).add(BigInteger.ONE);
    }

    public static BigDecimal add(BigDecimal number1, BigDecimal number2, BigDecimal... numbers) {
        List<BigDecimal> realNumbers = Lists.newArrayList(number1, number2);
        if (!DataUtil.isNullOrEmpty(numbers)) {
            Collections.addAll(realNumbers, numbers);
        }
        return realNumbers.stream()
                .filter(x -> x != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static Long add(Long number1, Long number2, Long... numbers) {
        List<Long> realNumbers = Lists.newArrayList(number1, number2);
        if (!DataUtil.isNullOrEmpty(numbers)) {
            Collections.addAll(realNumbers, numbers);
        }
        return realNumbers.stream()
                .filter(x -> x != null)
                .reduce(0L, (x, y) -> x + y);
    }

    /**
     * add
     *
     * @param obj1 BigDecimal
     * @param obj2 BigDecimal
     * @return BigDecimal
     */
    public static BigInteger add(BigInteger obj1, BigInteger obj2) {
        if (obj1 == null) {
            return obj2;
        } else if (obj2 == null) {
            return obj1;
        }

        return obj1.add(obj2);
    }

    /**
     * @param number
     * @param pattern
     * @return
     * @author Ham format so thuc ve dang co max la 4 chu so thap phan.
     * Trim() so 0 vo nghia
     */
    public static String getFormattedString4Digits(String number, String pattern) {
        double amount = 0;
        try {
            amount = Double.parseDouble(number);
            DecimalFormat formatter = new DecimalFormat(pattern);
            return formatter.format(amount);
        } catch (Exception ex) {
            return number;
        }
    }

    public static Character safeToCharacter(Object value) {
        return safeToCharacter(value, '0');
    }

    public static Character safeToCharacter(Object value, Character defaulValue) {
        if (value == null) return defaulValue;
        return String.valueOf(value).charAt(0);
    }

    public static Collection<Long> objLstToLongLst(List<Object> list) {
        Collection<Long> result = new ArrayList<>();
        if (!list.isEmpty()) {
            result.addAll(list.stream().map(DataUtil::safeToLong).collect(Collectors.toList()));
        }
        return result;
    }

    public static Collection<Short> objLstToShortLst(List<Object> list) {
        Collection<Short> result = new ArrayList<>();
        if (!list.isEmpty()) {
            result.addAll(list.stream().map(DataUtil::safeToShort).collect(Collectors.toList()));
        }
        return result;
    }

    public static Collection<BigDecimal> objLstToBigDecimalLst(List<Object> list) {
        Collection<BigDecimal> result = new ArrayList<>();
        if (!list.isEmpty()) {
            result.addAll(list.stream().map(DataUtil::safeToBigDecimal).collect(Collectors.toList()));
        }
        return result;
    }

    public static Collection<Double> objLstToDoubleLst(List<Object> list) {
        Collection<Double> result = new ArrayList<>();
        if (!list.isEmpty()) {
            result.addAll(list.stream().map(DataUtil::safeToDouble).collect(Collectors.toList()));
        }
        return result;
    }

    public static Collection<Character> objLstToCharLst(List<Object> list) {
        Collection<Character> result = new ArrayList<>();
        if (!list.isEmpty()) {
            result.addAll(list.stream().map(item -> item.toString().charAt(0)).collect(Collectors.toList()));
        }

        return result;
    }

    public static Collection<String> objLstToStringLst(List<Object> list) {
        Collection<String> result = new ArrayList<>();
        if (!list.isEmpty()) {
            result.addAll(list.stream().map(DataUtil::safeToString).collect(Collectors.toList()));
        }

        return result;
    }

    /**
     * Collect values of a property from an object list instead of doing a for:each then call a getter
     * Consider using stream -> map -> collect of java 8 instead
     *
     * @param source       object list
     * @param propertyName name of property
     * @param returnClass  class of property
     * @return value list of property
     */
    @Deprecated
    public static <T> List<T> collectProperty(@Nonnull Collection<?> source, @Nonnull String propertyName, @Nonnull Class<T> returnClass) {
        List<T> propertyValues = Lists.newArrayList();
        try {
            String getMethodName = "get" + upperFirstChar(propertyName);
            for (Object x : source) {
                Class<?> clazz = x.getClass();
                Method getMethod = clazz.getMethod(getMethodName);
                Object propertyValue = getMethod.invoke(x);
                if (propertyValue != null && returnClass.isAssignableFrom(propertyValue.getClass())) {
                    propertyValues.add(returnClass.cast(propertyValue));
                }
            }
            return propertyValues;
        } catch (Exception e) {
            return Lists.newArrayList();
        }
    }

    /**
     * Collect distinct values of a property from an object list instead of doing a for:each then call a getter
     * Consider using stream -> map -> collect of java 8 instead
     *
     * @param source       object list
     * @param propertyName name of property
     * @param returnClass  class of property
     * @return value list of property
     */
    @Deprecated
    public static <T> Set<T> collectUniqueProperty(Collection<?> source, String propertyName, Class<T> returnClass) {
        List<T> propertyValues = collectProperty(source, propertyName, returnClass);
        return Sets.newHashSet(propertyValues);
    }


    /**
     * Check an object is active
     *
     * @param status   status of object
     * @param isDelete isdetete status of object
     * @return
     */
    public static boolean isActive(Character status, Character isDelete) {
        return Objects.equals(status, '1') && (isDelete == null || isDelete.equals('0'));
    }

    public static <T> T getMapValue(Map<String, Object> params, String key, Class<T> type) {
        Object obj = params.get(key);
        if (obj == null) return null;
        if (obj.getClass().isAssignableFrom(obj.getClass())) {
            return type.cast(obj);
        }

        return null;
    }

    public static <T> T nvl(T... objs) {
        for (T obj : objs) {
            if (obj != null) {
                return obj;
            }
        }

        return null;
    }


    public static String strNvl(String... objs) {
        for (String obj : objs) {
            if (!DataUtil.isNullOrEmpty(obj)) {
                return obj;
            }
        }

        return null;
    }

    public static boolean isNullObject(Object obj1) {
        if (obj1 == null) {
            return true;
        }
        if (obj1 instanceof String) {
            return isNullOrEmpty(obj1.toString());
        }
        return false;
    }

    public static <T> T mapToNewClass(Object source, Class<T> destClass) {
        try {
            if (source == null) {
                return null;
            }
            T dto;
            dto = destClass.getConstructor().newInstance();
            BeanUtils.copyProperties(source, dto);
            return dto;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            return null;
        }
    }

    public static <T> List<T> mapToNewClass(Iterable<?> sourceList, Class<T> destClass) {
        List<T> destList = Lists.newArrayList();

        if (sourceList == null) {
            return destList;
        }

        for (Object t : sourceList) {
            T destObject = mapToNewClass(t, destClass);
            if (destObject != null) {
                destList.add(mapToNewClass(t, destClass));
            }
        }
        return destList;
    }

    public static String toUpper(String input) {
        if (isNullOrEmpty(input)) {
            return input;
        }
        return input.toUpperCase();
    }


    /**
     * Validate Data theo Pattern
     */
    public static boolean validateStringByPattern(String value, String regex) {
        if (isNullOrEmpty(regex) || isNullOrEmpty(value)) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public static String prepareFileName(String fileName) {
        if (fileName == null) {
            return "";
        }
        return fileName.replace(" ", "_");
    }

    /**
     * Bien cac ki tu dac biet ve dang ascii
     *
     * @param input
     * @return
     */
    public static String convertCharacter(String input) {
        if (input == null) {
            return "";
        }
        String[] aList = {"à", "á", "ả", "ã", "ạ", "â", "ầ", "ấ", "ẩ", "ẫ", "ậ", "ă", "ằ", "ắ", "ẳ", "ẵ", "ặ"};
        String[] eList = {"è", "é", "ẻ", "ẽ", "ẹ", "ê", "ề", "ế", "ể", "ễ", "ệ"};
        String[] iList = {"ì", "í", "ỉ", "ĩ", "ị"};
        String[] oList = {"ò", "ó", "ỏ", "õ", "ọ", "ô", "ồ", "ố", "ổ", "ỗ", "ộ", "ơ", "ờ", "ớ", "ở", "ỡ", "ợ"};
        String[] uList = {"ù", "ú", "ủ", "ũ", "ụ", "ư", "ừ", "ứ", "ử", "ữ", "ự"};
        String[] yList = {"ý", "ỳ", "ỷ", "ỹ", "ỵ"};
        String[] AList = {"À", "Á", "Ả", "Ã", "Ạ", "Â", "Ầ", "Ấ", "Ẩ", "Ẫ", "Ậ", "Ă", "Ằ", "Ắ", "Ẳ", "Ẵ", "Ặ"};
        String[] EList = {"È", "É", "Ẻ", "Ẽ", "Ẹ", "Ê", "Ề", "Ế", "Ể", "Ễ", "Ệ"};
        String[] IList = {"Ì", "Í", "Ỉ", "Ĩ", "Ị"};
        String[] OList = {"Ò", "Ó", "Ỏ", "Õ", "Ọ", "Ô", "Ồ", "Ố", "Ổ", "Ỗ", "Ộ", "Ơ", "Ờ", "Ớ", "Ở", "Ỡ", "Ợ"};
        String[] UList = {"Ù", "Ú", "Ủ", "Ũ", "Ụ", "Ư", "Ừ", "Ứ", "Ử", "Ữ", "Ự"};
        String[] YList = {"Ỳ", "Ý", "Ỷ", "Ỹ", "Ỵ"};
        for (String s : aList) {
            input = input.replace(s, "a");
        }
        for (String s : AList) {
            input = input.replace(s, "A");
        }
        for (String s : eList) {
            input = input.replace(s, "e");
        }
        for (String s : EList) {
            input = input.replace(s, "E");
        }
        for (String s : iList) {
            input = input.replace(s, "i");
        }
        for (String s : IList) {
            input = input.replace(s, "I");
        }
        for (String s : oList) {
            input = input.replace(s, "o");
        }
        for (String s : OList) {
            input = input.replace(s, "O");
        }
        for (String s : uList) {
            input = input.replace(s, "u");
        }
        for (String s : UList) {
            input = input.replace(s, "U");
        }
        for (String s : yList) {
            input = input.replace(s, "y");
        }
        for (String s : YList) {
            input = input.replace(s, "Y");
        }
        input = input.replace("đ", "d");
        input = input.replace("Đ", "D");
        return input;
    }

    public static boolean isNumber(String string) {
        return !isNullOrEmpty(string) && string.trim().matches("^\\d+$");
    }

    public static <T> List<T> intersection(Collection<T> a, Collection<T> b) {
        if (a == null || b == null) {
            return new ArrayList<>();
        }
        List<T> list = new ArrayList<>(a);
        list.retainAll(b);
        return list;
    }

    public static String removeStartingZeroes(String number) {
        if (DataUtil.isNullOrEmpty(number)) {
            return "";
        }
        return CharMatcher.anyOf("0").trimLeadingFrom(number);
    }

    /**
     * Trim tat ca ki tu trang, bao gom ca ki tu trang 2 byte ma ham trim binh thuong cua java khong trim duoc
     *
     * @param needToTrimString Xau can trim
     * @return "" neu la xau null hoac trang, con lai tra ve xau sau khi trim, "" neu trim xong khong con gi
     */
    public static String trim(String needToTrimString) {
        if (needToTrimString == null) {
            return "";
        }
        return CharMatcher.WHITESPACE.trimFrom(needToTrimString);
    }

    /**
     * Trim string
     *
     * @param str
     * @param alt: sau thay the khi str null
     * @return
     */
    public static String trim(String str, String alt) {
        if (str == null) {
            return alt;
        }
        return str.trim();
    }

    public static BigDecimal defaultIfSmallerThanZero(BigDecimal value) {
        return defaultIfSmallerThanZero(value, BigDecimal.ZERO);
    }

    public static BigDecimal defaultIfSmallerThanZero(BigDecimal value, BigDecimal defaultValue) {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            return defaultValue;
        }
        return value;
    }

    public static String convertUnicode2Nosign(String org) {

        char arrChar[] = org.toCharArray();
        char result[] = new char[arrChar.length];
        for (int i = 0; i < arrChar.length; i++) {
            switch (arrChar[i]) {
                case '\u00E1':
                case '\u00E0':
                case '\u1EA3':
                case '\u00E3':
                case '\u1EA1':
                case '\u0103':
                case '\u1EAF':
                case '\u1EB1':
                case '\u1EB3':
                case '\u1EB5':
                case '\u1EB7':
                case '\u00E2':
                case '\u1EA5':
                case '\u1EA7':
                case '\u1EA9':
                case '\u1EAB':
                case '\u1EAD':
                case '\u0203':
                case '\u01CE': {
                    result[i] = 'a';
                    break;
                }
                case '\u00E9':
                case '\u00E8':
                case '\u1EBB':
                case '\u1EBD':
                case '\u1EB9':
                case '\u00EA':
                case '\u1EBF':
                case '\u1EC1':
                case '\u1EC3':
                case '\u1EC5':
                case '\u1EC7':
                case '\u0207': {
                    result[i] = 'e';
                    break;
                }
                case '\u00ED':
                case '\u00EC':
                case '\u1EC9':
                case '\u0129':
                case '\u1ECB': {
                    result[i] = 'i';
                    break;
                }
                case '\u00F3':
                case '\u00F2':
                case '\u1ECF':
                case '\u00F5':
                case '\u1ECD':
                case '\u00F4':
                case '\u1ED1':
                case '\u1ED3':
                case '\u1ED5':
                case '\u1ED7':
                case '\u1ED9':
                case '\u01A1':
                case '\u1EDB':
                case '\u1EDD':
                case '\u1EDF':
                case '\u1EE1':
                case '\u1EE3':
                case '\u020F': {
                    result[i] = 'o';
                    break;
                }
                case '\u00FA':
                case '\u00F9':
                case '\u1EE7':
                case '\u0169':
                case '\u1EE5':
                case '\u01B0':
                case '\u1EE9':
                case '\u1EEB':
                case '\u1EED':
                case '\u1EEF':
                case '\u1EF1': {
                    result[i] = 'u';
                    break;
                }
                case '\u00FD':
                case '\u1EF3':
                case '\u1EF7':
                case '\u1EF9':
                case '\u1EF5': {
                    result[i] = 'y';
                    break;
                }
                case '\u0111': {
                    result[i] = 'd';
                    break;
                }
                case '\u00C1':
                case '\u00C0':
                case '\u1EA2':
                case '\u00C3':
                case '\u1EA0':
                case '\u0102':
                case '\u1EAE':
                case '\u1EB0':
                case '\u1EB2':
                case '\u1EB4':
                case '\u1EB6':
                case '\u00C2':
                case '\u1EA4':
                case '\u1EA6':
                case '\u1EA8':
                case '\u1EAA':
                case '\u1EAC':
                case '\u0202':
                case '\u01CD': {
                    result[i] = 'A';
                    break;
                }
                case '\u00C9':
                case '\u00C8':
                case '\u1EBA':
                case '\u1EBC':
                case '\u1EB8':
                case '\u00CA':
                case '\u1EBE':
                case '\u1EC0':
                case '\u1EC2':
                case '\u1EC4':
                case '\u1EC6':
                case '\u0206': {
                    result[i] = 'E';
                    break;
                }
                case '\u00CD':
                case '\u00CC':
                case '\u1EC8':
                case '\u0128':
                case '\u1ECA': {
                    result[i] = 'I';
                    break;
                }
                case '\u00D3':
                case '\u00D2':
                case '\u1ECE':
                case '\u00D5':
                case '\u1ECC':
                case '\u00D4':
                case '\u1ED0':
                case '\u1ED2':
                case '\u1ED4':
                case '\u1ED6':
                case '\u1ED8':
                case '\u01A0':
                case '\u1EDA':
                case '\u1EDC':
                case '\u1EDE':
                case '\u1EE0':
                case '\u1EE2':
                case '\u020E': {
                    result[i] = 'O';
                    break;
                }
                case '\u00DA':
                case '\u00D9':
                case '\u1EE6':
                case '\u0168':
                case '\u1EE4':
                case '\u01AF':
                case '\u1EE8':
                case '\u1EEA':
                case '\u1EEC':
                case '\u1EEE':
                case '\u1EF0': {
                    result[i] = 'U';
                    break;
                }
                case '\u00DD':
                case '\u1EF2':
                case '\u1EF6':
                case '\u1EF8':
                case '\u1EF4': {
                    result[i] = 'Y';
                    break;
                }

                case '\u0110':
                case '\u00D0':
                case '\u0089': {
                    result[i] = 'D';
                    break;
                }
                default:
                    result[i] = arrChar[i];
            }
        }
        return new String(result);
    }

    public static Object convertNullToEmpty(Object value) {
        return value == null ? "" : value;
    }

	 public static Map<String, String> convertStringToMap(String temp, String regex, String regexToMap) {
        if (isNullOrEmpty(temp)) {
            return null;
        }
        String[] q = temp.split(regex);
        Map<String, String> lstParam = new HashMap<>();
        for (int i = 0; i < q.length; i++) {
            String a = q[i];
            String key = a.substring(0, a.indexOf(regexToMap));
            String value = a.substring(a.indexOf(regexToMap) + 1);
            lstParam.put(key.trim(), value.trim());
        }
        return lstParam;
    }
	

    //Phund them co dinh
    public static String apList2String(List lstAPModel) {
        String result = "";
        if (lstAPModel != null && !DataUtil.isNullOrEmpty(lstAPModel)) {
            result = Joiner.on("@").skipNulls().join(lstAPModel) + "@";
        }
        return result;
    }

    public static boolean safeEqual(Object obj1, Object obj2) {
        return ((obj1 != null) && (obj2 != null) && obj2.toString().equals(obj1.toString()));
    }

    //format cho don vi tien te khi in hoa don
    public static Object convertCommaToDot(Object value) {
        if (!(value instanceof Number)) {
            return value;
        }
        DecimalFormat formatter = new DecimalFormat("###,###");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);
        return formatter.format(value);
    }

    public static String formatSerial(int serialLength, BigInteger serial) {
        int prefix = serialLength - serial.toString().length();
        String serialFormat = prefix == 0 ? "%d" : ("%0" + String.valueOf(serialLength) + "d");
        return String.format(serialFormat, serial);
    }

    public static String getMimeType(String fileExtension) {
        switch (fileExtension) {
            case "pdf":
                return "application/pdf";
            case "png":
                return "image/png";
            case "jpg":
                return "image/jpeg";
            case "bmp":
                return "image/bmp";
            case "gif":
                return "image/gif";
            case "jpe":
                return "image/jpeg";
            case "jpeg":
                return "image/jpeg";
            default:
                return "";
        }
    }

    /**
     * ham compare 2 object Model, chi dung voi cac thuoc tinh kieu nguyen thuy (primitive type)
     *
     * @param oldObj
     * @param newObj
     * @return
     */
    public static boolean compareTwoObj(Object oldObj, Object newObj) {
        try {
            if ((oldObj == null && newObj != null) || (oldObj != null && newObj == null)) {
                return false;
            }
            if (oldObj == null && newObj == null) {
                return true;
            }
            if (!safeEqual(oldObj.getClass().getName(), newObj.getClass().getName())) {
                return false;
            }
            Method[] arrMethod = oldObj.getClass().getDeclaredMethods();
            Method tempMethod = null;
            for (int i = 0; i < arrMethod.length; i++) {
                tempMethod = arrMethod[i];
                if (!tempMethod.getName().startsWith("get")) {
                    continue;
                }
                Object oldBO = null;
                if (oldObj != null) {
                    oldBO = tempMethod.invoke(oldObj, new Object[0]);
                }
                Object newBO = tempMethod.invoke(newObj, new Object[0]);
                String oldValue = "";
                if (oldBO != null) {
                    if (oldBO instanceof Date || oldBO instanceof java.sql.Date) {
//                        oldValue = DateTimeUtils.convertDateTimeToString((Date) oldBO);
                        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        oldValue = yyyyMMdd.format(oldBO);
                    } else {
                        oldValue = oldBO.toString();
                    }
                }
                String newValue = "";
                if (newBO != null) {
                    if (newBO instanceof Date || newBO instanceof java.sql.Date) {
//                        newValue = DateTimeUtils.convertDateTimeToString((Date) newBO);
                        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        newValue = yyyyMMdd.format(newBO);
                    } else {
                        newValue = newBO.toString();
                    }
                }
                if (!oldValue.equals(newValue)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            return hashtext;
        } catch (Exception e) {
            System.out.println("File name cannot encrypt: " + input);

            e.printStackTrace();
        }
        return "";
    }

    public static boolean isValidFraction(String str) {
        if (str != null) {
            try {
                String tmp[] = str.split("/");
                if (tmp.length == 2) {
                    if (safeToLong(tmp[0]) < safeToLong(tmp[1])) {
                        return true;
                    }
                }
            } catch (Exception e) {

            }
        }
        return false;

    }

    public static String convertStrNumber(String strNumber, String tap, String tapEnd, String endTap) {
        if (strNumber == null || strNumber.length() == 0) {
            return "0" + endTap;
        }
        String strTmp = "";
        String endTab = "";
        for (int i = 0; i < strNumber.length(); i++) {
            if (strNumber.charAt(i) == '.') {
                if (i + 1 < strNumber.length()) {
                    for (int j = i + 1; j < strNumber.length(); j++) {
                        endTab += strNumber.charAt(j);
                    }
                }
                break;
            }
            strTmp += strNumber.charAt(i);
        }
        String strNumberExe = String.valueOf(Long.valueOf(strTmp));
        String addStatrt = "";
        int start = 0;
        if (strNumberExe.charAt(0) == '-') {
            addStatrt = "-";
            //So am
            start = 1;
        }
        if (endTab != null && endTab.length() > 0) {
            endTab = tapEnd + endTab;
        }
        String strNumberBuffer = endTab;
        int dem = 0;
        String s = "";
        for (int i = strNumberExe.length() - 1; i >= start; i--) {
            dem++;
            s = strNumberExe.charAt(i) + s;
            if (dem > 0 && dem % 3 == 0) {
                if (i > start) {
                    strNumberBuffer = tap + s + strNumberBuffer;
                } else {
                    strNumberBuffer = s + strNumberBuffer;
                }
                s = "";
            }
        }
        if (s != null && s.length() > 0) {
            strNumberBuffer = s + strNumberBuffer;
        }
        if (addStatrt != null && addStatrt.length() > 0) {
            strNumberBuffer = addStatrt + strNumberBuffer;
        }
        return strNumberBuffer;
    }


    //  ham xu li khoang trang giua cac so
    public static String replaceSpace(String inputLocation) {
        try {
            if (isNullObject(inputLocation)) {
                return null;
            } else {
                return inputLocation.replaceAll(" ", "").trim();
            }
        } catch (Exception e) {
            return null;
        }
    }


    public static String getSysDateTime(String format) throws Exception {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            throw e;
        }
    }


    public static boolean validateCharacterNumberAndSpace(String str) {
        Pattern p = Pattern.compile("[a-zA-Z0-9_ ]*");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean checkPhone(String input) {
        boolean isOk = true;
        if (input == null || input == "") {
            return isOk;
        } else {
            if (input == null) {
                return isOk;

            } else {
                for (int i = 0; i < input.length(); i++) {
                    isOk = input.matches(PHONE_PATTERN);
                }
            }
        }
        return isOk;
    }

    public static boolean checkMail(String input) {
        boolean isOk = true;
        if (input == null || input == "") {
            return isOk;
        } else {
            String[] datamail = input.split(";");
            if (datamail == null) {
                return isOk;

            } else {
                for (int i = 0; i < datamail.length; i++) {
                    isOk = datamail[i].matches(EMAIL_PATTERN);
                    if (isOk == true)
                        continue;
                    else break;
                }
            }
        }
        return isOk;
    }

    //kiem tra co nhap ki tu dac biet hay khong
    public static boolean checkNotSpecical(String input) {
        boolean isOk = true;
        if (input == null || input == "") {
            return isOk;
        } else {
            String[] data = input.split(";");
            if (data == null) {
                return isOk;

            } else {
                for (int i = 0; i < data.length; i++) {
                    isOk = data[i].matches(NOT_SPECICAL_PATTERN);
                    if (isOk == true)
                        continue;
                    else break;
                }
            }
        }
        return isOk;
    }

    //ham replace kÃ­ tá»± tiáº¿ng viá»‡t báº±ng tiáº¿ng anh
    public static String perform(String string) {
        StringBuilder stringSau = new StringBuilder();

        char[] c1 = string.toCharArray();
        for (int i = 0; i < c1.length; i++) {
            //ky tu can thay the
            char kyTuCanThayThe = c1[i];
            //tim ky tu nay trong cacKyTuTiengViet
            for (int j = 0; j < cacKyTuTiengViet.length; j++) {
                char kyTuTiengViet = cacKyTuTiengViet[j];
                if (kyTuTiengViet == kyTuCanThayThe) {
                    kyTuCanThayThe = cacKyTuTiengVietDaBoDau[j];
                    break;
                }
            }
            stringSau.append(kyTuCanThayThe);
        }

        return stringSau.toString().trim();
    }


    public static String readableFileSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "kB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public static Long convertBigDecima2Long(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).longValue();
        }
        if (value instanceof String) {
            try {
                return Long.parseLong((String) value);
            } catch (Exception e) {
                // logger.error(e);
            }
        }
        if (value instanceof Long) {
            return (Long) value;
        }
        try {
            return Long.parseLong(value.toString());
        } catch (Exception e) {
            // logger.error(e);
        }
        return null;
    }

    public static String convertObject2String(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            return (String) value;
        }
        return value.toString();
    }



    public static Integer convertBigDecima2Integer(Object value) {

        if (value == null) {
            return null;
        }
        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).intValue();
        }
        if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (Exception e) {
                // logger.error(e);
            }
        }
        return null;
    }

    public static List<Long> splitLongFromString(String arrayListString) {
        if (arrayListString == null || "".equals(arrayListString.trim())) {
            return null;
        }
        String[] array = arrayListString.split(";");
        if (array.length == 0) return null;
        List<Long> listLong = new ArrayList<Long>();
        for (String tmp : array) {
            Long tmpLong = Long.valueOf(tmp);
            listLong.add(tmpLong);
        }
        return listLong;

    }

    public static List<String> splitStringFromString(String arrayListString) {
        if (arrayListString == null || "".equals(arrayListString.trim())) {
            return null;
        }
        String[] array = arrayListString.split(";");
        if (array.length == 0) return null;
        List<String> listString = new ArrayList<String>();
        for (String tmp : array) {
            listString.add(tmp);
        }
        return listString;

    }


    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list =
                new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static String trimByBytes(String string, int byteLength) {
        for (int index = 0, len = string.length(), bytes = 0; index < len; ++index) {
            bytes += String.valueOf(string.charAt(index)).getBytes().length;

            if (bytes > byteLength)
                return string.substring(0, index);
        }

        return string;
    }

    /**
     * @param source
     * @param <T>
     * @return
     */
    public static <T> List<T> copyList(List<T> source) {
        List<T> dest = new ArrayList<T>();
        for (T item : source) {
            dest.add(item);
        }
        return dest;
    }


    public static String convertDateToTimeString(Date date) throws Exception{
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        //Date today = Calendar.getInstance().getTime();
        String reportDate ="";
        reportDate=df.format(date);
        return reportDate;
    }
    public static Date convertStringTimeToDate(String time) throws Exception{
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date date = formatter.parse(time);
        return date;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static String readDataInFile(String filePath){
        StringBuilder dataInFile = new StringBuilder("");
        try {
            File fileDir = new File(filePath);

            BufferedReader in;
            in = new BufferedReader(
                    new InputStreamReader(new FileInputStream(fileDir), "UTF8"));

            String str;
            while ((str = in.readLine()) != null) {
                dataInFile.append(str);
            }

            in.close();
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return dataInFile.toString();
    }

    public static double roundDouble(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    public static String convertNumberUsingCurrentLocale(Number number) {
        if (number == null) {
            return "0";
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,##0");
        DecimalFormatSymbols symbols = decimalFormat.getDecimalFormatSymbols();
        symbols.setGroupingSeparator(DataUtil.safeToCharacter("."));
        symbols.setDecimalSeparator(DataUtil.safeToCharacter(","));
        decimalFormat.setDecimalFormatSymbols(symbols);
        return decimalFormat.format(number);
    }
}
