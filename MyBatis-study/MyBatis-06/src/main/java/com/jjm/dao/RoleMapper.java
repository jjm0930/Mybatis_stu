package com.jjm.dao;

import com.jjm.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @author jjm
 * @version 1.0
 * 8
 */
public interface RoleMapper {
    //获取角色列表
    List<Role> getRoleList();

    //增加角色信息
    int addRole(Role role);

    //增加角色信息  使用Map作为参数传入
    int addRoleMap(HashMap<String, Object> map);

    //通过Id删除角色
    int deleteRoleById(@Param("id") int delId);

    //修改角色信息
    int modifyRole(Role role);
    //修改角色信息 使用Map作为参数传入
    int modifyRoleMap(HashMap<String, Object> map);

    //通过Id获取role
    Role getRoleById(@Param("id") int id);

    //根据roleCode，进行角色编码的唯一性验证
    int roleCodeIsExist(@Param("roleCode") String roleCode);
}
