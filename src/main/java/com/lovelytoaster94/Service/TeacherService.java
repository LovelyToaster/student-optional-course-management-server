package com.lovelytoaster94.Service;

import com.lovelytoaster94.Pojo.Teacher;

import java.util.List;

public interface TeacherService {
    List<Teacher> allTeacherInfo();

    boolean modifyTeacherInfo(Teacher teacher);

    boolean deleteTeacherInfo(String teacherNo);

    List<Teacher> searchTeacherInfo(Teacher teacher);

    boolean addTeacherInfo(Teacher teacher);
}
