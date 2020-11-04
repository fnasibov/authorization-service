package com.nasibov.authorization.service.service.user;

import com.nasibov.authorization.service.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthUserDetailsService extends UserDetailsService {
    public User create(User user);
}
