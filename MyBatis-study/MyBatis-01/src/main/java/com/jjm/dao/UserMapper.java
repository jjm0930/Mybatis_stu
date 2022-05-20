package com.jjm.dao;

import com.jjm.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jjm
 * @version 1.0
 */
public interface UserMapper {
    List<User> getAllUser();
    User selectUserByNP(@Param("username") String username, @Param("pwd") String pwd);
    int addUser(User user);
    int updateUser(int id);
    int deleteUser(String name);

}

