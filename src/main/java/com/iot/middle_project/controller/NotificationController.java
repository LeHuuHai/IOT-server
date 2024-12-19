package com.iot.middle_project.controller;

import com.iot.middle_project.dto.SoilMoisturePostDTO;
import com.iot.middle_project.model.Alert;
import com.iot.middle_project.model.State;
import com.iot.middle_project.model.Device;
import com.iot.middle_project.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {
    private final String destination = "/topic/alert";

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public Alert buildAlert(SoilMoisturePostDTO data){
        Device device = deviceService.findByDeviceId(data.getDeviceId());
        float low = device.getLow();
        float high = device.getHigh();
        State s;
        if(data.getSoilMoistureValue() > high)
            s = State.HUMID;
        else if(data.getSoilMoistureValue() < low)
            s = State.DRY;
        else
            s= State.NORMAL;

        if(s!=State.NORMAL)
            return Alert.builder()
                    .state(s)
                    .soilMoisture(data.getSoilMoistureValue())
                    .time(data.getTime())
                    .build();
        return null;
    }

    public void sendAlert(String destination, Alert alert) {
        messagingTemplate.convertAndSend(destination, alert);
    }

    public void check(SoilMoisturePostDTO data){
        Alert alert = buildAlert(data);
        if(alert!=null){
            sendAlert(destination, alert);
        }
    }
}
