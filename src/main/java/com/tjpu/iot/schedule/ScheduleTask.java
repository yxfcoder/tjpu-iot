package com.tjpu.iot.schedule;

import com.tjpu.iot.common.RestResult;
import com.tjpu.iot.common.TJPUCommon;
import com.tjpu.iot.dao.DataMapper;
import com.tjpu.iot.dao.DeviceMapper;
import com.tjpu.iot.dto.DeviceLogDto;
import com.tjpu.iot.dto.DownTimeDto;
import com.tjpu.iot.pojo.Data;
import com.tjpu.iot.pojo.Device;
import com.tjpu.iot.service.DeviceService;
import com.tjpu.iot.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@Configuration
@EnableScheduling
public class ScheduleTask {

    private static final String deviceId = "aeb2fcbf-6483-4add-8de9-2ff3afd155c9";

    @Autowired
    private DeviceService deviceService;

    @Resource
    private DeviceMapper deviceMapper;

    @Resource
    private DataMapper dataMapper;

    @Scheduled(fixedRate = 600000)
    public void dataAnalysis() {
        String year = String.valueOf(DateUtil.getYear());
        String month = String.valueOf(DateUtil.getMonth());
        int days = DateUtil.getDaysOfMonth(new Date());
        List<DeviceLogDto> deviceLogDtoList = new ArrayList<>();
        for (int i = 1; i <= days; i++) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("apiKey", TJPUCommon.KEY);
            HttpEntity<String> entity = new HttpEntity<>(null, headers);
            ResponseEntity<RestResult> result = restTemplate.
                    exchange("https://tjiot.net/EDoctor/api/v1/device/log/listPagedDatedDeviceLogById?device_id="
                                    + deviceId
                                    + "&year=" + year +
                                    "&month=" + month +
                                    "&day=" + i +
                                    "&pageNum=1&pageSize=1000000000000",
                            HttpMethod.GET, entity, RestResult.class);
            result.getBody().getData().getData().forEach(deviceLogDto -> deviceLogDtoList.add(deviceLogDto));
//            deviceLogDtoList.removeIf(deviceLogDto -> !deviceLogDto.getService().getData().getTemperature().equals(0));
        }
        // 去除心跳包
        deviceLogDtoList.removeIf(deviceLogDto -> !Optional.ofNullable(deviceLogDto.getService().getData().getTemperature()).isPresent());
        // 更新设备状态
        int electricity = Integer.parseInt(deviceLogDtoList.get(deviceLogDtoList.size() - 1).getElectricity());
        Device device = new Device();
        device.setDeviceId(deviceId);
        device.setDeviceState("在线");
        if (electricity == 0) {
            device.setDeviceRunState("关机");
            deviceMapper.updateByPrimaryKeySelective(device);
        } else if (electricity > 0 && electricity <= 840) {
            device.setDeviceRunState("空载");
            deviceMapper.updateByPrimaryKeySelective(device);
        } else if (electricity > 840) {
            device.setDeviceRunState("负载");
            deviceMapper.updateByPrimaryKeySelective(device);
        }

        // 计算停机时间
        long downTime;
        List<DownTimeDto> downTimeDtoList = new ArrayList<>();
        List<DeviceLogDto> deviceLogDtoListDown = new ArrayList<>();
        deviceLogDtoList.forEach(deviceLogDto -> deviceLogDtoListDown.add(deviceLogDto));
        int a = 0;
        int b = 0;
        for (int i = 0; i < deviceLogDtoListDown.size() - 1; i++) {
            if (a == 0) {
                if (deviceLogDtoListDown.get(i).getElectricity().equals("0")) {
                    downTimeDtoList.add(new DownTimeDto(deviceLogDtoListDown.get(i).getEventTime(), null));
                    a = 1;
                }
            } else if (a == 1) {
                if (!deviceLogDtoListDown.get(i).getElectricity().equals("0")) {
                    downTimeDtoList.get(b).setEnd(deviceLogDtoListDown.get(i).getEventTime());
                    a = 0;
                    b ++;
                }
            }

        }
        downTime = downTimeDtoList.stream().mapToLong(downTimeDto -> DateUtil.getDatePoorMin(downTimeDto.getStart(), downTimeDto.getEnd())).sum();
        Data data = new Data();
        data.setDeviceId(deviceId);
        data.setYear(year);
        data.setMonth(month);
        data.setDownTime(String.valueOf(downTime));
        deviceLogDtoListDown.removeIf(deviceLogDto -> !deviceLogDto.getService().getData().getTemperature().equals(0));
        data.setDownTimes(deviceLogDtoListDown.size());
        dataMapper.updateDownTime(data);
    }
}
