package com.iot.middle_project.controller;

import com.iot.middle_project.model.Alert;
import com.iot.middle_project.model.SoilMoistureData;
import com.iot.middle_project.model.State;
import com.iot.middle_project.model.Threshold;
import com.iot.middle_project.service.ThresholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {
    private final String destination = "/topic/alert";

    @Autowired
    private ThresholdService thresholdService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public Alert buildAlert(SoilMoistureData data){
        Threshold t = thresholdService.getThresholdById(data.getDeviceId());
        State s;
        if(data.getSoilMoistureValue() > t.getHigh())
            s = State.HUMID;
        else if(data.getSoilMoistureValue() < t.getLow())
            s = State.DRY;
        else
            s= State.NORMAL;

        if(s!=State.NORMAL)
            return Alert.builder()
                    .state(s)
                    .soilMoisture(data.getSoilMoistureValue())
                    .build();
        return null;
    }

    public void sendAlert(String destination, Alert alert) {
        messagingTemplate.convertAndSend(destination, alert);
    }

    public void check(SoilMoistureData data){
        Alert alert = buildAlert(data);
        if(alert!=null){
            sendAlert(destination, alert);
        }
    }
}
