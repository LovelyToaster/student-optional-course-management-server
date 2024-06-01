package com.lovelytoaster94.Service;

import com.lovelytoaster94.Dao.CourseMapper;
import com.lovelytoaster94.Pojo.Course;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseMapper courseMapper;

    public CourseServiceImpl(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    public List<Course> allCourseInfo() {
        return courseMapper.allCourseInfo();
    }

    public boolean modifyCourseInfo(Course course) {
        return courseMapper.modifyCourseInfo(course);
    }

    public boolean deleteCourseInfo(String courseNo) {
        return courseMapper.deleteCourseInfo(courseNo);
    }

    public List<Course> searchCourseInfo(Course course) {
        return courseMapper.searchCourseInfo(course);
    }
}
