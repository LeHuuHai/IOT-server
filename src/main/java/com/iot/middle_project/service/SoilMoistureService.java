package com.iot.middle_project.service;

import com.iot.middle_project.model.SoilMoistureData;
import com.iot.middle_project.repository.QuerySoilMoistureRepository;
import com.iot.middle_project.repository.SoilMoistureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SoilMoistureService {

    @Autowired
    private SoilMoistureRepository soilMoistureRepository;

    @Autowired
    private QuerySoilMoistureRepository querySoilMoistureRepository;

    public SoilMoistureData save(SoilMoistureData data) {
        try {
            return soilMoistureRepository.save(data);
        } catch (Exception e) {
            System.out.println("Lỗi khi lưu dữ liệu: " + e.getMessage());
            return null;
        }
    }


    public List<SoilMoistureData> getToday(List<SoilMoistureData> data) {
        LocalDate today = LocalDate.now();
        return data.stream()
                .filter(d -> d.getTime().toLocalDate().isEqual(today))
                .sorted(Comparator.comparing(SoilMoistureData::getTime))
                .collect(Collectors.toList());
    }

    public List<SoilMoistureData> get7Days(List<SoilMoistureData> data) {
        LocalDateTime now = LocalDateTime.now();
        List<SoilMoistureData> dataRaw = data.stream()
                .filter(d -> d.getTime().isAfter(now.minusDays(7)))
                .sorted(Comparator.comparing(SoilMoistureData::getTime).reversed())
                .toList();
        return process7Days(dataRaw);
    }

    public List<SoilMoistureData> process7Days(List<SoilMoistureData> datas){
        if(datas.isEmpty())
            return new ArrayList<>();
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
                    .time(time)
                    .build());
        }
        return convertedData;
    }
}
