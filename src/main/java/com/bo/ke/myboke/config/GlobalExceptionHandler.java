package com.bo.ke.myboke.config;

import com.bo.ke.myboke.exception.CusException;
import com.ex.framework.data.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义异常
     */
    /*@ExceptionHandler(CusException.class)
    public Result handleRRException(CusException e) {
        log.error(e.getMessage(), e);
        return Result.error(409,"全局捕获token");
    }*/
    @ExceptionHandler(UnauthorizedException.class)
    public Result handleAuthorizationException(UnauthorizedException e) {
        log.error(e.getMessage(), e);
        return Result.error("没有权限，请联系管理员授权");
    }

    /**
     * 认证异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(AuthenticationException.class)
    public Result handleAuthorizationException(AuthenticationException e) {
        // log.error(e.getMessage(), e);
        return Result.error(-1, "认证过期，请重新登陆");
    }


    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.error("操作失败，" + e.getMessage());
    }

}
