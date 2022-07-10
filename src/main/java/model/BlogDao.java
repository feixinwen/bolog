package model;

import model.DButil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// 这个类用于封装博客表的基本操作（增删查改）
public class BlogDao {
    //    1、往博客表里，插入一条记录（一篇博客）
    public static void insert(Blog blog){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        // JDBC 基本的代码
        try {
            //1、和数据库建立连接
            connection = DButil.getConnection();
            //2、构造sql语句
            // 这里的 now() 表示取得是当前系统的时间
            String sql = "insert into blog values(null,?,?,?,now())";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,blog.getTitle());
            preparedStatement.setString(2, blog.getContent());
            preparedStatement.setInt(3,blog.getUserId());
            //3、执行SQL语句
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //4、关闭连接，回收资源
            DButil.close(connection,preparedStatement,null);
        }
    }

    //    2、能够获取到博客表中的所有博客的信息【用于博客列表页显示博客有多少】
//    另外，我们的博客列表，会从每篇博文中截取一段内容，因此截取的内容不一定是截取到完整的博客内容。
//    因为截取到完整的博客的内容，占用的空间会很大！两三行还行，多了就不利于显示更多的博文信息
    public static List<Blog> selectAll(){
        List<Blog> blogs = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        // JDBC 基本的代码
        try {
            //1、和数据库建立连接
            connection = DButil.getConnection();
            //2、构造sql语句
            String sql = "select * from blog order by postTime desc ";
            preparedStatement = connection.prepareStatement(sql);
            //3、执行SQL语句
            resultSet = preparedStatement.executeQuery();
            // 4、 遍历结果集
            while(resultSet.next()){
                Blog blog = new Blog();
                blog.setBlogId(resultSet.getInt("blogId"));
                blog.setTitle(resultSet.getString("title"));
                blog.setContent(resultSet.getString("content"));
                blog.setUserId(resultSet.getInt("userId"));
                blog.setTimestamp(resultSet.getTimestamp("postTime"));
                blogs.add(blog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //5、关闭连接，回收资源
            DButil.close(connection,preparedStatement,resultSet);
        }
        return blogs;
    }

    //    3、能够根据 博客id 读取到指定的博客内容【用于博客详情页显示一篇博客的详情内容】
    public static Blog selectOne(int blogId){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        // JDBC 基本的代码
        try {
            //1、和数据库建立连接
            connection = DButil.getConnection();
            //2、构造sql语句
            String sql = "select * from blog where blogId = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,blogId);
            //3、执行SQL语句
            resultSet = preparedStatement.executeQuery();
            // 4、 遍历结果集：此处我们是使用主键 作为查询条件
            // 因此查询的结果要么是一条，要么是零条。
            if(resultSet.next()){
                Blog blog = new Blog();
                blog.setBlogId(resultSet.getInt("blogId"));
                blog.setTitle(resultSet.getString("title"));
                blog.setContent(resultSet.getString("content"));
                blog.setUserId(resultSet.getInt("userId"));
                blog.setTimestamp(resultSet.getTimestamp("postTime"));
                return blog;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //5、关闭连接，回收资源
            DButil.close(connection,preparedStatement,resultSet);
        }
        return null;
    }

    //    4、从博客列表中，根据博客Id 来删除博客
    public static void delete(int blogId){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        // JDBC 基本的代码
        try {
            //1、和数据库建立连接
            connection = DButil.getConnection();
            //2、构造sql语句
            String sql = "delete from blog where blogId = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,blogId);
            //3、执行SQL语句
            preparedStatement.executeUpdate();
            // 4、 遍历结果集：此处我们是使用主键 作为查询条件
            // 因此查询的结果要么是一条，要么是零条。
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //5、关闭连接，回收资源
            DButil.close(connection,preparedStatement,null);
        }
    }

//    注意上述的4个操作只涉及到增删查，不涉及到 改。
//    改，这个操作可以被实现，但是不在这里实现。
//    换句话来说：实现该这个操作 和 前面的增删查，并没有什么区别。
//    所以在这里只是显示几个经典的功能，如果你们想要实现 改，加油！
//    我偷个懒。
}
