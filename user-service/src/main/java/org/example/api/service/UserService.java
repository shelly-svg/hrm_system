package org.example.api.service;

import org.example.api.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User create(User user);

}
