package com.example.test.Severce;

import javax.sql.DataSource;
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
        String url = "jdbc:mysql://127.0.0.1:3306/lab1_bank";
        String user = "root";
        String password = "zyk0108";
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
