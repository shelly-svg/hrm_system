package org.example.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.api.constant.ApiConstants;
import org.example.api.entity.User;
import org.example.api.repository.UserRepository;
import org.example.api.security.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(ApiConstants.USER_DOES_NOT_EXIST_MESSAGE));
        return SecurityUser.fromUser(user);
    }

}
