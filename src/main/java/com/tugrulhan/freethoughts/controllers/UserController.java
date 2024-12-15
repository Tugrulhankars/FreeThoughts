package com.tugrulhan.freethoughts.controllers;

import com.tugrulhan.freethoughts.dtos.response.GetAllUserResponse;
import com.tugrulhan.freethoughts.dtos.response.UserResponse;
import com.tugrulhan.freethoughts.services.impl.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<GetAllUserResponse> getAllUser() {
        return (ResponseEntity<GetAllUserResponse>) userService.getAllUser();
    }
}
