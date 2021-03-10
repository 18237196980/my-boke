package com.bo.ke.myboke.common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonSerialize(
        using = ResultSerializer.class
)
public class Result implements Serializable {
    private int code;
    private String msg;
    private Object data;

    public static Result success() {
        return new Result(1, null);
    }

    public static Result success(Object data) {
        return new Result(1, data);
    }

    public static Result error(String msg) {
        return new Result(0, msg);
    }

    public Result(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", this.code);
        map.put("msg", this.msg);
        map.put("data", this.data);
        return map;
    }
}
