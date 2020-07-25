package com.moft.xfapply.utils;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Transient;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangquan on 2017/4/19.
 */

public class ReflectionUtil {
    public static void convertToObj(Object dest, Object src) {
        if(dest == null || src == null) {
            return;
        }

        List<Method> srcMethods = new ArrayList<>();
        //查找包括子类方法
        if(getDeclaredMethods(src.getClass(), srcMethods) && srcMethods.size() > 0) {
            for (Method srcMethod : srcMethods) {
                String objMethodName = srcMethod.getName();
                if (objMethodName.startsWith("get")) {
                    try {
                        String setM = objMethodName.replaceFirst("get", "set");
                        //get包括子类方法
                        Method newM = getDeclaredMethod(setM, srcMethod.getReturnType(), dest.getClass());
                        if (newM != null) {
                            Object param = srcMethod.invoke(src, (Object[])null);
                            if (null != param) {
                                newM.invoke(dest, param);
                            }
                        }
                    } catch (IllegalAccessException e) {
                        //e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        //e.printStackTrace();
                    }
                }
            }
        }


    }

    public static Map<String, Object> diffAttributes(Object dest, Object src, String[] filters) {
        Map<String, Object> diffMap = new HashMap<String, Object>();

        if(dest == null || src == null) {
            return diffMap;
        }

        Field[] fields = src.getClass().getDeclaredFields();
        for(Field field : fields) {
            if(filters == null || filters.length == 0 || !Arrays.asList(filters).contains(field.getName())) {
                if (!field.isAnnotationPresent(Transient.class) && !field.isAnnotationPresent(Id.class)) {
                    try {
                        field.setAccessible(true);
                        Object destValue = field.get(dest);
                        Object srcValue = field.get(src);

                        if (destValue != null && !destValue.equals(srcValue)) {
                            diffMap.put(field.getName(), destValue);
                        } else if (srcValue != null && !srcValue.equals(destValue)) {
                            diffMap.put(field.getName(), destValue);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return diffMap;
    }
    public static Map<String, String> convertToMap(Object obj) {
        Map<String, String> map = new HashMap<String, String>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for(Field field : fields) {
            if (!field.isAnnotationPresent(Transient.class)) {
                field.setAccessible(true);
                map.put(field.getName(), getValue(field, obj));
            }
        }
        return map;
    }
    public static Map<String, Object> convertToObjMap(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for(Field field : fields) {
            if (!field.isAnnotationPresent(Transient.class)) {
                try {
                    field.setAccessible(true);
                    map.put(field.getName(), field.get(obj));
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    private static boolean getDeclaredMethods(Class cls, List<Method> list) {
        boolean ret = false;
        if(cls != null && cls != Object.class) {
            getDeclaredMethods(cls.getSuperclass(), list);
            Method[] methods = cls.getMethods();
            list.addAll(new ArrayList<Method>(Arrays.asList(methods)));
            ret = true;
        }
        return ret;
    }

    private static Method getDeclaredMethod(String methodName, Class<?> paramType, Class cls) {
        Method method = null;
        if(cls != null && cls != Object.class) {
            try {
                method = cls.getDeclaredMethod(methodName, paramType);
            } catch (NoSuchMethodException e) {
                method = null;
            }
            if(method == null) {
                method = getDeclaredMethod(methodName, paramType, cls.getSuperclass());
            }
        }
        return method;
    }

    private static String getValue(Field field, Object obj) {
        String retValue = "";
        try {
            if(field.getType() == Date.class) {
                DateUtil.format((Date)field.get(obj));
            } else {
                retValue = field.get(obj).toString();
            }
        }catch (Exception e) {

        }
        return retValue;
    }
}
