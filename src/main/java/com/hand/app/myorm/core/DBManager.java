package com.hand.app.myorm.core;

import com.hand.app.myorm.config.DBConfiguration;
import com.hand.app.myorm.connPool.DBConnPool;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @Description
 * @Author zifu.li@hand-china.com
 * @Date 2020/8/8 9:46
 * @Version 1.0
 */
public class DBManager {


    /**
     * 配置信息
     */
    private static DBConfiguration conf;
    /**
     * 连接池对象
     */
    private static DBConnPool pool;

    public static DBConfiguration getConf() {
        return conf;
    }

    static {
        Properties pros = new Properties();

        try {
            pros.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        conf = new DBConfiguration();
        conf.setDriver(pros.getProperty("driver"));
        conf.setPassword(pros.getProperty("password"));
        conf.setUrl(pros.getProperty("url"));
        conf.setUser(pros.getProperty("user"));
        conf.setSrcPath(pros.getProperty("srcPath"));
        conf.setPoPackage(pros.getProperty("poPackage"));
        conf.setUsingDB(pros.getProperty("usingDB"));
        conf.setQueryClass(pros.getProperty("queryClass"));
        conf.setPoolMaxSize(Integer.parseInt(pros.getProperty("poolMaxSize")));
        conf.setPoolMinSize(Integer.parseInt(pros.getProperty("poolMinSize")));


    }

    /**
     * 获得数据库连接
     * @return 连接
     */
    public static Connection createConnection() {
        try {
            Class.forName(conf.getDriver());
            return DriverManager.getConnection(conf.getUrl(), conf.getUser(), conf.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从连接池中获得数据库连接
     * @return 连接
     */
    public static Connection getConnection() {

        if (pool == null)
            pool = new DBConnPool();
        return pool.getConnection();

    }

    /**
     * 关闭资源
     * @param rs
     * @param ps
     * @param conn
     */
    public static void close(ResultSet rs, Statement ps, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pool.close(conn);
    }
}



