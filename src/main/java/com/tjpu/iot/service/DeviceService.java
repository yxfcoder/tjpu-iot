package com.tjpu.iot.service;

import com.tjpu.iot.common.ResponseResult;
import com.tjpu.iot.common.StatusCode;
import com.tjpu.iot.dao.DeviceMapper;
import com.tjpu.iot.pojo.Device;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class DeviceService {

    @Resource
    private DeviceMapper deviceMapper;

    public ResponseResult addDevice(Device device) {
        log.info("[DeviceServiceImpl] addDevice() 进入添加设备方法");
        if (device == null) {
            log.info("[DeviceServiceImpl] addDevice() 参数为空");
            return new ResponseResult(false, "参数为空", StatusCode.ERROR_INVALID_RREQUEST);
        }
        try {
            if (deviceMapper.insertSelective(device) == 0) {
                log.info("[DeviceServiceImpl] addDevice() 添加设备执行失败");
                return new ResponseResult(false, "添加设备失败", StatusCode.OPERATIONERROR);
            }
            log.info("[DeviceServiceImpl] addDevice() 添加设备成功: " + device.toString());
            return new ResponseResult(true, "添加设备信息成功", StatusCode.SUCCESS_POST_PUT_PATCH);
        } catch (DuplicateKeyException e) {
            log.info("[DeviceServiceImpl] addDevice() 添加设备出错，违反唯一约束");
            return new ResponseResult(false, "设备字段重复", StatusCode.OPERATIONERROR);
        }
    }

    public ResponseResult deleteDevice(String deviceId) {
        log.info("[DeviceServiceImpl] deleteDevice() 进入删除设备方法");
        if (StringUtils.isBlank(deviceId)) {
            log.info("[DeviceServiceImpl] deleteDevice() deviceId为空，不能删除设备");
            return new ResponseResult(false, "参数为空", StatusCode.ERROR_INVALID_RREQUEST);
        }
        int result = deviceMapper.deleteByPrimaryKey(deviceId);
        if (result == 0) {
            log.info("[DeviceServiceImpl] deleteDevice() 设备不存在，deviceId: " + deviceId);
            return new ResponseResult(false, "设备不存在", StatusCode.OPERATIONERROR);
        }
        log.info("[DeviceServiceImpl] deleteDevice() 删除设备成功: " + deviceId);
        return new ResponseResult(true, "删除设备成功", StatusCode.SUCCESS_DELETE);
    }

    public ResponseResult updateDevice(Device device) {
        log.info("[DeviceServiceImpl] updateDevice() 进入更新设备方法");
        if (device == null) {
            log.info("[DeviceServiceImpl] updateDevice() 参数为空");
            return new ResponseResult(false, "参数为空", StatusCode.ERROR_INVALID_RREQUEST);
        }
        if (deviceMapper.updateByPrimaryKeySelective(device) == 0) {
            log.info("[DeviceServiceImpl] updateDevice() 更新设备执行失败");
            return new ResponseResult(false, "更新设备失败", StatusCode.OPERATIONERROR);
        }
        log.info("[DeviceServiceImpl] updateDevice() 更新设备成功: " + device.toString());
        return new ResponseResult(true, "更新设备成功", StatusCode.SUCCESS_POST_PUT_PATCH);
    }

    public ResponseResult queryDevice(String deviceId) {
        log.info("[DeviceServiceImpl] queryDevice() 进入查询设备方法");
        if (StringUtils.isBlank(deviceId)) {
            log.info("[DeviceServiceImpl] queryDevice() deviceId为空，不能查询设备");
            return new ResponseResult(false, "参数为空", StatusCode.ERROR_INVALID_RREQUEST);
        }
        Device device = deviceMapper.selectByPrimaryKey(deviceId);
        if (device == null) {
            log.info("[DeviceServiceImpl] queryDevice() 设备不存在，deviceId: " + deviceId);
            return new ResponseResult(false, "设备不存在", StatusCode.OPERATIONERROR);
        }
        log.info("[DeviceServiceImpl] queryDevice() 查询设备成功: " + device.toString());
        return new ResponseResult(device, true, "查询设备成功", StatusCode.SUCCESS_GET);
    }

    public ResponseResult queryAllDevices() {
        log.info("[DeviceServiceImpl] queryAllDevices() 进入查询所有设备方法");
        List<Device> deviceList = deviceMapper.queryAllDevice();
        if (CollectionUtils.isEmpty(deviceList)) {
            log.info("[DeviceServiceImpl] queryAllDevices() 没有查到设备");
            return new ResponseResult(false, "没有查到设备", StatusCode.OPERATIONERROR);
        }
        log.info("[DeviceServiceImpl] queryAllDevices() 查询所有设备成功，共查到" + deviceList.size() + "条设备数据");
        return new ResponseResult(deviceList, true, "查询设备成功", StatusCode.SUCCESS_GET);
    }
}
