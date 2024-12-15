package com.tugrulhan.freethoughts.dtos.response;

import com.tugrulhan.freethoughts.enums.AuthProvider;
import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private String firstname;
    private String lastname;
    private AuthProvider authProvider;
    private String name;
    private String imageUrl;
}
