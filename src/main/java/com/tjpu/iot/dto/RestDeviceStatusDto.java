package com.tjpu.iot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RestDeviceStatusDto {

    private RestDeviceStatusDetailsDto status;
//    private Object status;

    private Object statusDetail;
}
