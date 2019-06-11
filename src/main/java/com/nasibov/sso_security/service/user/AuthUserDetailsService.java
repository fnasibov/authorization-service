package com.nasibov.sso_security.service.user;

import com.nasibov.sso_security.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthUserDetailsService extends UserDetailsService {
    public User create(User user);
}
