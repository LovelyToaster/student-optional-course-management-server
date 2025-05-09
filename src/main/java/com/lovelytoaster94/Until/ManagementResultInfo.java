package com.lovelytoaster94.Until;

import java.util.List;

public class ManagementResultInfo {
    public Result allInfo(Object data) {
        return new Result(Code.SEARCH_SUCCESS, "查询完成", data);
    }

    public Result modifyInfo(Boolean data) {
        if (data) {
            return new Result(Code.MODIFY_SUCCESS, "修改成功");
        } else {
            return new Result(Code.MODIFY_FAILED, "修改失败，请检查数据");
        }
    }

    public Result deleteInfo(Boolean data) {
        if (data) {
            return new Result(Code.DELETE_SUCCESS, "删除成功");
        } else {
            return new Result(Code.DELETE_FAILED, "删除失败，请刷新重试");
        }
    }

    public <T> Result searchInfo(List<T> data) {
        if (!data.isEmpty()) {
            return new Result(Code.SEARCH_SUCCESS, "查询成功" + "，共" + data.size() + "条数据", data);
        } else {
            return new Result(Code.SEARCH_FAILED, "没有查询到数据");
        }
    }

    public <T> Result addInfo(Boolean verify, Object data) {
        if (verify) {
            return new Result(Code.ADD_SUCCESS, "添加成功", data);
        }
        return new Result(Code.ADD_FAILED, "添加失败，请检查数据");
    }
}
