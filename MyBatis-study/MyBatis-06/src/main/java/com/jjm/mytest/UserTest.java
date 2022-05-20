package com.jjm.mytest;

import com.jjm.dao.UserMapper;
import com.jjm.pojo.User;
import com.jjm.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import sun.util.resources.LocaleData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author jjm
 * @version 1.0
 */
public class UserTest {
    @Test
    public void testGetLoginUser(){
        SqlSession sqlsession = MyBatisUtil.getSession();
        UserMapper mapper = sqlsession.getMapper(UserMapper.class);
        System.out.println(mapper.getLoginUser("zhaoyan"));
        sqlsession.close();
    }
    @Test
    public void testGetUserList(){
        SqlSession sqlsession = MyBatisUtil.getSession();
        UserMapper mapper = sqlsession.getMapper(UserMapper.class);
        HashMap<String, Object> map = new HashMap<>();
        map.put("currentPageNo",0);
        map.put("pageSize",3);
        String userName="张";    //模糊匹配
        map.put("userName","%"+userName+"%");
        List<User> users=mapper.getUserList(map);
        if(users!=null){
            for (User user : users) {
                System.out.println(user);
            }
        }
        sqlsession.close();
    }
    @Test
    public void testQueryUserById(){
        SqlSession sqlSession = MyBatisUtil.getSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        System.out.println(mapper.queryUserById(5));
        sqlSession.close();
    }
    @Test
    public void testGetUserCounts(){
        SqlSession sqlSession = MyBatisUtil.getSession();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            HashMap<String, Object> map = new HashMap<>();
            map.put("userRoleName","%普%");
            System.out.println(mapper.getUserCounts(map));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }
    @Test
    public void testAddUser(){
        SqlSession sqlSession = MyBatisUtil.getSession();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User user = new User();
            user.setAddress("圣诞节多福多寿发生的纠纷");
            user.setGender(new Integer(2));
            user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse("2001-01-01"));
            user.setCreatedBy(1);
            user.setCreationDate(new Date());
            user.setUserCode("kuga");
            user.setPhone("110212");
            user.setUserPassword("1234566");
            user.setUserRole(new Integer(1));
            user.setUserName("空我");
            int i = mapper.addUser(user);
            if(i==1){
                sqlSession.commit();
            }
            else {
                System.out.println("插入失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }
}
