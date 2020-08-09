package com.hand.app.myorm.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author zifu.li@hand-china.com
 * @Date 2020/8/8 9:24
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ColumnInfo {

    public static final int NORMAL_KEY = 0;
    public static final int PRIMARY_KEY = 1;
    public static final int FOREIGN_KEY = 2;
    /**
     *  字段名称
     */
    private String colName;

    /**
     * 字段类型
     */
    private String dataType;

    /**
     * 字段的键类型
     * ０:普通键 1:主键 2:外键
     */
    private int keyType;
}
