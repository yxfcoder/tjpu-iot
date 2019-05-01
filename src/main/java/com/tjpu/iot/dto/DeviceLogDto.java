package com.tjpu.iot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeviceLogDto {

    private String id;

    private String deviceId;

    private String deviceType;

    private String serviceType;

    private String serviceId;

    private DeviceDataDto data;

    private String electricity;

    private Date eventTime;

    private String serviceInfo;

    private String log_type;
}
