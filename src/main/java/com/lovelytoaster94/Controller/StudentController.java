package com.lovelytoaster94.Controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.lovelytoaster94.Pojo.Student;
import com.lovelytoaster94.Service.StudentService;
import com.lovelytoaster94.Service.UserService;
import com.lovelytoaster94.Until.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/student")
public class StudentController {

    private final StudentService studentService;
    private final UserService userService;
    private final ManagementResultInfo managementResultInfo = new ManagementResultInfo();

    public StudentController(StudentService studentService, UserService userService) {
        this.studentService = studentService;
        this.userService = userService;
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
        String email = student.getEmail();
        if (email != null) {
            MailUntil.setUserService(userService);
            if (!MailUntil.isValidEmail(email)) {
                return new Result(Code.MODIFY_FAILED, "邮箱格式不正确");
            }
            if (MailUntil.checkEmailRepeat(email, student.getStudentNo()) != null) {
                return new Result(Code.EMAIL_REPEAT, "邮箱已被注册");
            }
        }
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
    public Result searchStudentInfo(Student student) {
        List<Student> data = studentService.searchStudentInfo(student);
        return managementResultInfo.searchInfo(data);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Result addStudentInfo(Student student) {
        AddVerify addVerify = new AddVerify();
        if (!addVerify.verify(student)) {
            return new Result(Code.ADD_FAILED, "添加失败，请检查数据是否填写正确");
        }
        boolean verify = studentService.addStudentInfo(student);
        Object data = null;
        if (verify) {
            data = studentService.searchStudentInfo(student);
        }
        return managementResultInfo.addInfo(verify, data);
    }

    @RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
    @ResponseBody
    public Result batchAddStudentInfo(@RequestBody List<Student> studentList) {
        List<Object> data = new ArrayList<>();
        for (Student student : studentList) {
            boolean verify = studentService.addStudentInfo(student);
            if (verify) {
                data.add(studentService.searchStudentInfo(student));
            }
        }
        if (data.size() == studentList.size()) {
            return new Result(Code.ADD_SUCCESS, "添加成功，共成功添加" + data.size() + "条数据", data);
        }
        return new Result(Code.ADD_FAILED, "添加失败，共成功添加" + data.size() + "条数据", data);
    }

    @RequestMapping(value = "/batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Result batchDelStudentInfo(@RequestBody List<Student> studentList) {
        List<Object> data = new ArrayList<>();
        for (Student student : studentList) {
            boolean verify = studentService.deleteStudentInfo(student.getStudentNo());
            if (verify) {
                data.add(student.getStudentNo());
            }
        }
        if (data.size() == studentList.size()) {
            return new Result(Code.DELETE_SUCCESS, "删除成功，共成功删除" + data.size() + "条数据", data);
        }
        return new Result(Code.DELETE_FAILED, "删除失败，共成功删除" + data.size() + "条数据", data);
    }
}
