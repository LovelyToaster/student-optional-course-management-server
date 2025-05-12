package com.lovelytoaster94.Dao;

import com.lovelytoaster94.Pojo.CourseTask;

import java.util.List;

public interface CourseTaskMapper {
    boolean addCourseTaskInfo(CourseTask courseTask);

    List<CourseTask> allCourseTaskInfo();

    List<CourseTask> searchCourseTaskInfo(CourseTask courseTask);
}
