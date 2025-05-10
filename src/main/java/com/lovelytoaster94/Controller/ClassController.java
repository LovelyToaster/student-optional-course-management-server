package com.lovelytoaster94.Controller;

import com.lovelytoaster94.Pojo.Class;
import com.lovelytoaster94.Service.ClassService;
import com.lovelytoaster94.Until.AddVerify;
import com.lovelytoaster94.Until.Code;
import com.lovelytoaster94.Until.ManagementResultInfo;
import com.lovelytoaster94.Until.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/class")
public class ClassController {
    private final ClassService classService;
    private final ManagementResultInfo managementResultInfo = new ManagementResultInfo();

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Result allClassInfo() {
        return managementResultInfo.allInfo(classService.allClassInfo());
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public Result modifyClassInfo(Class clazz) {
        boolean data = classService.modifyClassInfo(clazz);
        return managementResultInfo.modifyInfo(data);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteClassInfo(@RequestParam("classNo") String classNo) {
        boolean data = classService.deleteClassInfo(classNo);
        return managementResultInfo.deleteInfo(data);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public Result searchClassInfo(Class clazz) {
        List<Class> data = classService.searchClassInfo(clazz);
        return managementResultInfo.searchInfo(data);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Result addClassInfo(Class clazz) {
        AddVerify addVerify = new AddVerify();
        if (!addVerify.verify(clazz)) {
            return new Result(Code.ADD_FAILED, "添加失败，请检查数据是否填写正确");
        }
        boolean verify = classService.addClassInfo(clazz);
        Object data = null;
        if (verify) {
            data = classService.searchClassInfo(clazz);
        }
        return managementResultInfo.addInfo(verify, data);
    }

    @RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
    @ResponseBody
    public Result batchAddClassInfo(@RequestBody List<Class> classList) {
        List<Object> data = new ArrayList<>();
        for (Class clazz : classList) {
            boolean verify = classService.addClassInfo(clazz);
            if (verify) {
                data.add(classService.searchClassInfo(clazz));
            }
        }
        if (data.size() == classList.size()) {
            return new Result(Code.ADD_SUCCESS, "添加成功，共成功添加" + data.size() + "条数据", data);
        }
        return new Result(Code.ADD_FAILED, "添加失败，共成功添加" + data.size() + "条数据", data);
    }

    @RequestMapping(value = "/batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Result batchDelClassInfo(@RequestBody List<Class> classList) {
        List<Object> data = new ArrayList<>();
        for (Class clazz : classList) {
            boolean verify = classService.deleteClassInfo(clazz.getClassNo());
            if (verify) {
                data.add(clazz.getClassNo());
            }
        }
        if (data.size() == classList.size()) {
            return new Result(Code.DELETE_SUCCESS, "删除成功，共成功删除" + data.size() + "条数据", data);
        }
        return new Result(Code.DELETE_FAILED, "删除失败，共成功删除" + data.size() + "条数据", data);
    }
}
