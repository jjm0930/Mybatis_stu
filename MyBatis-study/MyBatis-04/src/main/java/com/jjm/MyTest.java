package com.jjm;

import com.jjm.dao.BlogMapper;
import com.jjm.pojo.Blog;
import com.jjm.utils.MyBatisUtil;
import com.jjm.utils.UidUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author jjm
 * @version 1.0
 */
public class MyTest {
    @Test
    public void testQueryBlogIf(){
        SqlSession sqlsession=MyBatisUtil.getSqlSession();
        BlogMapper mapper = sqlsession.getMapper(BlogMapper.class);
        HashMap<String, Object> map = new HashMap<>();
        //map.put("title","Java如此简单");
        map.put("author","jjm");
        List<Blog> blogs = mapper.queryBlogIf(map);
        if(blogs!=null){
            for (Blog blog : blogs) {
                System.out.println(blog);
            }
        }
        sqlsession.close();
    }
    @Test
    public void testUpdate(){
        SqlSession sqlsession=MyBatisUtil.getSqlSession();
        BlogMapper mapper = sqlsession.getMapper(BlogMapper.class);
        HashMap<String, Object> map = new HashMap<>();
        map.put("title","Java如此不简单");
        //map.put("author","jjm");
        map.put("id","ebb8857e7b5047fcb565f334c0cf6853");
        mapper.updateBlogSet(map);
        sqlsession.commit();
        sqlsession.close();
    }
    @Test
    public void testForeach(){
        SqlSession sqlSession=MyBatisUtil.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

        HashMap<String, Object> map = new HashMap<>();
        ArrayList<String> ids = new ArrayList<>();

        ids.add("ebb8857e7b5047fcb565f334c0cf6853");
        ids.add("96e17b24dc3f48baa6ed533a215ced09");
        map.put("ids",ids);

        List<Blog> blogs = mapper.queryBlogForeach(map);
        if(blogs!=null){
            for (Blog blog : blogs) {
                System.out.println(blog);
            }
        }
        sqlSession.close();
    }
    public static void main(String[] args) {
        SqlSession session = MyBatisUtil.getSqlSession();
        BlogMapper mapper = session.getMapper(BlogMapper.class);

        Blog blog = new Blog();
        blog.setId(UidUtil.getUID());
        blog.setTitle("Mybatis如此简单");
        blog.setAuthor("狂神说");
        blog.setCreate_time(new Date());
        blog.setViews(9999);

        mapper.addBlog(blog);

        blog.setId(UidUtil.getUID());
        blog.setTitle("Java如此简单");
        mapper.addBlog(blog);

        blog.setId(UidUtil.getUID());
        blog.setTitle("Spring如此简单");
        mapper.addBlog(blog);

        blog.setId(UidUtil.getUID());
        blog.setTitle("微服务如此简单");
        mapper.addBlog(blog);

        session.commit();
        session.close();
    }
}
