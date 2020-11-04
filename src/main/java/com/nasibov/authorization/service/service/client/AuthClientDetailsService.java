package com.nasibov.authorization.service.service.client;

import com.nasibov.authorization.service.model.AuthClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;

public interface AuthClientDetailsService extends ClientDetailsService {
    public AuthClientDetails create(AuthClientDetails authClientDetails);
}
