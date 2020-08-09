package com.hand.app.myorm.core;

/**
 * @Description   负责java类型和数据库类型的相互转换
 * @Author zifu.li@hand-china.com
 * @Date 2020/8/8 12:11
 * @Version 1.0
 */
public interface TypeConvertor {

    /**
     * 将数据库类型转换为java类型
     *
     * @param columnType   columnType 数据库字段类型
     * @return  java类型
     */
     String databaseType2JavaType(String columnType);

    /**
     * 将java类型转换为数据库类型
     *
     * @param javaType  javaType java类型
     * @return  数据库类型
     */
    public String javaType2DatabaseType(String  javaType);

}
