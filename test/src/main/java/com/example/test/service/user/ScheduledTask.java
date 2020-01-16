package com.example.test.service.user;

import com.example.test.utils.DateUtils;
import com.example.test.utils.common.DingDingSend;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description 
 * @author leiel
 * @Date 2020/1/16 9:21 AM
 */
@Component
@EnableScheduling
public class ScheduledTask {

    @Scheduled(cron="0 0 8 1/1 * ?")
    private void schedule() {

        //获取两个时间的时间差
        int days = DateUtils.daysBetween(DateUtils.getDateYYYYMMdd("2019-10-16"), new Date());

        DingDingSend.sendMarkdown("我的小可爱", new StringBuffer().
                append("傻白甜，这是我们在一起的第").append(days).append("天哦, 好爱好爱你！！！").
                append("\n\n").
                append("么么哒, muma"), null, "1bd203cfb2805fc428ac6e576235bb84362939aa4cede116dbf26421db66a77a", true);

    }

}
