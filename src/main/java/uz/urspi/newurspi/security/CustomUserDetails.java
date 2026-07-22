package uz.urspi.newurspi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import uz.urspi.newurspi.permissions.Permissions;
import uz.urspi.newurspi.roles.Role;
import uz.urspi.newurspi.users.User;
import uz.urspi.newurspi.utils.Status;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<GrantedAuthority> authorities = new HashSet<>();

        if (user.getRoles() == null) {
            return authorities;
        }

        for (Role role : user.getRoles()) {

            // Role
            authorities.add(
                    new SimpleGrantedAuthority("ROLE_" + role.getName())
            );

            // Permissions
            if (role.getPermissions() != null) {
                for (Permissions permission : role.getPermissions()) {
                    authorities.add(
                            new SimpleGrantedAuthority(permission.getName())
                    );
                }
            }
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getStatus() == Status.ACTIVE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getStatus() == Status.ACTIVE;
    }
}