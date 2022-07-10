package model;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DButil {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/java_blog?characterEncoding=utf8&useSSL=false";
    private static final String User = "root";
    private static final String Password = "123456";
    // 加上 volatile 防止编译器对其进行优化。
    private static volatile DataSource dataSource = null;// 数据源对象
    // 创建数据源，描述服务器的所在地址，用户名，以及用户密码
    private static  DataSource getDataSource(){
        // 提升效率
        if(dataSource == null){
            // 线程安全
            synchronized (DButil.class){
                if(dataSource == null){
                    dataSource = new MysqlDataSource();
                    ((MysqlDataSource)dataSource).setURL(URL);
                    ((MysqlDataSource)dataSource).setUser(User);
                    ((MysqlDataSource)dataSource).setPassword(Password);
                }
            }
        }
        return dataSource;
    }
    // 获取练剑
    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }
    //资源回收
    public static void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet){
        if (resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (preparedStatement != null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
