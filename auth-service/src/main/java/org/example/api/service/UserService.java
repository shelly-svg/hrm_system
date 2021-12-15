package org.example.api.service;

import org.example.api.entity.User;

import java.util.Optional;

public interface UserService {

    /**
     * Returns an optional that contains User object with given {@code username}. If there is no such user at the
     * user data holder, returns an empty optional
     *
     * @param username username of the user
     * @return optional that contains User object with given {@code username} if present, otherwise an empty optional
     */
    Optional<User> getUserByUsername(String username);

}
