package com.zanonjonascodes.ssmts.core.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zanonjonascodes.ssmts.core.security.UserPrincipal;
import com.zanonjonascodes.ssmts.user.UserEntity;
import com.zanonjonascodes.ssmts.user.UserRepository;

@Service
public class UserDetailsServiceConfig implements UserDetailsService {
  @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return new UserPrincipal(user);
    }
}
