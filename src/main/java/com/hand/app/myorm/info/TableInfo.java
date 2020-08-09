package com.hand.app.myorm.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author zifu.li@hand-china.com
 * @Date 2020/8/8 9:37
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TableInfo {

    /**
     * 表名
     */
    private String tabName;
    /**
     * 所有字段信息
     */
    private Map<String, ColumnInfo> columns;

    /**
     * 表主键
     */
    private ColumnInfo priKey;

    /**
     * 联合主键
     */
    private List<ColumnInfo> priKeys;

}
