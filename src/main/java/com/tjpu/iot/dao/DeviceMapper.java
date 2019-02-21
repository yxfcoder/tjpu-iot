package com.tjpu.iot.dao;

import com.tjpu.iot.pojo.Device;

import java.util.List;

import com.tjpu.iot.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DeviceMapper {

    int insertSelective(Device device);

    int deleteByPrimaryKey(String deviceId);

    int updateByPrimaryKeySelective(Device device);

    Device selectByPrimaryKey(String deviceId);

    List<Device> queryAllDevice();
}