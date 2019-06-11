package com.nasibov.sso_security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


@EnableResourceServer
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SsoSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoSecurityApplication.class, args);
    }

}
