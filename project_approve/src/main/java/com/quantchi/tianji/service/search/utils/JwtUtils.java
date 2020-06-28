package com.quantchi.tianji.service.search.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @Description 
 * @author leiel
 * @Date 2020/4/1 1:24 PM
 */
@Slf4j
public class JwtUtils {

    /*设置token的过期时间,默认单位为ms*/
    private static final long EXPIRE_TIME =  2 * 60 * 60 * 1000;

    /**
     * 校验token是否正确
     * @param userId 用户名
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String userId, String secret) {
        try {
            //根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("userId", userId)
                    .build();
            //效验TOKEN
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asString();
        } catch (JWTDecodeException e) {
            log.error("解析token出错");
            return null;
        }
    }

    /**
     * 生成签名,30min后过期
     *
     * @param userId 用户名
     * @param secret   用户的密码
     * @return 加密的token
     */
    public static String sign(String userId, String secret) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        return JWT.create()
                .withClaim("userId", userId)
                .withExpiresAt(date)
                .sign(algorithm);

    }

    public static void main(String[] args) {

        String token = JwtUtils.sign("9001009", "deqingswjweb");

        System.out.println(token);

        boolean deqingweb = verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1ODU4MjUxNTQsInVzZXJJZCI6IjkwMDEwMDkifQ.3UDkLgmHVta8efvHgr4jj4IIPW-6Bc2JpBC62JkZR2s",
                "9001009", "deqingswjweb");

        System.out.println(deqingweb);
    }

}
