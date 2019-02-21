package com.tjpu.iot.controller;

import com.tjpu.iot.common.ResponseResult;
import com.tjpu.iot.pojo.Device;
import com.tjpu.iot.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/tjpu/iot/device/")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @RequestMapping(value = "device", method = RequestMethod.POST)
    public ResponseResult add(@RequestBody Device device) {
        log.info("[DeviceController] add() 进入添加设备方法");
        return deviceService.addDevice(device);
    }

    @RequestMapping(value = "{deviceId}", method = RequestMethod.DELETE)
    public ResponseResult delete(@PathVariable("deviceId") String deviceId) {
        log.info("[DeviceController] delete() 进入删除设备方法");
        return deviceService.deleteDevice(deviceId);
    }

    @RequestMapping(value = "device", method = RequestMethod.PUT)
    public ResponseResult update(@RequestBody Device device) {
        log.info("[DeviceController] update() 进入更新设备方法");
        return deviceService.updateDevice(device);
    }

    @RequestMapping(value = "{deviceId}", method = RequestMethod.GET)
    public ResponseResult queryOne(@PathVariable("deviceId") String deviceId) {
        log.info("[DeviceController] queryOne() 进入查询设备方法");
        return deviceService.queryDevice(deviceId);
    }

    @RequestMapping(value = "device", method = RequestMethod.GET)
    public ResponseResult queryAll() {
        log.info("[DeviceController] queryAll() 进入查询所有设备方法");
        return deviceService.queryAllDevices();
    }
}
