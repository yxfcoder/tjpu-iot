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
public class DeviceServiceDto {

    private String serviceId;

    private String serviceType;

    private DeviceDataDto data;

    private Date eventTime;
}
