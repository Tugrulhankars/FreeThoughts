package com.tugrulhan.freethoughts.services;

import com.tugrulhan.freethoughts.dtos.response.GetAllUserResponse;
import com.tugrulhan.freethoughts.mappers.UserMapper;
import com.tugrulhan.freethoughts.models.User;
import com.tugrulhan.freethoughts.repositories.UserRepository;
import com.tugrulhan.freethoughts.services.impl.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username);
            }
        };
    }

    @Override
    public List<GetAllUserResponse> getAllUser() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toGetAllUserResponse).toList();
    }
}
