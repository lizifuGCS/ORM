package com.hand.app.myorm.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Description
 * @Author zifu.li@hand-china.com
 * @Date 2020/8/8 10:48
 * @Version 1.0
 */
public class ReflectUtil {

    /**
     * 调用对象的get方法
     *
     * @param fieldName
     * @param obj
     * @return
     */
    public static Object invokeGet(String fieldName, Object obj){
        try {
            Class<?> c = obj.getClass();
            Method method = c.getDeclaredMethod("get" + StringUtil.firstChar2UpperCase(fieldName));
            return method.invoke(obj,null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 调用对象的set方法
     *
     * @param fieldName
     * @param obj
     * @return
     */
    public static Object invokeSet(String fieldName, Object obj){
        try {
            Class<?> c = obj.getClass();
            Method method = c.getDeclaredMethod("set" + StringUtil.firstChar2UpperCase(fieldName),obj.getClass());
            return method.invoke(obj,obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
