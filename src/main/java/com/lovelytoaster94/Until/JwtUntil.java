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
                .withClaim("permissions", user.getPermission())
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
            System.out.println(decodedJWT.getClaim("userName").asString());
            jsonObject.put("userName", decodedJWT.getClaim("userName").asString());
            jsonObject.put("permissions", decodedJWT.getClaim("permissions").asInt());
            jsonObject.put("verify", true);
            return jsonObject;
        } catch (Exception e) {
            System.out.println(e.getMessage() + " " + "token failed");
            jsonObject.put("verify", false);
            return jsonObject;
        }
    }

    public String loginStatus(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    JSONObject data = verifyToken(cookie.getValue());
                    if (!(Boolean) data.get("verify")) {
                        response.addHeader("Cache-Control", "no-cache");
                        response.setStatus(301);
                    }
                    return data.toJSONString();
                }
            }
        } else {
            response.addHeader("Cache-Control", "no-cache");
            response.setStatus(301);
        }
        return null;
    }
}
