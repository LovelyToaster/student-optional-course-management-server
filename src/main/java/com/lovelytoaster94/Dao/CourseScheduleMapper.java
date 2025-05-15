package com.lovelytoaster94.Dao;

import com.lovelytoaster94.Pojo.CourseSchedule;

import java.util.List;

public interface CourseScheduleMapper {
    List<CourseSchedule> allCourseScheduleInfo();

    boolean addCourseScheduleInfo(CourseSchedule courseSchedule);

    List<CourseSchedule> searchCourseScheduleInfo(CourseSchedule courseSchedule);

    boolean deleteCourseScheduleInfo(String courseScheduleNo);

}
