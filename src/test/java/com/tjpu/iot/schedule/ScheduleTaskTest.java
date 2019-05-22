package com.tjpu.iot.schedule;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduleTaskTest {

    @Test
    public void testDataAnalysis() {
//        ScheduleTask scheduleTask = new ScheduleTask();
//        scheduleTask.dataAnalysis();
//        long a = 31 * 8 * 60;
//        log.info("a：" + a);
        float timeOEE = (float)943 / (31 * 8 * 60);
        log.info("时间加工率：" + timeOEE);
    }

}