package org.example.api.service;

import lombok.RequiredArgsConstructor;
import org.example.api.entity.User;
import org.example.api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

}
