package com.lovelytoaster94.Controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.lovelytoaster94.Pojo.Teacher;
import com.lovelytoaster94.Service.TeacherService;
import com.lovelytoaster94.Until.Code;
import com.lovelytoaster94.Until.ManagementResultInfo;
import com.lovelytoaster94.Until.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/teacher", method = RequestMethod.GET)
public class TeacherController {
    private final TeacherService teacherService;
    private final ManagementResultInfo managementResultInfo = new ManagementResultInfo();

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
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
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(teacher));
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            if (entry.getValue() == null || entry.getValue().equals(0)) {
                return new Result(Code.ADD_FAILED, "添加失败,请输入数据");
            }
        }
        boolean verify = teacherService.addTeacherInfo(teacher);
        Object data = null;
        if (verify) {
            data = teacherService.searchTeacherInfo(teacher);
        }
        return managementResultInfo.addInfo(verify, data);
    }
}
