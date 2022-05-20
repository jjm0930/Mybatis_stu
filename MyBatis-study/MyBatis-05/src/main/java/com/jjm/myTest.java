package com.jjm;

import com.jjm.dao.UserMapper;
import com.jjm.pojo.User;
import com.jjm.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 * @author jjm
 * @version 1.0
 */
public class myTest {
    public static void main(String[] args) {
        SqlSession sqlSession = MyBatisUtil.getSession();
        SqlSession sqlSession2 = MyBatisUtil.getSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user=mapper.querySingle(2);
        System.out.println(user);
        sqlSession.close();


        UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);
        User user1=mapper2.querySingle(2);
        System.out.println(user1);
        System.out.println(user1==user);
        sqlSession2.close();

    }
}
