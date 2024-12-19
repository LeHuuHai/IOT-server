package com.iot.middle_project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
@Data
@Document(collection = "SoilMoistureData")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SoilMoistureData {
    @Id
    @Field("_id")
    private ObjectId id;
    private LocalDateTime time;
    private float soilMoistureValue;
}
