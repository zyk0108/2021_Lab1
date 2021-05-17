package com.example.test.mapper;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * jdbc操作工具类
 * 用于初始化数据库连接池、获取数据库连接、释放数据库连接
 * @author wangj
 */
public class JDBCUtil {
    private static DataSource dataSource;

    /*static {
        dataSource = new  ComboPooledDataSource("testApp");
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }*/

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        // 加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 获取连接
        String url = "jdbc:mysql://127.0.0.1:3306/lab1_?serverTimezone=Asia/Shanghai";
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
