package com.iot.middle_project.service;

import com.iot.middle_project.model.SoilMoistureData;
import com.iot.middle_project.repository.QuerySoilMoistureRepository;
import com.iot.middle_project.repository.SoilMoistureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SoilMoistureService {

    @Autowired
    private SoilMoistureRepository soilMoistureRepository;

    @Autowired
    private QuerySoilMoistureRepository querySoilMoistureRepository;

    public void save(SoilMoistureData data) {
        try {
            soilMoistureRepository.save(data);
        } catch (Exception e) {
            System.out.println("Lỗi khi lưu dữ liệu: " + e.getMessage());
        }
    }

    public ArrayList<SoilMoistureData> getToday(String deviceId){
        try{
            return querySoilMoistureRepository.getByDay(deviceId);
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy dữ liệu: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public ArrayList<SoilMoistureData> getWeek(String deviceId){
        try{
            return querySoilMoistureRepository.getByWeek(deviceId);
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy dữ liệu: " + e.getMessage());
            return new ArrayList<>();
        }
    }

//    public void run() {
//        // Gọi các phương thức sau khi ứng dụng khởi động
//        querySoilMoistureRepository.getByDay();
//        querySoilMoistureRepository.getByWeek();
//    }
}
