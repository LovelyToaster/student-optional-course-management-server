package com.lovelytoaster94.Filter;

import com.alibaba.fastjson2.JSONObject;
import com.lovelytoaster94.Until.Code;
import com.lovelytoaster94.Until.JwtUntil;
import com.lovelytoaster94.Until.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;

public class LoginHandler implements HandlerInterceptor {
    private static final List<String> unnecessaryLoginPath = Arrays.asList("/user/emailSend","/user/forgetPassword");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (unnecessaryLoginPath.contains(request.getServletPath())){
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }
        JwtUntil jwtUntil = new JwtUntil();
        Result result = jwtUntil.loginStatus(request, response);
        JSONObject jsonObject;
        if (result.getCode() == Code.LOGIN_SUCCESS) {
            jsonObject = (JSONObject) result.getData();
            request.setAttribute("userName", jsonObject.get("userName"));
            request.setAttribute("permissions", jsonObject.get("permissions"));
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }
        jsonObject = new JSONObject();
        jsonObject.put("code", result.getCode());
        jsonObject.put("message", result.getMessage());
        jsonObject.put("data", result.getData());
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(jsonObject.toString());
        return false;
    }
}
