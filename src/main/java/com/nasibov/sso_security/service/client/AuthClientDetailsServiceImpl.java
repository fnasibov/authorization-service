package com.nasibov.sso_security.service.client;

import com.nasibov.sso_security.model.AuthClientDetails;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
public class AuthClientDetailsServiceImpl implements AuthClientDetailsService {
    @Override
    public AuthClientDetails create(AuthClientDetails authClientDetails) {
        return null;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return null;
    }
}
