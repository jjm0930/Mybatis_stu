package com.jjm.dao;

import com.jjm.pojo.Blog;

import java.util.HashMap;
import java.util.List;

/**
 * @author jjm
 * @version 1.0
 */
public interface BlogMapper {
    int addBlog(Blog blog);

    List<Blog> queryBlogIf(HashMap<String,Object> map);

    int updateBlogSet(HashMap<String,Object> map);

    List<Blog> queryBlogForeach(HashMap map);

}
