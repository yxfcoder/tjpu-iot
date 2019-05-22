package com.tjpu.iot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeviceStatusDto {

    private Integer online;

    private Integer offline;

    private Integer load;

    private Integer trouble;
}
