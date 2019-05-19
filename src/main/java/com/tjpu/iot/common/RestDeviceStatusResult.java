package com.tjpu.iot.common;

import com.tjpu.iot.dto.RestDeviceStatusDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RestDeviceStatusResult {

    private Integer code;

    private String msg;

    private RestDeviceStatusDto data;
}
