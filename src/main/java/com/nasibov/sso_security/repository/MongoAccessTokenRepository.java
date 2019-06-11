package com.nasibov.sso_security.repository;

import com.nasibov.sso_security.model.MongoAccessToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MongoAccessTokenRepository extends MongoRepository<MongoAccessToken, String> {
    void deleteByRefreshToken(String refreshTokenId);

    MongoAccessToken findByAuthenticationId(String authenticationId);

    List<MongoAccessToken> findAllByClientIdAndUsername(String clientId, String username);

    List<MongoAccessToken> findAllByClientId(String clientId);

    Optional<MongoAccessToken> findByTokenId(String tokenId);

    void deleteByTokenId(String tokenId);
}
