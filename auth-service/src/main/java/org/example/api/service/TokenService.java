package org.example.api.service;

import org.example.api.entity.Token;

public interface TokenService {

    /**
     * Adds {@code token} to the black list of the tokens
     *
     * @param token token to add to the black list
     */
    void addToBlacklist(Token token);

    /**
     * Returns true if {@code token} is blacklisted
     *
     * @param token token to check
     * @return true if {@code token} is blacklisted
     */
    boolean isTokenBlacklisted(String token);

}
