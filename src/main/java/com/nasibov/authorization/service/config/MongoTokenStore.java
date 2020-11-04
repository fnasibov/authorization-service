package com.nasibov.authorization.service.config;

import com.nasibov.authorization.service.model.MongoAccessToken;
import com.nasibov.authorization.service.model.MongoRefreshToken;
import com.nasibov.authorization.service.repository.MongoAccessTokenRepository;
import com.nasibov.authorization.service.repository.MongoRefreshTokenRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.nasibov.authorization.service.util.TokenExtension.extractTokenKey;


@Configuration
public class MongoTokenStore implements TokenStore {
    private final AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();
    private final MongoRefreshTokenRepository mongoRefreshTokenRepository;
    private final MongoAccessTokenRepository mongoAccessTokenRepository;

    public MongoTokenStore(MongoRefreshTokenRepository mongoRefreshTokenRepository, MongoAccessTokenRepository mongoAccessTokenRepository) {
        this.mongoRefreshTokenRepository = mongoRefreshTokenRepository;
        this.mongoAccessTokenRepository = mongoAccessTokenRepository;
    }

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return readAuthentication(token.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        return mongoAccessTokenRepository.findByTokenId(extractTokenKey(token))
                .map(MongoAccessToken::getAuthentication)
                .orElse(null);

    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        String refreshToken = null;

        if (token.getRefreshToken() != null) {
            refreshToken = token.getRefreshToken().getValue();
        }

        if (readAccessToken(token.getValue()) != null) {
            removeAccessToken(token);
        }

        MongoAccessToken mongoAccessToken = new MongoAccessToken();
        mongoAccessToken.setTokenId(extractTokenKey(token.getValue()));
        mongoAccessToken.setToken(token);
        mongoAccessToken.setAuthenticationId(authenticationKeyGenerator.extractKey(authentication));
        mongoAccessToken.setUsername(authentication.isClientOnly() ? null : authentication.getName());
        mongoAccessToken.setClientId(authentication.getOAuth2Request().getClientId());
        mongoAccessToken.setAuthentication(authentication);
        mongoAccessToken.setRefreshToken(extractTokenKey(refreshToken));

        mongoAccessTokenRepository.save(mongoAccessToken);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        Optional<MongoAccessToken> mongoAccessToken = mongoAccessTokenRepository.findByTokenId(extractTokenKey(tokenValue));
        return mongoAccessToken.map(MongoAccessToken::getToken).orElse(null);
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        mongoAccessTokenRepository.deleteByTokenId(extractTokenKey(token.getValue()));
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        MongoRefreshToken mongoRefreshToken = new MongoRefreshToken();
        mongoRefreshToken.setTokenId(extractTokenKey(refreshToken.getValue()));
        mongoRefreshToken.setToken(refreshToken);
        mongoRefreshToken.setAuthentication(authentication);

        mongoRefreshTokenRepository.save(mongoRefreshToken);
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        return mongoRefreshTokenRepository.findByTokenId(extractTokenKey(tokenValue))
                .map(MongoRefreshToken::getToken)
                .orElse(null);
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return mongoRefreshTokenRepository.findByTokenId(extractTokenKey(token.getValue()))
                .map(MongoRefreshToken::getAuthentication)
                .orElse(null);
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {
        mongoRefreshTokenRepository.deleteById(extractTokenKey(token.getValue()));
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        mongoAccessTokenRepository.deleteByRefreshToken(extractTokenKey(refreshToken.getValue()));
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        OAuth2AccessToken accessToken = null;

        String authenticationId = authenticationKeyGenerator.extractKey(authentication);
        MongoAccessToken mongoAccessToken = mongoAccessTokenRepository
                .findByAuthenticationId(authenticationId);

        if (mongoAccessToken != null) {
            accessToken = mongoAccessToken.getToken();
            if (accessToken != null && authenticationId.equals(this.authenticationKeyGenerator.extractKey(this.readAuthentication(accessToken)))) {
                removeAccessToken(accessToken);
                storeAccessToken(accessToken, authentication);
            }
        }
        return accessToken;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        return mongoAccessTokenRepository.findAllByClientIdAndUsername(clientId, userName)
                .stream()
                .map(MongoAccessToken::getToken)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        return mongoAccessTokenRepository.findAllByClientId(clientId)
                .stream()
                .map(MongoAccessToken::getToken)
                .collect(Collectors.toList());
    }
}
