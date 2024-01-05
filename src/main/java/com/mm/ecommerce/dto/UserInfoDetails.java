package com.mm.ecommerce.dto;

import com.mm.ecommerce.domain.User;
import com.mm.ecommerce.enums.UserRole;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Data
public class UserInfoDetails implements UserDetails {

    private String email;
    private String password;
    private List<GrantedAuthority> authorities;

    public UserInfoDetails(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        authorities = UserRole.getAuthorities(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
