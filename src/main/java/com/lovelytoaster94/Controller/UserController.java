package com.lovelytoaster94.Controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.lovelytoaster94.Pojo.User;
import com.lovelytoaster94.Service.UserService;
import com.lovelytoaster94.Until.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Controller
@RequestMapping(value = "/user", method = RequestMethod.GET)
public class UserController {
    private final UserService userService;
    private final ManagementResultInfo managementResultInfo = new ManagementResultInfo();

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //  本地
    private static final String UPLOAD_FOLDER = "C:\\Users\\Lovel\\OneDrive\\LovelyToaster94\\Code\\JetBrains_IDEA\\Project\\student-optional-course-management-web-refresh\\src\\assets\\avatar\\";
    private static final String GET_AVATAR_PATH = "http://localhost:5173/src/assets/avatar/";

//    服务器部署
//    private static final String UPLOAD_FOLDER = "/var/www/html/ssm/avatar/";
//    private static final String GET_AVATAR_PATH = "https://ssm.lovelytoaster94.top/avatar/";


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result loginVerify(User user, HttpServletResponse response, HttpServletRequest request) {
        request.getSession().setAttribute("isLoginOut", false);
        User data = userService.loginVerify(user);
        JwtUntil jwtUntil = new JwtUntil();
        JSONObject jsonData = new JSONObject();
        if (data != null) {
            jsonData.put("userName", data.getUserName());
            jsonData.put("permissions", data.getPermissions());
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
    public Result setPassword(@RequestParam("userName") String userName, @RequestParam("userPassword") String userPassword, @RequestParam("newPassword") String newPassword, HttpServletResponse response) {
        User user = new User();
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        User data = userService.loginVerify(user);
        if (data != null) {
            boolean isSetSuccess = userService.setPassword(userName, newPassword);
            if (isSetSuccess) {
                response.addHeader("Set-Cookie", "token=" + ";Max-Age=0;Path=/;HttpOnly");
                return new Result(Code.MODIFY_SUCCESS, "密码修改成功！稍后将返回登录界面");
            }
        }
        return new Result(Code.MODIFY_FAILED, "密码修改失败，请检查当前密码是否正确");
    }

    @RequestMapping(value = "/setAvatar", method = RequestMethod.POST)
    @ResponseBody
    public Result setAvatar(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getAttribute("userName").toString();
        int permissions = (int) request.getAttribute("permissions");
        String fileName = String.valueOf(UUID.nameUUIDFromBytes(userName.getBytes()));
        User user;
        JwtUntil jwtUntil;
        boolean isSuccess = userService.setAvatar(userName, fileName);
        if (!isSuccess) {
            return new Result(Code.MODIFY_FAILED, "头像修改失败");
        }
        file.transferTo(new File(UPLOAD_FOLDER + fileName + ".jpg"));
        user = new User();
        jwtUntil = new JwtUntil();
        user.setUserName(userName);
        user.setPermissions(permissions);
        user.setAvatarName(fileName);
        response.addHeader("Set-Cookie", "token=" + jwtUntil.createToken(user) + ";Path=/;HttpOnly");
        return new Result(Code.MODIFY_SUCCESS, "头像修改成功", GET_AVATAR_PATH + fileName + ".jpg");
    }

    @RequestMapping(value = "/getAvatar", method = RequestMethod.GET)
    @ResponseBody
    public Result getAvatar(@RequestParam("userName") String userName) {
        User user = new User();
        user.setUserName(userName);
        List<User> data = userService.searchUserInfo(user);
        if (data != null && !data.isEmpty()) {
            if (data.getFirst().getUserName().equals(userName)) {
                return new Result(Code.SEARCH_SUCCESS, "获取头像成功", GET_AVATAR_PATH + data.getFirst().getAvatarName() + ".jpg");
            }
        }
        return new Result(Code.SEARCH_FAILED, "没有此用户");
    }

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public Result allUserInfo() {
        List<User> userList = userService.allUserInfo();
        JSONArray jsonArray = getObjects(userList);
        return managementResultInfo.allInfo(jsonArray);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public Result searchUserInfo(User user) {
        List<User> userList = userService.searchUserInfo(user);
        JSONArray jsonArray = getObjects(userList);
        return managementResultInfo.searchInfo(jsonArray);
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public Result resetPassword(@RequestParam("userName") String userName) {
        boolean isSuccess = userService.resetPassword(userName);
        if (isSuccess) {
            return new Result(Code.MODIFY_SUCCESS, "密码重置成功！密码重置为：123456");
        } else {
            return new Result(Code.MODIFY_FAILED, "密码重置失败，可能发生错误");
        }
    }

    @RequestMapping(value = "/loginOut", method = RequestMethod.GET)
    @ResponseBody
    public Result loginOut(HttpServletResponse response) {
        response.addHeader("Set-Cookie", "token=" + ";Max-Age=0;Path=/;HttpOnly");
        return new Result(Code.LOGIN_OUT_SUCCESS, "退出登录成功!");
    }

    private static JSONArray getObjects(List<User> userList) {
        JSONArray jsonArray = new JSONArray();
        for (User u : userList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userName", u.getUserName());
            jsonObject.put("permissions", u.getPermissions());
            jsonObject.put("email", u.getEmail());
            if (u.getAvatarName() != null)
                jsonObject.put("avatarPath", GET_AVATAR_PATH + u.getAvatarName() + ".jpg");
            else
                jsonObject.put("avatarPath", "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png");
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @RequestMapping(value = "/emailSend", method = RequestMethod.POST)
    @ResponseBody
    public Result sendCode(@RequestParam("email") String email, HttpSession session) throws MessagingException {
        Long codeTime = (Long) session.getAttribute("codeTime");

        if (codeTime != null && System.currentTimeMillis() - codeTime < 5 * 60 * 1000) {
            return new Result(Code.EMAIL_SEND_TOO_FREQUENT, "5分钟内请勿重复获取验证码");
        }

        String code = String.valueOf(new Random().nextInt(899999) + 100000);
        session.setAttribute("emailCode", code);
        session.setAttribute("codeTime", System.currentTimeMillis());
        session.setAttribute("emailTarget", email);
        MailUntil.sendMail(email, code);
        return new Result(Code.EMAIL_SEND_SUCCESS, "验证码发送成功！");
    }

    @RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
    @ResponseBody
    public Result forgetPassword(@RequestParam("email") String email, @RequestParam("code") String code, @RequestParam("newPassword") String newPassword, HttpSession session) {
        Result verificationResult = verifyCode(email, code, session);
        if (verificationResult.getCode() != Code.CODE_VERIFY_SUCCESS) {
            return verificationResult;
        }

        User userSearch = new User();
        userSearch.setEmail(email);
        User user = userService.searchUserInfo(userSearch).getFirst();
        if (user == null) {
            return new Result(Code.CODE_VERIFY_FAILED, "邮箱未注册");
        }

        userService.setPassword(user.getUserName(), newPassword);

        session.removeAttribute("emailCode");
        session.removeAttribute("codeTime");
        session.removeAttribute("emailTarget");

        return new Result(Code.CODE_VERIFY_SUCCESS, "密码已重置");
    }

    @RequestMapping(value = "/setEmail",method = RequestMethod.POST)
    @ResponseBody
    public Result setEmail(@RequestParam("email") String email, @RequestParam("code") String code, @RequestParam("newEmail") String newEmail, HttpSession session) {
        Result verificationResult = verifyCode(email, code, session);
        if (verificationResult.getCode() != Code.CODE_VERIFY_SUCCESS) {
            return verificationResult;
        }

        User userSearch = new User();
        userSearch.setEmail(email);
        User user = userService.searchUserInfo(userSearch).getFirst();
        if (user == null) {
            return new Result(Code.CODE_VERIFY_FAILED, "邮箱未注册");
        }
        userService.setEmail(user.getUserName(), newEmail);

        session.removeAttribute("emailCode");
        session.removeAttribute("codeTime");
        session.removeAttribute("emailTarget");

        return new Result(Code.CODE_VERIFY_SUCCESS, "邮箱已更新");
    }

    private Result verifyCode(String email, String code, HttpSession session) {
        String sessionCode = (String) session.getAttribute("emailCode");
        Long codeTime = (Long) session.getAttribute("codeTime");
        String emailTarget = (String) session.getAttribute("emailTarget");

        if (sessionCode == null || codeTime == null || emailTarget == null) {
            return new Result(Code.CODE_VERIFY_FAILED, "验证码不存在或已过期");
        }

        if (!email.equals(emailTarget)) {
            return new Result(Code.CODE_VERIFY_FAILED, "邮箱不匹配，请使用接收验证码的邮箱");
        }

        if (System.currentTimeMillis() - codeTime > 5 * 60 * 1000) {
            return new Result(Code.CODE_VERIFY_FAILED, "验证码已过期");
        }

        if (!sessionCode.equals(code)) {
            return new Result(Code.CODE_VERIFY_FAILED, "验证码错误");
        }

        return new Result(Code.CODE_VERIFY_SUCCESS, "验证码验证成功");
    }

}
