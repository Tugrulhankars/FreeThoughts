package com.tugrulhan.freethoughts.services.impl;

import com.tugrulhan.freethoughts.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface JwtService {
    String extractUsername(String token);

    String generateToken(UserDetails userDetails);
    String generateToken(String email,String name);

    boolean isTokenValid(String token, UserDetails userDetails);
}
