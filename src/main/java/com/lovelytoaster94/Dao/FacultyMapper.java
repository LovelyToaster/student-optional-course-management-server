package com.lovelytoaster94.Dao;

import com.lovelytoaster94.Pojo.Faculty;

import java.util.List;

public interface FacultyMapper {
    List<Faculty> allFacultyInfo();

    boolean modifyFacultyInfo(Faculty faculty);

    boolean deleteFacultyInfo(String facultyNo);

    List<Faculty> searchFacultyInfo(Faculty faculty);

    boolean addFacultyInfo(Faculty faculty);


}
