package com.bo.ke.myboke.utils;

import com.bo.ke.myboke.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jdk.nashorn.internal.parser.Token;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtToken生成的工具类
 * JWT token的格式：header.payload.signature
 * header的格式（算法、token的类型）：
 * {"alg": "HS512","type": "JWT"}
 * payload的格式（用户名、创建时间、生成时间）：
 * {"sub":"wang","created":1489079981393,"exp":1489684781}
 * signature的生成算法：
 * HMACSHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
 */
@Component
@Slf4j
public class JwtUtil {
    private static final String CLAIM_KEY_UID = "id";
    private static final String CLAIM_KEY_USERNAME = "username";
    private static final String secret = "161527841418433603825452153579";
    private static final Long expiration = 24 * 60 * 60l; // 60l代表60s后jwt过期

    public static void main(String[] args) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_UID, "20192019");
        claims.put(CLAIM_KEY_USERNAME, "test");
        String token = createToken(claims);
        log.info("jwt---:" + token);

        Claims claimsFromToken = getClaimsFromToken(token);
        log.info("claimsFromToken---:" + claimsFromToken);

        String id = getUserNameFromToken(token);
        log.info("id---:" + id);

        User user = new User();
        user.setId("20192019");
        user.setUsername("test");

        String str = token;
        boolean b = validateToken(str, user);
        log.info("b---:" + b);
        if (b) {
            long remainingTime = getRemainingTime(str);
            log.info("剩余时间:" + remainingTime + "s");
            refreshToken(str);
        } else {
            log.info("【请重新登陆】");
        }

    }

    /**
     * 生成JWT
     */
    public static String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                   .setClaims(claims)
                   .setExpiration(generateExpirationDate())
                   .signWith(SignatureAlgorithm.HS512, secret)
                   .compact();
    }

    /**
     * 从token中获取JWT中的负载
     */
    public static Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                         .setSigningKey(secret)
                         .parseClaimsJws(token)
                         .getBody();
        } catch (Exception e) {
            log.info("JWT格式验证失败:{}", token);
        }
        return claims;
    }

    /**
     * 生成token的过期时间
     */
    public static Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 获取token的剩余过期时间
     */
    public static long getRemainingTime(String token) {
        long result = 0;
        try {
            long nowMillis = System.currentTimeMillis();
            result = getClaimsFromToken(token).getExpiration()
                                              .getTime() - nowMillis;
        } catch (Exception e) {
            log.error("error={}", e);
        }
        return result;
    }

    /**
     * 从token中获取登录用户id
     */
    public static String getIdFromToken(String token) {
        String id;
        try {
            Claims claims = getClaimsFromToken(token);
            id = (String) claims.get(CLAIM_KEY_UID);
        } catch (Exception e) {
            id = null;
        }
        return id;
    }

    /**
     * 从token中获取登录用户username
     */
    public static String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = (String) claims.get(CLAIM_KEY_USERNAME);
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 验证token是否还有效
     *
     * @param token 客户端传入的token
     * @param user  从数据库中查询出来的用户信息
     */
    public static boolean validateToken(String token, User user) {
        try {
            String username = getUserNameFromToken(token);
            if (StringUtils.isEmpty(username)) {
                return false;
            }
            return username.equals(user.getUsername()) && !isTokenExpired(token);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断token是否已经失效
     */
    public static boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     */
    public static Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 根据用户信息生成token
     */
    public static String generateToken(User userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_UID, userDetails.getId());
        return createToken(claims);
    }

    /**
     * 判断token是否可以被刷新
     */
    public static boolean canRefresh(String token) {
        return !isTokenExpired(token);
    }

    /**
     * 刷新token
     */
    public static String refreshToken(String token) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, getUserNameFromToken(token));
        claims.put(CLAIM_KEY_UID, getIdFromToken(token));
        return createToken(claims);
    }
}
