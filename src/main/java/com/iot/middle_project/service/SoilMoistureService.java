package com.iot.middle_project.service;

import com.iot.middle_project.model.SoilMoistureData;
import com.iot.middle_project.repository.QuerySoilMoistureRepository;
import com.iot.middle_project.repository.SoilMoistureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            ArrayList<SoilMoistureData> datas = querySoilMoistureRepository.getByWeek(deviceId);
            return processWeekData(datas);
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy dữ liệu: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public ArrayList<SoilMoistureData> processWeekData(ArrayList<SoilMoistureData> datas){
        if(datas.isEmpty())
            return new ArrayList<>();

        String deviceId = datas.get(0).getDeviceId();

        Map<LocalDateTime, List<SoilMoistureData>> groupByHour = datas.stream()
                .collect(Collectors.groupingBy(d -> d.getTime().toLocalDate().atTime(d.getTime().getHour(), 0,0)));

        ArrayList<SoilMoistureData> convertedData = new ArrayList<>();

        for (Map.Entry<LocalDateTime, List<SoilMoistureData>> entry : groupByHour.entrySet()){
            LocalDateTime time = entry.getKey();
            List<SoilMoistureData> dataList = entry.getValue();
            float averageSoildMoisture = (float) dataList.stream()
                    .mapToDouble(SoilMoistureData::getSoilMoistureValue)
                    .average()
                    .orElse(0.0);
            convertedData.add(SoilMoistureData.builder()
                    .soilMoistureValue(averageSoildMoisture)
                    .deviceId(deviceId)
                    .time(time)
                    .build());
        }
        return convertedData;
    }
}
