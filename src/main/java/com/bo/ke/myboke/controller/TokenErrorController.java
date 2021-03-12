package com.bo.ke.myboke.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class TokenErrorController extends BasicErrorController {

    public TokenErrorController() {
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }

    @Override
    @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
        HttpStatus status = getStatus(request);

        JSONObject result = new JSONObject();
        result.put("code", 403);
        result.put("msg", body.get("message"));
        return new ResponseEntity(result.toString(), status);
    }
}
