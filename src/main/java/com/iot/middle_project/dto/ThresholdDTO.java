package com.iot.middle_project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThresholdDTO {
    private float low;
    private float high;
    private String deviceId;
}
