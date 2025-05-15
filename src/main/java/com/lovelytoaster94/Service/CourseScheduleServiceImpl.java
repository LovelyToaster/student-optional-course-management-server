package com.lovelytoaster94.Service;

import com.lovelytoaster94.Dao.CourseScheduleMapper;
import org.springframework.stereotype.Service;
import com.lovelytoaster94.Pojo.CourseSchedule;

import java.util.List;

@Service
public class CourseScheduleServiceImpl implements CourseScheduleService {

    private final CourseScheduleMapper courseScheduleMapper;

    public CourseScheduleServiceImpl(CourseScheduleMapper courseScheduleMapper) {
        this.courseScheduleMapper = courseScheduleMapper;
    }

    public List<CourseSchedule> allCourseScheduleInfo() {
        return courseScheduleMapper.allCourseScheduleInfo();
    }

    public boolean addCourseScheduleInfo(CourseSchedule courseSchedule) {
        return courseScheduleMapper.addCourseScheduleInfo(courseSchedule);
    }

    public List<CourseSchedule> searchCourseScheduleInfo(CourseSchedule courseSchedule) {
        return courseScheduleMapper.searchCourseScheduleInfo(courseSchedule);
    }

    public boolean deleteCourseScheduleInfo(String courseScheduleNo) {
        return courseScheduleMapper.deleteCourseScheduleInfo(courseScheduleNo);
    }

}
