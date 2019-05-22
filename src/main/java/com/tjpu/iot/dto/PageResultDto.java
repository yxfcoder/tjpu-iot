package com.tjpu.iot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PageResultDto {

    private Integer totalRowCount;

    private Integer totalPageCount;

    private Integer previousPageNum;

    private Integer currentPageNum;

    private Integer nextPageNum;

    private Long pageSize;

    private List<DeviceLogDto> data;
}
