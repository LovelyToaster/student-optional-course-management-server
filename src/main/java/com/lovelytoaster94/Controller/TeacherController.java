package com.lovelytoaster94.Controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.lovelytoaster94.Pojo.Teacher;
import com.lovelytoaster94.Service.TeacherService;
import com.lovelytoaster94.Service.UserService;
import com.lovelytoaster94.Until.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/teacher", method = RequestMethod.GET)
public class TeacherController {
    private final TeacherService teacherService;
    private final UserService userService;
    private final ManagementResultInfo managementResultInfo = new ManagementResultInfo();

    public TeacherController(TeacherService teacherService, UserService userService) {
        this.teacherService = teacherService;
        this.userService = userService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Result allTeacherInfo() {
        List<Teacher> teacherData = teacherService.allTeacherInfo();
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = JSONArray.parseArray(JSONArray.toJSONString(teacherData));
        int manCount = 0;
        for (Teacher data : teacherData) {
            if (data.getTeacherSex().equals("男"))
                manCount++;
        }
        jsonObject.put("manCount", manCount);
        jsonObject.put("womanCount", teacherData.size() - manCount);
        jsonObject.put("totalCount", teacherData.size());
        jsonObject.put("data", jsonArray);
        return managementResultInfo.allInfo(jsonObject);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public Result modifyTeacherInfo(Teacher teacher) {
        Result result = emailVerify(teacher);
        if (!(result.getCode() == Code.ADD_SUCCESS)) {
            return result;
        }
        boolean data = teacherService.modifyTeacherInfo(teacher);
        return managementResultInfo.modifyInfo(data);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteTeacherInfo(@RequestParam("teacherNo") String teacherNo) {
        boolean data = teacherService.deleteTeacherInfo(teacherNo);
        return managementResultInfo.deleteInfo(data);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public Result searchTeacherInfo(Teacher teacher) {
        List<Teacher> data = teacherService.searchTeacherInfo(teacher);
        return managementResultInfo.searchInfo(data);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Result addTeacherInfo(Teacher teacher) {
        AddVerify addVerify = new AddVerify();
        if (!addVerify.verify(teacher)) {
            return new Result(Code.ADD_FAILED, "添加失败，请检查数据是否填写正确");
        }
        Result result = emailVerify(teacher);
        if (!(result.getCode() == Code.ADD_SUCCESS)) {
            return result;
        }
        boolean verify = teacherService.addTeacherInfo(teacher);
        Object data = null;
        if (verify) {
            data = teacherService.searchTeacherInfo(teacher);
        }
        return managementResultInfo.addInfo(verify, data);
    }

    @RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
    @ResponseBody
    public Result batchAddStudentInfo(@RequestBody List<Teacher> teacherList) {
        List<Object> data = new ArrayList<>();
        for (Teacher teacher : teacherList) {
            boolean verify = teacherService.addTeacherInfo(teacher);
            if (verify) {
                data.add(teacherService.searchTeacherInfo(teacher));
            }
        }
        if (data.size() == teacherList.size()) {
            return new Result(Code.ADD_SUCCESS, "添加成功，共成功添加" + data.size() + "条数据", data);
        }
        return new Result(Code.ADD_FAILED, "添加失败，共成功添加" + data.size() + "条数据", data);
    }

    @RequestMapping(value = "/batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Result batchDelStudentInfo(@RequestBody List<Teacher> teacherList) {
        List<Object> data = new ArrayList<>();
        for (Teacher teacher : teacherList) {
            boolean verify = teacherService.deleteTeacherInfo(teacher.getTeacherNo());
            if (verify) {
                data.add(teacher.getTeacherNo());
            }
        }
        if (data.size() == teacherList.size()) {
            return new Result(Code.DELETE_SUCCESS, "删除成功，共成功删除" + data.size() + "条数据", data);
        }
        return new Result(Code.DELETE_FAILED, "删除失败，共成功删除" + data.size() + "条数据", data);
    }

    private Result emailVerify(Teacher teacher) {
        String email = teacher.getEmail();
        if (email != null) {
            if (!MailUntil.isValidEmail(email)) {
                MailUntil.setUserService(userService);
                return new Result(Code.MODIFY_FAILED, "邮箱格式不正确");
            }
            if (MailUntil.checkEmailRepeat(email, teacher.getTeacherNo()) != null) {
                return new Result(Code.EMAIL_REPEAT, "邮箱已被注册");
            }
        }
        return new Result(Code.ADD_SUCCESS, "邮箱格式正确");
    }
}
