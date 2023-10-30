package com.powerreaderapi.powerreaderapi.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN(Set.of(Permission.CONNECT_DEVICE, Permission.READ_SENSOR_READINGS)),
    REGULAR_USER(Set.of(Permission.READ_SENSOR_READINGS));

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name())); //ZA SPRING SITUACIJU ZA SAMU ROLE
        return authorities;
    }
}
