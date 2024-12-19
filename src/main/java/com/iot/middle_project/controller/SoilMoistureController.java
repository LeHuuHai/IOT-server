package com.iot.middle_project.controller;


import com.iot.middle_project.dto.SoilMoisturePostDTO;
import com.iot.middle_project.model.Device;
import com.iot.middle_project.model.SoilMoistureData;
import com.iot.middle_project.repository.QuerySoilMoistureRepository;
import com.iot.middle_project.service.DeviceService;
import com.iot.middle_project.service.MUserDetailService;
import com.iot.middle_project.service.SoilMoistureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class SoilMoistureController {

    @Autowired
    private SoilMoistureService soilMoistureService;

    @Autowired
    private QuerySoilMoistureRepository querySoilMoistureRepository;

    @Autowired
    private NotificationController notificationController;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private MUserDetailService mUserDetailService;

    @Autowired
    private Helper helper;

    @PostMapping("/soilMoisture")
    public String receiveMoisture(@RequestBody SoilMoisturePostDTO data) throws Exception {
        if(helper.verifySoilMoistureDTO(data)) {
            SoilMoistureData soilMoistureData = soilMoistureService.save(SoilMoistureData.builder()
                    .time(data.getTime())
                    .soilMoistureValue(data.getSoilMoistureValue())
                    .build());
            Device device = deviceService.findByDeviceId(data.getDeviceId());
            device.getSoilMoistureData().add(soilMoistureData);
            deviceService.save(device);
            notificationController.check(data);
            return "Data save successfully";
        }
        return "Verify data failed";
    }

    @PostMapping("/soilMoisture/list")
    public String receiveListMoisture(@RequestBody List<SoilMoisturePostDTO> datas) {
        System.out.println("Received soil moisture " + datas.toString());
        datas.forEach(data -> {
            try {
                this.receiveMoisture(data);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return "Data save successfully";
    }

    @GetMapping("/soilMoisture/today/{deviceId}")
    public ResponseEntity<Object> getDataByDay(@PathVariable String deviceId){
        Device device = helper.validateDevice(deviceId);
        if(device!=null){
            List<SoilMoistureData> data = device.getSoilMoistureData();
            return new ResponseEntity<>(soilMoistureService.getToday(data), HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>("Access denied: You don not own this device" ,HttpStatusCode.valueOf(403));
    }

    @GetMapping("/soilMoisture/last-week/{deviceId}")
    public ResponseEntity<Object> getDataByWeek(@PathVariable String deviceId){
        Device device = helper.validateDevice(deviceId);
        if(device!=null){
            List<SoilMoistureData> data = device.getSoilMoistureData();
            return new ResponseEntity<>(soilMoistureService.get7Days(data), HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>("Access denied: You don not own this device" ,HttpStatusCode.valueOf(403));
    }

}


