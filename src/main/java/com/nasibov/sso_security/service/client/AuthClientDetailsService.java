package com.nasibov.sso_security.service.client;

import com.nasibov.sso_security.model.AuthClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;

public interface AuthClientDetailsService extends ClientDetailsService {
    public AuthClientDetails create(AuthClientDetails authClientDetails);
}
