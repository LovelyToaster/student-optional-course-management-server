package com.lovelytoaster94.Controller;

import com.alibaba.fastjson2.JSONObject;
import com.lovelytoaster94.Pojo.Faculty;
import com.lovelytoaster94.Service.FacultyService;
import com.lovelytoaster94.Until.AddVerify;
import com.lovelytoaster94.Until.Code;
import com.lovelytoaster94.Until.ManagementResultInfo;
import com.lovelytoaster94.Until.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/faculty")
public class FacultyController {
    private final FacultyService facultyService;
    private final ManagementResultInfo managementResultInfo = new ManagementResultInfo();

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Result allFacultyInfo() {
        return managementResultInfo.allInfo(facultyService.allFacultyInfo());
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public Result modifyFacultyInfo(Faculty faculty) {
        boolean data = facultyService.modifyFacultyInfo(faculty);
        return managementResultInfo.modifyInfo(data);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteFacultyInfo(@RequestParam("facultyNo") String facultyNo) {
        boolean data = facultyService.deleteFacultyInfo(facultyNo);
        return managementResultInfo.deleteInfo(data);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public Result searchFacultyInfo(Faculty faculty) {
        List<Faculty> data = facultyService.searchFacultyInfo(faculty);
        return managementResultInfo.searchInfo(data);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Result addFacultyInfo(Faculty faculty) {
        AddVerify addVerify = new AddVerify();
        if (!addVerify.verify(faculty)) {
            return new Result(Code.ADD_FAILED, "添加失败，请检查数据是否填写正确");
        }
        boolean verify = facultyService.addFacultyInfo(faculty);
        Object data = null;
        if (verify) {
            data = facultyService.searchFacultyInfo(faculty);
        }
        return managementResultInfo.addInfo(verify, data);
    }

    @RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
    @ResponseBody
    public Result batchAddStudentInfo(@RequestBody List<Faculty> facultyList) {
        List<Object> data = new ArrayList<>();
        for (Faculty faculty : facultyList) {
            boolean verify = facultyService.addFacultyInfo(faculty);
            if (verify) {
                data.add(facultyService.searchFacultyInfo(faculty));
            }
        }
        if (data.size() == facultyList.size()) {
            return new Result(Code.ADD_FAILED, "添加成功，共成功添加" + data.size() + "条数据", data);
        }
        return new Result(Code.ADD_FAILED, "添加失败，共成功添加" + data.size() + "条数据", data);
    }

    @RequestMapping(value = "/batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Result batchDelStudentInfo(@RequestBody List<Faculty> facultyList) {
        List<Object> data = new ArrayList<>();
        for (Faculty faculty : facultyList) {
            boolean verify = facultyService.deleteFacultyInfo(faculty.getFacultyNo());
            if (verify) {
                data.add(faculty.getFacultyNo());
            }
        }
        if (data.size() == facultyList.size()) {
            return new Result(Code.ADD_FAILED, "删除成功，共成功删除" + data.size() + "条数据", data);
        }
        return new Result(Code.ADD_FAILED, "删除失败，共成功删除" + data.size() + "条数据", data);
    }
}
