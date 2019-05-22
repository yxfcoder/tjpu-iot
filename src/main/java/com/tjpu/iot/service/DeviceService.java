package com.tjpu.iot.service;

import com.tjpu.iot.common.*;
import com.tjpu.iot.dao.DeviceMapper;
import com.tjpu.iot.dao.UserMapper;
import com.tjpu.iot.dto.DeviceLogDto;
import com.tjpu.iot.pojo.Device;
import com.tjpu.iot.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Slf4j
@Service
public class DeviceService {

    @Resource
    private DeviceMapper deviceMapper;

    @Resource
    private UserMapper userMapper;

    /**
     * addDevice: 添加设备
     *
     * @param device
     * @param session
     * @return
     */
    public ResponseResult addDevice(Device device, HttpSession session) {
        log.info("[DeviceService] addDevice() 进入添加设备方法");
        String userId = (String) session.getAttribute(session.getId());
        if (StringUtils.isBlank(userId)) {
            return new ResponseResult(false, "用户未登录", StatusCode.ERROR_UNAUTHORIZED);
        }
        if (device == null) {
            log.info("[DeviceService] addDevice() 参数为空");
            return new ResponseResult(false, "参数为空", StatusCode.ERROR_INVALID_RREQUEST);
        }
        try {
            device.setDeviceUserId(userId);
            device.setDeviceRunState("NORMAL");
            device.setDeviceState("OFFLINE");
            if (deviceMapper.insertSelective(device) == 0) {
                log.info("[DeviceService] addDevice() 添加设备执行失败");
                return new ResponseResult(false, "添加设备失败", StatusCode.OPERATIONERROR);
            }
            log.info("[DeviceService] addDevice() 添加设备成功: " + device.toString());
            return new ResponseResult(true, "添加设备信息成功", StatusCode.SUCCESS_POST_PUT_PATCH);
        } catch (DuplicateKeyException e) {
            log.info("[DeviceService] addDevice() 添加设备出错，违反唯一约束");
            return new ResponseResult(false, "设备字段重复", StatusCode.OPERATIONERROR);
        }
    }

    /**
     * deleteDevice: 删除设备
     *
     * @param deviceId
     * @return
     */
    public ResponseResult deleteDevice(String deviceId) {
        log.info("[DeviceService] deleteDevice() 进入删除设备方法");
        if (StringUtils.isBlank(deviceId)) {
            log.info("[DeviceService] deleteDevice() deviceId为空，不能删除设备");
            return new ResponseResult(false, "参数为空", StatusCode.ERROR_INVALID_RREQUEST);
        }
        int result = deviceMapper.deleteByPrimaryKey(deviceId);
        if (result == 0) {
            log.info("[DeviceService] deleteDevice() 设备不存在，deviceId: " + deviceId);
            return new ResponseResult(false, "设备不存在", StatusCode.OPERATIONERROR);
        }
        log.info("[DeviceService] deleteDevice() 删除设备成功: " + deviceId);
        return new ResponseResult(true, "删除设备成功", StatusCode.SUCCESS_DELETE);
    }

    /**
     * updateDevice: 更新设备信息
     *
     * @param device
     * @return
     */
    public ResponseResult updateDevice(Device device) {
        log.info("[DeviceService] updateDevice() 进入更新设备方法");
        if (device == null) {
            log.info("[DeviceService] updateDevice() 参数为空");
            return new ResponseResult(false, "参数为空", StatusCode.ERROR_INVALID_RREQUEST);
        }
        if (deviceMapper.updateByPrimaryKeySelective(device) == 0) {
            log.info("[DeviceService] updateDevice() 更新设备执行失败");
            return new ResponseResult(false, "更新设备失败", StatusCode.OPERATIONERROR);
        }
        log.info("[DeviceService] updateDevice() 更新设备成功: " + device.toString());
        return new ResponseResult(true, "更新设备成功", StatusCode.SUCCESS_POST_PUT_PATCH);
    }

    /**
     * queryDevice: 查询指定设备
     *
     * @param deviceId
     * @return
     */
    public ResponseResult queryDevice(String deviceId) {
        log.info("[DeviceService] queryDevice() 进入查询设备方法");
        if (StringUtils.isBlank(deviceId)) {
            log.info("[DeviceService] queryDevice() deviceId为空，不能查询设备");
            return new ResponseResult(false, "参数为空", StatusCode.ERROR_INVALID_RREQUEST);
        }
        Device device = deviceMapper.selectByPrimaryKey(deviceId);
        if (device == null) {
            log.info("[DeviceService] queryDevice() 设备不存在，deviceId: " + deviceId);
            return new ResponseResult(false, "设备不存在", StatusCode.OPERATIONERROR);
        }
        log.info("[DeviceService] queryDevice() 查询设备成功: " + device.toString());
        return new ResponseResult(device, true, "查询设备成功", StatusCode.SUCCESS_GET);
    }

    /**
     * queryAllDevices: 查询所有设备
     *
     * @return
     */
    public ResponseResult queryAllDevices() {
        log.info("[DeviceService] queryAllDevices() 进入查询所有设备方法");
        List<Device> deviceList = deviceMapper.queryAllDevice();
        if (CollectionUtils.isEmpty(deviceList)) {
            log.info("[DeviceService] queryAllDevices() 没有查到设备");
            return new ResponseResult(false, "没有查到设备", StatusCode.OPERATIONERROR);
        }
        log.info("[DeviceService] queryAllDevices() 查询所有设备成功，共查到" + deviceList.size() + "条设备数据");
        return new ResponseResult(deviceList, true, "查询设备成功", StatusCode.SUCCESS_GET);
    }

