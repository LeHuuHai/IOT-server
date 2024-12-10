package com.iot.middle_project.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum State {
    DRY("Khô hạn"),
    HUMID("Ngập úng"),
    NORMAL("Ổn định");

    private String state;

    public String getState() {
        return state;
    }
}
