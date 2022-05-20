package com.jjm.dao;

import com.jjm.pojo.Student;

import java.util.List;

/**
 * @author jjm
 * @version 1.0
 */
public interface StudentMapper {
    //查询所有学生及对应的老师信息
    List<Student> getAll();
    List<Student> getStudents2();
}
