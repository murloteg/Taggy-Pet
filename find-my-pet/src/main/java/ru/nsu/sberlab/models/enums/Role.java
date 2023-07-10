package ru.nsu.sberlab.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_PRIVILEGED_ACCESS;

    @Override
    public String getAuthority() {
        return name();
    }
}
