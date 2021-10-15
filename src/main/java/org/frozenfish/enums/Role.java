package org.frozenfish.enums;

import org.springframework.security.core.GrantedAuthority;

public enum  Role  implements GrantedAuthority {
    USER,
    RL,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}

