package com.lovelytoaster94.Filter;

import com.lovelytoaster94.Until.JwtUntil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginHandler implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        JwtUntil jwtUntil = new JwtUntil();
        jwtUntil.loginStatus(request, response);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
