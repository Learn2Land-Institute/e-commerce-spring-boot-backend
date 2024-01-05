package com.mm.ecommerce.enums;

import com.mm.ecommerce.domain.Consumer;
import com.mm.ecommerce.domain.Merchant;
import com.mm.ecommerce.domain.SystemAdmin;
import com.mm.ecommerce.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public enum UserRole {
    SYSTEM_ADMIN("ROLE_SYSTEM_ADMIN"),
    CONSUMER("ROLE_CONSUMER"),
    MERCHANT("ROLE_MERCHANT");

    private final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public static List<GrantedAuthority> getAuthorities(User user) {
        if (user instanceof SystemAdmin) {
            return List.of(new SimpleGrantedAuthority(SYSTEM_ADMIN.getRoleName()));
        } else if (user instanceof Consumer) {
            return List.of(new SimpleGrantedAuthority(CONSUMER.getRoleName()));
        } else if (user instanceof Merchant) {
            return List.of(new SimpleGrantedAuthority(MERCHANT.getRoleName()));
        } else {
            throw new IllegalArgumentException("Unsupported user type");
        }
    }
}