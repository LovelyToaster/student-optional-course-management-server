package com.lovelytoaster94.Controller;

import com.alibaba.fastjson2.JSONObject;
import com.lovelytoaster94.Pojo.User;
import com.lovelytoaster94.Service.UserService;
import com.lovelytoaster94.Until.Code;
import com.lovelytoaster94.Until.JwtUntil;
import com.lovelytoaster94.Until.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping(value = "/user", method = RequestMethod.GET)
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private static final String UPLOAD_FOLDER = "C:\\Users\\Lovel\\OneDrive\\LovelyToaster94\\Program\\JetBrains_IDEA\\Project\\student-optional-course-management-web\\src\\assets\\img\\";
    private static final String GET_AVATAR_PATH = "http://localhost:5173/src/assets/img/";

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public Result loginVerify(User user, HttpServletResponse response) {
        User data = userService.loginVerify(user);
        JwtUntil jwtUntil = new JwtUntil();
        JSONObject jsonData = new JSONObject();
        if (data != null) {
            jsonData.put("userName", data.getUserName());
            jsonData.put("permissions", data.getPermission());
            jsonData.put("avatarPath", GET_AVATAR_PATH + data.getAvatarName() + ".jpg");
            response.addHeader("Set-Cookie", "token=" + jwtUntil.createToken(data) + ";Path=/;HttpOnly");
            return new Result(Code.LOGIN_SUCCESS, "登陆成功", jsonData);
        } else {
            return new Result(Code.LOGIN_FAILED, "登陆失败，用户名或密码错误");
        }
    }

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    @ResponseBody
    public Result loginStatus(HttpServletRequest request, HttpServletResponse response) {
        JwtUntil jwtUntil = new JwtUntil();
        Result result = jwtUntil.loginStatus(request, response);
        JSONObject jsonData = (JSONObject) result.getData();
        jsonData.put("avatarPath", GET_AVATAR_PATH + jsonData.get("avatarName") + ".jpg");
        jsonData.remove("avatarName");
        return new Result(Code.LOGIN_SUCCESS, "获取登录信息成功", jsonData);
    }

    @RequestMapping(value = "/setPassword", method = RequestMethod.POST)
    @ResponseBody
    public Result setPassword(@ModelAttribute("userName") String userName, @ModelAttribute("userPassword") String userPassword, @ModelAttribute("newPassword") String newPassword) {
        User user = new User();
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        User data = userService.loginVerify(user);
        if (data != null) {
            boolean isSetSuccess = userService.setPassword(userName, newPassword);
            if (isSetSuccess) {
                return new Result(Code.MODIFY_SUCCESS, "密码修改成功！");
            }
        }
        return new Result(Code.MODIFY_FAILED, "密码修改失败，请检查当前密码是否正确");
    }

    @RequestMapping(value = "/setAvatar", method = RequestMethod.POST)
    @ResponseBody
    public Result setAvatar(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String userName = request.getAttribute("userName").toString();
        String fileName = String.valueOf(UUID.nameUUIDFromBytes(userName.getBytes()));
        boolean isSuccess = userService.setAvatar(userName, fileName);
        if (!isSuccess) {
            return new Result(Code.MODIFY_FAILED, "头像修改失败");
        }
        try {
            file.transferTo(new File(UPLOAD_FOLDER + fileName + ".jpg"));
        } catch (IOException e) {
            return new Result(Code.SERVICE_FAILED, "发生错误，请联系管理员", e.getMessage());
        }
        return new Result(Code.MODIFY_SUCCESS, "头像修改成功", GET_AVATAR_PATH + fileName + ".jpg");
    }
}
