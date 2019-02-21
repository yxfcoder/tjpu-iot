package com.tjpu.iot.service;

import com.tjpu.iot.common.ResponseResult;
import com.tjpu.iot.pojo.Device;

public interface DeviceService {

    ResponseResult addDevice(Device device);

    ResponseResult deleteDevice(String deviceId);

    ResponseResult updateDevice(Device device);

    ResponseResult queryDevice(String deviceId);

    ResponseResult queryAllDevices();
}
