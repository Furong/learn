package net.ibytes.study.dbutils;

import java.sql.*;

/**
 * @author dingfurong
 * @date 2019/4/8
 * @description
 */
public class DBUtils {
    /**
     * 数据库连接URL
     */
    private static String URL;
    /**
     * 用户名
     */
    private static String USERNAME;
    /**
     * 密码
     */
    private static String PASSWORD;
    /**
     * 数据库驱动
     */
    private static String DRIVER;

    static {
        URL = "jdbc:postgresql://localhost:5432/db";
        USERNAME = "postgres";
        PASSWORD = "root";
        DRIVER = "org.postgresql.Driver";
    }

    /**
     * 获取数据库连接
     * @return
     */
    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     * @param rs
     * @param stat
     * @param conn
     */
    public static void close(ResultSet rs, Statement stat, Connection conn){
        try {
            if(rs != null) {
                rs.close();
            }
            if(stat != null){
                stat.close();
            }
            if(conn != null) {
                conn.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
