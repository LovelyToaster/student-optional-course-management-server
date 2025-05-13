package com.lovelytoaster94.Service;

import com.lovelytoaster94.Dao.CourseTaskMapper;
import com.lovelytoaster94.Pojo.CourseTask;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseTaskServiceImpl implements CourseTaskService {

    private final CourseTaskMapper courseTaskMapper;

    public CourseTaskServiceImpl(CourseTaskMapper courseTaskMapper) {
        this.courseTaskMapper = courseTaskMapper;
    }

    public boolean addCourseTaskInfo(CourseTask courseTask) {
        return courseTaskMapper.addCourseTaskInfo(courseTask);
    }

    public List<CourseTask> allCourseTaskInfo() {
        return courseTaskMapper.allCourseTaskInfo();
    }

    public List<CourseTask> searchCourseTaskInfo(CourseTask courseTask) {
        return courseTaskMapper.searchCourseTaskInfo(courseTask);
    }

    public boolean deleteCourseTaskInfo(String courseTaskNo) {
        return courseTaskMapper.deleteCourseTaskInfo(courseTaskNo);
    }
}
