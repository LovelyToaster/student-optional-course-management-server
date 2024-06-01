package com.lovelytoaster94.Controller;

import com.lovelytoaster94.Pojo.Course;
import com.lovelytoaster94.Pojo.Student;
import com.lovelytoaster94.Service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/course")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Result allCourseInfo() {
        return new Result(Code.SEARCH_SUCCESS, "查询完成", courseService.allCourseInfo());
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public Result modifyStudentInfo(Course course) {
        boolean data = courseService.modifyCourseInfo(course);
        if (data) {
            return new Result(Code.MODIFY_SUCCESS, "修改成功");
        } else {
            return new Result(Code.MODIFY_FAILED, "修改失败");
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteStudentInfo(@RequestParam("courseNo") String courseNo) {
        boolean data = courseService.deleteCourseInfo(courseNo);
        if (data) {
            return new Result(Code.DELETE_SUCCESS, "删除成功");
        } else {
            return new Result(Code.DELETE_FAILED, "删除失败");
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public Result searchTeacherInfo(Course course) {
        List<Course> data = courseService.searchCourseInfo(course);
        if (!data.isEmpty()) {
            return new Result(Code.SEARCH_SUCCESS, "查询成功" + "共" + data.size() + "条数据", data);
        } else {
            return new Result(Code.SEARCH_FAILED, "查询失败");
        }
    }
}