    /**
     * getDevicesLogs: 查询设备日志
     *
     * @param year
     * @param month
     * @param day
     * @param session
     * @return
     */
    public ResponseResult getDevicesLogs(String year, String month, String day, HttpSession session) {
        log.info("[DeviceService] getDevicesLogs() 进入获取设备日志方法");
        String userId = (String) session.getAttribute(session.getId());
        if (StringUtils.isBlank(userId)) {
            return new ResponseResult(false, "用户未登录", StatusCode.ERROR_UNAUTHORIZED);
        }
        List<Device> deviceList = deviceMapper.queryByUserId(userId);
        if (deviceList.size() == 0) {
            return new ResponseResult(false, "用户没有设备", StatusCode.OPERATIONERROR);
        }
        List<DeviceLogDto> deviceLogDtoList = new ArrayList<>();
        deviceList.forEach(device -> {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("apiKey", TJPUCommon.KEY);
            HttpEntity<String> entity = new HttpEntity<>(null, headers);
            ResponseEntity<RestResult> result = restTemplate.
                    exchange("https://tjiot.net/EDoctor/api/v1/device/log/listPagedDatedDeviceLogById?device_id="
                                    + device.getDeviceId()
                                    + "&year=" + year +
                                    "&month=" + month +
                                    "&day=" + day +
                                    "&pageNum=1&pageSize=1000000000000",
                            HttpMethod.GET, entity, RestResult.class);
            result.getBody().getData().getData().forEach(deviceLogDto -> deviceLogDtoList.add(deviceLogDto));
        });
        deviceLogDtoList.removeIf(deviceLogDto -> !Optional.ofNullable(deviceLogDto.getService().getData().getTemperature()).isPresent());
        deviceLogDtoList.forEach(deviceLogDto -> {
            deviceLogDto.setServiceId(deviceLogDto.getService().getServiceId());
            deviceLogDto.setServiceType(deviceLogDto.getService().getServiceType());
            deviceLogDto.setElectricity(deviceLogDto.getService().getData().getTemperature());
            deviceLogDto.setEventTime(deviceLogDto.getService().getEventTime());
        });
        log.info("[DeviceService] getDevicesLogs() 获取设备日志成功");
        return new ResponseResult(deviceLogDtoList, true, "获取设备日志成功", StatusCode.SUCCESS_GET);
    }

    public ResponseResult getDevicesStatus(HttpSession session) {
        log.info("[DeviceService] getDevicesStatus() 进入获取设备状态方法");
        String userId = (String) session.getAttribute(session.getId());
        if (StringUtils.isBlank(userId)) {
            return new ResponseResult(false, "用户未登录", StatusCode.ERROR_UNAUTHORIZED);
        }
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            return new ResponseResult(false, "没有找到用户", StatusCode.ERROR_INTERNAL_SERVER_ERROR);
        }
        List<Integer> list = new ArrayList();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("apiKey", TJPUCommon.KEY);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<RestDeviceStatusResult> result = restTemplate.
                exchange("https://tjiot.net/EDoctor/api/v1/Device/operation/getDeviceStatusAndStatusDetailStatistics?companyId="
                                + user.getUserCompanyId(), HttpMethod.GET, entity, RestDeviceStatusResult.class);
        if (result.getBody().getData().getStatus().getOnline() == null) {
            list.add(0);
            list.add(0);
            list.add(0);
            list.add(0);
        }
        list.add(result.getBody().getData().getStatus().getOnline());
        list.add(0);
        list.add(0);
        list.add(0);
        log.info("[DeviceService] getDevicesStatus() 获取设备状态成功");
        return new ResponseResult(list, true, "获取设备状态成功", StatusCode.SUCCESS_GET);
    }

    public ResponseResult getDevicesDownTimes(HttpSession session) {
        log.info("[DeviceService] getDevicesLogs() 进入获取设备日志方法");
        String userId = (String) session.getAttribute(session.getId());
        if (StringUtils.isBlank(userId)) {
            return new ResponseResult(false, "用户未登录", StatusCode.ERROR_UNAUTHORIZED);
        }
        List<Device> deviceList = deviceMapper.queryByUserId(userId);
        if (deviceList.size() == 0) {
            return new ResponseResult(false, "用户没有设备", StatusCode.OPERATIONERROR);
        }
        List<DeviceLogDto> deviceLogDtoList = new ArrayList<>();
        deviceList.forEach(device -> {
            for (int i = 0; i < 31; i++) {
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.add("api 8Key", TJPUCommon.KEY);
                HttpEntity<String> entity = new HttpEntity<>(null, headers);
                ResponseEntity<RestResult> result = restTemplate.
                exchange("https://tjiot.net/EDoctor/api/v1/device/log/listPagedDatedDeviceLogById?device_id="
                                + device.getDeviceId()
                                + "&year=" + "2019" +
                                "&month=" + "05" +
                                "&day=" + i +
                                "&pageNum=1&pageSize=1000000000000",
                        HttpMethod.GET, entity, RestResult.class);
                result.getBody().getData().getData().forEach(deviceLogDto -> deviceLogDtoList.add(deviceLogDto));
            }
        });
        deviceLogDtoList.removeIf(deviceLogDto -> !Optional.ofNullable(deviceLogDto.getService().getData().getTemperature()).isPresent());
        deviceLogDtoList.removeIf(deviceLogDto -> !deviceLogDto.getService().getData().getTemperature().equals(0));
        return new ResponseResult(deviceLogDtoList.size(), true, "获取设备停机次数成功", StatusCode.SUCCESS_GET);
    }
}