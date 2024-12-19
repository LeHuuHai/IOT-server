package com.iot.middle_project.repository;

import com.iot.middle_project.model.RefreshToken;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends MongoRepository<RefreshToken, ObjectId> {
    RefreshToken findByToken(String token);

    RefreshToken save(RefreshToken refreshToken);
}
