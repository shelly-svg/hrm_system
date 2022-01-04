package org.example.api.repository;

import org.example.api.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, String> {

    /**
     * Returns true if {@code token} exists at the token data holder
     *
     * @param token token to check
     * @return true if {@code token} exists at the token data holder
     */
    boolean existsByTokenEquals(String token);

}
