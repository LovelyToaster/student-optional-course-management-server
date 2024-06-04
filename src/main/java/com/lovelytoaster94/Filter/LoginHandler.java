package com.lovelytoaster94.Filter;

import com.alibaba.fastjson2.JSONObject;
import com.lovelytoaster94.Until.Code;
import com.lovelytoaster94.Until.JwtUntil;
import com.lovelytoaster94.Until.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginHandler implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        JwtUntil jwtUntil = new JwtUntil();
        Result result = jwtUntil.loginStatus(request, response);
        if (result.getCode() == Code.LOGIN_FAILED) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", Code.LOGIN_FAILED);
            jsonObject.put("message", result.getMessage());
            jsonObject.put("data", result.getData());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(jsonObject.toString());
            return false;
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
