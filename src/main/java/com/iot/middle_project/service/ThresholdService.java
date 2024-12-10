package com.iot.middle_project.service;

import com.iot.middle_project.model.Threshold;
import com.iot.middle_project.repository.ThresholdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThresholdService {
    @Autowired
    private ThresholdRepository thresholdRepository;

    public Threshold getThresholdById(String id){
        try {
            return thresholdRepository.findByDeviceId(id);
        }catch(Exception e){
            System.out.println("Lỗi khi lấy ngưỡng: " + e.getMessage());
            return null;
        }
    }

    public void saveThreshold(Threshold threshold){
        Threshold t = thresholdRepository.findByDeviceId(threshold.getDeviceId());
        if(t==null){
            thresholdRepository.save(threshold);
        }else{
            t.setHigh(threshold.getHigh());
            t.setLow(threshold.getLow());
            thresholdRepository.save(t);
        }
    }
}
