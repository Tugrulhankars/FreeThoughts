package com.tugrulhan.freethoughts.dtos.reuqest;

public record SignUpRequest(
        String firstName,
        String lastName,
        String email,
        String password
) {
}
