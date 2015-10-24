package com.example.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class SecurityUser extends User implements UserDetails{
    public SecurityUser(User user){
        super.setUserId(user.getUserId());
        super.setUsername(user.getUsername());
        super.setPassword(user.getPassword());
        super.setEnabled(user.isEnabled());
        super.setUserAuthorities(user.getUserAuthorities());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        ArrayList<UserAuthority> userAuthorities = (ArrayList<UserAuthority>) getUserAuthorities();
        Iterator<UserAuthority> userGroupIterator = userAuthorities.iterator();
        UserAuthority userAuthority = null;
        while (userGroupIterator.hasNext()) {
            userAuthority = userGroupIterator.next();
            authorities.add(new SimpleGrantedAuthority(userAuthority.getAuthorityName()));
        }
        return authorities;
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
}
