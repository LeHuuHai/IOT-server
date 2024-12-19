package com.iot.middle_project.repository;

import com.iot.middle_project.model.Device;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends MongoRepository<Device, ObjectId> {
    Device findByDeviceId(String deviceId);
}
