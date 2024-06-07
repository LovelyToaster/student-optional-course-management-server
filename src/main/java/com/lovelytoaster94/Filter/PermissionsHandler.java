package com.lovelytoaster94.Filter;

import com.alibaba.fastjson2.JSONObject;
import com.lovelytoaster94.Until.Code;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;

public class PermissionsHandler implements HandlerInterceptor {
    private static final List<String> unnecessaryPermissionsPath = Arrays.asList("/teacher/all", "/teacher/search", "/course/all", "/course/search", "/user/login", "/user/status");

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!unnecessaryPermissionsPath.contains(request.getServletPath())) {
            if ((int) request.getAttribute("permissions") != 1) {
                String userName = null;
                if (request.getParameter("studentNo") != null) {
                    userName = request.getParameter("studentNo");
                } else if (request.getParameter("userName") != null) {
                    userName = request.getParameter("userName");
                }
                if (!request.getAttribute("userName").equals(userName)) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("code", Code.PERMISSIONS_FAILED);
                    jsonObject.put("message", "错误，禁止越权操作");
                    response.setContentType("application/json;charset=utf-8");
                    response.getWriter().write(jsonObject.toString());
                    return false;
                }
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
