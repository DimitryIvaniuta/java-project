package com.javaproject.server.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class SecurityUser extends User {

    /**
     * SecurityUser wrapper for Spring-Boot user
     * which represents an authenticated user in Spring Security.
     *
     * @param username    a user name
     * @param password    a user password
     * @param authorities permissions or roles assigned to a user
     */
    public SecurityUser(final String username, final String password, final Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

}
