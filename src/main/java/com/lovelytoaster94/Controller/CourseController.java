package com.lovelytoaster94.Controller;

import com.lovelytoaster94.Pojo.Course;
import com.lovelytoaster94.Service.CourseService;
import com.lovelytoaster94.Until.AddVerify;
import com.lovelytoaster94.Until.Code;
import com.lovelytoaster94.Until.Result;
import com.lovelytoaster94.Until.ManagementResultInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/course")
public class CourseController {
    private final CourseService courseService;
    private final ManagementResultInfo managementResultInfo = new ManagementResultInfo();

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Result allCourseInfo() {
        return managementResultInfo.allInfo(courseService.allCourseInfo());
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public Result modifyCourseInfo(Course course) {
        boolean data = courseService.modifyCourseInfo(course);
        if (data) {
            return new Result(Code.MODIFY_SUCCESS, "修改成功");
        } else {
            return new Result(Code.MODIFY_FAILED, "修改失败");
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteCourseInfo(@RequestParam("courseNo") String courseNo) {
        boolean data = courseService.deleteCourseInfo(courseNo);
        if (data) {
            return new Result(Code.DELETE_SUCCESS, "删除成功");
        } else {
            return new Result(Code.DELETE_FAILED, "删除失败");
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public Result searchCourseInfo(Course course) {
        List<Course> data = courseService.searchCourseInfo(course);
        return managementResultInfo.searchInfo(data);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Result addCourseInfo(Course course) {
        AddVerify  addVerify = new AddVerify();
        if (!addVerify.verify(course)) {
            return new Result(Code.ADD_FAILED, "添加失败，请检查数据是否填写正确");
        }
        boolean verify = courseService.addCourseInfo(course);
        Object data = null;
        if (verify) {
            data = courseService.searchCourseInfo(course);
        }
        return managementResultInfo.addInfo(verify, data);
    }

    @RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
    @ResponseBody
    public Result batchAddCourseInfo(@RequestBody List<Course> courseList) {
        List<Object>  data = new ArrayList<>();
        for (Course course : courseList) {
            boolean verify = courseService.addCourseInfo(course);
            if (verify) {
                data.add(courseService.searchCourseInfo(course));
            }
        }
        if (data.size() == courseList.size()) {
            return new Result(Code.ADD_SUCCESS, "添加成功，共成功添加" + data.size() + "条数据", data);
        }
        return new Result(Code.ADD_FAILED, "添加失败，共成功添加" + data.size() + "条数据", data);
    }

    @RequestMapping(value = "/batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Result batchDelCourseInfo(@RequestBody List<Course> courseList) {
        List<Object>  data = new ArrayList<>();
        for (Course course : courseList) {
            boolean verify = courseService.deleteCourseInfo(course.getCourseNo());
            if (verify) {
                data.add(course.getCourseNo());
            }
        }
        if (data.size() == courseList.size()) {
            return new Result(Code.DELETE_SUCCESS, "删除成功，共成功删除" + data.size() + "条数据", data);
        }
        return new Result(Code.DELETE_FAILED, "删除失败，共成功删除" + data.size() + "条数据", data);
    }
}
