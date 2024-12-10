package com.iot.middle_project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "SoilMoisture")
@AllArgsConstructor
@NoArgsConstructor
public class SoilMoistureData {
    private String deviceId;
    private LocalDateTime time;
    private float soilMoistureValue;
}
