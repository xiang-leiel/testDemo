package com.example.test.controller;

import com.example.test.utils.DateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


/**
 * @Description 
 * @author leiel
 * @Date 2019/12/14 5:11 PM
 */
@RestController
@RequestMapping("/love")
public class testController {

    @GetMapping("/timeDistance")
    public String timeDistance() {

        //获取两个时间的时间差
        int days = DateUtils.daysBetween(DateUtils.getDateYYYYMMdd("2019-10-16"), new Date());

        return "傻白甜，这是我们在一起的第"+days+"天哦, 好爱好爱你！！！么么哒";

    }

}
