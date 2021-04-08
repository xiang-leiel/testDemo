package com.example.test.controller;

import com.example.test.config.TestConfiguration;
import com.example.test.controller.test.CarVerifyService;
import com.example.test.controller.test.CarVerifyServiceImpl;
import com.example.test.controller.test.User;
import com.example.test.dao.UserInfoMapper;
import com.example.test.entity.UserInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description 
 * @author leiel
 * @Date 2019/12/14 5:11 PM
 */
@RestController
@RequestMapping("/love")
public class testController {

    @Resource
    private UserInfoMapper userInfoMapper;

    @GetMapping("/timeDistance")
    public String timeDistance() {

/*        userInfoMapper.insert(userInfo1);*/

        //获取两个时间的时间差
        //int days = DateUtils.daysBetween(DateUtils.getDateYYYYMMdd("2019-10-16"), new Date());

        return "傻白甜，这是我们在一起的第"+""+"天哦, 好爱好爱你！！！么么哒";

    }
    @GetMapping("/test")
    public void test() {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("甜");
        userInfo.setPhone("18188181818");
        userInfoMapper.insert(userInfo);

        userInfoMapper.selectAll();

        System.out.println(userInfo);

    }

    @Resource
    private CarVerifyService carVerifyService;

    @Resource
    private TestConfiguration testConfiguration;

    @GetMapping("/testObject")
    public void testObject() {
        User user = new User();
        user.setName("项磊");
        user.setUid("001");

        carVerifyService.verify(user);

    }




}
