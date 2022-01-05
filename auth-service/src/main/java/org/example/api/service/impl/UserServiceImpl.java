package org.example.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.api.entity.User;
import org.example.api.repository.UserRepository;
import org.example.api.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

}
