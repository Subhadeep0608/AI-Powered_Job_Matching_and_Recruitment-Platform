package com.deep.job_portal.Repo;

import java.util.Collection;
import java.util.EnumSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.deep.job_portal.Model.User;
import com.deep.job_portal.Model.enums.Role;



public class UserPrincipal implements UserDetails {
    private User user;

    public UserPrincipal(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role role = user.getRole() == null ? Role.CANDIDATE : user.getRole();
        return EnumSet.of(role).stream()
                .map(currentRole -> new SimpleGrantedAuthority("ROLE_" + currentRole.name()))
                .toList();
    }

    @Override
    public  String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    
    
}
