package com.lovelytoaster94.Controller;

import com.lovelytoaster94.Pojo.Course;
import com.lovelytoaster94.Pojo.CourseSchedule;
import com.lovelytoaster94.Service.CourseScheduleService;
import com.lovelytoaster94.Service.CourseService;
import com.lovelytoaster94.Until.AddVerify;
import com.lovelytoaster94.Until.Code;
import com.lovelytoaster94.Until.ManagementResultInfo;
import com.lovelytoaster94.Until.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/courseSchedule")
public class CourseScheduleController {
    private final CourseScheduleService courseScheduleService;
    private final CourseService courseService;
    private final ManagementResultInfo managementResultInfo = new ManagementResultInfo();

    public CourseScheduleController(CourseScheduleService courseScheduleService, CourseService courseService) {
        this.courseScheduleService = courseScheduleService;
        this.courseService = courseService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Object allCourseScheduleInfo() {
        List<CourseSchedule> courseScheduleList = courseScheduleService.allCourseScheduleInfo();
        return managementResultInfo.allInfo(courseScheduleList);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object addCourseScheduleInfo(CourseSchedule courseSchedule) {
        AddVerify addVerify = new AddVerify();
        if (!addVerify.verify(courseSchedule)) {
            return new Result(Code.ADD_FAILED, "添加失败，请检查数据是否填写正确");
        }

        Course courseQuery = new Course();
        courseQuery.setCourseNo(courseSchedule.getCourseNo());
        List<Course> matchedCourses = courseService.searchCourseInfo(courseQuery); // 假设你有 courseService

        if (matchedCourses.isEmpty()) {
            return new Result(Code.ADD_FAILED, "添加失败：课程信息不存在，无法确定授课教师");
        }

        String teacherNo = matchedCourses.get(0).getTeacherNo();

        Course teacherQuery = new Course();
        teacherQuery.setTeacherNo(teacherNo);
        List<Course> teacherCourses = courseService.searchCourseInfo(teacherQuery);
        Set<String> teacherCourseNos = teacherCourses.stream()
                .map(Course::getCourseNo)
                .collect(Collectors.toSet());

        List<CourseSchedule> allSchedules = courseScheduleService.allCourseScheduleInfo();

        String newStart = courseSchedule.getStartTime();
        String newEnd = courseSchedule.getEndTime();
        int newWeekStart = Integer.parseInt(newStart.split("-")[1]);
        int newWeekEnd = Integer.parseInt(newEnd.split("-")[1]);

        for (CourseSchedule schedule : allSchedules) {
            int existStart = Integer.parseInt(schedule.getStartTime().split("-")[1]);
            int existEnd = Integer.parseInt(schedule.getEndTime().split("-")[1]);

            boolean timeConflict = !(newWeekEnd < existStart || newWeekStart > existEnd);

            if (timeConflict &&
                    teacherCourseNos.contains(schedule.getCourseNo()) &&
                    schedule.getWeek().equals(courseSchedule.getWeek()) &&
                    schedule.getTime().equals(courseSchedule.getTime()) &&
                    !schedule.getCourseNo().equals(courseSchedule.getCourseNo())
            ) {
                return new Result(Code.ADD_FAILED, "添加失败：该教师在此时间段已有其他课程安排");
            }

            if (timeConflict &&
                    schedule.getClassNo().equals(courseSchedule.getClassNo()) &&
                    schedule.getWeek().equals(courseSchedule.getWeek()) &&
                    schedule.getTime().equals(courseSchedule.getTime())) {
                return new Result(Code.ADD_FAILED, "添加失败：该班级在此时间段已有课程安排");
            }

            if (timeConflict &&
                    schedule.getPlace().equals(courseSchedule.getPlace()) &&
                    schedule.getWeek().equals(courseSchedule.getWeek()) &&
                    schedule.getTime().equals(courseSchedule.getTime()) &&
                    !schedule.getCourseNo().equals(courseSchedule.getCourseNo())) {
                return new Result(Code.ADD_FAILED, "添加失败：该教室在此时间段已被其他课程使用");
            }
        }

        boolean verify = courseScheduleService.addCourseScheduleInfo(courseSchedule);
        Object data = null;
        if (verify) {
            data = courseScheduleService.searchCourseScheduleInfo(courseSchedule);
        }
        return managementResultInfo.addInfo(verify, data);
    }


    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public Object searchCourseScheduleInfo(@RequestParam(value = "week", required = false) String week, @RequestParam(value = "classNo", required = false) String classNo, @RequestParam(value = "teacherNo", required = false) String teacherNo) {
        List<CourseSchedule> courseScheduleList;
        if (classNo != null && !classNo.isEmpty()) {
            CourseSchedule courseSchedule = new CourseSchedule();
            courseSchedule.setClassNo(classNo);
            courseScheduleList = courseScheduleService.searchCourseScheduleInfo(courseSchedule);
        } else if (teacherNo != null && !teacherNo.isEmpty()) {
            CourseSchedule courseSchedule = new CourseSchedule();
            courseSchedule.setTeacherNo(teacherNo);
            courseScheduleList = courseScheduleService.searchCourseScheduleInfo(courseSchedule);
        } else {
            courseScheduleList = courseScheduleService.allCourseScheduleInfo();
        }
        if (week != null && !week.isEmpty()) {
            String[] parts = week.split("-");
            if (parts.length == 2) {
                int targetYear = Integer.parseInt(parts[0]);
                int targetWeek = Integer.parseInt(parts[1]);

                List<CourseSchedule> filteredList = new ArrayList<>();
                for (CourseSchedule course : courseScheduleList) {
                    String[] startParts = course.getStartTime().split("-");
                    String[] endParts = course.getEndTime().split("-");

                    int startYear = Integer.parseInt(startParts[0]);
                    int startWeek = Integer.parseInt(startParts[1]);
                    int endYear = Integer.parseInt(endParts[0]);
                    int endWeek = Integer.parseInt(endParts[1]);

                    boolean inRange = false;
                    if (targetYear > startYear && targetYear < endYear) {
                        inRange = true;
                    } else if (targetYear == startYear && targetYear == endYear) {
                        inRange = targetWeek >= startWeek && targetWeek <= endWeek;
                    } else if (targetYear == startYear) {
                        inRange = targetWeek >= startWeek;
                    } else if (targetYear == endYear) {
                        inRange = targetWeek <= endWeek;
                    }

                    if (inRange) {
                        filteredList.add(course);
                    }
                }
                courseScheduleList = filteredList;
            }
        }

        return managementResultInfo.allInfo(courseScheduleList);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteCourseScheduleInfo(@RequestParam("courseScheduleNo") String courseScheduleNo) {
        boolean verify = courseScheduleService.deleteCourseScheduleInfo(courseScheduleNo);
        return managementResultInfo.deleteInfo(verify);
    }
}
