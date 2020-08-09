package com.hand.app.myorm.context;

import com.hand.app.myorm.core.DBManager;
import com.hand.app.myorm.core.MysqlTypeConvertor;
import com.hand.app.myorm.info.ColumnInfo;
import com.hand.app.myorm.info.TableInfo;
import com.hand.app.myorm.util.JavaFileUtil;
import com.hand.app.myorm.util.StringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 负责获取管理数据库所有表结构和类结构的关系，并可以根据表结构生成类结构。
 * @Author zifu.li@hand-china.com
 * @Date 2020/8/8 11:27
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@Component
public class TableContext {

    //表名为key，表信息对象为value
    public static Map<String, TableInfo> tables = new HashMap<>();

    //将po的class对象和表信息对象关联起来，便于重用！
    public static Map<Class, TableInfo> poClassTableMap = new HashMap<>();


    static {
        try {
            //获取表信息
            Connection con = DBManager.getConnection();
            DatabaseMetaData dbmd = con.getMetaData();

            ResultSet tableRet = dbmd.getTables(null, "%", "my_%", new String[]{"TABLE"});

            while (tableRet.next()) {
                String tableName = (String) tableRet.getObject("TABLE_NAME");

                TableInfo tableInfo = TableInfo.builder()
                        .tabName(tableName)
                        .columns(new HashMap<>())
                        .priKeys(new ArrayList<>()).build();
                tables.put(tableName, tableInfo);

                //查询表中的所有字段
                ResultSet columns = dbmd.getColumns(null, "%", tableName, "%");
                while (columns.next()) {
                    ColumnInfo ci = new ColumnInfo(columns.getString("COLUMN_NAME"),
                            columns.getString("TYPE_NAME"), 0);

                    tableInfo.getColumns().put(columns.getString("COLUMN_NAME"), ci);
                }

                //查询主键
                ResultSet primaryKey = dbmd.getPrimaryKeys(null, "%", tableName);
                while (primaryKey.next()) {
                    ColumnInfo columnInfo = tableInfo.getColumns().get(primaryKey.getString("COLUMN_NAME"));
                    columnInfo.setKeyType(ColumnInfo.PRIMARY_KEY);
                    tableInfo.getPriKeys().add(columnInfo);
                }

                ////取唯一主键。。方便使用。如果是联合主键。则为空！
                if (tableInfo.getPriKeys().size() > 0) {
                    tableInfo.setPriKey(tableInfo.getPriKeys().get(0));
                }


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //更新类结构
        updateJavaPOFile();


    }


    /**
     * 根据表结构，更新配置po包下的java类
     */
    private static void updateJavaPOFile() {
        Map<String, TableInfo> map = tables;

        for (TableInfo tableInfo : map.values()) {
            try {
                JavaFileUtil.createJavaPOFile(tableInfo, new MysqlTypeConvertor());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 加载po包下的类
     */
    private static void loadPOTables() {
        for (TableInfo table : tables.values()) {
            try {
                Class<?> c = Class.forName(DBManager.getConf().getPoPackage() + "." + StringUtil.firstChar2UpperCase(table.getTabName()));
                poClassTableMap.put(c, table);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    public static Map<Class, TableInfo> getPoClassTableMap() {
        //加载po包下的所有的类
        loadPOTables();
        return poClassTableMap;
    }


}
