package com.iot.middle_project.repository;

import com.iot.middle_project.model.SoilMoistureData;
import org.bson.types.ObjectId;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoilMoistureRepository extends MongoRepository<SoilMoistureData, ObjectId>{

}
