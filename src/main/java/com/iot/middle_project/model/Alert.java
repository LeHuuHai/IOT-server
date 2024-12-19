package com.iot.middle_project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alert {
    private float soilMoisture;
    private State state;
    private LocalDateTime time;

    public String getAlert(){
        return state.getState() + ": " + soilMoisture;
    }
}
