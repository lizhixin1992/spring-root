package com.blue.spring.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.blue.spring.exception.BusinessException;
import com.blue.spring.resultMessage.CommonResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

/*
 * Pprun's Public Domain.
 */

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.apache.commons.beanutils.BeanUtils;

/**
 * Constant used in the utility method.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class CommonUtil {
    private static final String ipRegex = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";
    private static Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    public static BigDecimal round(BigDecimal value) {
        return value.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 四舍五入
     * @param num1
     * @param num2
     * @return
     */
    public static int round(int num1, int num2) {
        if (num1 == 0 || num2 == 0) {
            return 0;
        }
        BigDecimal b1 = new BigDecimal(num1);
        BigDecimal b2 = new BigDecimal(num2);
        Double d = b1.divide(b2,BigDecimal.ROUND_HALF_UP).doubleValue();
        return d.intValue();
    }

    /**
     * Make an empty {@code ""} if the given String is {@code null}.
     *
     * @param src
     * @return
     */
    public static String nullToEmpty(String src) {
        return src == null ? "" : src;
    }

    public static String join(Collection<String> collectionOfStrings,
                              String delimeter) {
        StringBuilder result = new StringBuilder();
        for (String s : collectionOfStrings) {
            result.append(s);
            result.append(delimeter);
        }
        return result.substring(0, result.length() - 1);
    }

    /**
     * 将列名转换成属性名
     *
     * @return
     */
    public static String changeDbName2JavaName(String name){
        if (!StringUtils.hasText(name)) {
            return null;
        }
        name = name.toLowerCase();
        String[] split = name.split("_");
        if (split.length > 1) {
            StringBuilder sb = new StringBuilder(split[0]);
            for (int i = 1; i < split.length; i++) {
                String columnName = split[i].substring(0, 1).toUpperCase() + split[i].substring(1, split[i].length());
                sb.append(columnName);
            }
            return sb.toString();
        } else {
            return name;
        }
    }

    /**
     * 将属性名换成列名
     *
     * @param attributeName
     * @return
     */
    public static String convertAttrNameToColumnName(String attributeName) {
        char[] flag = attributeName.toCharArray();
        StringBuilder sum = new StringBuilder();
        for (char a : flag) {

            String str = String.valueOf(a);
            if (str.toLowerCase().charAt(0) - a == 32) {
                str = "_" + str.toLowerCase();
            }
            sum.append(str.toUpperCase());
        }
        return sum.toString();

    }

    /**
     * 将一个字符串的首字母改成大写
     *
     * @param str
     * @return
     */
    public static String toFirstLetterUpperCase(String str) {
        StringBuffer sb = new StringBuffer();
        sb.append(str.substring(0, 1).toUpperCase());
        sb.append(str.substring(1));
        return sb.toString();
    }

    /**
     * 将一个字符串的首字母改成小写
     *
     * @param str
     * @return
     */
    public static String toFirstLetterLowerCase(String str) {
        StringBuffer sb = new StringBuffer();
        sb.append(str.substring(0, 1).toLowerCase());
        sb.append(str.substring(1));
        return sb.toString();
    }


    /**
     * 判断给定字符串是否符合属性命名规范
     * 首字母小写的驼峰命名规范
     * @param str
     * @return
     */
    public boolean isPropertyName(String str){
        boolean flag = true ;
        for(int i=0;i<str.length();i++){
            char ch = str.charAt(i);
            //判断字符是否为字母
            if(!Character.isLetter(ch)){
                flag = false;
                break;
            }
            //判断首字母是否小写
            if(i == 0 && Character.isUpperCase(ch)){
                flag = false;
                break;
            }
        }
        return flag;
    }

    private String getPropertyName(String str){
        return "get"+toFirstLetterUpperCase(str);
    }
    public boolean notNullAndEmpty(String str){
        boolean result = true;
        if(null == str||"".equals(str.trim())){
            result =false;
        }

        return result;
    }

    /**
     * 把一些原有系统的字典值转成现有系统的字典值
     *
     * @param valueId
     * @param objectType
     * @param typeId
     * @param count
     * @return
     */
    public Integer fillValueId(String valueId,int objectType, Integer typeId, int count) {
        if (valueId == null || valueId.length() < 1) {
            return null;
        }
        if ("-1".equals(valueId)) {
            return -1;
        }
        valueId = valueId.trim();

        StringBuilder sb = new StringBuilder();
        while (valueId.length() < count) {
            sb.append("0").append(valueId);
            valueId = sb.toString();
            sb = new StringBuilder();
        }

        return Integer.parseInt(String.valueOf(typeId) +objectType+ valueId);
    }

    public Float formatFloat(Object o) {
        if (o == null) {
            return 0F;
        }
        String s = o.toString();
        if (StringUtils.hasLength(s)) {
            return Float.parseFloat(s);
        }
        return 0F;
    }

    public String formatString(Object o) {
        if (o == null) {
            return Const.BLANK_STRING;
        }
        String s = o.toString();
        if (StringUtils.hasLength(s)) {
            return s;
        } else {
            return Const.BLANK_STRING;
        }
    }

    public int formatNum(Object o) {
        if (o == null) {
            return Const.INIT_NUM;
        } else {
            String os = o.toString();
            if (Pattern.matches(Const.POSITIVE_INTEGER,os)){
                int num = Integer.parseInt(os);
                return num;
            }
            return Const.INIT_NUM;

        }
    }

    /**
     * 包含正整数和负整数的校验
     * @param o
     * @return
     */
    public int formatInteger(Object o) {
        if (o == null) {
            return Const.INIT_NUM;
        } else {
            String os = o.toString();
            if (Pattern.matches(Const.INTEGER,os)){
                int num = Integer.parseInt(os);
                return num;
            }
            return Const.INIT_NUM;
        }
    }

    public Long formatLong(Object o) {
        if (o == null) {
            return 0L;
        }
        String os = o.toString();
        if (Pattern.matches(Const.POSITIVE_INTEGER,os)){
            return Long.parseLong(os);
        }
        return 0L;
    }

    public Long formatString2Long(Object o) {
        if (o == null) {
            return 0L;
        }
        String s = o.toString();
        if (StringUtils.hasLength(s)) {
            if (Pattern.matches(Const.POSITIVE_INTEGER,s)){
                return Long.parseLong(s);
            }else{
                return 0L;
            }
        }
        return 0L;
    }

    public int formatString2Num(Object o) {
        if (o == null) {
            return Const.INIT_NUM;
        }
        String os =  o.toString();
        if (StringUtils.hasLength(os) && !"null".equals(os)) {

            if (Pattern.matches(Const.POSITIVE_INTEGER,os)){
                int num = Integer.parseInt(os);
                return num;
            }
            return Const.INIT_NUM;
        }else {
            return Const.INIT_NUM;
        }

    }

    public int formatTime2Num(Object o) {
        if (o == null) {
            return Const.INIT_NUM;
        }
        String s = (String) o;
        if (StringUtils.hasLength(s) && !"null".equals(s)) {
            String[] array = s.split(":");
            int sum = 0;
            sum = Integer.parseInt(array[0]) * 3600 + Integer.parseInt(array[1]) * 60 + Integer.parseInt(array[2]);
            return sum;
        }else {
            return Const.INIT_NUM;
        }

    }

    /**
     * 验证IP格式
     * @param ipAddr
     * @return
     */
    public boolean isIPAddress(String ipAddr) {
        Pattern pattern = Pattern.compile(ipRegex);
        Matcher m = pattern.matcher(ipAddr);
        return m.matches();
    }


    public Boolean checkValueBelong2Key(Object object, String checkKey, Object checkValue) {
        Boolean bo = false;
        if (object != null) {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(object);
            if (jsonObject.containsKey(checkKey)) {
                Object key = jsonObject.get(checkKey);
                if (key instanceof Integer) {
                    return (Integer)key == Integer.parseInt(checkValue.toString());
                }
                if (key instanceof Long) {
                    return (Long)key == Long.parseLong(checkValue.toString());
                }
                if (key.equals(checkValue)) {
                    bo = true;
                }
            }
        }
        return bo;
    }

    public Integer formatString2Int(String valueName,String value)throws BusinessException{
        if (!Pattern.matches(Const.NON_ZERO_POSITIVE_INTEGER, value)) {
            logger.error("参数{}={}，无法转换为int",valueName,value);
            throw new BusinessException(CommonResultMessage.PARAM_ERROR,valueName);
        }
        return Integer.parseInt(value);
    }

    public static Long formatString2Long(String valueName,String value)throws BusinessException{
        if (!Pattern.matches(Const.NON_ZERO_POSITIVE_INTEGER, value)) {
            logger.error("参数{}={}，无法转换为long",valueName,value);
            throw new BusinessException(CommonResultMessage.PARAM_ERROR,valueName);
        }
        return Long.parseLong(value);
    }

    /**
     * 字符串转数字
     * @param valueName
     * @param value
     * @return
     */
    public static Number parseNumber(String valueName, String value) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(value), "parseNumber is empty");
        if (!Pattern.matches(Const.NON_ZERO_POSITIVE_INTEGER, value)) {
            logger.error("参数{}={}，无法转换为数字",valueName,value);
            throw new BusinessException(CommonResultMessage.PARAM_ERROR, valueName);
        }
        try {
            NumberFormat nf = NumberFormat.getNumberInstance();
            return nf.parse(value);
        } catch (Exception e) {
            logger.error("参数{}={}，无法转换为数字",valueName,value);
            throw new BusinessException(CommonResultMessage.PARAM_ERROR, valueName);
        }
    }

    // Map --> Bean 2: 利用org.apache.commons.beanutils 工具类实现 Map --> Bean
    public static void transMap2Bean2(Map<String, Object> map, Object obj) {
        if (map == null || obj == null) {
            return;
        }
        try {
            BeanUtils.populate(obj, map);
        } catch (Exception e) {
            logger.error("transMap2Bean2 Error " + e);
        }
    }

    // Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean
    public static void transMap2Bean(Map<String, Object> map, Object obj) {

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    // 得到property对应的setter方法
                    Method setter = property.getWriteMethod();
                    setter.invoke(obj, value);
                }

            }

        } catch (Exception e) {
            logger.error("transMap2Bean Error " + e);
        }

        return;

    }

    // Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
    public static Map<String, Object> transBean2Map(Object obj) {

        if(obj == null){
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                }

            }
        } catch (Exception e) {
            logger.error("transBean2Map Error " + e);
        }

        return map;

    }

}
