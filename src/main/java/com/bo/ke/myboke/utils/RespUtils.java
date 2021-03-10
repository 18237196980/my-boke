package com.bo.ke.myboke.utils;

import com.bo.ke.myboke.common.Const;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 返回流信息
 */
@Slf4j
public class RespUtils {

    public static void addHeader(HttpServletResponse response, String jwt) {
        response.addHeader("Access-Control-Expose-Headers", Const.AUTH);
        response.addHeader(Const.AUTH, jwt);
    }

    public static void returnJson(ServletResponse response, String json) {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);
        } catch (IOException e) {
            log.error("response error", e);
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
