package com.iot.middle_project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "Devices")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Device {
    @Id
    @Field("_id")
    private ObjectId id;
    @Indexed(unique = true)
    private String deviceId;
    private String publicKey;
    private String name;
    private float low;
    private float high;
    @DBRef
    @Field("data")
    private List<SoilMoistureData> soilMoistureData;
}
