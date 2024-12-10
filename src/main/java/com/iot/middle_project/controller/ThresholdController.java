package com.iot.middle_project.controller;

import com.iot.middle_project.model.Threshold;
import com.iot.middle_project.service.ThresholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ThresholdController {

    @Autowired
    private ThresholdService thresholdService;

    @PostMapping("/threshold")
    public String reciveThreshold(@RequestBody Threshold threshold){
        System.out.println("Received threshold " + threshold.toString());
        thresholdService.saveThreshold(threshold);
        return "Threshold save successfully";
    }

    @GetMapping("/threshold/{deviceId}")
    public Threshold getThreshold(@PathVariable String deviceId){
        return thresholdService.getThresholdById(deviceId);
    }
}
