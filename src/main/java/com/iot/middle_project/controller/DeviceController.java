package com.iot.middle_project.controller;

import com.iot.middle_project.dto.ThresholdDTO;
import com.iot.middle_project.model.Device;
import com.iot.middle_project.model.MUser;
import com.iot.middle_project.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private Helper helper;

    @GetMapping("/devices")
    public ResponseEntity<Object> getAllDevice(){
        MUser user = helper.validateUser();
        if(user==null)
            return new ResponseEntity<>("Unauthorized", HttpStatusCode.valueOf(401));
        List<Device> devices = user.getDevices();
        return new ResponseEntity<>(devices, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/threshold/{deviceId}")
    public ResponseEntity<Object> getThreshold(@PathVariable String deviceId){
        Device device = helper.validateDevice(deviceId);
        if(device!=null){
            return new ResponseEntity<>(ThresholdDTO.builder()
                    .deviceId(device.getDeviceId())
                    .low(device.getLow())
                    .high(device.getHigh())
                    .build(),
                    HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>("Access denied: You don not own this device" ,HttpStatusCode.valueOf(403));
    }

    @PostMapping("/threshold")
    public ResponseEntity<Object> sendThreshold(@RequestBody ThresholdDTO thresholdDTO){
        Device device = helper.validateDevice(thresholdDTO.getDeviceId());
        if(device!=null){
            device.setLow(thresholdDTO.getLow());
            device.setHigh(thresholdDTO.getHigh());
            deviceService.save(device);
            return new ResponseEntity<>("Update threshold successful", HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>("Access denied: You don not own this device" ,HttpStatusCode.valueOf(403));
    }
}
