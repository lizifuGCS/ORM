package com.hand.app.myorm.util;

/**
 * @Description
 * @Author zifu.li@hand-china.com
 * @Date 2020/8/8 10:52
 * @Version 1.0
 */
public class StringUtil {

    public static String firstChar2UpperCase(String str){
       return str.toUpperCase().substring(0,1)+str.substring(1);
    }
}
