package com.lovelytoaster94.Until;

import com.alibaba.fastjson2.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lovelytoaster94.Pojo.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

public class JwtUntil {
    private static final String secretToken = "lovelytoaster94";
    private static final long time = 3600L;

    public String createToken(User user) {
        Date expirationDate = new Date(System.currentTimeMillis() + time * 1000);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("alg", "HS256");
        hashMap.put("typ", "JWT");
        return JWT.create()
                .withHeader(hashMap)
                .withClaim("userName", user.getUserName())
                .withClaim("permissions", user.getPermissions())
                .withClaim("avatarName", user.getAvatarName())
                .withExpiresAt(expirationDate)
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC256(secretToken));
    }

    private static JSONObject verifyToken(String token) {
        DecodedJWT decodedJWT;
        JSONObject jsonObject = new JSONObject();
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secretToken)).build();
            decodedJWT = jwtVerifier.verify(token);
            jsonObject.put("userName", decodedJWT.getClaim("userName").asString());
            jsonObject.put("permissions", decodedJWT.getClaim("permissions").asInt());
            jsonObject.put("avatarName", decodedJWT.getClaim("avatarName").asString());
            return jsonObject;
        } catch (Exception e) {
            return null;
        }
    }

    public Result loginStatus(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    JSONObject data = verifyToken(cookie.getValue());
                    if (data != null) {
                        return new Result(Code.LOGIN_SUCCESS, "登陆成功", data);
                    }
                    response.addHeader("Cache-Control", "no-cache");
                    return new Result(Code.LOGIN_FAILED, "登录验证失败，可能token已经失效");
                }
            }
        }
        return new Result(Code.LOGIN_FAILED,"请先登录，再进行操作");
    }
}
