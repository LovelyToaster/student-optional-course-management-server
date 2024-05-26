package com.lovelytoaster94.Dao;

import com.lovelytoaster94.Pojo.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentMapper {
    List<Student> allStudentInfo();

    boolean modifyStudentInfo(Student student);

    boolean deleteStudentInfo(@Param("studentNo") String studentNo);

    List<Student> searchStudentInfo(Student student);
}
