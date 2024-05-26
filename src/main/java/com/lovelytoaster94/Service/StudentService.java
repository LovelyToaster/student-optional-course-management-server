package com.lovelytoaster94.Service;

import com.lovelytoaster94.Pojo.Student;

import java.util.List;

public interface StudentService {
    List<Student> allStudentInfo();

    boolean modifyStudentInfo(Student student);

    boolean deleteStudentInfo(String studentNo);

    List<Student> searchStudentInfo(Student student);
}
