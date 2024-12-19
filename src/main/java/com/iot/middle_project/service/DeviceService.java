package com.iot.middle_project.service;

import com.iot.middle_project.model.Device;
import com.iot.middle_project.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;

    public Device findByDeviceId(String deviceId){
        return deviceRepository.findByDeviceId(deviceId);
    }

    public Device save(Device device){
        return deviceRepository.save(device);
    }
}
