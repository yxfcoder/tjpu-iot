package com.tjpu.iot.common;

import com.tjpu.iot.dto.DeviceLogDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RestResult {

    private Integer code;

    private String msg;

    private List<DeviceLogDto> data;
}
