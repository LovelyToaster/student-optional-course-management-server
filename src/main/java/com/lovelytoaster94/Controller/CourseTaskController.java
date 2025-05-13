package com.lovelytoaster94.Controller;

import com.lovelytoaster94.Pojo.CourseTask;
import com.lovelytoaster94.Service.CourseTaskService;
import com.lovelytoaster94.Until.AddVerify;
import com.lovelytoaster94.Until.Code;
import com.lovelytoaster94.Until.ManagementResultInfo;
import com.lovelytoaster94.Until.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/courseTask")
public class CourseTaskController {
    private final CourseTaskService courseTaskService;
    private final ManagementResultInfo managementResultInfo = new ManagementResultInfo();

    public CourseTaskController(CourseTaskService courseTaskService) {
        this.courseTaskService = courseTaskService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Result addCourseTaskInfo(CourseTask courseTask) {
        AddVerify addVerify = new AddVerify();
        if (!addVerify.verify(courseTask)) {
            return new Result(Code.ADD_FAILED, "添加失败，请检查数据是否填写正确");
        }
        boolean verify = courseTaskService.addCourseTaskInfo(courseTask);
        return managementResultInfo.addInfo(verify, courseTask);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Result allCourseTaskInfo() {
        List<CourseTask> courseTaskList = courseTaskService.allCourseTaskInfo();
        return managementResultInfo.allInfo(courseTaskList);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteCourseTaskInfo(@RequestParam("courseTaskNo") String courseTaskNo) {
        boolean verify = courseTaskService.deleteCourseTaskInfo(courseTaskNo);
        return managementResultInfo.deleteInfo(verify);
    }
}
