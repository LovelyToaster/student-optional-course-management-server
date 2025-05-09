package com.lovelytoaster94.Controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.lovelytoaster94.Pojo.GPA;
import com.lovelytoaster94.Pojo.Grade;
import com.lovelytoaster94.Service.GradeService;
import com.lovelytoaster94.Until.AddVerify;
import com.lovelytoaster94.Until.Code;
import com.lovelytoaster94.Until.Result;
import com.lovelytoaster94.Until.ManagementResultInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
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

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Result addGradeInfo(Grade grade) {
        AddVerify addVerify = new AddVerify();
        if (!addVerify.verify(grade)) {
            return new Result(Code.ADD_FAILED, "添加失败，请检查数据是否填写正确");
        }
        boolean verify = gradeService.addGradeInfo(grade);
        return managementResultInfo.addInfo(verify, grade);
    }

    @RequestMapping(value = "/getGPA", method = RequestMethod.GET)
    @ResponseBody
    public Result getGPA(@RequestParam("studentNo") String studentNo) {
        Grade grade = new Grade();
        grade.setStudentNo(studentNo);
        List<Grade> data = gradeService.searchGradeInfo(grade);
        List<String> termList = new ArrayList<>();
        List<GPA> gpaList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        for (Grade item : data) {
            if (!termList.contains(item.getTerm())) {
                termList.add(item.getTerm());
            }
        }
        double averageGPA = 0;
        for (String item : termList) {
            double gpa = 0;
            double credit = 0;
            for (Grade gradeItem : data) {
                if (gradeItem.getTerm().equals(item)) {
                    gpa += gradeItem.getCoursePoint() * gradeItem.getCourseGrade();
                    credit += gradeItem.getCourseGrade();
                }
            }
            gpa = gpa / credit;
            averageGPA += gpa;
            GPA gpaItem = new GPA();
            gpaItem.setTerm(item);
            gpaItem.setGpa(Double.parseDouble(String.format("%.2f", gpa)));
            gpaItem.setCourseGrade((int) credit);
            gpaList.add(gpaItem);
        }
        averageGPA = averageGPA / termList.size();
        jsonObject.put("averageGPA", Double.parseDouble(String.format("%.2f", averageGPA)));
        jsonObject.put("GPA", gpaList);
        return new Result(Code.SEARCH_SUCCESS, "查询成功", jsonObject);
    }

    @RequestMapping(value = "/getGradeStatistics", method = RequestMethod.GET)
    @ResponseBody
    public Result getGradeStatistics(@RequestParam("studentNo") String studentNo) {
        Grade grade = new Grade();
        grade.setStudentNo(studentNo);
        List<Grade> data = gradeService.searchGradeInfo(grade);
        JSONArray jsonArray = new JSONArray();
        JSONObject gradeStatistics;
        String[] gradeName = {"90-100", "80-90", "70-80", "60-70", "不及格"};
        int[] gradeCount = new int[5];
        for (Grade item : data) {
            if (item.getGrade() >= 90 && item.getGrade() <= 100) {
                gradeCount[0]++;
            }
            if (item.getGrade() >= 80 && item.getGrade() <= 89) {
                gradeCount[1]++;
            }
            if (item.getGrade() >= 70 && item.getGrade() <= 79) {
                gradeCount[2]++;
            }
            if (item.getGrade() >= 60 && item.getGrade() <= 69) {
                gradeCount[3]++;
            }
            if (item.getGrade() >= 0 && item.getGrade() <= 59) {
                gradeCount[4]++;
            }
        }
        for (int i = 0; i < gradeName.length; i++) {
            if (gradeCount[i] == 0)
                continue;
            gradeStatistics = new JSONObject();
            gradeStatistics.put("item", gradeName[i]);
            gradeStatistics.put("count", gradeCount[i]);
            gradeStatistics.put("percent", Double.parseDouble(String.format("%.2f", (double) gradeCount[i] / data.size())));
            jsonArray.add(gradeStatistics);
        }
        return new Result(Code.SEARCH_SUCCESS, "查询成功", jsonArray);
    }
}
