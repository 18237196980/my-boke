package com.bo.ke.myboke;

import com.bo.ke.myboke.service.MBlogService;
import com.bo.ke.myboke.service.OtherService;
import com.bo.ke.myboke.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MyBokeApplicationTests {

    @Autowired
    UserService userService;

    @Autowired
    MBlogService mBlogService;

    @Autowired
    OtherService otherService;



    @Test
    public void test1() {
        try {
            userService.testTrans();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        mBlogService.addMblog();
//        otherService.test();
    }

}
