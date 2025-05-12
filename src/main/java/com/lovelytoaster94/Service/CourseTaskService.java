package com.lovelytoaster94.Service;

import com.lovelytoaster94.Pojo.CourseTask;

import java.util.List;

public interface CourseTaskService {
    boolean addCourseTaskInfo(CourseTask courseTask);

    List<CourseTask> allCourseTaskInfo();

    List<CourseTask> searchCourseTaskInfo(CourseTask courseTask);
}
