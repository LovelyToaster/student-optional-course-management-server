package com.lovelytoaster94.Service;

import com.lovelytoaster94.Pojo.Course;

import java.util.List;

public interface CourseService {
    List<Course> allCourseInfo();

    boolean modifyCourseInfo(Course course);

    boolean deleteCourseInfo(String courseNo);

    List<Course> searchCourseInfo(Course course);
}
