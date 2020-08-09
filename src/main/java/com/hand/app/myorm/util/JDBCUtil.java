package com.hand.app.myorm.util;

import org.springframework.util.ObjectUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Description   JDBC工具类
 * @Author zifu.li@hand-china.com
 * @Date 2020/8/8 10:36
 * @Version 1.0
 */
public class JDBCUtil {

    public static void handleParams(PreparedStatement ps, Object[] params) {
        if (!ObjectUtils.isEmpty(params)){
            for (int i = 0; i < params.length; i++) {
                try {
                    ps.setObject(i+1,params[i]);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
