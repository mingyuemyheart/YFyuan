package com.moft.xfapply.utils;

import java.text.DecimalFormat;
import java.util.List;

public class StringUtil {

    public static boolean isNull(String s) {
        return null == s;
    }

    public static boolean isEmpty(String s) {
        return null == s || "".equals(s);
    }

    public static String get(String s) {
        if (isEmpty(s) || "null".equals(s) || "无".equals(s)) {
            return "";
        } else {
            return s;
        }
    }

    public static String get(Boolean s) {
        if (s == null) {
            return "false";
        }
        if (s) {
            return "true";
        } else {
            return "false";
        }
    }

    public static String get(Double s) {
        if (s == null) {
            return "";
        } else {
            return s + "";
        }
    }

    public static String get(Integer s) {
        if (s == null) {
            return "";
        } else {
            return s + "";
        }
    }
    
    public static boolean isPasswordInvalid(String pwd) {
        if (isEmpty(pwd)) {
            return true;
        }

//        if (pwd.length() < 8) {
//            return true;
//        }
        
        return false;
    }
    
    public static boolean isNumericOrChar(String str){  
        for (int i = str.length(); --i>=0;){ 
            if (!(Character.isDigit(str.charAt(i)) || 
                    Character.isLetter(str.charAt(i)))){
                return false;
            }
        }
           return true;   
     }

    // 根据Unicode编码完美的判断中文汉字和符号
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }
 
    // 完整的判断中文汉字和符号
    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    //首字母大写
    public static String captureName(String name) {
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return  name;
    }

    public static String getSimpleAddress(String address) {
        String simpleAdd = address;

        if (!StringUtil.isEmpty(address)) {
            if (address.startsWith("中国")) {
                simpleAdd = address.replaceFirst("中国", "");
            }
            int proIndex = -1;
            int cityIndex = simpleAdd.indexOf("市");
            if (cityIndex != -1) {
                String cityBefore = simpleAdd.substring(0, cityIndex);
                proIndex = cityBefore.indexOf("省");
            }
            if (proIndex != -1) {
                simpleAdd = simpleAdd.substring(proIndex+1);
            }
        }

        return simpleAdd;
    }

    /*
    * @Author 王旭
    * @Date 2019-03-29
    * @Description ID 925915 【移动终端】地图页面搜索时，因输入法问题，导致sql中包含分隔符引发异常。 王旭 2019-03-29
    */
    public static String handleTransferChar(String key) {
        if (StringUtil.isEmpty(key)) {
            return key;
        }
        return key.replaceAll("'", "''");
    }

    public static String formatLatLng(double l) {
        DecimalFormat df = new DecimalFormat("#.######");
        return df.format(l);
    }

    public static String list2String(List<String> uuids) {
        Boolean flag = false;
        StringBuilder result = new StringBuilder();
        for(String uuid : uuids) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append("'");
            result.append(uuid);
            result.append("'");
        }

        return result.toString();
    }


    public static String getDurationDesc(int duration) {
        if (-1 == duration) {
            return "未知";
        }
        String desc = "约需";
        if (duration / 3600 == 0) {
            desc += duration / 60 + "分钟";
        } else {
            desc += duration / 3600 + "小时" + (duration % 3600) / 60 + "分钟";
        }
        return desc;
    }

    public static String getDistanceDesc(int distance) {
        if (-1 == distance) {
            return "未知";
        }
        String desc = "距离约";
        if (distance < 1000) {
            desc += distance + "米";
        } else {
            DecimalFormat df = new DecimalFormat("#.##");
            double d = ((double)distance)/1000;
            desc += df.format(d) + "千米";
        }
        return desc;
    }
}
