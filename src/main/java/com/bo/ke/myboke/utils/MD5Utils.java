package com.bo.ke.myboke.utils;

import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;


public class MD5Utils {

    public static String md5(String str){
        if (StringUtils.isEmpty(str)){
            return null;
        }
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

}
