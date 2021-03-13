package com.bo.ke.myboke.utils;

import com.bo.ke.myboke.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Jwt工具类，封装了操作Token的各种方法
 */
@Slf4j
public class JwtUtils {
    /*================================= 属性 =================================*/
    //Jwt的加密密钥
    private static String secretKey = "8972134584813456854613153254852";
    //使用BASE64加密后的Jwt的加密密钥
    private static final String BASE64_SECRET_KEY = TextCodec.BASE64.encode(secretKey);
    //Token的过期时间
    private static long expirationTime = 60l * 60 * 24 * 7; // 60l代表60秒
    //Token的加密算法，默认使用HS256
    private static SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    // 存储用户id,用户username
    private static final String ID = "id";
    private static final String USERNAME = "username";

    public static void main(String[] args) {
        User user = new User();
        user.setId("2018110615541619824983321");
        user.setUsername("admin");

        String token = generateToken(user);
        log.info("token:" + token);
        String id = getElement(token, ID);
        log.info("id:" + id);
    }

    /*================================= 方法 =================================*/

    /**
     * 根据用户名生成Token字符串
     *
     * @return 要生成的Token字符串
     */
    public static String generateToken(User user) {
        //用于存储Payload中的信息
        Map<String, Object> claims = new HashMap<>();
        String username = user.getUsername();
        //设置有效载荷(Payload)
        claims.put(ID, user.getId());
        claims.put(USERNAME, username);
        //签发时间
        claims.put(Claims.ISSUED_AT, new Date());
        //过期时间
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime * 1000);
        //使用JwtBuilder生成Token，其中需要设置Claims、过期时间，最后再
        String token = Jwts
                .builder()//获取DefaultJwtBuilder
                .setClaims(claims)//设置声明
                .setExpiration(expirationDate)
                .signWith(signatureAlgorithm, BASE64_SECRET_KEY)//使用指定加密方式和密钥进行签名
                .compact();//生成字符串类型的Token
        return token;
    }

    /**
     * 判断Token是否过期
     * 使用Token有效载荷中的过期时间与当前时间进行比较
     *
     * @param token 要判断的Token字符串
     * @return 是否过期
     */
    public static boolean isTokenExpired(String token) {
        try {
            //从Token中获取有效载荷
            Claims claims = parseToken(token);
            //从有效载荷中获取用户名
            String username = claims.get(USERNAME, String.class);
            if (StringUtils.isEmpty(username)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 获取token的剩余过期时间
     */
    public static long getRemainingTime(String token) {
        long result = 0;
        try {
            long nowMillis = System.currentTimeMillis();
            result = parseToken(token).getExpiration()
                                      .getTime() - nowMillis;
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 解析Token字符串并且从其中的有效载荷中获取指定Key的元素
     */
    public static String getElement(String token, String key) {
        Object element;
        try {
            Claims claims = parseToken(token);
            element = claims.get(key);
        } catch (Exception e) {
            return "";
        }
        return element == null ? "" : element.toString();
    }

    /**
     * 解析Token字符串并且从其中的有效载荷中获取指定Key的元素，获取的是指定泛型类型的元素
     *
     * @param token 解析哪个Token字符串并获取其中的有效载荷
     * @param key   有效载荷中元素的Key
     * @param clazz 指定获取元素的类型
     * @param <T>   元素的类型
     * @return 要获取的元素
     */
    public static <T> T getElement(String token, String key, Class<T> clazz) {
        T element;
        try {
            Claims claims = parseToken(token);
            element = claims.get(key, clazz);
        } catch (JwtException e) {
            e.printStackTrace();
            return null;
        }
        return element;
    }

    /**
     * 根据Token字符串获取其有效载荷,同时也是在校验Token的有效性
     * 需要使用特定的密钥来解析该Token字符串，该解析密钥必须与加密密钥一致
     * 如果解析失败则会抛出JwtException异常，解析失败的原因有很多种：
     * - 令牌过期
     * - 令牌签名验证不通过
     * - 令牌结构不正确
     * - 令牌有效载荷中数据的类型不匹配
     * ...
     * 抛出JwtException表示该Token无效！
     *
     * @param token 要解析的Token字符串
     * @return 要获取的有效载荷
     * @throws JwtException Token解析错误的异常信息
     */
    public static Claims parseToken(String token) throws JwtException {
        //在JwtParser解析器中配置用于解析的密钥，然后将Token字符串解析为Jws对象，最后从Jws对象中获取Claims
        Claims claims = Jwts
                .parser()//获取DefaultJwtParser
                .setSigningKey(BASE64_SECRET_KEY)//为获取DefaultJwtParser设置签名时使用的密钥
                .parseClaimsJws(token)//解析Token
                .getBody();//获取Claims
        return claims;
    }



    /*================================= 属性设置器 =================================*/

    /**
     * 设置Token有效期，可以使用链式编程
     *
     * @param expirationTime Token有效期(单位为毫秒)
     * @return 返回当前JwtUtils对象
     */
    public JwtUtils setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
        return this;
    }

    /**
     * 设置Jwt的加密算法，可以使用链式编程
     *
     * @param signatureAlgorithm 加密算法
     * @return 返回当前JwtUtils对象
     */
    public JwtUtils setSignatureAlgorithm(SignatureAlgorithm signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
        return this;
    }

    /**
     * 设置Jwt的加密密钥，可以使用链式编程
     *
     * @param secretKey 加密密钥
     * @return 返回当前JwtUtils对象
     */
    public JwtUtils setSecretKey(String secretKey) {
        this.secretKey = secretKey;
        return this;
    }
}
