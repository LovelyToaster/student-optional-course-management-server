package com.lovelytoaster94.Service;

import com.lovelytoaster94.Pojo.CourseSchedule;

import java.util.List;

public interface CourseScheduleService {
    List<CourseSchedule> allCourseScheduleInfo();

    boolean addCourseScheduleInfo(CourseSchedule courseSchedule);

    List<CourseSchedule> searchCourseScheduleInfo(CourseSchedule courseSchedule);

    boolean deleteCourseScheduleInfo(String courseScheduleNo);
}
