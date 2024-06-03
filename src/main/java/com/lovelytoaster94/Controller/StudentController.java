package com.lovelytoaster94.Controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.lovelytoaster94.Pojo.Student;
import com.lovelytoaster94.Pojo.Teacher;
import com.lovelytoaster94.Service.StudentService;
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
        return new Result(Code.SEARCH_SUCCESS, "查询成功", jsonObject);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public Result modifyStudentInfo(Student student) {
        boolean data = studentService.modifyStudentInfo(student);
        if (data) {
            return new Result(Code.MODIFY_SUCCESS, "修改成功");
        } else {
            return new Result(Code.MODIFY_FAILED, "修改失败");
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteStudentInfo(@RequestParam("studentNo") String studentNo) {
        boolean data = studentService.deleteStudentInfo(studentNo);
        if (data) {
            return new Result(Code.DELETE_SUCCESS, "删除成功");
        } else {
            return new Result(Code.DELETE_FAILED, "删除失败");
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public Result searchTeacherInfo(Student student) {
        List<Student> data = studentService.searchStudentInfo(student);
        if (!data.isEmpty()) {
            return new Result(Code.SEARCH_SUCCESS, "查询成功" + "共" + data.size() + "条数据", data);
        } else {
            return new Result(Code.SEARCH_FAILED, "查询失败");
        }
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
        if (data) {
            return new Result(Code.ADD_SUCCESS, "添加成功");
        }
        return new Result(Code.ADD_FAILED, "添加失败");
    }
}
