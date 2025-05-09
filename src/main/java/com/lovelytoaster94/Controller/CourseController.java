package com.lovelytoaster94.Controller;

import com.alibaba.fastjson2.JSONObject;
import com.lovelytoaster94.Pojo.Course;
import com.lovelytoaster94.Service.CourseService;
import com.lovelytoaster94.Until.Code;
import com.lovelytoaster94.Until.Result;
import com.lovelytoaster94.Until.ManagementResultInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(course));
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            if (entry.getValue() == null || entry.getValue().equals(0)) {
                return new Result(Code.ADD_FAILED, "添加失败,请输入数据");
            }
        }
        boolean verify = courseService.addCourseInfo(course);
        return managementResultInfo.addInfo(verify, course);
    }
}
