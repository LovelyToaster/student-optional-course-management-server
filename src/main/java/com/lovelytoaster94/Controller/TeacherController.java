package com.lovelytoaster94.Controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.lovelytoaster94.Dao.TeacherMapper;
import com.lovelytoaster94.Pojo.Teacher;
import com.lovelytoaster94.Service.TeacherService;
import com.lovelytoaster94.Service.TeacherServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/teacher", method = RequestMethod.GET)
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public String allTeacherInfo() {
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
        return jsonObject.toJSONString();
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public String modifyTeacherInfo(Teacher teacher) {
        JSONObject jsonObject = new JSONObject();
        boolean data = teacherService.modifyTeacherInfo(teacher);
        if (!data) {
            jsonObject.put("message", false);
            return jsonObject.toJSONString();
        }
        jsonObject.put("message", true);
        return jsonObject.toJSONString();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteTeacherInfo(@RequestParam("teacherNo") int teacherNo) {
        JSONObject jsonObject = new JSONObject();
        boolean data = teacherService.deleteTeacherInfo(teacherNo);
        if (!data) {
            jsonObject.put("message", false);
            return jsonObject.toJSONString();
        }
        jsonObject.put("message", true);
        return jsonObject.toJSONString();
    }
}
