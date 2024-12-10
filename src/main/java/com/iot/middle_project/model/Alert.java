package com.iot.middle_project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alert {
    private float soilMoisture;
    private State state;

    public String getAlert(){
        return state.getState() + ": " + soilMoisture;
    }
}
