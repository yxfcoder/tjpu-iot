package com.tjpu.iot.dao;

import com.tjpu.iot.pojo.Data;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DataMapper {

    int insertSelective(Data data);

    int updateByPrimaryKeySelective(Data data);

    int updateDownTime(Data data);

    Data selectByPrimaryKey(String dataId);

    Data queryByYearAndMonth(String year, String month);
}
