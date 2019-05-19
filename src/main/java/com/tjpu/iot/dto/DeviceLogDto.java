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

    private String requestId;

    private String companyId;

    private String appId;

    private String deviceId;

    private String gatewayId;

    private DeviceServiceDto service;

    private String logType;

    private String serviceId;

    private String serviceType;

    private String electricity;

    private Date eventTime;
}
