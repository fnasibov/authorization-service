package com.nasibov.sso_security.repository;

import com.nasibov.sso_security.model.AuthClientDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AuthClientRepository extends MongoRepository<AuthClientDetails, String> {
    Optional<AuthClientDetails> findByClientId(String clientId);
}