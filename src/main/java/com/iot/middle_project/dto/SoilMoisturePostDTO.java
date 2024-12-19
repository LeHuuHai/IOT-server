package com.iot.middle_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoilMoisturePostDTO {
    private LocalDateTime time;
    private float soilMoistureValue;
    private String deviceId;
    private String signature;
}
