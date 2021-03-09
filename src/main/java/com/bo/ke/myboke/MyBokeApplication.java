package com.bo.ke.myboke;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.bo.ke.myboke.mapper")
public class MyBokeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyBokeApplication.class, args);
    }

}
