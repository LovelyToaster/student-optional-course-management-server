package com.lovelytoaster94.Service;

import com.lovelytoaster94.Dao.FacultyMapper;
import com.lovelytoaster94.Pojo.Faculty;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyMapper facultyMapper;

    public FacultyServiceImpl(FacultyMapper facultyMapper) {
        this.facultyMapper = facultyMapper;
    }

    public List<Faculty> allFacultyInfo() {
        return facultyMapper.allFacultyInfo();
    }

    public boolean modifyFacultyInfo(Faculty faculty) {
        return facultyMapper.modifyFacultyInfo(faculty);
    }

    public boolean deleteFacultyInfo(String facultyNo) {
        return facultyMapper.deleteFacultyInfo(facultyNo);
    }

    public List<Faculty> searchFacultyInfo(Faculty faculty) {
        return facultyMapper.searchFacultyInfo(faculty);
    }

    public boolean addFacultyInfo(Faculty faculty) {
        return facultyMapper.addFacultyInfo(faculty);
    }
}
