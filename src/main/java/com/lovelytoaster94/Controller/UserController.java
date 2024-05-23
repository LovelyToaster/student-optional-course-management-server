package com.lovelytoaster94.Controller;

import com.alibaba.fastjson2.JSONObject;
import com.lovelytoaster94.Dao.UserMapper;
import com.lovelytoaster94.Pojo.User;
import com.lovelytoaster94.Until.JwtUntil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public String loginVerify(User user, HttpServletResponse response) {
        User data = userMapper.loginVerify(user);
        JwtUntil jwtUntil = new JwtUntil();
        JSONObject jsonData = new JSONObject();
        if (data != null) {
            jsonData.put("userName", data.getUserName());
            jsonData.put("permissions", data.getPermission());
            jsonData.put("verify", true);
            response.addHeader("Set-Cookie", "token=" + jwtUntil.createToken(data) + ";Path=/;HttpOnly");
        } else {
            jsonData.put("verify", false);
        }
        return jsonData.toJSONString();
    }

    @RequestMapping(value = "/login/status", method = RequestMethod.GET)
    @ResponseBody
    public String loginStatus(HttpServletRequest request, HttpServletResponse response) {
        JwtUntil jwtUntil = new JwtUntil();
        return jwtUntil.loginStatus(request, response);
    }
}
