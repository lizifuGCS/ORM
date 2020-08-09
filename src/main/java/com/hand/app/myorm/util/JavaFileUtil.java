package com.hand.app.myorm.util;

import com.hand.app.myorm.core.DBManager;
import com.hand.app.myorm.core.TypeConvertor;
import com.hand.app.myorm.info.ColumnInfo;
import com.hand.app.myorm.info.JavaFieldGetSet;
import com.hand.app.myorm.info.TableInfo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author zifu.li@hand-china.com
 * @Date 2020/8/8 12:09
 * @Version 1.0
 */
public class JavaFileUtil {

    /**
     * 创建表对应的类信息
     * @param tableInfo
     * @param convertor
     */
    public static void createJavaPOFile(TableInfo tableInfo, TypeConvertor convertor) throws IOException {
        String src = createJavaSrc(tableInfo, convertor);
        String srcPath = DBManager.getConf().getSrcPath() + "//";
        String packagePath = DBManager.getConf().getPoPackage().replaceAll("\\.", "/");

        File f = new File(srcPath+packagePath+File.separator + StringUtil.firstChar2UpperCase(tableInfo.getTabName()) + ".java");

        if (!f.exists()) {
            f.createNewFile();
        }
        BufferedWriter  bufferedWriter = null;
        try {
            FileWriter fileWriter = new FileWriter(f.getAbsoluteFile());
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(src);
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }
    }

    /**
     * 根据表信息生成java类源码
     * @param tableInfo 表信息
     * @param convertor 数据类型转换器
     * @return java类源码
     */

    private static String createJavaSrc(TableInfo tableInfo, TypeConvertor convertor) {
        StringBuffer src = new StringBuffer();

        Map<String, ColumnInfo> columns = tableInfo.getColumns();
        List<JavaFieldGetSet> javaFields = new ArrayList<>();

        for (ColumnInfo value : columns.values()) {
            javaFields.add(createFieldGetSetSRC(value, convertor));
        }

        //生成package语句
        src.append("package " + DBManager.getConf().getPoPackage() + ";\n\n");

        //生成import语句
        src.append("import java.sql.*;\n");
        src.append("import java.util.*;\n\n");
        //生成类声明语句
        src.append("public class " + StringUtil.firstChar2UpperCase(tableInfo.getTabName()) + " {\n\n");
//        System.out.println("public class "+StringUtil.firstChar2UpperCase(tableInfo.getTabName())+" {\n\n");


        //生成属性列表
        for (JavaFieldGetSet f : javaFields) {
            src.append(f.getFieldInfo());
        }
        src.append("\n\n");

        //生成get方法列表
        for (JavaFieldGetSet f : javaFields) {
            src.append(f.getGetInfo());
        }

        //生成set方法列表
        for (JavaFieldGetSet f : javaFields) {
            src.append(f.getSetInfo());
        }

        //生成类结束
        src.append("}\n");
//        System.out.println(src);
        return src.toString();

    }

    /**
     *根据字段信息生成java属性信息
     * @param colum 字段信息
     * @param convertor 类型转换器
     * @return java属性的set/get方法
     */
    private static JavaFieldGetSet createFieldGetSetSRC(ColumnInfo colum, TypeConvertor convertor) {

        JavaFieldGetSet fieldSet = new JavaFieldGetSet();

        String javaFieldType = convertor.databaseType2JavaType(colum.getDataType());

        fieldSet.setFieldInfo("\tprivate " + javaFieldType + " " + colum.getColName() + ";\n");

        StringBuffer getSrc = new StringBuffer();
        //生成get方法源码
        getSrc.append("\tpublic "+javaFieldType+" get"+StringUtil.firstChar2UpperCase(colum.getColName())+"(){\n"   );
        getSrc.append("\t\treturn " + colum.getColName() + ";\n");
        getSrc.append("\t}\n");
        fieldSet.setGetInfo(getSrc.toString());

        //生成set方法源码
        StringBuffer setSrc = new StringBuffer();
        setSrc.append("\tpublic void set" + StringUtil.firstChar2UpperCase(colum.getColName()) + "(");
        setSrc.append(javaFieldType + " " + colum.getColName() + "){\n");
        setSrc.append("\t\tthis." + colum.getColName() + "=" + colum.getColName() + ";\n");
        setSrc.append("\t}\n");
        fieldSet.setSetInfo(setSrc.toString());
        return fieldSet;
    }
}

