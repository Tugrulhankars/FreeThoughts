package com.tugrulhan.freethoughts.dtos.response;

import com.tugrulhan.freethoughts.enums.AuthProvider;

public record GetAllUserResponse(
        Long id,
        String email,
        String firstname,
        String lastname

) {

}
