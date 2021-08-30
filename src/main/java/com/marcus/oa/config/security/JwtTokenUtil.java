package com.marcus.oa.config.security;


import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class JwtTokenUtil {

    //jwt用户名
    private static final String CLAIM_KEY_USERNAME = "sub";

    //jwt的创建时间
    private static final String CLAIM_KEY_CREATED = "created";

    //jwt的密钥
    @Value("${jwt.secret}")
    private String secret;

    //jwt的失效时间
    @Value("${jwt.expiration}")
    private Long expiration;


    /**
     * 需要实现的功能有
     * 1、根据用户信息   --  生产Token
     *    用户信息的获取，通过Security框架中UserDetails获取，
     *    获取到之后组装成我们需要的荷载
     *    具体怎么通过荷载生产Token，需要使用jwts.builder()等方法
     *
     *
     *
     * 2、通过Token     --  获取到用户名
     *    先从Token中获取荷载，需要使用Jwts.parser()转成荷载
     *    再通过荷载获取用户信息claims.getSubject();
     *
     * 3、判断Token是否有效
     *     3.1、Token是否过期?
     *     3.2、Token荷载中的用户名 和 UserDetails中的用户名是否一致，不一致是无效的
     *
     * 4、Token是否失效
     *       //在当前时间前就是失效的
     *
     * 5、是否可以刷新Token
     *
     *
     */
    /**
     * 1、
     * 根据security框架获取用户名
     * 根据用户信息   -----  生成Token
     *
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 根据荷载等信息，通过jwt生成 jwt token
     *
     * @param claims
     * @return
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)                                //设置荷载
                .setExpiration(generateExpirationDate())          //设置失效时间
                .signWith(SignatureAlgorithm.HS512, secret)       //签名 + 密钥方式
                .compact();

    }

    /**
     * 到什么时候失效
     * 生成token失效时间
     *
     * @return
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }







    /**
     * 2、
     * 从token中获取登录用户名
     * 通过token 获取到 荷载， 通过荷载获取到用户名
     *
     * @param token
     * @return
     */
    public String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }


    /**
     * 从token中获取荷载
     * 通过jwt 设置密钥
     *
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)           //签名
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }


    /**
     * 3、
     * 验证token是否有效
     *
     * @param token
     * @param userDetails
     * @return
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserNameFromToken(token);

        //是否失效
        //荷载中的用户名与security框架中的用户名是否一致
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 判断token是否失效
     *
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        Date expireDate = getExpiredDateFromToken(token);
        //在当前时间前就是失效的
        return expireDate.before(new Date());
    }

    /**
     * 从token中获取失效时间
     *
     * @param token
     * @return
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }


    /**
     * 4、
     * token失效的时候就可以刷新
     * <p>
     * 判断token是否可以被刷新
     *
     * @param token
     * @return
     */
    public boolean canRefresh(String token) {
        //过期了，肯定可以刷新
        return !isTokenExpired(token);
    }


    /**
     * 5、
     * <p>
     * 刷新token
     * 也就是组装出新荷载，主要是更新荷载创建的时间
     * 通过荷载生产新Token
     *
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }
}