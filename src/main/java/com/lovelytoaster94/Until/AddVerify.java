package com.lovelytoaster94.Until;

import com.alibaba.fastjson2.JSONObject;

import java.util.Map;

public class AddVerify {
    public boolean verify(Object object) {
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(object));
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            if (entry.getValue() == null || entry.getValue().equals(0)) {
                return false;
            }
        }
        return true;
    }
}
