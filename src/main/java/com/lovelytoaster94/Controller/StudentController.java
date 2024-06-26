package com.lovelytoaster94.Controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.lovelytoaster94.Pojo.Student;
import com.lovelytoaster94.Service.StudentService;
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
@RequestMapping(value = "/student")
public class StudentController {

    private final StudentService studentService;
    private final ManagementResultInfo managementResultInfo = new ManagementResultInfo();

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Result allStudentInfo() {
        List<Student> studentData = studentService.allStudentInfo();
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = JSONArray.parseArray(JSONArray.toJSONString(studentData));
        int manCount = 0;
        for (Student student : studentData) {
            if (student.getStudentSex().equals("男"))
                manCount++;
        }
        jsonObject.put("manCount", manCount);
        jsonObject.put("womanCount", studentData.size() - manCount);
        jsonObject.put("totalCount", studentData.size());
        jsonObject.put("data", jsonArray);
        return managementResultInfo.allInfo(jsonObject);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public Result modifyStudentInfo(Student student) {
        boolean data = studentService.modifyStudentInfo(student);
        return managementResultInfo.modifyInfo(data);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteStudentInfo(@RequestParam("studentNo") String studentNo) {
        boolean data = studentService.deleteStudentInfo(studentNo);
        return managementResultInfo.deleteInfo(data);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public Result searchTeacherInfo(Student student) {
        List<Student> data = studentService.searchStudentInfo(student);
        return managementResultInfo.searchInfo(data);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Result addStudentInfo(Student student) {
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(student));
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            if (entry.getValue() == null || entry.getValue().equals(0)) {
                return new Result(Code.ADD_FAILED, "添加失败,请输入数据");
            }
        }
        boolean data = studentService.addStudentInfo(student);
        return managementResultInfo.addInfo(data);
    }
}
