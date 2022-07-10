package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Blog;
import model.BlogDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/blog")
public class blogServlet extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();
    // 这个方法用来获取到数据库中的博客列表
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf8");
        // 先尝试获取到 req 中的 blogId 参数
        // 如果参数存在，说明接下来要处理的详情页的逻辑；反之，接下来就要处理列表页的逻辑

        String str = req.getParameter("blogId");
        if(str == null){
            // 如果 str 为 null，说明：接下来就要处理列表页的逻辑
            // 从数据数据中查询到博客列表，将格式转化成json格式，然后直接返回即可
            List<Blog> blogs = BlogDao.selectAll();
            // 把 blogs 转化成json数组的形式
            String respJson = objectMapper.writeValueAsString(blogs);
//            resp.setContentType("application/json; charset=utf8");
            resp.getWriter().write(respJson);
        }else{
            // 反之，接下来就要处理列表页的逻辑
            // 将获取到的 blogId 转换成 整形
            int blogId = Integer.parseInt(str);
            // 调用我们之前书写 selectOne 方法，来查询指定的博客信息
            Blog blog = BlogDao.selectOne(blogId);
            // 将 blog对垒 转换成 json格式的字符串
            String respJson = objectMapper.writeValueAsString(blog);
//            resp.setContentType("application/json; charset=utf8");
            resp.getWriter().write(respJson);
        }

    }
}
