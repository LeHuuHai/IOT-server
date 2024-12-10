package com.iot.middle_project.repository;

import com.iot.middle_project.model.Threshold;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThresholdRepository extends MongoRepository<Threshold, ObjectId> {
    public Threshold findByDeviceId(String id);
}
