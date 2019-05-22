package com.tjpu.iot.common;

import com.tjpu.iot.dto.PageResultDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RestResult {

    private Integer code;

    private String msg;

    private PageResultDto data;
}
