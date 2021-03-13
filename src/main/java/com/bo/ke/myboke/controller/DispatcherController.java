package com.bo.ke.myboke.controller;

import com.ex.framework.data.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常转发跳转controller
 */
@RestController
@RequestMapping("/dispatcher")
@Slf4j
public class DispatcherController {

    @RequestMapping(value = "/-1")
    public Result notLogin(HttpServletRequest request) {
        // 取出错误信息
        String errMsg = (String) request.getAttribute("errMsg");
        log.info("错误信息:" + errMsg);
        return Result.error(-1, errMsg);
    }

}
