package com.tjpu.iot.controller;

import com.tjpu.iot.common.ResponseResult;
import com.tjpu.iot.pojo.Device;
import com.tjpu.iot.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/tjpu/iot/device/")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    /**
     * add: 添加设备
     *
     * @param device
     * @param session
     * @return
     */
    @PostMapping("device")
    public ResponseResult add(@RequestBody Device device, HttpSession session) {
        log.info("[DeviceController] add() 进入添加设备方法");
        return deviceService.addDevice(device, session);
    }

    /**
     * delete: 删除设备
     *
     * @param deviceId
     * @return
     */
    @DeleteMapping("{deviceId}")
    public ResponseResult delete(@PathVariable("deviceId") String deviceId) {
        log.info("[DeviceController] delete() 进入删除设备方法");
        return deviceService.deleteDevice(deviceId);
    }

    /**
     * update: 更新设备
     *
     * @param device
     * @return
     */
    @PutMapping("device")
    public ResponseResult update(@RequestBody Device device) {
        log.info("[DeviceController] update() 进入更新设备方法");
        return deviceService.updateDevice(device);
    }

    /**
     * queryOne: 查询指定设备
     *
     * @param deviceId
     * @return
     */
    @GetMapping("{deviceId}")
    public ResponseResult queryOne(@PathVariable("deviceId") String deviceId) {
        log.info("[DeviceController] queryOne() 进入查询设备方法");
        return deviceService.queryDevice(deviceId);
    }

    /**
     * queryAll: 查询所有设备
     *
     * @return
     */
    @GetMapping("device")
    public ResponseResult queryAll() {
        log.info("[DeviceController] queryAll() 进入查询所有设备方法");
        return deviceService.queryAllDevices();
    }

    /**
     * getDevicesLogs: 获取设备日志
     *
     * @param year
     * @param month
     * @param day
     * @param session
     * @return
     */
    @GetMapping("log")
    public ResponseResult getDevicesLogs(String year, String month, String day, HttpSession session) {
        log.info("[DeviceController] getDevicesLogs() 进入获取设备日志方法");
        return deviceService.getDevicesLogs(year, month, day, session);
    }

    /**
     * getDevicesStatus: 获取设备状态
     *
     * @param session
     * @return
     */
    @GetMapping("status")
    public ResponseResult getDevicesStatus(HttpSession session) {
        log.info("[DeviceController] getDevicesStatus() 进入获取设备状态方法");
        return deviceService.getDevicesStatus(session);
    }

    @GetMapping("down")
    public ResponseResult getDevicesDownTimes(HttpSession session) {
        log.info("[DeviceController] getDevicesDownTimes() 进入获取设备停机次数方法");
        return deviceService.getDevicesDownTimes(session);
    }
}
