package com.jjm;

import com.jjm.dao.StudentMapper;
import com.jjm.dao.TeacherMapper;
import com.jjm.pojo.Student;
import com.jjm.pojo.Teacher;
import com.jjm.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author jjm
 * @version 1.0
 */
public class MyTest {
    @Test
    public void selectOne(){
        SqlSession sqlSession = MyBatisUtil.getSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        Teacher teacher = mapper.selectTeacher("李老师");
        if(teacher!=null){
            System.out.println(teacher);
        }
        else {
            System.out.println("失败！");
        }
        sqlSession.close();
    }
    @Test
    public void getAllStudent(){
        SqlSession sqlSession = MyBatisUtil.getSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        //List<Student> students = mapper.getAll();
        List<Student> students = mapper.getStudents2();
        if(students!=null) {
            for (Student student : students) {
                System.out.println("{" + student.getId() + "," + student.getName() + "," + student.getTeacher().getName());
            }
        }
        sqlSession.close();
    }
    @Test
    public void getTeacherStu(){
        SqlSession sqlSession = MyBatisUtil.getSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        Teacher teacher = mapper.getTeacher1(2);
        //Teacher teacher= mapper.getTeacher(2);
        if(teacher!=null){
            System.out.println(teacher);
        }
        sqlSession.close();
    }
    public static void main(String[] args) {

    }
}
