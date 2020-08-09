package com.hand.app.myorm.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author zifu.li@hand-china.com
 * @Date 2020/8/8 9:26
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DBConfiguration {

    /**
     * 驱动类
     */
    private String driver;
    /**
     * jdbc-url
     */
    private String url;
    /**
     * 数据库用户
     */
    private String user;
    /**
     * 数据库用户密码
     */
    private String password;
    /**
     * 使用的数据库
     */
    private String usingDB;
    /**
     * 项目的源码路径
     */
    private String srcPath;
    /**
     * 项目的类路径
     */
    private String poPackage;
    /**
     * 项目使用的查询类
     */
    private String queryClass;
    /**
     * 连接池中最小的连接数
     */
    private Integer poolMinSize;
    /**
     * 连接池中最大的连接数
     */
    private Integer poolMaxSize;

}
