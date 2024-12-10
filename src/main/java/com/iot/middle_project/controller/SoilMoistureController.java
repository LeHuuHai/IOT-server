package com.iot.middle_project.controller;


import com.iot.middle_project.model.SoilMoistureData;
import com.iot.middle_project.repository.QuerySoilMoistureRepository;
import com.iot.middle_project.service.SoilMoistureService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/soilMoisture")
    public String receiveMoisture(@RequestBody SoilMoistureData data) {
        System.out.println("Received soil moisture " + data.toString());
        soilMoistureService.save(data);
        notificationController.handle(data);
        return "Data save successfully";
    }

    @PostMapping("/soilMoisture/list")
    public String receiveListMoisture(@RequestBody List<SoilMoistureData> datas) {
        System.out.println("Received soil moisture " + datas.toString());
        datas.forEach(data -> soilMoistureService.save(data));
        return "Data save successfully";
    }

    @GetMapping("/soilMoisture/today/{deviceId}")
    public List<SoilMoistureData> getDataByDay(@PathVariable String deviceId){
        return querySoilMoistureRepository.getByDay(deviceId);
    }

    @GetMapping("/soilMoisture/last-week/{deviceId}")
    public List<SoilMoistureData> getDataByWeek(@PathVariable String deviceId){
        return querySoilMoistureRepository.getByWeek(deviceId);
    }

}


