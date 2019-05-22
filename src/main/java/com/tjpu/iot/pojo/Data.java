package com.tjpu.iot.pojo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Data {

    private Long dataId;

    private String deviceId;

    private String year;

    private String month;

    private Integer downTimes;

    private String downTime;

    private String timeOEE;

    private String performanceOEE;

    private String goodOEE;
}
