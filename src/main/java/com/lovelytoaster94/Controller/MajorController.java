package com.lovelytoaster94.Controller;

import com.lovelytoaster94.Pojo.Major;
import com.lovelytoaster94.Service.MajorService;
import com.lovelytoaster94.Until.AddVerify;
import com.lovelytoaster94.Until.Code;
import com.lovelytoaster94.Until.ManagementResultInfo;
import com.lovelytoaster94.Until.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/major")
public class MajorController {
    private final MajorService majorService;
    private final ManagementResultInfo managementResultInfo = new ManagementResultInfo();

    public MajorController(MajorService majorService) {
        this.majorService = majorService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Result allMajorInfo() {
        return managementResultInfo.allInfo(majorService.allMajorInfo());
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public Result modifyMajorInfo(Major major) {
        boolean data = majorService.modifyMajorInfo(major);
        return managementResultInfo.modifyInfo(data);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteMajorInfo(@RequestParam("majorNo") String majorNo) {
        boolean data = majorService.deleteMajorInfo(majorNo);
        return managementResultInfo.deleteInfo(data);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public Result searchMajorInfo(Major major) {
        List<Major> data = majorService.searchMajorInfo(major);
        return managementResultInfo.searchInfo(data);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Result addMajorInfo(Major major) {
        AddVerify addVerify = new AddVerify();
        if (!addVerify.verify(major)) {
            return new Result(Code.ADD_FAILED, "添加失败，请检查数据是否填写正确");
        }
        boolean verify = majorService.addMajorInfo(major);
        Object data = null;
        if (verify) {
            data = majorService.searchMajorInfo(major);
        }
        return managementResultInfo.addInfo(verify, data);
    }

    @RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
    @ResponseBody
    public Result batchAddMajorInfo(@RequestBody List<Major> majorList) {
        List<Object> data = new ArrayList<>();
        for (Major major : majorList) {
            boolean verify = majorService.addMajorInfo(major);
            if (verify) {
                data.add(majorService.searchMajorInfo(major));
            }
        }
        if (data.size() == majorList.size()) {
            return new Result(Code.ADD_FAILED, "添加成功，共成功添加" + data.size() + "条数据", data);
        }
        return new Result(Code.ADD_FAILED, "添加失败，共成功添加" + data.size() + "条数据", data);
    }

    @RequestMapping(value = "/batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Result batchDelMajorInfo(@RequestBody List<Major> majorList) {
        List<Object> data = new ArrayList<>();
        for (Major major : majorList) {
            boolean verify = majorService.deleteMajorInfo(major.getMajorNo());
            if (verify) {
                data.add(major.getMajorNo());
            }
        }
        if (data.size() == majorList.size()) {
            return new Result(Code.DELETE_SUCCESS, "删除成功，共成功删除" + data.size() + "条数据", data);
        }
        return new Result(Code.DELETE_FAILED, "删除失败，共成功删除" + data.size() + "条数据", data);
    }
}
