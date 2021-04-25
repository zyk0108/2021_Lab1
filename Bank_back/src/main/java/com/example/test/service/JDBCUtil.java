package com.example.test.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库操作
 */
public class JDBCUtil {

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        // 加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 获取连接
        String url = "jdbc:mysql://127.0.0.1:3306/lab1?serverTimezone=Asia/Shanghai";
        String user = "root";
        String password = "123456cxp";
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    public static void releaseConnection(Connection connection){
        try {
            if(connection != null){
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
