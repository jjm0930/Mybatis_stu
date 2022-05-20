package com.jjm.dao;

import com.jjm.pojo.Student;
import com.jjm.pojo.Teacher;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author jjm
 * @version 1.0
 */
public interface TeacherMapper {
    Teacher selectTeacher(@Param("name") String name);

    Teacher getTeacher(@Param("id") int id);
    Teacher getTeacher1(@Param("id") int id);

}
