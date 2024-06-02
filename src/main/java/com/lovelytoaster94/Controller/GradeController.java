package com.lovelytoaster94.Controller;

import com.lovelytoaster94.Pojo.Grade;
import com.lovelytoaster94.Service.GradeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/grade")
public class GradeController {
    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Result allGradeInfo() {
        return new Result(Code.SEARCH_SUCCESS, "查询成功", gradeService.allGradeInfo());
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public Result modifyStudentInfo(Grade grade) {
        boolean data = gradeService.modifyGradeInfo(grade);
        if (data) {
            return new Result(Code.MODIFY_SUCCESS, "修改成功");
        } else {
            return new Result(Code.MODIFY_FAILED, "修改失败");
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteStudentInfo(@RequestParam("no") String no) {
        boolean data = gradeService.deleteGradeInfo(no);
        if (data) {
            return new Result(Code.DELETE_SUCCESS, "删除成功");
        } else {
            return new Result(Code.DELETE_FAILED, "删除失败");
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public Result searchTeacherInfo(Grade grade) {
        List<Grade> data = gradeService.searchGradeInfo(grade);
        if (!data.isEmpty()) {
            return new Result(Code.SEARCH_SUCCESS, "查询成功" + "共" + data.size() + "条数据", data);
        } else {
            return new Result(Code.SEARCH_FAILED, "查询失败");
        }
    }
}
