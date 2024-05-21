package com.lovelytoaster94.Until;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
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
                .withClaim("username", user.getUserName())
                .withClaim("permissions", user.getPermission())
                .withExpiresAt(expirationDate)
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC256(secretToken));
    }

    private static boolean verifyToken(String token) {
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secretToken)).build();
            jwtVerifier.verify(token);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage() + " " + "token failed");
            return false;
        }
    }

    public void loginStatus(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    boolean verify = verifyToken(cookie.getValue());
                    if (!verify) {
                        response.addHeader("Cache-Control", "no-cache");
                        response.setStatus(301);
                    }
                }
            }
        } else {
            response.addHeader("Cache-Control", "no-cache");
            response.setStatus(301);
        }
    }
}
