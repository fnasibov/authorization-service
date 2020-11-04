package com.nasibov.authorization.service.service.client;

import com.nasibov.authorization.service.repository.AuthClientRepository;
import com.nasibov.authorization.service.model.AuthClientDetails;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
public class AuthClientDetailsServiceImpl implements AuthClientDetailsService {
    private final AuthClientRepository authClientRepository;

    public AuthClientDetailsServiceImpl(AuthClientRepository authClientRepository) {
        this.authClientRepository = authClientRepository;
    }

    @Override
    public AuthClientDetails create(AuthClientDetails authClientDetails) {
        return null;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return authClientRepository.findByClientId(clientId)
                .orElseThrow(() -> new ClientRegistrationException("Client not found"));
    }
}
