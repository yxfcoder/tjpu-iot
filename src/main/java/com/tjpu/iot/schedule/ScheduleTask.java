package com.tjpu.iot.schedule;

import com.tjpu.iot.common.RestResult;
import com.tjpu.iot.common.TJPUCommon;
import com.tjpu.iot.dao.DataMapper;
import com.tjpu.iot.dao.DeviceMapper;
import com.tjpu.iot.dto.DeviceLogDto;
import com.tjpu.iot.dto.DownTimeDto;
import com.tjpu.iot.pojo.Data;
import com.tjpu.iot.pojo.Device;
import com.tjpu.iot.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Component
@Configuration
@EnableScheduling
public class ScheduleTask {

    private static final String deviceId = "aeb2fcbf-6483-4add-8de9-2ff3afd155c9";

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
        List<DeviceLogDto> deviceLogDtoListOEE = new ArrayList<>();
        List<Long> workTimeList = new ArrayList<>();
        List<Long> nullTimeList = new ArrayList<>();
        log.info("[ScheduleTask] dataAnalysis() 开始请求远程数据");
//        for (int i = 1; i <= days; i++) {
        for (int i = 1; i <= 9; i++) {
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
            result.getBody().getData().getData().forEach(deviceLogDto -> deviceLogDtoListOEE.add(deviceLogDto));
            deviceLogDtoListOEE.removeIf(deviceLogDto -> !Optional.ofNullable(deviceLogDto.getService().getData().getTemperature()).isPresent());
            if (deviceLogDtoListOEE.size() != 0) {
                deviceLogDtoListOEE.forEach(deviceLogDto -> {
                    deviceLogDto.setElectricity(deviceLogDto.getService().getData().getTemperature());
                    deviceLogDto.setEventTime(deviceLogDto.getService().getEventTime());
                });
                int finalI = i;
                deviceLogDtoListOEE.removeIf(deviceLogDto -> deviceLogDto.getEventTime().before(DateUtil.stringToDate(year + "-" + month + "-" + finalI + " 09:00:00")));
                deviceLogDtoListOEE.removeIf(deviceLogDto -> deviceLogDto.getEventTime().after(DateUtil.stringToDate(year + "-" + month + "-" + finalI + " 17:00:00")));
                long workTime;
                List<DownTimeDto> workTimeDtoList = new ArrayList<>();
                int a = 0;
                int b = 0;
                for (int j = 0; j < deviceLogDtoListOEE.size() - 1; j++) {
                    if (a == 0) {
                        if (!deviceLogDtoListOEE.get(j).getElectricity().equals("0")) {
                            workTimeDtoList.add(new DownTimeDto(deviceLogDtoListOEE.get(j).getEventTime(), null));
                            a = 1;
                        }
                    } else if (a == 1) {
                        if (deviceLogDtoListOEE.get(j).getElectricity().equals("0")) {
                            workTimeDtoList.get(b).setEnd(deviceLogDtoListOEE.get(j).getEventTime());
                            a = 0;
                            b ++;
                        }
                    }
                }
                workTimeDtoList.remove(workTimeDtoList.size() - 1);
                workTime = workTimeDtoList.stream().mapToLong(downTimeDto -> DateUtil.getDatePoorMin(downTimeDto.getStart(), downTimeDto.getEnd())).sum();
                workTimeList.add(workTime);

                long nullTime;
                List<DownTimeDto> nullTimeDtoList = new ArrayList<>();
                int c = 0;
                int d = 0;
                for (int j = 0; j < deviceLogDtoListOEE.size() - 1; j++) {
                    if (c == 0) {
                        if (deviceLogDtoListOEE.get(j).getElectricity().equals("0")) {
                            nullTimeDtoList.add(new DownTimeDto(deviceLogDtoListOEE.get(j).getEventTime(), null));
                            c = 1;
                        }
                    } else if (c == 1) {
                        if (!deviceLogDtoListOEE.get(j).getElectricity().equals("0")) {
                            nullTimeDtoList.get(d).setEnd(deviceLogDtoListOEE.get(j).getEventTime());
                            c = 0;
                            d ++;
                        }
                    }
                }
                nullTimeDtoList.remove(nullTimeDtoList.size() - 1);
                nullTime = nullTimeDtoList.stream().mapToLong(downTimeDto -> DateUtil.getDatePoorMin(downTimeDto.getStart(), downTimeDto.getEnd())).sum();
                nullTimeList.add(nullTime);
            }
        }
        log.info("[ScheduleTask] dataAnalysis() 共" + deviceLogDtoList.size() + "条远程数据");
        // 去除心跳包
        deviceLogDtoList.removeIf(deviceLogDto -> !Optional.ofNullable(deviceLogDto.getService().getData().getTemperature()).isPresent());
        deviceLogDtoList.forEach(deviceLogDto -> {
            deviceLogDto.setElectricity(deviceLogDto.getService().getData().getTemperature());
            deviceLogDto.setEventTime(deviceLogDto.getService().getEventTime());
        });
        log.info("[ScheduleTask] dataAnalysis() 去除心跳包后共" + deviceLogDtoList.size() + "条数据");
        // 更新设备状态
        log.info("[ScheduleTask] dataAnalysis() 开始更新设备状态");
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
        log.info("[ScheduleTask] dataAnalysis() 更新设备运行状态成功");
        log.info("[ScheduleTask] dataAnalysis() 开始计算停机次数和时间");
        // 计算停机时间
        final long downTime;
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
        downTimeDtoList.remove(downTimeDtoList.size() - 1);
        downTime = downTimeDtoList.stream().mapToLong(downTimeDto -> DateUtil.getDatePoorMin(downTimeDto.getStart(), downTimeDto.getEnd())).sum();
        Data dataDown = new Data();
        dataDown.setDeviceId(deviceId);
        dataDown.setYear(year);
        dataDown.setMonth(month);
        dataDown.setDownTime(String.valueOf(downTime));
        deviceLogDtoListDown.removeIf(deviceLogDto -> !deviceLogDto.getElectricity().equals("0"));
        dataDown.setDownTimes(deviceLogDtoListDown.size());
        dataMapper.updateDownTime(dataDown);
        log.info("[ScheduleTask] dataAnalysis() 停机信息: " + dataDown.toString());
        log.info("[ScheduleTask] dataAnalysis() 开始计算时间稼动率");
        long allWorkTime = workTimeList.stream().mapToLong(workTime -> workTime).sum();
        float timeOEE = (float) allWorkTime / (days * 8 * 60);
        Data dataTimeOEE = new Data();
        dataTimeOEE.setDeviceId(deviceId);
        dataTimeOEE.setYear(year);
        dataTimeOEE.setMonth(month);
        dataTimeOEE.setTimeOEE(String.valueOf(timeOEE));
        dataMapper.updateTimeOEE(dataTimeOEE);
        log.info("[ScheduleTask] dataAnalysis() 时间稼动率: " + timeOEE);
        log.info("[ScheduleTask] dataAnalysis() 开始计算性能稼动率");
        long allNullTime = nullTimeList.stream().mapToLong(nullTime -> nullTime).sum();
        float performanceOEE = (float) (allWorkTime - allNullTime) / allWorkTime;
        Data dataPerformanceOEE = new Data();
        dataPerformanceOEE.setDeviceId(deviceId);
        dataPerformanceOEE.setYear(year);
        dataPerformanceOEE.setMonth(month);
        dataPerformanceOEE.setPerformanceOEE(String.valueOf(performanceOEE));
        dataMapper.updatePerformanceOEE(dataPerformanceOEE);
        log.info("[ScheduleTask] dataAnalysis() 性能稼动率: " + performanceOEE);
    }
}
