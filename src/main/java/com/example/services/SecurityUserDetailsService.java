package com.example.services;

import com.example.daos.UserRepository;
import com.example.models.SecurityUser;
import com.example.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityUser securityUser = null;
        User user = userRepository.findOneByUsername(username);
        if(user != null){
            securityUser = new SecurityUser(user);
        }
        return securityUser;
    }
}
