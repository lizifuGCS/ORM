package com.hand.app.myorm.connPool;

import com.hand.app.myorm.core.DBManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author zifu.li@hand-china.com
 * @Date 2020/8/8 9:43
 * @Version 1.0
 */
public class DBConnPool {
    /**
     * 连接池对象
     */
    private List<Connection> pool;
    /**
     * 最大连接数
     */
    private static final int POOL_MAX_SIZE = DBManager.getConf().getPoolMaxSize();
    /**
     * 最小连接数
     */
    private static final int POOL_MIN_SIZE = DBManager.getConf().getPoolMinSize();


    /**
     * 初始化连接池
     */
    public void initPool() {
        if (pool == null) {
            pool = new ArrayList<>();
        }
        while (pool.size() < POOL_MIN_SIZE) {
            pool.add(DBManager.createConnection());
        }
    }


    /**
     * 获取数据库连接池中一个连接对象
     * @return
     */
    public synchronized Connection getConnection() {
        if (pool ==null){
            initPool();
        }
        int last_index=pool.size()-1;
        Connection connection = pool.get(last_index);
        pool.remove(last_index);
        return connection;
    }

    /**
     * 关闭连接
     * @param conn
     */
    public synchronized  void close(Connection conn) {
        if (pool.size() > POOL_MAX_SIZE)
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        else {
            pool.add(conn);
        }
    }
}
