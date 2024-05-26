package com.lovelytoaster94.Controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.lovelytoaster94.Pojo.Student;
import com.lovelytoaster94.Service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public String allStudentInfo() {
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
        return jsonObject.toJSONString();
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public String modifyStudentInfo(Student student) {
        JSONObject jsonObject = new JSONObject();
        boolean data = studentService.modifyStudentInfo(student);
        if (!data) {
            jsonObject.put("message", false);
            return jsonObject.toJSONString();
        }
        jsonObject.put("message", true);
        return jsonObject.toJSONString();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteStudentInfo(@RequestParam("studentNo") String studentNo) {
        JSONObject jsonObject = new JSONObject();
        boolean data = studentService.deleteStudentInfo(studentNo);
        if (!data) {
            jsonObject.put("message", false);
            return jsonObject.toJSONString();
        }
        jsonObject.put("message", true);
        return jsonObject.toJSONString();
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public String searchTeacherInfo(Student student) {
        List<Student> data = studentService.searchStudentInfo(student);
        JSONObject jsonObject = new JSONObject();
        if (!data.isEmpty()) {
            JSONArray jsonArray = JSONArray.parseArray(JSONArray.toJSONString(data));
            jsonObject.put("data", jsonArray);
        } else {
            jsonObject.put("data", false);
        }
        return jsonObject.toJSONString();
    }
}
