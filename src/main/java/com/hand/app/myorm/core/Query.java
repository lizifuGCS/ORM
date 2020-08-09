package com.hand.app.myorm.core;

import com.hand.app.myorm.context.TableContext;
import com.hand.app.myorm.domain.My_orm;
import com.hand.app.myorm.info.TableInfo;
import com.hand.app.myorm.util.JDBCUtil;
import com.hand.app.myorm.util.ReflectUtil;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author zifu.li@hand-china.com
 * @Date 2020/8/8 10:29
 * @Version 1.0
 */
public  class Query  implements Cloneable {

    private Query query;
    private Connection conn =DBManager.getConnection();
    private PreparedStatement ps=null;
    private ResultSet rs = null;


    /**
     *采用模板方法模式将JDBC操作封装成模板
     *
     * @param sql   sql语句
     * @param params  参数
     * @param clazz  记录要封装到的java类
     * @param back    查询结果回调
     * @return
     */
    public Object executeQueryTemplate(String sql,Object[] params, Class clazz,CallBack back){


        try {
            ps= conn.prepareStatement(sql);
            JDBCUtil.handleParams(ps, params);
            rs=ps.executeQuery();
            return back.doExecute(conn,ps,rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }finally {
            DBManager.close(null, ps, conn);
        }
    }

    /**
     *执行DML语句
     *
     * @param sql   sql语句
     * @param params  参数
     * @return
     */
    public int executeDML(String sql,Object[] params){
      int count =0;
        try {
            ps =conn.prepareStatement(sql);
            JDBCUtil.handleParams(ps, params);
            count = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBManager.close(null, ps, conn);
        }
        return count;
    }

    /**
     * 执行保存逻辑
     *
     * @param obj
     */
    public void save(My_orm obj) {
        Class c = obj.getClass();

        TableInfo tableInfo = TableContext.getPoClassTableMap().get(c);
        List<Object> params =new ArrayList<>();
        StringBuilder sql=new StringBuilder("insert into "+tableInfo.getTabName()+" (");
        Field[] fs=c.getDeclaredFields();
        int count =0;

        for (Field f : fs) {
            String name = f.getName();
            Object fieldValue = ReflectUtil.invokeGet(name, obj);

            if (fieldValue!=null){
                count++;
                sql.append(name+",");
                params.add(fieldValue);
            }
        }
        sql.setCharAt(sql.length()-1,')');
        sql.append(" values (");
        for (int i = 0; i < count; i++) {
            sql.append("?,");
        }
        sql.setCharAt(sql.length()-1, ')');
        this.executeDML(sql.toString(), params.toArray());
    }


}
