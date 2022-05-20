package com.jjm.dao;

import com.jjm.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @author jjm
 * @version 1.0
 * 9
 */
public interface UserMapper {
    //得到要登录的用户信息
    User getLoginUser(@Param("userCode") String userCode);

    //根据条件查询获取用户列表(userName、userRole、currentPageNo、pageSize)
    List<User> getUserList(HashMap map);

    //根据id、newPassword修改密码
    int updatePassword(HashMap map);

    //根据用户名 或 角色 查询用户总数
    int getUserCounts(HashMap map);

    //用户管理模块添加用户
    int addUser(User user);

    //删除用户
    int deleteUser(@Param("id") int id);

    //根据id查询用户
    User queryUserById(@Param("id") int id);


    //修改用户信息
    int modifyUser(User user);
    int updateUser(HashMap map);
}
