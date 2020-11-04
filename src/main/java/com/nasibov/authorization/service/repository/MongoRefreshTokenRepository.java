package com.nasibov.authorization.service.repository;

import com.nasibov.authorization.service.model.MongoRefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MongoRefreshTokenRepository extends MongoRepository<MongoRefreshToken, String> {

    Optional<MongoRefreshToken> findByTokenId(String tokenId);
}
