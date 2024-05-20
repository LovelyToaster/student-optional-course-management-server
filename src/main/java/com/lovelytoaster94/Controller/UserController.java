package com.lovelytoaster94.Controller;

import com.alibaba.fastjson2.JSONObject;
import com.lovelytoaster94.Dao.UserMapper;
import com.lovelytoaster94.Pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/user", method = RequestMethod.GET)
public class UserController {
    private final UserMapper userMapper;

    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public String loginVerify(User user) {
        User data = userMapper.loginVerify(user.getUserName(), user.getUserPassword());
        JSONObject jsonData = new JSONObject();
        if (data != null) {
            jsonData.put("userName", data.getUserName());
            jsonData.put("permission", data.getPermission());
            jsonData.put("verify", "true");
        }
        else {
            jsonData.put("verify", "false");
        }
        return jsonData.toJSONString();
    }
}
