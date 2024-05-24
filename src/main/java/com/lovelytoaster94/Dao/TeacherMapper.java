package com.lovelytoaster94.Dao;

import com.lovelytoaster94.Pojo.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherMapper {
    List<Teacher> allTeacherInfo();

    boolean modifyTeacherInfo(Teacher teacher);

    boolean deleteTeacherInfo(@Param("teacherNo") int teacherNo);
}
