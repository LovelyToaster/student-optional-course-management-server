package com.lovelytoaster94.Service;

import com.lovelytoaster94.Pojo.Faculty;

import java.util.List;

public interface FacultyService {
    List<Faculty> allFacultyInfo();

    boolean modifyFacultyInfo(Faculty faculty);

    boolean deleteFacultyInfo(String facultyNo);

    List<Faculty> searchFacultyInfo(Faculty faculty);

    boolean addFacultyInfo(Faculty faculty);
}
