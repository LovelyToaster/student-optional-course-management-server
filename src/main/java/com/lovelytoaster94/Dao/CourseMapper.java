package com.lovelytoaster94.Dao;

import com.lovelytoaster94.Pojo.Course;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseMapper {
    List<Course> allCourseInfo();

    boolean modifyCourseInfo(Course course);

    boolean deleteCourseInfo(@Param("courseNo") String courseNo);

    List<Course> searchCourseInfo(Course course);

    boolean addCourseInfo(Course course);
}
