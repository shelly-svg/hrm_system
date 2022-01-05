package org.example.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.api.entity.Token;
import org.example.api.repository.TokenRepository;
import org.example.api.service.TokenService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Override
    public void addToBlacklist(Token token) {
        tokenRepository.save(token);
    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        return tokenRepository.existsByTokenEquals(token);
    }

}
