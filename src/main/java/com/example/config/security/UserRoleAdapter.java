package com.example.config.security;

import com.example.models.UserRole;
import org.springframework.security.core.GrantedAuthority;

public class UserRoleAdapter implements GrantedAuthority {
    private UserRole role;

    public UserRoleAdapter(UserRole role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.getName();
    }
}
