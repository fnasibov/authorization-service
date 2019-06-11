package com.nasibov.sso_security.command;

import com.nasibov.sso_security.model.AuthClientDetails;
import com.nasibov.sso_security.model.Authorities;
import com.nasibov.sso_security.model.User;
import com.nasibov.sso_security.repository.AuthClientRepository;
import com.nasibov.sso_security.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class DefaultClientAndUserCreator implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AuthClientRepository authClientRepository;

    public DefaultClientAndUserCreator(UserRepository userRepository, AuthClientRepository authClientRepository) {
        this.userRepository = userRepository;
        this.authClientRepository = authClientRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Optional<AuthClientDetails> browserClient = authClientRepository.findByClientId("browser");
        if (browserClient.isEmpty()) {
            authClientRepository.save(createClient());
        }

        Optional<User> user = userRepository.findByUsername("randomuser");
        if (user.isEmpty()) {
            userRepository.save(createUser());
        }
    }

    private User createUser() {
        Set<Authorities> authorities = new HashSet<>();
        authorities.add(Authorities.ROLE_USER);

        User user = new User();
        user.setActivated(true);
        user.setAuthorities(authorities);
        user.setPassword("$2a$10$fWNTd3H.u7G/aNROVQSifebOkZ2xzU5nUPOCI2Ld42M8E25/ljJqK");
        user.setUsername("randomuser");
        return user;
    }

    private AuthClientDetails createClient() {
        AuthClientDetails browserClientDetails = new AuthClientDetails();
        browserClientDetails.setClientId("browser");
        browserClientDetails.setClientSecret("$2a$10$fWNTd3H.u7G/aNROVQSifebOkZ2xzU5nUPOCI2Ld42M8E25/ljJqK");
        browserClientDetails.setScopes("ui");
        browserClientDetails.setGrantTypes("refresh_token,password");
        browserClientDetails.setAccessTokenValiditySeconds(3600);

        return browserClientDetails;
    }
}
