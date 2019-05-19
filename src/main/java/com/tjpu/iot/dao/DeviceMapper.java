package com.tjpu.iot.dao;

import com.tjpu.iot.pojo.Device;

import java.util.List;

import com.tjpu.iot.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DeviceMapper {

    /**
     * insertSelective: 动态添加设备
     *
     * @param device
     * @return
     */
    int insertSelective(Device device);

    /**
     * deleteByPrimaryKey: 通过设备编号删除设备
     *
     * @param deviceId
     * @return
     */
    int deleteByPrimaryKey(String deviceId);

    /**
     * updateByPrimaryKeySelective: 动态更新设备信息
     *
     * @param device
     * @return
     */
    int updateByPrimaryKeySelective(Device device);

    /**
     * selectByPrimaryKey: 通过设备编号查询指定设备
     *
     * @param deviceId
     * @return
     */
    Device selectByPrimaryKey(String deviceId);

    /**
     * queryAllDevice: 查询所有设备
     *
     * @return
     */
    List<Device> queryAllDevice();

    /**
     * queryByUserId: 通过用户编号查询该用户的设备
     *
     * @param userId
     * @return
     */
    List<Device> queryByUserId(String userId);
}