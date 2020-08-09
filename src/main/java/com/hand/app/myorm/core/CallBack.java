package com.hand.app.myorm.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @Description
 * @Author zifu.li@hand-china.com
 * @Date 2020/8/8 10:13
 * @Version 1.0
 */
public interface CallBack {
     Object doExecute(Connection conn, PreparedStatement ps, ResultSet rs);

}
