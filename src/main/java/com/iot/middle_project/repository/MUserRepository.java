package com.iot.middle_project.repository;

import com.iot.middle_project.model.MUser;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MUserRepository extends MongoRepository<MUser, ObjectId> {
    MUser findByUsername(String username);
}
