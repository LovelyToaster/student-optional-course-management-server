package com.lovelytoaster94.Service;

import com.lovelytoaster94.Dao.StudentMapper;
import com.lovelytoaster94.Pojo.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentMapper studentMapper;

    public StudentServiceImpl(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }


    public List<Student> allStudentInfo() {
        return studentMapper.allStudentInfo();
    }

    public boolean modifyStudentInfo(Student student) {
        return studentMapper.modifyStudentInfo(student);
    }

    public boolean deleteStudentInfo(String studentNo) {
        return studentMapper.deleteStudentInfo(studentNo);
    }

    public List<Student> searchStudentInfo(Student student) {
        return studentMapper.searchStudentInfo(student);
    }
}
