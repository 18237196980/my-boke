package com.bo.ke.myboke.config.shiro;

import com.bo.ke.myboke.common.Const;
import com.bo.ke.myboke.exception.CusException;
import com.bo.ke.myboke.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 鉴权登录拦截器
 **/
@Slf4j
public class AuthFilter extends BasicHttpAuthenticationFilter {

    /**
     * 在Filter中注入HandlerExceptionResolver
     **/
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    /**
     * 执行登录认证
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try {
            executeLogin(request, response);
            return true;
        } catch (Exception e) {
            // throw new AuthenticationException("Token失效，请重新登录", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws AuthenticationException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String requestURI = ((HttpServletRequest) request).getRequestURI();
        log.info("请求路径：{}", requestURI);
        String token = httpServletRequest.getHeader(Const.AUTH);

        if (StringUtils.isEmpty(token)) {
            throw new AuthenticationException("token为空，请重新登录!");
        }
        boolean expired = JwtUtils.isTokenExpired(token);
        long remainingTime = JwtUtils.getRemainingTime(token);
        log.info("剩余过期时间:" + remainingTime);
        log.info("是否过期:" + expired);

        if (expired) {
            throw new CusException(-11, "token失效，请重新登陆！");
            /** 通过HandlerExceptionResolver抛出可被全局异常处理捕获到的异常 **/
            //resolver.resolveException(httpServletRequest, resp, null, new CusException(407, "token失效啦"));
        }

        WebToken webToken = new WebToken(token);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(webToken);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod()
                              .equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
