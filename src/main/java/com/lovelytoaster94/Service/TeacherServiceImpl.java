package com.lovelytoaster94.Service;

import com.lovelytoaster94.Dao.TeacherMapper;
import com.lovelytoaster94.Pojo.Teacher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherMapper teacherMapper;

    public TeacherServiceImpl(TeacherMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
    }

    public List<Teacher> allTeacherInfo() {
        return teacherMapper.allTeacherInfo();
    }

    public boolean modifyTeacherInfo(Teacher teacher) {
        return teacherMapper.modifyTeacherInfo(teacher);
    }

    public boolean deleteTeacherInfo(int teacherNo) {
        return teacherMapper.deleteTeacherInfo(teacherNo);
    }

    public List<Teacher> searchTeacherInfo(Teacher teacher) {
        return teacherMapper.searchTeacherInfo(teacher);
    }
}
