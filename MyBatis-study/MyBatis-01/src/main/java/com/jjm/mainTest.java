package com.jjm;

import com.jjm.dao.UserMapper;
import com.jjm.pojo.User;
import com.jjm.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import sun.security.provider.MD5;

import java.util.List;

/**
 * @author jjm
 * @version 1.0
 */
public class mainTest {
    @Test
    public void addTest() {
        SqlSession session = MyBatisUtil.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        if (mapper.addUser(new User("关羽", "45678")) > 0)
            System.out.println("插入成功");
        session.commit();
        session.close();
    }
    @Test
    public void update(){
        SqlSession session = MyBatisUtil.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        if(mapper.updateUser(2)>0)
            System.out.println("修改成功");
        session.commit();
        session.close();
    }
    @Test
    public void delete(){
        SqlSession session = MyBatisUtil.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        if(mapper.deleteUser("李白")>0){
            System.out.println("删除成功！");
        }
        session.commit();
        session.close();
    }
    @Test
    public void log(){
        SqlSession session = MyBatisUtil.getSession();

        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user=mapper.selectUserByNP("关羽","45678");
            if(user!=null){
                System.out.println(user);
            }
            else {
                System.out.println("失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }


    }
    public static void main(String[] args) {
        SqlSession session = MyBatisUtil.getSession();
        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            List<User> allUser = mapper.getAllUser();
            for (User u : allUser) {
                System.out.println(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

}
