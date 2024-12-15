package com.tugrulhan.freethoughts.services.impl;

import com.tugrulhan.freethoughts.dtos.response.GetAllUserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserDetailsService userDetailsService();
    List<GetAllUserResponse> getAllUser();
}
