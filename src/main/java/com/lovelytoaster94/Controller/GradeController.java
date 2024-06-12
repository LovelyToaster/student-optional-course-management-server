package com.lovelytoaster94.Controller;

import com.lovelytoaster94.Pojo.Grade;
import com.lovelytoaster94.Service.GradeService;
import com.lovelytoaster94.Until.Code;
import com.lovelytoaster94.Until.Result;
import com.lovelytoaster94.Until.ManagementResultInfo;
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
    private final ManagementResultInfo managementResultInfo = new ManagementResultInfo();

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Result allGradeInfo() {
        return managementResultInfo.allInfo(gradeService.allGradeInfo());
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public Result modifyGradeInfo(Grade grade) {
        boolean data = gradeService.modifyGradeInfo(grade);
        return managementResultInfo.modifyInfo(data);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteGradeInfo(@RequestParam("no") String no) {
        boolean data = gradeService.deleteGradeInfo(no);
        return managementResultInfo.deleteInfo(data);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public Result searchGradeInfo(Grade grade) {
        List<Grade> data = gradeService.searchGradeInfo(grade);
        return managementResultInfo.searchInfo(data);
    }

}
