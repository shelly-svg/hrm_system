package org.example.api.repository;

import org.example.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Returns an optional that contains User object with given {@code username}. If there is no such user at the
     * user data holder, returns an empty optional
     *
     * @param username username of the user
     * @return optional that contains User object with given {@code username} if present, otherwise an empty optional
     */
    Optional<User> getUserByUsername(String username);

}
